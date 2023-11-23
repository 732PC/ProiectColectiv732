package org.example.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public String readFile(String filePath) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            while (line != null) {
                sb.append(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            System.out.println("email error reading file");
        }
        return sb.toString();
    }


    public void sendEmailFromTemplate(String recipientAddress, String filePath, String subject, String password) {

        try {
            MimeMessage message = mailSender.createMimeMessage();

            message.setFrom("proiectcolectiv732@outlook.com");
            message.setRecipients(MimeMessage.RecipientType.TO, recipientAddress);
            message.setSubject(subject);

            String htmlTemplate = readFile(filePath);

            htmlTemplate = htmlTemplate.replace("{name}", recipientAddress.substring(0, recipientAddress.indexOf("@")));
            htmlTemplate = htmlTemplate.replace("{password}", password);

            message.setContent(htmlTemplate, "text/html; charset=utf-8");

            mailSender.send(message);
        } catch (AddressException e) {
            System.out.println("email address exception");
        } catch (MessagingException e) {
            System.out.println("messaging exception");
        }
        catch (IOException e) {
            System.out.println("io exception");
        }
    }
}
