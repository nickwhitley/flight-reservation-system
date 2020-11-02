package com.nick;

/*

class will be used to display models for seat selection feature.

*/

enum Model {
    BOEING_737_800(162), BOEING_737_700(143), AIRBUS_A320(150), AIRBUS_A321(185), BOEING_757_200(200),
    BOMBARDIER_CRJ200(50), AIRBUS_A319(124), BOEING_737_900ER(178), EMBRAER_E175(76);

    private int seats;
    private int numOfCols;
    private int numOfRows;

    Model(int Seats) {
        this.seats = Seats;
    }

    public int numOfSeats() {
        return this.seats;
    }

    public int numOfColumns() {
        if (seats < 100) {
            numOfCols = 4;
        } else
            numOfCols = 6;

        return numOfCols;
    }

    public int numOfRows() {
        numOfRows = seats / this.numOfCols;
        return numOfRows;
    }
}

public class Plane {

    Model model;
    int numOfSeats = 50;
    int numOfRows;
    int numOfColumns = 4;
    String[][] seatArray;


    public Plane(Model model) {
        this.model = model;
//        this.createSeatArray();
        this.numOfSeats = model.numOfSeats();
        this.numOfColumns = model.numOfColumns();
        this.numOfRows = model.numOfRows();
    }

    public String[][] createSeatArray() {
        String[][] seatArray = new String[numOfRows][numOfColumns];
        try {
            String[] alphabet = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r"
                    , "s", "t", "u", "v", "w", "x", "y", "z", "aa", "bb", "cc", "dd", "ee", "ff", "gg", "hh", "ii", "jj", "kk", "ll", "mm", "nn", "oo", "pp"};
            int alphabetPos = 0;


            for (int i = 0; i < numOfRows; i++) {
                for (int j = 1; j < numOfColumns; i++) {
                    String columnToString = String.valueOf(j);
                    seatArray[i][j - 1] = alphabet[alphabetPos] + columnToString;
                }
                alphabetPos++;
            }
        } catch (Exception e) {
            System.out.println("Could not create seat array!");

        }
        return seatArray;
    }

    public void reserveSeat() {

    }

    public void printSeats() {
        try{
        for (int i = 0; i < this.numOfRows; i++) {
            for (int j = 1; j <= this.numOfColumns; j++) {
                if (this.numOfColumns == 4) {
                    if (j == 2) {
                        System.out.print(seatArray[i][j-1] + "       ");
                    } else {
                        System.out.print(seatArray[i][j-1] + " ");
                    }
                } else {
                    if (j == 3) {
                        System.out.print(seatArray[i][j-1] + "       ");
                    } else {
                        System.out.print(seatArray[i][j-1] + " ");
                    }
                }
            }
            System.out.println("\n");

        }
    } catch (Exception e) {
            System.out.println("Could not print seats!");
        }
    }
}
