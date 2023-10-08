package com.example.kiemtra1.Google;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.w3c.dom.Text;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
@Getter
public class Google {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String fullname;
    public String email;
    public String Userid;
    public String access_token;
}
