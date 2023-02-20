package com.capstone.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ResponsePaymentDTO<T> {
    private T data;
    private String event;
    private String created;
    private String business_id;
}
class MetaData{

}
