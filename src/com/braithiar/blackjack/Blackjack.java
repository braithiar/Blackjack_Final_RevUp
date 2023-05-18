package com.braithiar.blackjack;

import java.util.Scanner;

class Blackjack {
  public static void main(String[] args) {
    boolean displayMenu = true;
    Scanner input = new Scanner(System.in);
    
    printBanner();
    
    do {
      String option = getGameOption(input);

      switch (option) {
        case "1":
          playBlackjack(input);
          break;
        case "2":
          printRules();
          break;
        case "3":
          displayMenu = false;
          break;
      }
      
    } while (displayMenu);

    input.close();
  }

  /**
    Displays game menu and solicits player for input. Player can start the game, view the rules, or exit the game. 

    @param input the input <code>Scanner</code> to be used in the game.
    @return the string representing the player's choice.
  */
  private static String getGameOption(Scanner input) {
    String userOption = "";
    boolean invalid = true;
    
    do {      
      System.out.println("  \nSelect one of the following options: " + 
                          "1) Start Game\t 2) Rule\t 3) Exit");

      System.out.print("-->");      
       
      userOption = input.nextLine();      

      if (userOption.matches("1|2|3")) {
        invalid = false;
      } else {
        System.out.println("\nPlease select option 1), 2), or 3). Please, try again...\n");
      }
    } while (invalid);

    return userOption;
  }

  /**
    Prints the BLACKJACK game banner when the game starts.
  */
  private static void printBanner() {
    System.out.println(
      "\n     _______________\n\n" +
      "        BLACKJACK   \n" +
      "     _______________\n" +
      "\n\nLet's play some Blackjack!\n"
      
    );
  }

  /**
    Prints the rules for this version of blackjack.
  */
  private static void printRules() {
    System.out.println("\n\n     ___________\n\n" + 
                       "        RULES   \n     ___________\n\n" + 
                       "\tYou will play against the dealer. Both of you will be dealt " +
                      "2 cards; yours will be face up. While only one of the dealer's " +
                      "cards will be face up for you to see.\n\n" +
                      "\tThe goal of the game is to get as close to a score of 21" +
                      " without going over. You will go first. You can either hit, " +
                      "to draw another card, or stay, to keep your current hand. " +
                      "Then the dealer will then take their turn. " +
                       "The dealer will always stay on a 17 or great" +
                       "er. Going over 21 is an instant loss for you" +
                       "or the dealer.\n\n" +
                      "\tCard values are as follows:\n" +
                      "\t\t*All face cards are 10 points\n" +
                      "\t\t*Aces are worth either 1 or 11 points\n" +
                      "\t\t*All other cards are worth their value in points");               
  }

  /**
    Game logic for a game of blackjack.

    @param input the input <code>Scanner</code> to be used for the game.
  */
  private static void playBlackjack(Scanner input) {
    Player[] players = {
      new Player(true, "Dealer"), new Player(false, "Player")
    };
    boolean playing = true;
    
    do {
      //Make new deck and shuffle.
      Deck deck = new Deck();
      
      deck.shuffle();

      //Clear the players' hands and scores if this isn't the first game.
      if (players[0].getHandSize() > 0 || players[1].getHandSize() > 0) {
        for (Player p : players) {
          p.resetHand();
          p.resetScore();
        }        
      }
      
      //Deal
      deck.deal(players, 2);

      //Player's turn
      playerTurn(players, deck, input);

      //Dealer's turn
      try {
        dealerTurn(players, deck, input);
      } catch (InterruptedException ie) {
        ie.printStackTrace();
      }

      //Determine winner
      playing = settleGame(players, input);
      
    } while (playing);
  }

  /**
    Returns if the player has bust or not.

    @param p the <code>Player</code> who is being checked.
    @return true if the player has exceeded 21.
  */
  private static boolean isBust(Player p) {
    if (p.getScore() > 21) {
      return true;
    }

    return false;
  }

  /**
    Gets player input to hit or stay on their turn.

    @param input the input <code>Scanner</code> to be used.
    @return the string representing the player's choice.
  */
  private static String getPlayerInput(Scanner input) {
    String in = "";    
    
    System.out.println("\n\n(H)it or (S)tay?\n");
    System.out.print("-->");
        
    in = input.nextLine();  
    
    return in;
  }

  /**
    Validates that the player has entered hit or stay.

    @param in the player's input that needs validating.
    @return true if the player's input is valid.
  */
  private static boolean validatePlayerInput(String in) {
    switch (in.toLowerCase()) {
      case "h":
      case "hit":
         return true;
      case "s": 
      case "stay":
         return true;
      default:
         System.out.println("\nThat was not one of the options. " + 
                            "Please, try again...");
         return false;
    }
  }

  /**
    Logic for the player's turn.

    @param players the array of players in the game.
    @param deck the deck being used for the game.
    @param input the input <code>Scanner</code> to be used.
  */
  private static void playerTurn(Player[] players, Deck deck, Scanner input) {
   //Player loop
    boolean playerTurn = true;
    
    do {
      printHands(players);

      String playerInput = getPlayerInput(input);

      if (validatePlayerInput(playerInput)) {
        switch(playerInput.toLowerCase()) {
          case "h":
          case "hit":
            System.out.println("\n" + players[1].getName() + " hits!");
            players[1].addToHand(deck.draw());
            players[1].recalculateScore(); //In case value of Ace changed.
            if (isBust(players[1])) {
              playerTurn = false;
              System.out.println("\nUh oh!\n");
            }
            break;
          case "s":
          case "stay":
            System.out.println("\n" + players[1].getName() + " stays!");
            playerTurn = false;
            break;
        }
      }
    } while (playerTurn);
  }

  /**
    Game logic for the dealer's turn. Stays on 17 or higher.

    @param players the array of players in the game.
    @param deck the deck being used for the game.
    @param input the input <code>Scanner</code> to be used.
    @throws InterruptedException if Thread.sleep() is interrupted.
  */
  private static void dealerTurn(Player[] players, Deck deck, Scanner input) throws InterruptedException {
    boolean dealerTurn = true;
    //Get out of method if player already lost.
    if (isBust(players[1])) {
      printHands(players);
      return;
    }
    
    //Reveal dealer's hidden card
    System.out.println("\nDealer reveals his card...\n");
    players[0].dealerReveal();
    players[0].recalculateScore(); //In case value of Ace changed

    Thread.sleep(2000);
    
    while (dealerTurn && !isBust(players[0]) && !isBust(players[1])) {
      int count = 0;

      printHands(players);

      Thread.sleep(2000);
      
      if (players[0].getScore() <= 16) {
        if (count == 1) {
          System.out.println("\n" + players[0].getName() + " wants another card!\n");
        } else if (count > 1) {
          System.out.println("\n" + players[0].getName() + "hits, again!\n");
        } else {
          System.out.println("\n" + players[0].getName() + " hits!");
        }
        players[0].addToHand(deck.draw());
        players[0].recalculateScore();
        ++count;
      } else {
        System.out.println("\nDealer stays!");
        dealerTurn = false;
      }

      Thread.sleep(2000);
    }
  }

  /**
    Determines who has won or if there was a draw. 

    @param players the array of players in the game.
    @param input the input <code>Scanner</code> to be used.
  */
  private static boolean settleGame(Player[] players, Scanner input) {
    if (isBust(players[1])) {
      System.out.println("\nBust! " + players[1].getName() + " Loses!\n");
      players[0].addWin();
    } else if (isBust(players[0])) {
      System.out.println("\nBust! " + players[0].getName() + " Loses!\n");
      players[1].addWin();
    } else if (players[0].getScore() > players[1].getScore()) {
      System.out.println("\n" + players[0].getName() + " Wins!\n");
      players[0].addWin();
    } else if (players[0].getScore() < players[1].getScore()) {
      System.out.println("\n" + players[1].getName() + " Wins!\n");
      players[1].addWin();
    } else if (players[0].getScore() == players[1].getScore()) {
      System.out.println("\nIt's a draw!\n");
    } else {
      System.out.println("...Something went wrong...");
    }

    return getPlayAgain(input);
  }

  /**
    Asks the player if they would like to play another game and validates input.

    @param input the input <code>Scanner</code> to be used.
  */
  private static boolean getPlayAgain(Scanner input) {
    String userOption = "";
    boolean invalid = true;
    boolean playing = true;
    
    do {      
      System.out.println("  \nPlay again?: " + 
                          "(Y)es or (N)o");
  
      System.out.print("-->");
      
      userOption = input.nextLine();      
  
      switch (userOption.toLowerCase()) {
        case "y":
        case "yes":
          invalid = false;
          playing = true;
          break;
        case "n":
        case "no":
          invalid = false;
          playing = false;
          break;
        default:
          System.out.println("\nThat was neither yes, nor no. Please try again...");
      }
    } while (invalid);
  
    return playing;
  }

  /**
    Prints the players' hands.

    @param players the array of players in the game.   
  */
  private static void printHands(Player[] players) {
      System.out.println("\n\n\n");
      for (Player p : players) {
        p.showHand();
      }
      System.out.println("\n");
      }
}