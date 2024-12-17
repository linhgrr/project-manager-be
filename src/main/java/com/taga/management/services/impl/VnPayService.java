package com.taga.management.services.impl;

import com.taga.management.configurations.VnPayConfig;
import com.taga.management.models.PaymentTransaction;
import com.taga.management.models.User;
import com.taga.management.repository.PaymentTransactionRepository;
import com.taga.management.services.IUserService;
import com.taga.management.utils.SecurityUtils;
import com.taga.management.utils.VnPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class VnPayService {

    @Autowired
    private VnPayConfig vnPayConfig;
    @Autowired
    private IUserService userService;
    @Autowired
    private PaymentTransactionRepository paymentTransactionRepository;

    public String createPaymentUrl(HttpServletRequest request) throws UnsupportedEncodingException {
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_TmnCode = vnPayConfig.getTmnCode();
        String vnp_ReturnUrl = vnPayConfig.getReturnUrl();
        String vnp_HashSecret = vnPayConfig.getHashSecret();

        String vnp_TxnRef = VnPayUtil.getRandomNumber(8);
        String vnp_OrderInfo = "Thanh toan don hang " + vnp_TxnRef;
        String orderType = vnPayConfig.getOrderType();
        String vnp_Amount = vnPayConfig.getAmount();
        String vnp_Locale = "vn";
        String vnp_IpAddr = VnPayUtil.getIpAddress(request);
        String vnp_CreateDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        //save transaction to db
        PaymentTransaction paymentTransaction = PaymentTransaction.builder()
                .txnRef(vnp_TxnRef)
                .user(SecurityUtils.getPrincipal())
                .build();

        paymentTransactionRepository.save(paymentTransaction);

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", vnp_Amount);
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Locale", vnp_Locale);
        vnp_Params.put("vnp_ReturnUrl", vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        // Build data to hash and query string
        String hashData = VnPayUtil.hashAllFields(vnp_Params);
        String vnp_SecureHash = VnPayUtil.hmacSHA512(vnp_HashSecret, hashData);
        vnp_Params.put("vnp_SecureHash", vnp_SecureHash);
        String queryUrl = VnPayUtil.buildQueryString(vnp_Params);

        return vnPayConfig.getPayUrl() + "?" + queryUrl;
    }

    public Map<String, String> handleReturn(HttpServletRequest request) throws UnsupportedEncodingException {
        Map<String, String> fields = VnPayUtil.getParameterMap(request);
        String vnp_SecureHash = fields.remove("vnp_SecureHash");
        String hashData = VnPayUtil.hashAllFields(fields);
        String vnp_HashSecret = vnPayConfig.getHashSecret();
        String calculatedHash = VnPayUtil.hmacSHA512(vnp_HashSecret, hashData);

        Map<String, String> result = new HashMap<>();
        if (calculatedHash.equals(vnp_SecureHash)) {
            String vnp_ResponseCode = fields.get("vnp_ResponseCode");
            String vnp_TxnRef = fields.get("vnp_TxnRef");
            if ("00".equals(vnp_ResponseCode)) {
                result.put("message", "Thanh toán thành công!");
                // Save subscription to database
                PaymentTransaction paymentTransaction = paymentTransactionRepository.findByTxnRef(vnp_TxnRef);
                User user = paymentTransaction.getUser();
                userService.updateSubscription(user);
            } else {
                result.put("message", "Thanh toán không thành công!");
            }
        } else {
            result.put("message", "Chữ ký không hợp lệ!");
        }
        return result;
    }
}

