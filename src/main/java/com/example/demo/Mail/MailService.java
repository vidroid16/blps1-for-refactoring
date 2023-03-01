package com.example.demo.Mail;

/**
 * Enum с парами host-port для основных почтовых сервисов. Протокол передачи письем SMTP.
 * @autor Шаля Андрей
 * @version 2.0
 */
public enum MailService {
    GMAIL("smtp.gmail.com","587"),
    OUTLOOK("SMTP.Office365.com","587"),
    MAIL_RU("smtp.mail.ru", "465"),
    YANDEX("smtp.yandex.ru", "465");


    private String host;
    private String port;

    MailService(String host, String port){
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }
}
