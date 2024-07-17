package Atb.stage.MS_MailSend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public interface MailSend {
    String sendMail(File file, String to, String[] cc, String subject, String body);
    String sendMail( String to, String cc, String subject, String body);
}