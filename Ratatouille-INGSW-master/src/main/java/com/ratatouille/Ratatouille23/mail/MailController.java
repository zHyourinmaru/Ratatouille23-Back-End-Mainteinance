package com.ratatouille.Ratatouille23.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/v1/mail")
@RequiredArgsConstructor
public class MailController {

    private final EmailService emailService;
    @GetMapping("/sendMail")
    public void sendMail() {
        emailService.sendEmail("ratatouille23.ingsw@gmail.com", "First Email", "First Email");
    }

}
