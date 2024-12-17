package com.taga.management.controllers;

import com.taga.management.services.impl.VnPayService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

@RestController
@RequestMapping("/api/payment")
@Slf4j
public class PaymentController {

    @Autowired
    private VnPayService vnPayService;

    @GetMapping("/create")
    public ResponseEntity<?> createPayment(HttpServletRequest request) {
        try {
            String paymentUrl = vnPayService.createPaymentUrl(request);
            Map<String, String> response = new HashMap<>();
            response.put("paymentUrl", paymentUrl);
            return ResponseEntity.ok(response);
        } catch (UnsupportedEncodingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while creating payment URL");
        }
    }

    @GetMapping("/vnpay-return")
    public void vnPayReturn(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Chuyển hướng kết quả về frontend để xử lý
        String frontendResultUrl = "http://localhost:8081/payment-result";
        String queryString = request.getQueryString();
        log.info("Redirecting to frontend with query string: " + queryString);
        response.sendRedirect(frontendResultUrl + "?" + queryString);
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verifyPayment(HttpServletRequest request) {
        try {
            Map<String, String> result = vnPayService.handleReturn(request);
            return ResponseEntity.ok(result);
        } catch (UnsupportedEncodingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while verifying payment");
        }
    }
}

