package com.example.kiemtra1.SendPhone;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SendPhone {
    @Value("${ACCount_SId}")
    private String ACCount_SId1;
    @Value("${Auth_Token}")
    private String Auth_Token1;
    @Value("${myphone}")
    private String myphone;


    public void Sendphone(String body) {
        Twilio.init(ACCount_SId1, Auth_Token1);
        Message message = Message.creator(
                new PhoneNumber(myphone), new PhoneNumber(myphone),body
        ).create();
        System.out.println("Tin nhắn đã được gửi: " + message.getSid());
    }
}
