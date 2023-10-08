package com.example.kiemtra1.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Setter
@Getter
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id ;
    public int Quantity ;
    public double PriceTotal;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Cart_product",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products = new ArrayList<>();
}
