package com.nick;

import com.nick.databases.FlightDatabase;

public class Main {

    public static void main(String[] args) {
	// write your code here
//        Flight flightOne = new Flight(1300, 1800, "Bunich", 150, "11/20/2020");

        //testing admin menu and password entry
//        Terminal.displayInitialMenu();
//        Terminal.displayAdminMenu();
//        FlightDatabase.flightDatabase();
//        Terminal.adminCreateFlight();
//        String departDate = "10/1/2020";
//        StringBuilder dayCode = new StringBuilder();
//        if(!departDate.substring(5,6).equals("/")) {
//            dayCode.append("0");
//            dayCode.append(departDate.substring(3,4));
//        } else {
//            dayCode.append(departDate.substring(3,5));
//        }
//
//        System.out.println(dayCode);
//        String test = dateTest.substring(3,4);
//        System.out.println(test);

//        String dateTimeSplitTest = " 1:00 PM 10/30/2020";
//        String[] arrDateTimeSplitTest = dateTimeSplitTest.split(" ", 2);
//        String[] arrDateTimeSplitTest2 = dateTimeSplitTest.split(" ", -2);
//        String[] arrDateTimeSplitTest3 = dateTimeSplitTest.split(" ", 5);
//
//        for (String a : arrDateTimeSplitTest) {
//            System.out.println(a);
//        }
//        System.out.println("\n" +
//                "2\n" +
//                "");
//        for (String a : arrDateTimeSplitTest2) {
//            System.out.println(a);
//        }
//        System.out.println("\n" +
//                "3\n" +
//                "");
//        for (String a : arrDateTimeSplitTest3) {
//            System.out.println(a);
//        }
//
//        String firstPart = arrDateTimeSplitTest3[1];
//        String secondPart = arrDateTimeSplitTest3[2];
//        String date = arrDateTimeSplitTest3[3];
//
//        System.out.println(firstPart + " " + secondPart);
//        System.out.println(date);


        String flightNumber = "NE801331";
        int seats = FlightDatabase.getSeatsAvailable(flightNumber);
        String flightStatus = FlightDatabase.getFlightStatus(flightNumber);
        int ticketPrice = FlightDatabase.getTicketPrice(flightNumber);
        String departTime = FlightDatabase.getDepartTime(flightNumber);
        String departDate = FlightDatabase.getDepartDate(flightNumber);
        System.out.println(departTime + " " + departDate);
        System.out.println(seats);
        System.out.println(flightStatus);
        System.out.println(ticketPrice);


//
//        String choice = "a4";
//        char choiceRow = choice.charAt(0);
//        char choiceCol = choice.charAt(1);
//
//        System.out.println(choiceRow + " " + choiceCol);
    }
}
