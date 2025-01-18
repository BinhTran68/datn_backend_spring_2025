package com.poly.app.domain.admin.product.service;

import com.poly.app.domain.admin.product.response.brand.BrandResponseSelect;
import com.poly.app.domain.admin.product.response.product.ProductResponseSelect;
import com.poly.app.domain.model.Product;
import com.poly.app.domain.admin.product.request.product.ProductRequest;
import com.poly.app.domain.admin.product.response.product.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

     Product createProduct(ProductRequest request);

     ProductResponse updateProduct(ProductRequest request, int id);

     Page<ProductResponse> getAllProduct(int page, int product);

     Page<ProductResponse> fillbyProductName(int page, int product, String name);

     String delete(int id);

     ProductResponse getProduct(int id);

     boolean existsByProductName(String productName);

     boolean existsByProductNameAndIdNot (String productName, Integer id);
     List<ProductResponseSelect> getAll();
}
