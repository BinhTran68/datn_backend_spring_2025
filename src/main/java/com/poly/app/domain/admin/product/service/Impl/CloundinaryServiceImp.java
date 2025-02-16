package com.poly.app.domain.admin.product.service.Impl;

import com.poly.app.domain.admin.product.service.CloundinaryService;
import com.poly.app.domain.model.Image;
import com.poly.app.domain.repository.ImageRepository;
import com.poly.app.infrastructure.util.CloudinaryUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CloundinaryServiceImp implements CloundinaryService {

    private final RestTemplate restTemplate;  // Inject RestTemplate từ Spring
    private final ImageRepository imageRepository;

    private static final String CLOUDINARY_URL = "https://api.cloudinary.com/v1_1/dieyhvcou/image/destroy";
    private static final String API_KEY = "526492658683127";  // Thay thế bằng API key thực tế của bạn

    /**
     * Xóa ảnh trên Cloudinary bằng public_id.
     *
     * @param publicId ID của ảnh cần xóa trên Cloudinary.
     * @return ResponseEntity chứa phản hồi từ Cloudinary.
     */
    @Override
    public ResponseEntity<?> deleteImage(String publicId) {
        if (publicId == null || publicId.isEmpty()) {
            return ResponseEntity.badRequest().body("public_id is required");
        }

        // Lấy timestamp hiện tại (tính bằng giây)
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);

        // Tạo danh sách tham số để ký signature
        Map<String, String> params = new HashMap<>();
        params.put("public_id", publicId);
        params.put("timestamp", timestamp);

        // Ký signature bằng CloudinaryUtil
        String signature = CloudinaryUtil.generateSignature(params);

        // Tạo request body để gửi đến Cloudinary
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("public_id", publicId);
        requestBody.put("api_key", API_KEY);
        requestBody.put("timestamp", timestamp);
        requestBody.put("signature", signature);

        try {
            // Gửi yêu cầu DELETE đến Cloudinary
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Map> response = restTemplate.postForEntity(CLOUDINARY_URL, requestBody, Map.class);

            log.info("Xóa ảnh thành công: {}", publicId);

            try {
                List<Image> images = imageRepository.getImageByPublicId(publicId);
                if (images.isEmpty()) {
                    log.info("chua co image nay");

                }
                images.forEach(image -> {
                    log.info(image.toString());
                    imageRepository.deleteById(image.getId());
                });
            } catch (Exception e) {
                log.info("Lỗi xảy ra: " + e.getMessage());
            }


            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            log.error("Lỗi khi xóa ảnh {}: {}", publicId, e.getMessage());
            return ResponseEntity.internalServerError().body("Lỗi khi xóa ảnh: " + e.getMessage());
        }
    }
}
