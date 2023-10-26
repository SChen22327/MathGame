import java.util.Scanner;
import java.util.Arrays;

public class MathGame {

    private Player player1;
    private Player player2;
    private Player player3;
    private Player currentPlayer;
    private Player winner;
    private int[] losers;
    private boolean gameOver;
    private Scanner scanner;

    // create MathGame object
    public MathGame(Player player1, Player player2, Player player3, Scanner scanner) {
        this.player1 = player1;
        this.player2 = player2;
        this.player3 = player3;
        this.scanner = scanner;
        losers = new int[2];
        currentPlayer = null; // will get assigned at start of game
        winner = null; // will get assigned when a Player wins
        gameOver = false;
    }

    // ------------ PUBLIC METHODS (to be used by client classes) ------------

    // returns winning Player; will be null if neither Player has won yet
    public Player getWinner() {
        return winner;
    }

    // plays a round of the math game
    public void playRound() {
        chooseStartingPlayer();  // this helper method (shown below) sets currentPlayer to either player1 or player2
        while (!gameOver) {
            printGameState();   // this helper method (shown below) prints the state of the Game
            System.out.println("Current player: " + currentPlayer.getName());
            boolean correct = askQuestion();  // this helper method (shown below) asks a question and returns T or F
            if (correct) {
                System.out.println("Correct!");
                currentPlayer.incrementScore();  // this increments the currentPlayer's score
                currentPlayer.incrementStreak();
                swapPlayers();  // this helper method (shown below) sets currentPlayer to the other Player
                Arrays.fill(losers, 0);
            } else {
                System.out.println("INCORRECT!");
                currentPlayer.resetStreak();
                determineWinner();
                if (losers[0] != 0 && losers[1] != 0) {
                    gameOver = true;
                } else {
                    swapPlayers();
                }
            }
        }
    }

    // prints the current scores of the two players
    private void printGameState() {
        System.out.println("--------------------------------------");
        System.out.println("Current Scores:");
        System.out.println(player1.getName() + ": " + player1.getScore());
        System.out.println(player2.getName() + ": " + player2.getScore());
        System.out.println(player3.getName() + ": " + player3.getScore());
        System.out.println("--------------------------------------");
    }

    // resets the game back to its starting state
    public void resetGame() {
        player1.reset(); // this method resets the player
        player2.reset();
        player3.reset();
        gameOver = false;
        currentPlayer = null;
        winner = null;
        Arrays.fill(losers, 0);
    }

    // ------------ PRIVATE HELPER METHODS (internal use only) ------------

    // randomly chooses one of the Player objects to be the currentPlayer
    private void chooseStartingPlayer() {
        int randNum = (int) (Math.random() * 3) + 1;
        if (randNum == 1) {
            currentPlayer = player1;
        } else if (randNum == 2) {
            currentPlayer = player2;
        } else {
            currentPlayer = player3;
        }
    }

    // asks a math question and returns true if the player answered correctly, false if not
    private boolean askQuestion() {
        int operation = (int) (Math.random() * 4) + 1;
        int num1 = (int) (Math.random() * 100) + 1;
        int num2;
        int correctAnswer;
        System.out.println("Type in your answer as an integer (/ is int division)");
        if (operation == 1) {
            num2 = (int) (Math.random() * 100) + 1;
            System.out.print(num1 + " + " + num2 + " = ");
            correctAnswer = num1 + num2;
        } else if (operation == 2) {
            num2 = (int) (Math.random() * 100) + 1;
            System.out.print(num1 + " - " + num2 + " = ");
            correctAnswer = num1 - num2;
        } else if (operation == 3) {
            num2 = (int) (Math.random() * 10) + 1;
            System.out.print(num1 + " * " + num2 + " = ");
            correctAnswer = num1 * num2;
        } else {  // option == 4
            num2 = (int) (Math.random() * 10) + 1;
            System.out.print(num1 + " / " + num2 + " = ");
            correctAnswer = num1 / num2;
        }

        int playerAnswer = scanner.nextInt(); // get player's answer using Scanner
        scanner.nextLine(); // clear text buffer after numeric scanner input

        if (playerAnswer == correctAnswer) {
            return true;
        } else {
            return false;
        }
    }

    // swaps the currentPlayer to the other player
    private void swapPlayers() {
        if (currentPlayer == player1) {
            currentPlayer = player2;
        } else if (currentPlayer == player2) {
            currentPlayer = player3;
        } else {
            currentPlayer = player1;
        }
    }

    // sets the winner when the game ends based on the player that missed the question
    private void determineWinner() {
        int lost;
        if (currentPlayer == player1) {
            lost = 1;
        }else if (currentPlayer == player2) {
            lost = 2;
        } else {
            lost = 3;
        }
        if (losers[0] == 0) {
            losers[0] = lost;
        } else {
            losers[1] = lost;
        }
        if (losers[0] == 1) {
            if (losers[1] == 2) {
                winner = player3;
            } else if (losers[1] == 3) {
                winner = player2;
            }
        } else if (losers[0] == 2) {
            if (losers[1] == 1) {
                winner = player3;
            } else if (losers[1] == 3) {
                winner = player1;
            }
        } else if (losers[0] == 3) {
            if (losers[1] == 1) {
                winner = player2;
            } else if (losers[1] == 2) {
                winner = player1;
            }
        }
    }
    public String leaderboard() {
        String order = "1. " + winner.getName();
        if (winner == player1) {
            if (player2.getScore() > player3.getScore()) {
                order += "\n2. " + player2.getName() + "\n3. " + player3.getName();
            } else if (player3.getScore() > player2.getScore()) {
                order += "\n2. " + player3.getName() + "\n3. " + player2.getName();
            } else {
                order += "\n2. " + player2.getName() + "\n2. " + player3.getName();
                order += "\n" + player2.getName() + " and " + player3.getName() + " tied for second!";
            }
        } else if (winner == player2) {
            if (player1.getScore() > player3.getScore()) {
                order += "\n2. " + player1.getName() + "\n3. " + player3.getName();
            } else if (player3.getScore() > player1.getScore()) {
                order += "\n2. " + player3.getName() + "\n3. " + player1.getName();
            } else {
                order += "\n2. " + player1.getName() + "\n2. " + player3.getName();
                order += "\n" + player1.getName() + " and " + player3.getName() + " tied for second!";
            }
        } else {
            if (player2.getScore() > player1.getScore()) {
                order += "\n2. " + player2.getName() + "\n3. " + player1.getName();
            } else if (player1.getScore() > player2.getScore()) {
                order += "\n2. " + player1.getName() + "\n3. " + player2.getName();
            } else {
                order += "\n2. " + player2.getName() + "\n2. " + player1.getName();
                order += "\n" + player2.getName() + " and " + player1.getName() + " tied for second!";
            }
        }
        return order;
    }
}