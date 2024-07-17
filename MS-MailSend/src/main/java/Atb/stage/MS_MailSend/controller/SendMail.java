package Atb.stage.MS_MailSend.controller;

import Atb.stage.MS_MailSend.service.MailSend;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/mail")
@AllArgsConstructor
public class SendMail {

    private MailSend emailService;


    @PostMapping("/send")
    public String sendMail(@RequestParam(value = "file", required = false) File file, String to, String[] cc, String subject, String body) {
        return emailService.sendMail(file, to, cc, subject, body);
    }

    @PostMapping("/send1")
    public String sendMail(String to, String cc, String subject, String body) {
        return emailService.sendMail(to, null, subject, body);
    }
}
