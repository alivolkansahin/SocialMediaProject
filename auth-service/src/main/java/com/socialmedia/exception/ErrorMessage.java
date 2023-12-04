package com.socialmedia.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ErrorMessage { // kullanıcıya ne döneceksek onları tanımlayalım

    private int code;

    private String message;

    private List<String> fields;

    @Builder.Default
    private LocalDateTime dateTime = LocalDateTime.now();
}
