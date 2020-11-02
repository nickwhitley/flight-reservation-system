package com.nick;

import java.util.Date;

public class Flight {
    private int departTime;
    private int arrivalTime;
    private String departDate;
    private String arrivalDate;
    private String destination;
    private String flightCode;
    //used for creating seat array.
    int numOfSeats;
    int numOfCols;
    int numOfRows;
    private String[][] seatsArray;
    String[] alphabet = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r"
            , "s", "t", "u", "v", "w", "x", "y", "z", "aa", "bb", "cc", "dd", "ee", "ff", "gg", "hh", "ii", "jj", "kk", "ll", "mm", "nn", "oo", "pp"};
    int alphabetPos;


    public Flight(int departTime, String departDate, int arrivalTime, String arrivalDate, String destination, int numOfSeats, StringBuilder dayCode){

        this.departTime = departTime;
        this.arrivalTime = arrivalTime;
        this.destination = destination;
        this.flightCode = generateFlightCode(dayCode);
        this.numOfSeats = numOfSeats;
        numOfCols = this.getNumOfSeatColumns();
        numOfRows = this.getNumOfSeatRows();
        this.departDate = departDate;
        this.arrivalDate = arrivalDate;


    }

    public String generateFlightCode(StringBuilder dayCode) {
        //check if day is two characters or one and adding '0' if one character

        /*
        generate a 8 character code with the first 2 characters being involved with the destination and the last
        six being related to the departure time and date and arrival times.
        */
        StringBuilder code = new StringBuilder();
        String destCode = destination.substring(0,2);
        code.append(destCode.toUpperCase());
        String depTime = String.valueOf(departTime);
        code.append(depTime.substring(0,2));
        String arrTime = String.valueOf(arrivalTime);
        code.append(arrTime.substring(0,2));
        code.append(dayCode);

        return String.valueOf(code);
    }

    //methods below will be used to display models for seat selection feature.

    public int getNumOfSeatColumns() {
        if(numOfSeats < 100) {
            numOfCols = 4;
        } else {
            numOfCols = 6;
        }

        return numOfCols;
    }

    public int getNumOfSeatRows() {
        numOfRows = numOfSeats / numOfCols;
        return numOfRows;
    }

    public String[][] createSeatsArray() {
        seatsArray = new String[numOfRows][numOfCols];
        alphabetPos = 0;
        try {
            for (int row = 0; row < numOfRows; row++) {
                for (int col = 1; col <= numOfCols; col++) {
                    String columnToString = String.valueOf(col);
                    seatsArray[row][col - 1] = alphabet[alphabetPos] + columnToString;
                }
                alphabetPos++;
            }
        }catch (Exception e) {
            System.out.println("Error creating seats array.");
            e.getMessage();
        }

        return seatsArray;
    }

    public void reserveSeat(String choice){
        boolean seatReserved = false;
        int rowReference = 0;
        int colReference = 0 ;
        char choiceRow = choice.charAt(0);
        char choiceCol = choice.charAt(1);
        try {
            for (int col = 0; col < numOfCols; col++) {
                for (int row = 0; row < numOfRows; row++) {
                    if (seatsArray[row][col].equalsIgnoreCase(choice)) {
                        seatsArray[row][col] = "XX";
                        seatReserved = true;
                        System.out.println("Seat " + choice + " has been reserved.");
                        rowReference = row;
                        colReference = col;
                        return;
                    } else if (seatsArray[row][col].equalsIgnoreCase("xx")) {
                        System.out.println("Seat already reserved.");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error reserving seat");
            e.getMessage();
        }
    }

    public void printSeatsArray() {
        try {
            for (int i = 0; i < numOfRows; i++) {
                for (int j = 1; j <= numOfCols; j++) {
                    if (numOfCols == 4) {
                        if (j == 2) {
                            System.out.print(seatsArray[i][j - 1] + "       ");
                        } else {
                            System.out.print(seatsArray[i][j - 1] + " ");
                        }
                    } else {
                        if (j == 3) {
                            System.out.print(seatsArray[i][j - 1] + "       ");
                        } else {
                            System.out.print(seatsArray[i][j - 1] + " ");
                        }
                    }
                }
                System.out.println("\n");
            }
        } catch (Exception e) {
            System.out.println("Error when printing seats array.");
            e.getMessage();
        }

    }


    public int getDepartTime() {
        return departTime;
    }

    public void setDepartTime(int departTime) {
        this.departTime = departTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
