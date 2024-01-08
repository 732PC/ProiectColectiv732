package org.example.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public String readFile(String filePath) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            while (line != null) {
                sb.append(line);
                line = reader.readLine();
            }
        } catch (IOException ignored) {
        }
        return sb.toString();
    }

    public String configureEmailTemplateUserCreated(String recipientAddress, String password)
            throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:emailTemplateAccountConfirmation.txt");
        String template = readFile(file.getPath());
        template = template.replace("{name}", recipientAddress.substring(0, recipientAddress.indexOf("@")));
        template = template.replace("{password}", password);
        return template;
    }

    public String configureEmailTemplateCourseMaterials(String courseName, String materialTitle)
            throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:emailTemplateCourseMaterialsUploaded.txt");
        String template = readFile(file.getPath());
        template = template.replace("{courseName}", courseName);
        template = template.replace("{materialTitle}", materialTitle);
        return template;
    }

    public void sendEmailFromTemplate(String recipientAddress, String subject, String messageContent) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            message.setFrom("proiectcolectiv732@outlook.com");
            message.setRecipients(MimeMessage.RecipientType.TO, recipientAddress);
            message.setSubject(subject);
            message.setContent(messageContent, "text/html; charset=utf-8");
            mailSender.send(message);
        } catch (MessagingException ignored) {
        }
    }


}
