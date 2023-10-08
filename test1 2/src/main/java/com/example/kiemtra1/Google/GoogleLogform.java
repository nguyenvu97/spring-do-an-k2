package com.example.kiemtra1.Google;

import com.example.kiemtra1.Google.Google;
import com.example.kiemtra1.Google.GoogleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class GoogleLogform {
    @Autowired
    public GoogleRepo googleRepo;
    @GetMapping("/")
    public String userInfo(@AuthenticationPrincipal OAuth2User principal) {
        if (principal != null) {
            String email = principal.getAttribute("email");
            String access_token = principal.getAttribute("tokenValue");
            String userid = principal.getAttribute("sub");
            Google google = new Google();
            google.setFullname(principal.getAttribute("name"));
            google.setAccess_token(access_token);
            google.setUserid(userid);
            google.setEmail(email);
            googleRepo.save(google);
            return "Hello, " + principal.getAttribute("name") + "!";
        } else {
            return "User information not available.";
        }
    }
    @GetMapping("/1")
    public Principal user(Principal principal) {
        System.out.println("username : " + principal.getName());
        return principal;
    }
}
