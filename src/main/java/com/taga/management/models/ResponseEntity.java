package com.taga.management.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseEntity {
    private String message;
    private Object data;
}
