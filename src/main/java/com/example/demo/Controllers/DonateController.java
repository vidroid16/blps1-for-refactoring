package com.example.demo.Controllers;

import com.example.demo.Services.Implementations.DonationServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.*;

/**
 * Класс контроллер для отправления донатов.
 * @autor Шаля Андрей
 * @version 2.0
 */
@RestController
public class DonateController {
    private final DonationServiceImpl donationService;

    @Autowired
    public DonateController(DonationServiceImpl donationService) {
        this.donationService = donationService;
    }


    @Operation(summary = "Задонатить деньги")
    @PostMapping(path ="/donate")
    public String donate(@RequestParam(value = "login") String login, @RequestParam(value = "project_id") Long project_id, @RequestParam(value = "card") String cardNumber, @RequestParam(value = "sum") int sum) throws IOException {
        int donated = donationService.donate(login, project_id, sum, cardNumber);
        if(donated == 200) return "Операция произведена успешно";
        if(donated == 400) return "Не достаточно средств или некорректно введены данные карты";
        return "Что-то пошло не так";
    }

}
