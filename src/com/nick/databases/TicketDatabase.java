package com.nick.databases;

import jdk.swing.interop.SwingInterOpUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class TicketDatabase {
    /*
    ticket info needs to include:
    flight number
    name of owner
    destination
    departure time
    ticket status
    price of ticket
     */

    static Scanner scanner = new Scanner(System.in);

    public static final String DB_NAME = "ticketdatabase.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:D:\\Nick\\Documents\\Coding\\Projects\\FlightReservationSystem\\Databases\\" + DB_NAME;
    public static final String TABLE_TICKETS = "tickets";
    public static final String COLUMN_FLIGHT_NUMBER = "Flight Number";
    public static final String COLUMN_TICKET_NUMBER = "Ticket Number";
    public static final String COLUMN_CUSTOMER_NAME = "Customer Name";
    public static final String COLUMN_CUSTOMER_EMAIL = "Customer Email";
    public static final String COLUMN_SEAT_NUMBER = "Seat Number";
    public static final String COLUMN_TICKET_PRICE = "Ticket Price";
    public static final String COLUMN_DESTINATION = "Destination";
    public static final String COLUMN_TICKET_STATUS = "Ticket Status";
    public static final String COLUMN_DEPARTURE_TIME = "Depart Time";
    public static final String COLUMN_DEPARTURE_DATE = "Depart Date";

    public static Connection conn;
    public static Statement statement;

    static {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);
            statement = conn.createStatement();
        } catch (SQLException e) {
            System.out.println("Error when connecting to ticket database.");
            e.printStackTrace();
        }
    }

    public static void purchaseTicket(String ticketNumber, String flightNumber, String customerName, String customerEmail,
                                      String departDate, String departTime, String destination, String ticketStatus, String seatNumber,
                                      String ticketPrice) throws SQLException {

        //create data table
        try {

            statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_TICKETS +
                    " (" + COLUMN_TICKET_NUMBER + " TEXT, " +
                    COLUMN_FLIGHT_NUMBER + " TEXT, " +
                    COLUMN_CUSTOMER_NAME + " TEXT, " +
                    COLUMN_CUSTOMER_EMAIL + " TEXT, " +
                    COLUMN_DEPARTURE_TIME + " TEXT, " +
                    COLUMN_DEPARTURE_DATE + " TEXT, " +
                    COLUMN_DESTINATION + " TEXT, " +
                    COLUMN_TICKET_STATUS + " TEXT, " +
                    COLUMN_SEAT_NUMBER + " TEXT, " +
                    COLUMN_TICKET_PRICE + " INTEGER ) ");

        } catch (SQLException e) {
            System.out.println("Error when creating ticket entry");
            e.printStackTrace();
        }

        //add entry into ticket db
        try {

            statement.execute("INSERT INTO " + TABLE_TICKETS +
                    " (" + COLUMN_TICKET_NUMBER + ", " +
                    COLUMN_FLIGHT_NUMBER + ", " +
                    COLUMN_CUSTOMER_NAME + ", " +
                    COLUMN_CUSTOMER_EMAIL + ", " +
                    COLUMN_DEPARTURE_TIME + ", " +
                    COLUMN_DEPARTURE_DATE + ", " +
                    COLUMN_DESTINATION + ", " +
                    COLUMN_TICKET_STATUS + ", " +
                    COLUMN_SEAT_NUMBER + ", " +
                    COLUMN_TICKET_PRICE + ") " +
                    "VALUES('" +
                    ticketNumber + ", '" +
                    flightNumber + ", '" +
                    customerName + ", '" +
                    customerEmail + ", '" +
                    departTime + ", '" +
                    departDate + ", '" +
                    destination + ", '" +
                    ticketStatus + ", '" +
                    seatNumber + ", '" +
                    ticketPrice + ")");

        } catch (SQLException e) {
            System.out.println("Error when creating new ticket in ticket database.");
            e.printStackTrace();
        }

        statement.close();
        conn.close();


    }
}
