package com.nick.databases;

import jdk.swing.interop.SwingInterOpUtils;

import java.sql.*;
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
    public static final String CONNECTION_STRING = "jdbc:sqlite:D:\\Nick\\Documents\\Coding\\Projects\\flight-reservation-system\\Databases\\" + DB_NAME;
    public static final String TABLE_TICKETS = "tickets";
    public static final String COLUMN_FLIGHT_NUMBER = "FlightNumber";
    public static final String COLUMN_TICKET_NUMBER = "TicketNumber";
    public static final String COLUMN_CUSTOMER_NAME = "CustomerName";
    public static final String COLUMN_CUSTOMER_EMAIL = "CustomerEmail";
    public static final String COLUMN_TICKET_PRICE = "TicketPrice";
    public static final String COLUMN_DESTINATION = "Destination";
    public static final String COLUMN_TICKET_STATUS = "TicketStatus";
    public static final String COLUMN_DEPARTURE_TIME = "DepartTime";
    public static final String COLUMN_DEPARTURE_DATE = "DepartDate";

    public static Connection conn;
    public static Statement statement;

    static String ticketStatus;
    static String flightNumber;
    static {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);
            statement = conn.createStatement();
        } catch (SQLException e) {
            System.out.println("Error when connecting to ticket database.");
            e.printStackTrace();
        }
    }

    public static void createTicket(int ticketNumber, String flightNumber, String customerName, String customerEmail,
                                      String departDate, String departTime, String destination, String ticketStatus,
                                      int ticketPrice) throws SQLException {

        //create data table
        try {

            statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_TICKETS +
                    " (" + COLUMN_TICKET_NUMBER + " INTEGER, " +
                    COLUMN_FLIGHT_NUMBER + " TEXT, " +
                    COLUMN_CUSTOMER_NAME + " TEXT, " +
                    COLUMN_CUSTOMER_EMAIL + " TEXT, " +
                    COLUMN_DEPARTURE_TIME + " TEXT, " +
                    COLUMN_DEPARTURE_DATE + " TEXT, " +
                    COLUMN_DESTINATION + " TEXT, " +
                    COLUMN_TICKET_STATUS + " TEXT, " +
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
                    COLUMN_TICKET_PRICE + ") " +
                    "VALUES('" +
                    ticketNumber + "', '" +
                    flightNumber.toUpperCase() + "', '" +
                    customerName + "', '" +
                    customerEmail + "', '" +
                    departTime + "', '" +
                    departDate + "', '" +
                    destination + "', '" +
                    ticketStatus + "', '" +
                    ticketPrice + "')");

        } catch (SQLException e) {
            System.out.println("Error when creating new ticket in ticket database.");
            e.printStackTrace();
        }

        statement.close();
        conn.close();
    }

    public static String getTicketStatus(int ticketNumber) {
        try {
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(" SELECT " + COLUMN_TICKET_STATUS + " FROM " + TABLE_TICKETS +
                    " WHERE " + COLUMN_TICKET_NUMBER + " = '" + ticketNumber + "';");

            System.out.println();

            ticketStatus = result.getString("TicketStatus");
        } catch (SQLException e) {
            System.out.println("Error getting ticket status.");
            e.printStackTrace();
        }
        return ticketStatus;
    }

    //return the flight number for a given ticket
    public static String getFlightNumber(int ticketNumber) {
        try {
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(" SELECT " + COLUMN_FLIGHT_NUMBER + " FROM " + TABLE_TICKETS +
                    " WHERE " + COLUMN_TICKET_NUMBER + " = '" + ticketNumber + "';");

            System.out.println();

            flightNumber = result.getString("FlightNumber");
        } catch (SQLException e) {
            System.out.println("Error getting ticket status.");
            e.printStackTrace();
        }
        return flightNumber;
    }
}
