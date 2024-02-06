/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author hp
 */
public class MailAPI {

    public static void sendMail(String to, String sub, String msg) throws MessagingException {

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smpt.ssl.trust", "smtp.gmail.com");
        Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("chayma.gharbi@esprit.tn", "223JFT2662");
            }
        });
        MimeMessage message = new MimeMessage(session);

        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

        message.setSubject(sub);
        message.setText(msg);
        Transport.send(message);
        System.out.println("message sent");

    }


    /* public static void main(String[] args) {

        final String username = "chayma.gharbi@esprit.tn";
        final String password = "223JFT2662";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
          });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("chayma.gharbi@esprit.tn"));
            message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse("recipient_email_address"));
            message.setSubject("Subject of the Email");
            message.setText("Body of the Email");

            Transport.send(message);

            System.out.println("Email Sent");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }*/
}
