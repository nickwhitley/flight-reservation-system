package com.nick;

import com.nick.databases.FlightDatabase;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Terminal {
    public static Scanner scanner = new Scanner(System.in);
    private static String adminPassword = "X4juKYY76lgM";
    private static String flightStatus;
    private static Boolean adminPasswordEntered = false;
    //used to check if proper menu is being shown
    private static Boolean cameFromPurchaseMenu = false;

    public static void displayInitialMenu() {
        adminPasswordEntered = false;
        System.out.println("Welcome to the flight reservation system\n" +
                "Please choose an option below:\n" +
                "1. Check ticket and flight status.\n" +
                "2. Check available flights.\n" +
                "3. Purchase ticket.\n" +
                "4. Cancel ticket.\n" +
                "5. Admin login.\n" +
                "Enter choice here: ");

        try {
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    getTicketAndFlightStatus();
                    break;
                case 2:
                    availableFlights();
                    break;
                case 3:
                    cameFromPurchaseMenu = false;
                    purchaseTicket();
                    break;
                case 4:
                    cancelTicket();
                    break;
                case 5:
                    passwordCheck();
                    break;
                default:
                    System.out.println("Incorrect choice!\n" +
                            "Enter again.");
                    displayInitialMenu();
                    scanner.next();
                    break;
            }
        } catch (Exception e) {
            scanner.next();
            System.out.println("Wrong inuput");
            displayInitialMenu();
            scanner.next();
        }
    }



    public static void passwordCheck() {
        //variable for admin password
        String passEntry = null;

        if(!adminPasswordEntered) {
            System.out.println("Please enter admin password: ");
            passEntry = scanner.next();
            if(passEntry.equals(adminPassword)) {
                adminPasswordEntered = true;
                displayAdminMenu();
            } else {
                System.out.println("Entered incorrect password, try again.");
                passwordCheck();
            }
        } else if(adminPasswordEntered) {
            displayAdminMenu();
        }
    }

    public static void displayAdminMenu() {
        System.out.println("Welcome to the admin terminal.\n" +
                    "What would you like to do today?\n" +
                    "1. Create new flight.\n" +
                    "2. Update ticket status.\n" +
                    "3. Update flight status.\n" +
                    "4. Delete flight. \n" +
                    "5. Log out of admin console.");

        System.out.println("Please enter number of your choice.");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                adminCreateFlight();
                break;
            case 2:
                updateTicketStatus();
                break;
            case 3:
                updateFlightStatus();
                break;
            case 4:
                deleteFlight();
                break;
            default:
                displayInitialMenu();
                break;
        }
    }

    public static void displayPurchaseMenu() {

        adminPasswordEntered = false;
        System.out.println("Please choose an option below:\n" +
                "1. Check ticket and flight status.\n" +
                "2. Check available flights.\n" +
                "3. Purchase ticket.\n" +
                "4. Cancel ticket.\n" +
                "5. Admin login.\n" +
                "Enter choice here: ");

        try {
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    getTicketAndFlightStatus();
                    break;
                case 2:
                    availableFlights();
                    break;
                case 3:
                    cameFromPurchaseMenu = true;
                    purchaseTicket();
                    break;
                case 4:
                    cancelTicket();
                    break;
                case 5:
                    passwordCheck();
                    break;
                default:
                    System.out.println("Incorrect choice!\n" +
                            "Enter again.");
                    displayInitialMenu();
                    scanner.next();
                    break;
            }
        } catch (Exception e) {
            scanner.next();
            System.out.println("Wrong inuput");
            displayInitialMenu();
            scanner.next();
        }
    }

    public static void availableFlights() {
        /*
        Print out available flights along with menu without the "check available flights" option
        Pull flights from a DB and display for user
         */
        FlightDatabase.checkAvailableFlights();
        System.out.println(" \n" +
                "  ");
        displayPurchaseMenu();


    }

    public static void getTicketAndFlightStatus() {
        //display form to fill out to search for ticket.
        //search by name or ticket number
        //display reserved status, cancelled status, or expired ticket
        //display date of purchase and flight number
        //display general ticket info as well
        System.out.println("getting ticket status");

    }

    public static void purchaseTicket() {
        /*
        user can purchase ticket
        user will have to enter email address for confirmation email
        along with email containing their ticket
        when ticket gets purchased update seats available on flight db
         */

        String flightNumber = null;
        int numOfTickets = 0;

        if(!cameFromPurchaseMenu) {
            System.out.println("check available flights first.");
            availableFlights();
        } else if(cameFromPurchaseMenu) {
            System.out.println("Enter purchase info");
            System.out.println("Please enter the flight number for your preferred flight:");
            scanner.nextLine();
            flightNumber = scanner.nextLine();
            System.out.println("Choice: " + flightNumber);
            System.out.println("How many tickets are you wanting to purchase?\n" +
                    "Enter amount: ");
            numOfTickets = scanner.nextInt();

            //get seats available and if none are available have ana option to either choose different flight or
            //notify customer when seats become available
            int seatsAvailable = FlightDatabase.getSeatsAvailable(flightNumber.toUpperCase());

            if(seatsAvailable < numOfTickets) {
                System.out.println("Sorry, there aren't enough available seats for you!\n" +
                        "We can notify you when some become available for this flight.\n" +
                        "Please choose an option below.\n" +
                        "1. Choose different flight.\n" +
                        "2. Notify me.\n" +
                        "3. Back to menu.");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        purchaseTicket();
                        break;
                    case 2:
//                        notifyCustomer();
                        break;
                    case 3:
                        displayInitialMenu();
                        break;
                    default:
                        System.out.println("Entered incorrect choice, returning to initial menu...");
                        displayInitialMenu();
                        break;
                }

            } else {
                String customerName;
                String customerEmail;

                System.out.println("Looks like there are enough seats available.\n" +
                        "Please enter the following information to continue your purchase:\n" +
                        "If at any point you would like to cancel your purchase, please enter 'cancel'");
                System.out.println("Enter your full name: ");
                scanner.nextLine();
                //need to implement safeguard against customers only entering first name
                customerName = scanner.nextLine();
                System.out.println("Enter your email: ");
                scanner.nextLine();
                customerEmail = scanner.nextLine();
                System.out.println("Enter preferred seat number:");
                scanner.nextLine();
                String seatNumber = scanner.nextLine();
                //SEAT NUMBER IS TEMPORARY, UNTIL I IMPLEMENT METHOD FOR CHOOSING SEAT IN FLIGHT!!!!!!!!!!!
                Ticket ticket = new Ticket(customerName, customerEmail, flightNumber, seatNumber);

            }

        }

    }

    public static void cancelTicket() {
        System.out.println("Cancel ticket screen");

    }

    public static void updateTicketStatus() {

    }

    public static void updateFlightStatus() {
        String flightNumber;
        String newStatus;

        System.out.println("Updating flight status:\n" +
                "Please enter the flight number of the flight you would like to update the status for:");
        scanner.nextLine();
        flightNumber = scanner.nextLine();
        System.out.println("The current status of flight " + flightNumber.toUpperCase() + " is:");
        FlightDatabase.getFlightStatus(flightNumber.toUpperCase());

        System.out.println("What would you like to change the flight status to?\n" +
                "1. On Time\n" +
                "2. Delayed\n" +
                "3. Boarding\n" +
                "4. Departed\n" +
                "5. In Air\n" +
                "6. Arrived\n" +
                "7. Cancelled\n" +
                "8. Past Flight\n" +
                "Enter '9' if you would to cancel.");

        //use switch statement to handle choice
        int choice = scanner.nextInt();


        switch (choice) {
            case 1:
                newStatus = "On Time";
                break;
            case 2:
                newStatus = "Delayed";
                break;
            case 3:
                newStatus = "Boarding";
                break;
            case 4:
                newStatus = "Departed";
                break;
            case 5:
                newStatus = "In Air";
                break;
            case 6:
                newStatus = "Arrived";
                break;
            case 7:
                newStatus = "Cancelled";
                break;
            case 8:
                newStatus = "Past Flight";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + choice);
        }

        FlightDatabase.updateFlightStatus(flightNumber.toUpperCase(), newStatus);
        displayAdminMenu();

    }



    public static void deleteFlight() {
        System.out.println("Enter the flight number for the flight you want to be deleted or enter 'cancel' to go back to admin console. ");
        scanner.nextLine();
        String choice = scanner.nextLine();
        if(choice.equalsIgnoreCase("cancel")) {
            System.out.println("Going back to admin terminal.");
            displayAdminMenu();
        } else {
            String flightNumber = choice.toUpperCase();
            FlightDatabase.deleteFlight(flightNumber);
        }
        System.out.println("Going back to admin console.");
        displayAdminMenu();
    }


    public static void adminCreateFlight() {

        System.out.println("Create Flight: \n" +
                "Please enter information below.");
        System.out.println("Depart Time: (24 hour clock)");
        int departTimeInput = scanner.nextInt();
        System.out.println("Depart Date: ");
        String departDateInput = scanner.next();
        System.out.println("Arrival Time: (24 hour clock) ");
        int arrivalTimeInput = scanner.nextInt();
        System.out.println("Arrival Date: ");
        String arrivalDateInput = scanner.next();
        System.out.println("Destination: ");
        scanner.nextLine();
        String destinationInput = scanner.nextLine();
        System.out.println("Number of Seats: ");
        int numOfSeatsInput = scanner.nextInt();
        System.out.println("Ticket Price: ");
        int ticketPriceInput = scanner.nextInt();

        //testing if program got past entry points
        System.out.println("Passed inputs");

        //time is in military time for flight code so we need to convert
        String departTimeConverted;
        StringBuilder finalDepartTimeDate = new StringBuilder();
        String arrivalTimeConverted;
        StringBuilder finalArrivalTimeDate = new StringBuilder();
        if (departTimeInput > 1259) {
            departTimeConverted = String.valueOf(departTimeInput-1200);
            finalDepartTimeDate.append(departTimeConverted.substring(0,1));
            finalDepartTimeDate.append(":");
            finalDepartTimeDate.append(departTimeConverted.substring(1,3));
            finalDepartTimeDate.append(" PM");
        } else {
            departTimeConverted = String.valueOf(departTimeInput);
            finalDepartTimeDate.append(departTimeConverted.substring(0,1));
            finalDepartTimeDate.append(":");
            finalDepartTimeDate.append(departTimeConverted.substring(1,3));
            finalDepartTimeDate.append(" AM");
        }
        if (arrivalTimeInput > 1259) {
            arrivalTimeConverted = String.valueOf(arrivalTimeInput-1200);
            finalArrivalTimeDate.append(arrivalTimeConverted.substring(0,1));
            finalArrivalTimeDate.append(":");
            finalArrivalTimeDate.append(arrivalTimeConverted.substring(1,3));
            finalArrivalTimeDate.append(" PM");
        } else {
            arrivalTimeConverted = String.valueOf(departTimeInput);
            finalArrivalTimeDate.append(arrivalTimeConverted.substring(1));
            finalArrivalTimeDate.append(":");
            finalArrivalTimeDate.append(arrivalTimeConverted.substring(1,3));
            finalArrivalTimeDate.append(" AM");
        }

        //add depart and arrive dates to String
        finalDepartTimeDate.append(" " + departDateInput);
        finalArrivalTimeDate.append(" " + arrivalDateInput);

        //test to see if program gets passes time and date conversion
        System.out.println("Got passed time conversion.");

        //create last of code based on date
        StringBuilder dayCode = new StringBuilder();
        if(!departDateInput.substring(5,6).equals("/")) {
            dayCode.append("0");
            dayCode.append(departDateInput.substring(3,4));
        } else {
            dayCode.append(departDateInput.substring(3,5));
        }




        int departTime = departTimeInput;
        String departDate = departDateInput;
        int arrivalTime = arrivalTimeInput;
        String arrivalDate = arrivalDateInput;
        String destination = destinationInput;
        int numOfSeats = numOfSeatsInput;

        flightStatus = "On Time";


        System.out.println("Generating flight code");
        Flight flight = new Flight(departTime,departDate,arrivalTime,arrivalDate,destination,numOfSeats, dayCode);
        String flightNumber = flight.generateFlightCode(dayCode);

        System.out.println("Trying to set flight in system");
        try {
            FlightDatabase.createFlight(flightNumber, destination, finalDepartTimeDate.toString(), finalArrivalTimeDate.toString(), flightStatus,
                    numOfSeats, ticketPriceInput);
            System.out.println("Successfully Created flight!");
        } catch (SQLException e) {
            System.out.println("Error when creating flight");
            e.printStackTrace();
        }

        displayAdminMenu();
    }
}
