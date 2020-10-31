package com.nick;

import com.nick.databases.FlightDatabase;
import com.nick.databases.TicketDatabase;

import java.sql.SQLException;
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
        this.departTime = getDepartTime(flightNumber);
        this.departDate = getDepartDate(flightNumber);
        this.destination = getDestination(flightNumber);
        this.ticketStatus = getTicketStatus(flightNumber);


        purchaseTicket(customerName, customerEmail, flightNumber, seatNumber, ticketNumber, departTime, departDate,
                destination, ticketStatus, ticketPrice);
    }

    public static void purchaseTicket(String customerName, String customerEmail, String flightNumber,
                                      String seatNumber, int ticketNumber, String departTime,
                                      String departDate, String destination, String ticketStatus,
                                      int ticketPrice) {
        try {
            TicketDatabase.createTicket(ticketNumber, flightNumber, customerName, customerEmail,
                    departDate, departTime, destination, ticketStatus, seatNumber, ticketPrice);
            System.out.println("Ticket ");
        } catch (SQLException e) {
            System.out.println("Error creating ticket number, in Ticket.purchaseTicket()");
            e.printStackTrace();
        }


    }

    public String getDepartTime(String flightNumber) {
        return FlightDatabase.getDepartTime(flightNumber);
    }

    public String getDepartDate(String flightNumber) {
        return FlightDatabase.getDepartDate(flightNumber);
    }

    public String getDestination(String flightNumber) {
        return FlightDatabase.getDestination(flightNumber);
    }

    public String getTicketStatus(String flightNumber) {
        /*
        ticket status can include
        expired
        valid
        cancelled
         */
        String ticketStatus = null;
        String flightStatus = FlightDatabase.getFlightStatus(flightNumber);

        switch (flightStatus) {
            case "On Time":
            case "Boarding":
            case "Arrived":
            case "Delayed":
                ticketStatus = "Valid";
                break;
            case "Cancelled":
                ticketStatus = "Cancelled";
                break;
            case "Past Flight":
            case "Departed":
                ticketStatus = "Expired";
                break;
            default:
                System.out.println("Error on flight status!");
                break;
        }
        return ticketStatus;
    }

    public int getTicketPrice(String flightNumber) {
        flightNumber = this.flightNumber;
        int ticketPrice = FlightDatabase.getTicketPrice(flightNumber);

        return ticketPrice;
    }

    public static int generateTicketNumber() {
        int min = 10000;
        int max = 99999;
        int ticketNum = (int) (Math.random() * (max - min + 1) + min);
        return ticketNum;
    }
}
