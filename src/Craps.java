// Fig. 6.8: Craps.java
// Craps class simulates the dice game craps.

import java.security.SecureRandom;
import javax.swing.JFrame;
import javax.swing.JTextArea;

/**
 * Craps class to simulate playing dice craps game. Includes a main method
 * to load a GUI to play craps.
 * @author <a href="mailto:alexandre.d.paquette@gmail.com">Alexandre Paquette</a>
 * @date 04/21/2022
 */
public class Craps{
    private static final SecureRandom randomNumbers = new SecureRandom();// create secure random number generator for use in method rollDice
    //variables to play game
    private static Status gameStatus;
    private static int myPoint;
    private static int rollCount;
    private static double balance = 1000;
    
    static enum Status{// enum type with constants that represent the game status
        CONTINUE, WON, LOST
    };

    private static final int LOST = 0;
    private static final int WON = 1;
    private static final int GAMES = 1000;

    // constants that represent common rolls of the dice
    static final int SNAKE_EYES = 2;
    static final int TREY = 3;
    static final int SEVEN = 7;
    static final int YO_LEVEN = 11;
    static final int BOX_CARS = 12;
    
    /**
     * Method to roll two dice and return the sum. Takes a JTextArea to write 
     * results to.
     * @param content JTextArea to write rolls to
     * @return Sum of the two dice rolls
     */
    public static int rollDice(JTextArea content){// roll dice, calculate sum and display results
        rollCount++;
        
        // pick random die values
        int die1 = 1 + randomNumbers.nextInt(6); // first die roll
        int die2 = 1 + randomNumbers.nextInt(6); // second die roll

        int sum = die1 + die2; // sum of die values
        
        //display rolls to provided JTextArea
        content.append("Player rolled " + die1 + " + " + die2 + " = " + sum + "\n");
        
        return sum;
    }

    /**
     * Reset game to default by setting roll count to zero and balance to 1000
     */
    public static void resetGame(){
        rollCount = 0;
        balance = 1000;
    }

    /**
     * Return the current game status. Can be WON, LOST, or CONTINUE
     * @return Current game status
     */
    public static Status getGameStatus() {
        return gameStatus;
    }

    /**
     * Set game status to provided status. 
     * @param gameStatus Status to set the game to
     */
    public static void setGameStatus(Status gameStatus) {
        Craps.gameStatus = gameStatus;
    }

    /**
     * Return current point to win
     * @return Point to win
     */
    public static int getMyPoint() {
        return myPoint;
    }

    /**
     * Set point to win
     * @param myPoint Point to win
     */
    public static void setMyPoint(int myPoint) {
        Craps.myPoint = myPoint;
    }

    /**
     * Get roll count
     * @return Number of rolls so far
     */
    public static int getRollCount() {
        return rollCount;
    }

    /**
     * Reset roll count to zero
     * 
     */
    public static void resetRollCount() {
        rollCount = 0;
    }

    /**
     * Return current balance
     * @return Player cash balance
     */
    public static double getBalance() {
        return balance;
    }
    
    /**
     * Add a given amount to the craps balance.
     * @param balance Amount to add. Provide a negative value to subtract
     */
    public static void addToBalance(double balance){
        Craps.balance += balance;
    }
    
    /**
     * Executable method to start the program
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // GUI settings
        MasterFrame app = new MasterFrame();
        app.setSize(250, 320);
        app.setLocation(800, 400);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
    }
}

/**
 * ************************************************************************
 * Modified Jim Ronholm - Jan/Feb 2020
 * Modified Alex Paquette - April 2022
 * Original Copyright below
 * (C) Copyright 1992-2018 by Deitel & Associates, Inc. and * Pearson Education,
 * Inc. All Rights Reserved. * * DISCLAIMER: The authors and publisher of this
 * book have used their * best efforts in preparing the book. These efforts
 * include the * development, research, and testing of the theories and programs
 * * to determine their effectiveness. The authors and publisher make * no
 * warranty of any kind, expressed or implied, with regard to these * programs
 * or to the documentation contained in these books. The authors * and publisher
 * shall not be liable in any event for incidental or * consequential damages in
 * connection with, or arising out of, the * furnishing, performance, or use of
 * these programs. *
 * ***********************************************************************
 */
