package com.example.demo.Mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

 /**
 * Класс для отправки электронных писем
 * @autor Шаля Андрей
 * @version 2.0
 */
@Component
public class MailSender {

    /** С какой почты будет отправка(Gmail, Mailru, etc)*/
    private MailService service = MailService.MAIL_RU;

     /** Логин от почты с которой будет отправка писем, указан в файле application.properties*/
    @Value("{mail.login}")
    private String login;

     /** Пароль от почты с которой будет отправка писем, указан в файле application.properties*/
    @Value("{mail.password}")
    private String password;

     /**
      * Инициализация сессии для отправки письма
      * @return {@link Session}
      */
    public Session getGmailSession(){
        java.util.Properties props = new java.util.Properties();
        props.put("mail.smtp.host", service.getHost());
        props.put("mail.smtp.port", service.getPort());
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.connectiontimeout", "1000");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.socketFactory.port", service.getPort());
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(login, password);
            }
        });
        return session;
    }
     /**
      * Функция отправки электронного письма
      * @param session инициализированная методом {@link MailSender#getGmailSession()} сессия
      * @param theme тема письма
      * @param message текст письма
      * @param toEmail кому отправить письмо
      */
    public void send(Session session, String theme, String message, String toEmail){
        String to = toEmail;
        String from = login;
        String subject = theme;
        Message msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(from));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setSubject(subject);
            msg.setText(message);
            Transport.send(msg);
            System.out.println("Отправлено!");
        } catch (AuthenticationFailedException e) {
            e.printStackTrace();
        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
        }
    }
}
