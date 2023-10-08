package com.example.kiemtra1.Controller;


import com.example.kiemtra1.DTO.MembeaccountDTO;
import com.example.kiemtra1.DTO.MemberData;
import com.example.kiemtra1.DecodeJWT.JwtDecoder1;
import com.example.kiemtra1.Model.MemberAccount;
import com.example.kiemtra1.Repo.MemberAccountRepo;
import com.example.kiemtra1.Service.MemberaccountService;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageFilter;
import java.io.*;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

@Controller
class QRController {
    @Autowired
    private MemberAccountRepo memberAccountRepo;
    @Autowired
    private JwtDecoder1 jwtDecoder1;
    @Autowired
    private MemberaccountService memberaccountService;

    @GetMapping("/qr")
    private ResponseEntity<?> Qrcode(@RequestParam String token) {
        MemberData memberData = jwtDecoder1.decode(token);
        Optional<MemberAccount> memberAccount = memberAccountRepo.findByUsername(memberData.getSub());
        if (memberAccount.isPresent()) {
            MemberAccount memberAccount1 = memberAccount.get();
            MembeaccountDTO membeaccountDTO = new MembeaccountDTO();
            membeaccountDTO.setAddress(memberAccount1.getAddress());
            membeaccountDTO.setEmail(memberAccount1.getEmail());
            membeaccountDTO.setUsername(memberAccount1.getUsername());
            membeaccountDTO.setPhoneNo(memberAccount1.getPhoneNo());
            int height = 100;
            int wight = 100;
            String all = String.valueOf(membeaccountDTO);
            try {
                QRCodeWriter qrCodeWriter = new QRCodeWriter();
                BitMatrix bitMatrix = qrCodeWriter.encode(all, BarcodeFormat.QR_CODE, height, wight);
                BufferedImage bufferedImage = new BufferedImage(height, wight, BufferedImage.TYPE_INT_BGR);
                for (int x = 0; x < wight; x++) {
                    for (int y = 0; y < height; y++) {
                        int color = bitMatrix.get(x, y) ? Color.BLACK.getRGB() : Color.WHITE.getRGB();
                        bufferedImage.setRGB(x, y, color);
                    }
                }
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
                ByteArrayResource byteArrayResource = new ByteArrayResource(byteArrayOutputStream.toByteArray());
                org.springframework.http.HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vnpay_qr.png");
                return ResponseEntity.ok().headers(headers).contentType(MediaType.IMAGE_PNG).body(byteArrayResource);
            } catch (Exception e) {
                e.getMessage();
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("null");
    }
    @PostMapping("Qrcode1")
    public ResponseEntity<?> ReadQrcode(@RequestParam("QRcode") MultipartFile file)throws IOException, NotFoundException {
        String qrCodeString = memberaccountService.decodeQr(file.getBytes());
        return ResponseEntity.ok().body(qrCodeString);
    }
}