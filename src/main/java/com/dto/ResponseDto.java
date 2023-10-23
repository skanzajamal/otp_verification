package com.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {

    private OTPStatus status;
    private String message;

    public enum OTPStatus {
       DELIVERED, FAILED;
    }

} //ENDCLASS
