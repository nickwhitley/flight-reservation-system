package com.nick;

import com.nick.databases.FlightDatabase;

import java.util.Random;

public class Ticket {

    /*
    Ticket info that needs to be included:
    -ticket holder name
    -seat number
    -flight number
    -flight date and time
    -flight destination
    -ticker status
     */

    private int ticketNumber;
    private String customerName;
    private String customerEmail;
    private int ticketPrice;
    private String ticketStatus;
    private String flightNumber;
    private String seatNumber;
    private String departTime;
    private String departDate;
    private String destination;

    public Ticket(String customerName, String customerEmail, String flightNumber, String seatNumber) {
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.flightNumber = flightNumber;
        this.seatNumber = seatNumber;
        this.ticketPrice = getTicketPrice(flightNumber);
        this.ticketNumber = generateTicketNumber();

        purchaseTicket(customerName, customerEmail, flightNumber, seatNumber, ticketNumber);
    }

    public static void purchaseTicket(String customerName, String customerEmail, String flightNumber,
                                      String seatNumber, int ticketNumber, String departTime,
                                      String departDate, String destination, String ticketStatus,
                                      String ticketPrice) {



    }

    public int getTicketPrice(String flightNumber) {
        flightNumber = this.flightNumber;
        int ticketPrice = FlightDatabase.getTicketPrice(flightNumber);

        return ticketPrice;
    }

    public static int generateTicketNumber() {
        int ticketNum = (int) Math.random() * ((99999 - 10000) +10000);
        return ticketNum;
    }
}
