package com.poly.app.domain.admin.freeship_order.controller;


import com.poly.app.domain.admin.freeship_order.service.FreeshipOrderService;
import com.poly.app.domain.model.FreeshipOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/freeship-order")
public class FreeShipOrderController {

    @Autowired
    FreeshipOrderService freeshipOrderService;

    @PostMapping
    public ResponseEntity<?> handleSetMinOrder(Double minOrder) {
        freeshipOrderService.setMinOrderValue(minOrder);
        return ResponseEntity.ok("success");
    }

    @GetMapping
    public ResponseEntity<?> getMinOrderValueFreeShip() {
        FreeshipOrder freeshipOrder = freeshipOrderService.getMinOrderValue();
        return ResponseEntity.ok(freeshipOrder);
    }

}
