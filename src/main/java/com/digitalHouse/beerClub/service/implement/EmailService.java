package com.digitalHouse.beerClub.service.implement;

import com.digitalHouse.beerClub.service.interfaces.IEmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements IEmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void sendHtmlMessage(String to, String subject, String content) {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
        try {
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content,true);
            emailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar el correo", e);
        }
    }
    @Override
    public String buildContentWellcomeEmail(String username, String invoice, String amount, String description, String state){
        //Header
        String title = "¡Bienvenido a Beer Club!";

        //Body
        String paragraph1 = "Gracias por registrarte en Beer Club. Estamos emocionados de tenerte como parte de nuestra comunidad.";
        String paragraph2 = "Esperamos que disfrutes de la experiencia de pertenecer al mejor Club de Cerveza.";
        String paragraph3 = "No dudes en ponerte en contacto con nosotros si tienes alguna pregunta o necesitas ayuda.";
        String link = "http://ec2-54-82-22-67.compute-1.amazonaws.com/login";
        String buttonText = "Ir a Beer Club";

        //Footer
        String textFooter = "¡Salud!";

        String content = buildContentEmail(title, username, paragraph1, paragraph2, paragraph3, invoice, amount, description, state, link, buttonText, textFooter);
        return content;
    }
    @Override
    public String buildContentPaymentEmail(String username, String invoice, String amount, String description, String state){
        //Header
        String title = "Facturación Beer Club";

        //Body
        String paragraph1 = "Gracias por elegir Beer Club. Estamos felices de tenerte en nuestra comunidad.";
        String paragraph2 = "Esperamos que estes disfrutando la experiencia de pertenecer al mejor Club de Cerveza. Adjuntamos la factura correspondiente a tu suscripción";
        String paragraph3 = "No dudes en ponerte en contacto con nosotros si tienes alguna pregunta o necesitas ayuda.";
        String link = "http://ec2-54-82-22-67.compute-1.amazonaws.com/login";
        String buttonText = "Ir a Beer Club";

        //Footer
        String textFooter = "¡Salud!";

        String content = buildContentEmail(title, username, paragraph1, paragraph2, paragraph3, invoice, amount, description, state, link, buttonText, textFooter);
        return content;
    }
    @Override
    public String buildContentEmail(String title,String userName, String parag1, String parag2, String parag3, String invoice, String amount, String description, String state, String buttonLink, String buttonText, String textFooter){
        return "<!DOCTYPE html>" +
                "<html lang='es'>" +
                "<head>" +
                "    <meta charset='UTF-8'>" +
                "    <meta http-equiv='X-UA-Compatible' content='IE=edge'>" +
                "    <meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                "    <title>Bienvenido a Beer Club</title>" +
                "</head>" +
                "<body style='font-family: Arial, sans-serif;'>" +
                "    " + buildHeader(title) +
                "    " + buildBody(userName, parag1, parag2, parag3, invoice, amount, description, state, buttonLink, buttonText) +
                "    " + buildFooter(textFooter) +
                "</body>" +
                "</html>";
    }

    private String buildHeader(String title){
        String COLOR_HEADER = "#000000";
        String logoUrl = "Logo_sin_escudo_Color_Original.svg";
        return "    <table style='max-width: 600px; margin: 0 auto; padding: 20px; background-color: " + COLOR_HEADER + "; border-collapse: collapse;'>" +
                "        <tr>" +
                "            <td align='center' style='padding: 20px; color: #ffffff;'>" +
                "                <h2 style='margin: 0; color: #ffffff;'>"+ title +"</h2>" +
                "            </td>" +
                "        </tr>";
    }

    private String buildBody(String userName, String parag1, String parag2, String parag3, String invoice, String amount, String description, String state,  String buttonLink, String buttonText){
        String COLOR_BODY = "#eee9e1";
        String BUTTON_COLOR = "#CAB0A1";

        String tableHtml = buildPaymentTable(invoice, amount, description, state);

        return "        <tr>" +
                "            <td style='padding: 20px; background-color: " + COLOR_BODY + "; border-collapse: collapse;'>" +
                "                <p><b>Estimad@ " + userName + ",</b></p>" +
                "                <p>" + parag1 + "</p>" +
                "                <p>" + parag2 + "</p>" +
                "                " + tableHtml +
                "                <p style='text-align: center;'><a href='" + buttonLink + "' style='background-color: " + BUTTON_COLOR + "; color: #ffffff; padding: 10px 20px; text-decoration: none; display: inline-block; border-radius: 5px;'>"
                + buttonText + "</a></p>" +
                "                <p>" + parag3 + "</p>" +
                "            </td>" +
                "        </tr>";
    }

    private String buildFooter(String textFooter){
        String COLOR_FOOTER = "#806353";
        return "        <tr>" +
                "            <td style='padding: 5px; background-color: " + COLOR_FOOTER + "; color: #ffffff; text-align: center; border-collapse: collapse;'>" +
                "                <p><b>" +textFooter+"</b></p>" +
                "            </td>" +
                "        </tr>" +
                "    </table>";
    }

    private String buildPaymentTable(String numeroFactura, String monto, String descripcion, String estado) {
        StringBuilder tableHtml = new StringBuilder();
        tableHtml.append("<table style='margin: 0 auto; border-collapse: collapse; border: 1px solid #808080;'>");

        // Encabezado general de la tabla
        tableHtml.append("<tr>")
                .append("<th colspan='2' style='border: 1px solid #808080; background-color: #ceb5a7; color: #ffffff;'>Detalles de Facturación</th>")
                .append("</tr>");

        // Fila 1
        tableHtml.append("<tr>")
                .append("<td style='border: 1px solid #808080; padding: 8px; margin-right: 5px;'>").append("<b>Nro Factura</b>").append("</td>")
                .append("<td style='border: 1px solid #808080; padding: 8px;'>").append(numeroFactura).append("</td>")
                .append("</tr>");

        // Fila 2
        tableHtml.append("<tr>")
                .append("<td style='border: 1px solid #808080; padding: 8px;'>").append("<b>Monto</b>").append("</td>")
                .append("<td style='border: 1px solid #808080; padding: 8px;'>").append(monto).append("</td>")
                .append("</tr>");

        // Fila 3
        tableHtml.append("<tr>")
                .append("<td style='border: 1px solid #808080; padding: 8px; margin-right: 5px;'>").append("<b>Descripción</b>").append("</td>")
                .append("<td style='border: 1px solid #808080; padding: 8px;'>").append(descripcion).append("</td>")
                .append("</tr>");

        // Fila 4
        tableHtml.append("<tr>")
                .append("<td style='border: 1px solid #808080; padding: 8px; margin-right: 5px;'>").append("<b>Estado</b>").append("</td>")
                .append("<td style='border: 1px solid #808080; padding: 8px;'>").append(estado).append("</td>")
                .append("</tr>");

        tableHtml.append("</table>");
        return tableHtml.toString();
    }

}
