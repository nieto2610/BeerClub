package com.digitalHouse.beerClub.service.interfaces;

public interface IEmailService {
    void sendHtmlMessage(String to, String subject, String content);
    String buildContentWellcomeEmail(String username, String invoice, String amount, String description, String state);
    String buildContentPaymentEmail(String username, String invoice, String amount, String description, String state);
    String buildContentEmail(String title,String userName, String parag1, String parag2, String parag3, String invoice, String amount, String description, String state, String buttonLink, String buttonText, String textFooter);

}
