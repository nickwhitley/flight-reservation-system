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
    private String departTime;
    private String departDate;
    private String destination;

    public Ticket(String customerName, String customerEmail, String flightNumber, int numOfTickets) {
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.flightNumber = flightNumber;
        this.ticketPrice = getTicketPrice(flightNumber);
        this.ticketNumber = generateTicketNumber();
        this.departTime = getDepartTime(flightNumber);
        this.departDate = getDepartDate(flightNumber);
        this.destination = getDestination(flightNumber);
        this.ticketStatus = setInitialTicketStatus(flightNumber);
        numOfTickets = numOfTickets;


        purchaseTicket(customerName, customerEmail, flightNumber, ticketNumber, departTime, departDate,
                destination, ticketStatus, ticketPrice, numOfTickets);
    }

    public static void purchaseTicket(String customerName, String customerEmail, String flightNumber, int ticketNumber, String departTime,
                                      String departDate, String destination, String ticketStatus,
                                      int ticketPrice, int numOfTickets) {
        try {
            TicketDatabase.createTicket(ticketNumber, flightNumber, customerName, customerEmail,
                    departDate, departTime, destination, ticketStatus, ticketPrice);
            System.out.println("Ticket purchased, you will receive a confirmation email with your ticket information.\n" +
                    "Thank you!\n" +
                    "   ");
            FlightDatabase.reduceSeatsAvailable(flightNumber, numOfTickets);

            System.out.println("Returning to customer menu...\n" +
                    "  ");
            Terminal.displayInitialMenu();
        } catch (SQLException e) {
            System.out.println("Error creating ticket number, in Ticket.purchaseTicket()");
            e.printStackTrace();
        }


    }

    //used specifically for "1. Check ticket and flight status in initial menu
    public static String getTicketAndFlightStatus(int ticketNumber) {
        String ticketStatus = TicketDatabase.getTicketStatus(ticketNumber);
        String flightNumber = TicketDatabase.getFlightNumber(ticketNumber);
        String flightStatus = FlightDatabase.getFlightStatus(flightNumber);

        return "Your ticket is currently " + ticketStatus + " for flight " + flightNumber + " is " + flightStatus;
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

    public static String getTicketStatus(int ticketNumber){
        String ticketStatus = TicketDatabase.getTicketStatus(ticketNumber);

        return ticketStatus;
    }

    public String setInitialTicketStatus(String flightNumber) {
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
        // need to update to take ticketNumber as param
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

    public static void cancelTicket(int ticketNumber) {
        //call ticketDB method to cancel ticket
        TicketDatabase.cancelTicket(ticketNumber);
    }

    //used to update just one ticket
    public static void updateTicketStatus(int ticketNumber, String newStatus) {
        TicketDatabase.updateTicketStatus(ticketNumber, newStatus);
    }

    //used to update all tickets for a flight
    public static void updateTicketStatus(String flightNumber, String newStatus) {
        TicketDatabase.updateTicketStatus(flightNumber, newStatus);
    }
}
