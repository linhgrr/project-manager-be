package com.taga.management.DTOs.request;

public class VnPayRequest {
    private String vnp_Version = "2.1.0";
    private String vnp_Command = "pay";
    private String vnp_TmnCode;
    private String vnp_Amount;
    private String vnp_CurrCode = "VND";
    private String vnp_TxnRef;
    private String vnp_OrderInfo;
    private String vnp_OrderType;
    private String vnp_Locale = "vn";
    private String vnp_ReturnUrl;
    private String vnp_IpAddr;
    private String vnp_CreateDate;
    // Getter và Setter
}

