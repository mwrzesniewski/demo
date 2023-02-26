package pl.wrzesniewski.demo.email;


import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;




@Slf4j
@RestController
public class EmailController {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}") private String sender;

    public EmailController(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    @GetMapping("/email")
    public ResponseEntity<Void> sendEmail() {
        try {

            // Creating a simple mail message
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();

            // Setting up necessary details
            mailMessage.setFrom(sender);
            mailMessage.setTo("michal.wrzesniewski@outlook.com");
            mailMessage.setText("special content for test");
            mailMessage.setSubject("title test");

            // Sending the mail
            javaMailSender.send(mailMessage);
            log.info("Mail Sent Successfully...");
        }catch (Exception ex) {
            log.error(ex.getMessage());
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
        return ResponseEntity.ok().build();
    }
}
