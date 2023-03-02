package com.example.demo.Services;

public interface DonationService {
    int donate(String login, Long project_id, int sum, String cardNumber);
}
