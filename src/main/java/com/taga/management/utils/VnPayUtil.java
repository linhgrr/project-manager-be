package com.taga.management.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class VnPayUtil {

    public static String hmacSHA512(String key, String data) {
        try {
            Mac hmac = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
            hmac.init(secretKey);
            byte[] bytes = hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Hex.encodeHexString(bytes);
        } catch (Exception e) {
            throw new RuntimeException("Error while generating HMAC SHA512", e);
        }
    }

    public static String getRandomNumber(int length) {
        String chars = "0123456789";
        StringBuilder sb = new StringBuilder();
        Random rnd = new Random();
        while (sb.length() < length) {
            int index = (int) (rnd.nextFloat() * chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }

    public static String buildQueryString(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder query = new StringBuilder();
        for (Map.Entry<String, String> param : params.entrySet()) {
            query.append(URLEncoder.encode(param.getKey(), StandardCharsets.US_ASCII.toString()));
            query.append('=');
            query.append(URLEncoder.encode(param.getValue(), StandardCharsets.US_ASCII.toString()));
            query.append('&');
        }
        // Xóa ký tự '&' cuối cùng
        query.deleteCharAt(query.length() - 1);
        return query.toString();
    }

    public static String hashAllFields(Map<String, String> fields) throws UnsupportedEncodingException {
        List<String> fieldNames = new ArrayList<>(fields.keySet());
        Collections.sort(fieldNames);
        StringBuilder sb = new StringBuilder();
        for (String fieldName : fieldNames) {
            String fieldValue = fields.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                sb.append(fieldName);
                sb.append('=');
                sb.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                sb.append('&');
            }
        }
        // Xóa ký tự '&' cuối cùng
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public static Map<String, String> getParameterMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        for (Enumeration<String> en = request.getParameterNames(); en.hasMoreElements();) {
            String key = en.nextElement();
            String value = request.getParameter(key);
            map.put(key, value);
        }
        return map;
    }
}

