package com.nick;

/*
    class will be used to create a log of all activity including flight creation and deletion, ticket purchasing,
    status updates, and any other changes.
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogFile {
    private static File logFile = new File("flightReservationLog.txt");
    private static DateFormat df = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
    private static Date date = new Date();
    private static String dateOfLog = df.format(date) + ": ";
    private static String logEntry;

    /*
    use dateOfLog at the beginning of log entries
     */

    //add log for flight creation in admin menu
    public static void addLogCreateFlight(String flightNumber) {
        logEntry = dateOfLog + "Admin created new flight, flight number: " + flightNumber;
        try {
            FileWriter fw = new FileWriter(logFile.getName(), true);
            BufferedWriter writer = new BufferedWriter(fw);
            writer.write(logEntry + System.lineSeparator() +
                    "-----------------------" + System.lineSeparator());
            System.out.println("created log entry ");
            writer.close();
        } catch (IOException e) {
            System.out.println("\n failed to create log entry \n");
            e.printStackTrace();
        }
    }

    public static void addLogTicketPurchased(int ticketNumber, String flightNumber) {
        logEntry = dateOfLog + "Ticket purchased for flight " + flightNumber + ", ticket number: " + ticketNumber;
        try {
            FileWriter fw = new FileWriter(logFile.getName(), true);
            BufferedWriter writer = new BufferedWriter(fw);
            writer.write(logEntry + System.lineSeparator() +
                    "-----------------------" + System.lineSeparator());
            System.out.println("created log entry ");
            writer.close();
        } catch (IOException e) {
            System.out.println("\n failed to create log entry \n");
            e.printStackTrace();
        }
    }

}
