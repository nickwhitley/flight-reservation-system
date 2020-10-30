package com.nick.databases;

import com.nick.Terminal;
import jdk.swing.interop.SwingInterOpUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;
import java.util.Scanner;

public class FlightDatabase {

    static Scanner scanner = new Scanner(System.in);

    public static final String DB_NAME = "flightdatabase.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:D:\\Nick\\Documents\\Coding\\Projects\\FlightReservationSystem\\Databases\\" + DB_NAME;
    public static final String TABLE_FLIGHTS = "flights";
    public static final String COLUMN_FLIGHT_NUMBER = "FlightNumber";
    public static final String COLUMN_FLIGHT_DESTINATION = "FlightDestination";
    public static final String COLUMN_DEPARTURE_DATETIME = "DepartureDateTime";
    public static final String COLUMN_ARRIVAL_DATETIME = "ArrivalDateTime";
    public static final String COLUMN_FLIGHT_STATUS = "FlightStatus";
    public static final String COLUMN_SEATS_AVAILABLE = "SeatsAvailable";
    public static final String COLUMN_TICKET_PRICE = "TicketPrice";

    public static Connection conn;
    public static Statement statement;

    static {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING );
            statement = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void flightDatabase() {

        try{

            statement.execute("DROP TABLE IF EXISTS " + TABLE_FLIGHTS);
            System.out.println("Passed 1");

            createFlight( "BU1813", "Paris, France", "1:00 PM 10/29/2020",
                    "8:00 PM 10/29/2020", "ON SCHEDULE", 98, 230);


            statement.close();
            conn.close();

            System.out.println("DB Created");
        } catch(SQLException e) {
            System.out.println("Error in creating flight DB");
            e.getMessage();
            e.printStackTrace();
        }
    }

    public static void getFlightStatus(String flightNumber) {
        //used to print out flight status from a selected flight
        try{
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("SELECT " + COLUMN_FLIGHT_STATUS + " FROM " + TABLE_FLIGHTS +
                    " WHERE " + COLUMN_FLIGHT_NUMBER + " = '" + flightNumber + "';");
            String flightStatus = result.getString("FlightStatus");
            System.out.println(flightStatus);
        } catch (SQLException e) {
            System.out.println("Error getting the flight status.");
            e.printStackTrace();
        }
    }

    public static void updateFlightStatus(String flightNumber, String newStatus){
        try{
            statement.execute("UPDATE " + TABLE_FLIGHTS + " SET " + COLUMN_FLIGHT_STATUS + " = '" + newStatus +
                    "' WHERE " + COLUMN_FLIGHT_NUMBER + " = '" + flightNumber + "';");

            System.out.println("Flight " + flightNumber.toUpperCase() + " status has been changed to " + newStatus);
        } catch (SQLException e) {
            System.out.println("Error occurred while updating flight status.");
        }

        try{
            conn.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error when closing connection and statement in update flight status.");
        }
    }

    public static void deleteFlight(String flightNumber) {
        try {
            statement.execute("DELETE FROM " + TABLE_FLIGHTS +
                    " WHERE " + COLUMN_FLIGHT_NUMBER + " = '" + flightNumber + "';");

            System.out.println("You have deleted flight " + flightNumber);
        } catch (SQLException e) {
            System.out.println("Error when trying to delete flight " + flightNumber);
            e.printStackTrace();
        }

        try {
            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error closing connection and statement in delete flight.");
            e.printStackTrace();
        }

    }

    public static void createFlight(String flightNumber, String destination, String departureDateAndTime,
                                     String arrivalDateAndTime, String flightStatus, int seatsAvailable, int ticketPrice) throws SQLException {

        statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_FLIGHTS +
                " (" + COLUMN_FLIGHT_NUMBER + " text, " +
                COLUMN_FLIGHT_DESTINATION + " text, " +
                COLUMN_DEPARTURE_DATETIME + " text, " +
                COLUMN_ARRIVAL_DATETIME + " text, " +
                COLUMN_FLIGHT_STATUS + " text, " +
                COLUMN_SEATS_AVAILABLE + " integer, " +
                COLUMN_TICKET_PRICE + " integer )");

        statement.execute("INSERT INTO " + TABLE_FLIGHTS + " (" + COLUMN_FLIGHT_NUMBER + ", " + COLUMN_FLIGHT_DESTINATION +
                ", " + COLUMN_DEPARTURE_DATETIME + ", " + COLUMN_ARRIVAL_DATETIME + ", " + COLUMN_FLIGHT_STATUS + ", " +
                COLUMN_SEATS_AVAILABLE + ", " + COLUMN_TICKET_PRICE + " ) " +
                "VALUES('" + flightNumber + "', '" + destination + "', '" + departureDateAndTime + "', '" +
                arrivalDateAndTime + "', '" + flightStatus + "', " + seatsAvailable + ", " + ticketPrice + ")");

        statement.close();
        conn.close();
    }

    public static void checkAvailableFlights() {
        //search by destination and/or date

        System.out.println("Search by flight destination and/or date:\n" +
                "Enter 'cancel' to return to previous menu\n" +
                "If you would like to search by just destination, enter destination first then hit 'enter' twice" +
                "and if you would like to search by just departure date, hit enter once and then enter date followed by 'enter'" +
                "Destination: \n" +
                "Departure Date: ");
        String destinationSearch = scanner.nextLine();
        String departDateSearch = scanner.nextLine();

        if(departDateSearch.equalsIgnoreCase("cancel") || destinationSearch.equalsIgnoreCase("cancel")) {
            Terminal.displayInitialMenu();
        }


        if (!destinationSearch.isBlank() && !departDateSearch.isBlank()) {
            System.out.println("Searching for:\n" +
                    "Destination: " + destinationSearch + " - Date: " + departDateSearch);

            try {
                Statement statement = conn.createStatement();
                ResultSet result = statement.executeQuery("SELECT * FROM " + TABLE_FLIGHTS + " WHERE " +
                        COLUMN_FLIGHT_DESTINATION + " LIKE '%" + destinationSearch + "%' AND " +
                                COLUMN_DEPARTURE_DATETIME + " LIKE '%" + departDateSearch + "%';");

                ResultSetMetaData resultData = result.getMetaData();
                //number of columns in result data to loop through for print
                int columns = resultData.getColumnCount();

                //iterate through columns and print each value
                System.out.println();
                for(int i = 1; i <= columns; i++) {
                    System.out.print(resultData.getColumnLabel(i) + " | ");
                }
                while(result.next()) {

                    System.out.println();
                    System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");

                    for(int i = 1; i <= columns; i++) {
                        System.out.print(" " + result.getString(i) + "    |   ");
                    }

                }
            } catch (SQLException e) {
                System.out.println("Error in searching for available flights with destination and date!");
                e.printStackTrace();
            }


        } else if (departDateSearch.isBlank() && destinationSearch.isBlank()) {
            System.out.println("Selections are empty\n" +
                    "Returning to menu...");


        } else if (destinationSearch.isBlank()) {
            System.out.println("Searching for:\n" +
                    "Date: " + departDateSearch);
            try {
                Statement statement = conn.createStatement();
                ResultSet result = statement.executeQuery("SELECT * FROM " + TABLE_FLIGHTS + " WHERE " +
                        COLUMN_DEPARTURE_DATETIME + " LIKE '%" + departDateSearch + "%';");

                ResultSetMetaData resultData = result.getMetaData();
                //number of columns in result data to loop through for print
                int columns = resultData.getColumnCount();

                //iterate through columns and print each value
                System.out.println();
                for(int i = 1; i <= columns; i++) {
                    System.out.print(resultData.getColumnLabel(i) + "  |  ");
                }
                while(result.next()) {

                    System.out.println();
                    System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");

                    for(int i = 1; i <= columns; i++) {
                        System.out.print(" " + result.getString(i) + "    |   ");
                    }


                }
            } catch (SQLException e) {
                System.out.println("Error in searching for available flights with destination and date!");
                e.printStackTrace();
            }


        } else if (departDateSearch.isBlank()) {
            System.out.println("Searching for:\n" +
                    "Destination: " + destinationSearch);

            try {
                Statement statement = conn.createStatement();
                ResultSet result = statement.executeQuery("SELECT * FROM " + TABLE_FLIGHTS + " WHERE " +
                        COLUMN_FLIGHT_DESTINATION + " LIKE '%" + destinationSearch + "%';");

                ResultSetMetaData resultData = result.getMetaData();
                //number of columns in result data to loop through for print
                int columns = resultData.getColumnCount();

                //iterate through columns and print each value
                System.out.println();
                for(int i = 1; i <= columns; i++) {
                    System.out.print(resultData.getColumnLabel(i) + "  |  ");
                }
                while(result.next()) {


                    System.out.println();
                    System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");

                    for(int i = 1; i <= columns; i++) {
                        System.out.print(" " + result.getString(i) + "    |   ");
                    }


                }
            } catch (SQLException e) {
                System.out.println("Error in searching for available flights with destination and date!");
                e.printStackTrace();
            } finally {
            }
        }

        try{
            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Erroe closing connection and statement in flight availability check");
            e.printStackTrace();
        }
    }

    /*

    Below are all the methods required for the creation of flight tickets, including
    getting ticket price, ticketStatus, departTime, departDate, seats available


     */

    public static int getTicketPrice(String flightNumber) {
        int ticketPrice = 0;
        try {

            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery("SELECT " + COLUMN_TICKET_PRICE + " FROM " + TABLE_FLIGHTS +
                    " WHERE " + COLUMN_FLIGHT_NUMBER + " = '" + flightNumber + "';");

            ticketPrice = result.getInt("TicketPrice");



        } catch (SQLException e) {
            System.out.println("Error retrieving flight info for ticket price.");
            e.printStackTrace();
        }

        return ticketPrice;
    }

    public static int getSeatsAvailable(String flightNumber) {
        int seatsAvailable = 0;
        try {

            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery("SELECT " + COLUMN_SEATS_AVAILABLE + " FROM " + TABLE_FLIGHTS +
                    " WHERE " + COLUMN_FLIGHT_NUMBER + " = '" + flightNumber + "';");

            seatsAvailable = result.getInt("SeatsAvailable");



        } catch (SQLException e) {
            System.out.println("Error retrieving flight info for ticket price.");
            e.printStackTrace();
        }

        return seatsAvailable;
    }
    }

