package com.example.kiemtra1.WebRocket;

import lombok.*;


@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {
    public String content;
    public String sender;
    private MessageType type ;
}
