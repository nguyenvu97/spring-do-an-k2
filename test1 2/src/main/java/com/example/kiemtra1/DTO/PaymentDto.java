package com.example.kiemtra1.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto implements Serializable {
    public String status;
    public String message;
    public String url;
}
