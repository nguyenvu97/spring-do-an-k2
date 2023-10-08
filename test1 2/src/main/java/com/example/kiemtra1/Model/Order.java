
package com.example.kiemtra1.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "Order_table")
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class Order {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String orderNo  ;
        private String membername;
        private String email;
        private String address;
        private String phoneNo;
        private LocalDateTime orderDate;
        private double orderAmount;
        private Status status = Status.valueOf("Ok");
        //cartItems
        @OneToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "cartItem_id")
        private CartItem cartItems;
        // Account_Order
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "memberAccount_id")
        private MemberAccount Account_Order;
}
