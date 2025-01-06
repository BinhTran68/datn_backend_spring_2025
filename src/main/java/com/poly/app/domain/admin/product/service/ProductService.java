package com.poly.app.domain.admin.product.service;

import com.poly.app.domain.model.Product;
import com.poly.app.domain.admin.product.request.product.ProductRequest;
import com.poly.app.domain.admin.product.response.product.ProductResponse;

import java.util.List;

public interface ProductService {
     List<ProductResponse> getAllProduct();
     Product createProduct(ProductRequest request);
     ProductResponse updateProduct(ProductRequest request, int id);
     String deleteProduct (int id);
     ProductResponse getProduct(int id);
}
