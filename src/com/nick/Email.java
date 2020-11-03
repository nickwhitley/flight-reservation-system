package com.nick;

/*
class will be used to email confirmations for tickets
 */

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Email {

    private static String senderEmail = "xxxxxxxxxxxxxxxxxxxx";
    private static String password = "xxxxxxxxx";

    public static Properties setProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        return properties;
    }

    public static Session setSession() {

        Session session = Session.getInstance(setProperties(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, password);
            }
        });
        return session;
    }


    public static void sendConfirmEmail(String recipient, String customerName, String flightNumber, String departTime, String departDate,
                                        String destination, int ticketNum, int totalPrice) throws MessagingException {
        System.out.println("Preparing to send confirmation email.");

        Message message = prepareConfirmMessage(setSession(), senderEmail, recipient, customerName, flightNumber, departTime ,
                departDate, destination, ticketNum, totalPrice);

        Transport.send(message);
        System.out.println("Confirmation Email sent successfully.");

    }

    private static Message prepareConfirmMessage(Session session, String senderEmail, String recipient,
                                                 String customerName, String flightNumber, String departTime, String departDate,
                                                 String destination, int ticketNum, int totalPrice) {
        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject("Confirmation for Ticket Purchase");
            message.setText("Hello, " + customerName + ". Thank you for your purchase for flight " + flightNumber + ". " +
                    "We are excited to safely get you to where you need to go. Attached is your ticket(s) and all the information" +
                    " that you'll need. Please review your information and make sure everything is accurate so we can correct any " +
                    "mistakes in a timely manner. Thank you again and enjoy your flight.\n" +
                    "\n" +
                    "\n" +
                    "Ticket Information: \n" +
                    "Flight Number: " + flightNumber + "                  " + "Ticket Number: " + ticketNum +
                    "\nDestination: " + destination + "                     " + "Depart Time & Date: " + departTime + " " + departDate +
                    "\nTotal Price: $" + totalPrice);
            return message;
        } catch (AddressException e) {
            System.out.println("Error with the address in prepareConfirmMessage()");
            e.printStackTrace();
        } catch (MessagingException e) {
            System.out.println("Error with the message in prepareConfirmMessage()");
            e.printStackTrace();
        }
        return null;
    }

    public static void sendEmail(String recipient) throws MessagingException {
        System.out.println("Preparing to send email.");




        Message message = prepareMessage(setSession(), senderEmail, recipient);

        Transport.send(message);
        System.out.println("Message send successful");
    }



    private static Message prepareMessage(Session session, String senderEmail, String recipient) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject("Test email from java");
            message.setText("This a test email for java\n" +
                    "If it works that's awesome!");
            return message;
        } catch (Exception e) {
            System.out.println("Error creating message.");
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, e);
    }

        return null;

    }
}
