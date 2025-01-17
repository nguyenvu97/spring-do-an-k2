package com.example.kiemtra1.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;
@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class walletLogs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    private double Amount;
    private LocalDateTime Date;
    private String walletName ;
    private boolean status ;
    private double amount_deducted;
    private Long Order_id;
    private String stylepay;
}
