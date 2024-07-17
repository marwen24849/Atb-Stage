package Atb.Banque.MS_Credit.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@FeignClient(name = "MS-MAILSEND")
public interface MailClient {
    @PostMapping("/mail/send")
    String sendMail(@RequestParam(value = "file", required = false) File file,
                    @RequestParam("to") String to,
                    @RequestParam("cc") String[] cc,
                    @RequestParam("subject") String subject,
                    @RequestParam("body") String body);

    @PostMapping("/mail/send1")
    String sendMail(@RequestParam("to") String to,
                    @RequestParam("cc") String[] cc,
                    @RequestParam("subject") String subject,
                    @RequestParam("body") String body);


}
