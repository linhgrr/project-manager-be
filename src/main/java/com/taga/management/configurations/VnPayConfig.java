package com.taga.management.configurations;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class VnPayConfig {
    @Value("${vnpay.vnp_TmnCode}")
    private String tmnCode;
    @Value("${vnpay.vnp_HashSecret}")
    private String hashSecret;
    @Value("${vnpay.vnp_PayUrl}")
    private String payUrl;
    @Value("${vnpay.vnp_ReturnUrl}")
    private String returnUrl;
    @Value("${vnpay.vnp_OrderType}")
    private String orderType;
    @Value("${vnpay.amount}")
    private String amount;
}

