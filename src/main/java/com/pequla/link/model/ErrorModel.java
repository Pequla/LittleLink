package com.pequla.link.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorModel {
    private String name;
    private String message;
    private String path;
    private Long timestamp;
}
