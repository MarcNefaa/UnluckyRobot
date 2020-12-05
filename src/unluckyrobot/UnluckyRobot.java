/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unluckyrobot;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Marc
 */
public class UnluckyRobot {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int totalScore = 300;
        int itrCount = 0;
        int reward;
        char direction;
        int x = 0;
        int y = 0;
        
        for (; itrCount < 20; itrCount++) {
            displayInfo(x, y, itrCount, totalScore);
            direction = inputDirection();
            
            if (doesExceed(x, y, direction)) {
                System.out.println("Exceed boundry, -2000 damage applied");
                totalScore -= 2000;
            }
            
            switch (direction) {
                case 'u':
                    y += 1;
                    totalScore -= 10;
                    break;
                case 'd':
                    y -= 1;
                    totalScore -= 50;
                    break;
                case 'r':
                    x += 1;
                    totalScore -= 50;
                    break;
                default:
                    x -= 1;
                    totalScore -= 50;
            }
            
            reward = reward();
            reward = punichOrMercy(direction, reward);
            totalScore += reward;
            
            System.out.println();
            
            if (isGameOver(x, y, totalScore, itrCount)) {
                evaluation(totalScore);
                break;
            }
        }
    }
    
    /**
     * To print the current x and y coordinate of the robot, the number of
     * iterations made so far and the total score.
     * @param x the x coordinate of the robot
     * @param y the y coordinate of the robot
     * @param itrCount the number of iterations made so far
     * @param totalScore the total score
     */
    public static void displayInfo(int x, int y, int itrCount, int totalScore) {
        System.out.printf("For point (X=%d, Y=%d) and iterations: %d the total "
                + "score is: %d\n", x, y, itrCount, totalScore);
    }
    
    /**
     * To check if the robot exceeds the grid limits
     * @param x the x coordinate of the robot
     * @param y the y coordinate of the robot
     * @param direction the direction the user wants the robot to move towards
     * @return if the robot exceeds the grid limits
     */
    public static boolean doesExceed(int x, int y, char direction) {
        if (x == 0 && direction == 'l') {
            return true;
        }
        else if (x == 4 && direction == 'r') {
            return true;
        }
        else if (y == 0 && direction == 'd') {
            return true;
        }
        else if (y == 4 && direction == 'u') {
            return true;
        }
        else {
            return false;
        }
    }
    
    /**
     * To see what reward or punishment the user will receive
     * @return the amount of points that will be added or deducted
     */
    public static int reward() {
        Random rand = new Random();
        // dice simulation
        int dice = rand.nextInt(6) + 1;
        
        switch (dice) {
            case 1:
                System.out.println("Dice 1, reward: -100");
                return -100;
            case 2:
                System.out.println("Dice 2, reward: -200");
                return -200;
            case 3:
                System.out.println("Dice 3, reward: -300");
                return -300;
            case 4:
                System.out.println("Dice 4, reward: 300");
                return 300;
            case 5:
                System.out.println("Dice 5, reward: 400");
                return 400;
            default:
                System.out.println("Dice 6, reward: 600");
                return 600;
        }
    }
    
    /**
     * To decide if the user will receive the punishment or not
     * @param direction the direction the user wants the robot to move towards
     * @param reward the reward the user should receive
     * @return either the points that will be deducted (punishment) or 0 (mercy)
     */
    public static int punichOrMercy(char direction, int reward) {
        Random rand = new Random();
        // coin flip simulation: 0 = tails & 1 = heads
        int coin = rand.nextInt(2);
        
        if (reward < 0 && direction == 'u') {
            if (coin == 0) {
                System.out.println("Coin: tail | Mercy the negative reward is removed");
                reward = 0;
            }
            else
                System.out.println("Coin: head | No mercy, the negative reward is applied");
        }
        return reward;
    }
    
    /**
     * To convert a string with 2 words to title case
     * @param str the original string
     * @return the string in title case
     */
    public static String toTitleCase(String str) {
        String word1 = str.substring(0, str.indexOf(" ")).toLowerCase();
        String word2 = str.substring(str.indexOf(" ") + 1).toLowerCase();
        
        return word1.substring(0, 1).toUpperCase() + word1.substring(1) + " " 
                + word2.substring(0, 1).toUpperCase() + word2.substring(1);
    }
    
    /**
     * To print a statement based on the value of the total score
     * @param totalScore a message telling the user if they won or lost
     */
    public static void evaluation(int totalScore) {
        Scanner console = new Scanner(System.in);
        // to get the person's name
        System.out.print("Please enter your name (only two words): ");
        String name = toTitleCase(console.nextLine());
        
        if (totalScore >= 2000) {
            System.out.printf("Victory! %s, your score is %d\n", name, totalScore);
        }
        else {
            System.out.printf("Mission failed! %s, your score is %d\n", name, totalScore);
        }
    }
    
    /**
     * To ask the user which direction they want the robot to move towards
     * @return the direction the robot will move towards
     */
    public static char inputDirection() {
        Scanner console = new Scanner(System.in);
        String validDirections = "udlr"; // all the possible directions
        String direction;
                
        do {
            System.out.print("Please input a valid direction: ");
            direction = console.next().toLowerCase();
        } while (!validDirections.contains(direction));
        
        return direction.charAt(0);
    }
    
    /**
     * To check if the game is over
     * @param x the x coordinate of the robot
     * @param y the y coordinate of the robot
     * @param totalScore the total score of the user
     * @param itrCount the number of iterations so far
     * @return if the game will end
     */
    public static boolean isGameOver(int x, int y, int totalScore, int itrCount) {
        if (y == 0 && x == 4 || y == 4 && x == 4)
            return true;
        if (itrCount == 20)
            return true;
        if (totalScore > 2000 || totalScore < -1000)
            return true;
        else
            return false;   
    }
}
