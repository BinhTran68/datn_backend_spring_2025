package com.poly.app.domain.admin.bill.service;

import com.poly.app.domain.model.ProductDetail;
import com.poly.app.domain.model.TemporaryHold;
import com.poly.app.domain.repository.ProductDetailRepository;
import com.poly.app.domain.repository.TemporaryHoldRepository;
import com.poly.app.infrastructure.constant.HoldStatus;
import com.poly.app.infrastructure.exception.RestApiException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.stat.descriptive.summary.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ProductHoldService {

    @Autowired
    private ProductDetailRepository productRepository;

    @Autowired
    private TemporaryHoldRepository temporaryHoldRepository;

    // 1. Hàm hold số lượng
    @Transactional
    public void holdQuantity(String productId, int quantity, String billId) {
        try {
            // Lock và lấy product
            ProductDetail product = productRepository.findByIdWithLock(productId)
                    .orElseThrow(() -> new RestApiException("Product not found: ", HttpStatus.BAD_REQUEST));

            // Kiểm tra số lượng
            if (!product.hasAvailableQuantity(quantity)) {
                throw new  RestApiException("hasAvailableQuantity not found: ", HttpStatus.BAD_REQUEST);
            }

            // Tạo temporary hold
            TemporaryHold hold = TemporaryHold.createNew(productId, quantity, billId);
            temporaryHoldRepository.save(hold);

            // Cập nhật số lượng hold
            product.setHoldQuantity(product.getHoldQuantity() + quantity);
            productRepository.save(product);

            log.info("Successfully held quantity {} for product {}", quantity, productId);

        } catch (Exception e) {
            log.error("Error holding quantity: {}", e.getMessage());
            throw new RuntimeException("Failed to hold quantity", e);
        }
    }

    // 2. Hàm release số lượng
    @Transactional
    public void releaseQuantity(String productId, String billId) {
        try {
            // Tìm tất cả holds của bill
            List<TemporaryHold> holds = temporaryHoldRepository.findByBillId(billId);

            for (TemporaryHold hold : holds) {
                if (hold.getStatus() == HoldStatus.HOLDING) {
                    // Lock và lấy product
                    ProductDetail product = productRepository.findByIdWithLock(hold.getProductId())
                            .orElseThrow(() -> new RestApiException("Product not found: ", HttpStatus.BAD_REQUEST));

                    // Giảm số lượng hold
                    product.setHoldQuantity(product.getHoldQuantity() - hold.getQuantity());
                    productRepository.save(product);

                    // Cập nhật trạng thái hold
                    hold.setStatus(HoldStatus.RELEASED);
                    temporaryHoldRepository.save(hold);

                    log.info("Released hold for product {}, quantity {}",
                            hold.getProductId(), hold.getQuantity());
                }
            }
        } catch (Exception e) {
            log.error("Error releasing quantity: {}", e.getMessage());
            throw new RuntimeException("Failed to release quantity", e);
        }
    }

    // 3. Scheduled job để tự động release các hold hết hạn
    @Scheduled(fixedRate = 180000) // Chạy mỗi 3 phút
    @Transactional
    public void releaseExpiredHolds() {
        log.info("Releasing expired holds");
        try {
            List<TemporaryHold> expiredHolds = temporaryHoldRepository
                    .findByStatusAndExpireTimeBefore(
                            HoldStatus.HOLDING,
                            LocalDateTime.now()
                    );

            for (TemporaryHold hold : expiredHolds) {
                ProductDetail product = productRepository.findByIdWithLock(hold.getProductId())
                        .orElseThrow(() -> new RestApiException("Product not found: ", HttpStatus.BAD_REQUEST) );

                // Giảm số lượng hold
                product.setHoldQuantity(product.getHoldQuantity() - hold.getQuantity());
                productRepository.save(product);

                // Cập nhật trạng thái hold
                hold.setStatus(HoldStatus.RELEASED);
                temporaryHoldRepository.save(hold);

                log.info("Released expired hold: {}", hold);
            }
        } catch (Exception e) {
            log.error("Error in releaseExpiredHolds: {}", e.getMessage());
        }
    }

    // 4. Hàm xác nhận sử dụng số lượng (khi thanh toán thành công)
    @Transactional
    public void confirmQuantity(String billId) {
        try {
            List<TemporaryHold> holds = temporaryHoldRepository.findByBillId(billId);

            for (TemporaryHold hold : holds) {
                if (hold.getStatus() == HoldStatus.HOLDING) {
                    ProductDetail product = productRepository.findByIdWithLock(hold.getProductId())
                            .orElseThrow(() -> new RestApiException("Product not found: " + hold.getProductId(), HttpStatus.BAD_REQUEST));

                    // Giảm tổng số lượng và số lượng hold
                    product.setQuantity(product.getQuantity() - hold.getQuantity());
                    product.setHoldQuantity(product.getHoldQuantity() - hold.getQuantity());
                    productRepository.save(product);

                    // Cập nhật trạng thái hold
                    hold.setStatus(HoldStatus.CONFIRMED);
                    temporaryHoldRepository.save(hold);

                    log.info("Confirmed quantity for product {}, quantity {}",
                            hold.getProductId(), hold.getQuantity());
                }
            }
        } catch (Exception e) {
            log.error("Error confirming quantity: {}", e.getMessage());
            throw new RuntimeException("Failed to confirm quantity", e);
        }
    }
}