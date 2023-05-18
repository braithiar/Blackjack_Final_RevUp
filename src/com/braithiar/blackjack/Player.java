package com.braithiar.blackjack;

import java.util.ArrayList;

public class Player {
  /**
    Stores if this player is a dealer.
  */
  private final boolean dealer;
  
  /**
    This player's name.
  */
  private final String name;
  
  /**
    The total wins for this player.
  */
  private int wins = 0;
  
  /**
    The score of this player's current hand.
  */
  private int score = 0;
  
  /**
    This player's current hand.
  */
  private ArrayList<PlayingCard> hand;

  /**
    Constructs a <code>Player</code> object.

    @param dealer true if this player is the dealer.
    @param name this player's name.
  */
  public Player(boolean dealer, String name) {
    this.dealer = dealer;
    this.name = name;
    hand = new ArrayList<>(2);
  }

  /**
    Returns true if this player is dealer.

    @return true if player is dealer.
  */
  public boolean isDealer() {
    return this.dealer;
  }
  
  /**
    Increments this player's number of wins.
  */
  public void addWin() {
    ++wins;
  }

  /**
    Sets this players hand score back to zero.
  */
  public void resetScore() {
    this.score = 0;  
  }

  /**
    Reveals this players hidden card, if isDealer() returns true, and adds it to the hand score. Otherwise, has no effect.
  */
  public void dealerReveal() {
    if (!this.isDealer()) {
      return;
    }

    this.hand.get(0).flip();

    this.assignScore(this.hand.get(0));
  }
  
  /**
    Adds a <code>PlayingCard</code> object to this player's hand and updates the hand score.

    @param card the <code>PlayingCard</code> to add to this hand.
  */
  public void addToHand(PlayingCard card) {
    this.hand.add(card);
    this.assignScore(card);
  }

  /**
    Assigns the value of <code>PlayingCard</code> to this player's hand score. Values range from 1 to 11. Aces have a value of 11 if this player's current score is <= 10, otherwise, their value is 1.

    @param card the <code>PlayingCard</code> to have its value added to this hand's score.
  */
  private void assignScore(PlayingCard card) {
    if (!card.isFlipped()) {
      return;
    }
    
    switch(card.getRankAsString()) {
      case "King":
      case "Queen":
      case "Jack":
      case "Ten":
        this.score += 10;
        break;
      case "Nine":
        this.score += 9;
        break;
      case "Eight":
        this.score += 8;
        break;
      case "Seven":
        this.score += 7;
        break;
      case "Six":
        this.score += 6;
        break;
      case "Five":
        this.score += 5;
        break;
      case "Four":
        this.score += 4;
        break;
      case "Three":
        this.score += 3;
        break;
      case "Two":
        this.score += 2;
        break;
      case "Ace":
        if (this.score <= 10) {
          this.score += 11;
        } else {
          this.score += 1;
        }
        break;
      default: 
        this.score += 0;
    }
  }
  
  /**
    Removes all cards from this hand.
  */
  public void resetHand() {
    this.hand.clear();
  }

  /**
    Prints current hand to console. 
  */
  public void showHand() {
    System.out.println(this);
  }

   /**
    Returns this hand's size.

    @return the number of cards in this player's hand.
  */
  public int getHandSize() {
    return this.hand.size();
  }

  /**
    Returns the value of this player's hand.

    @return the player's hand score.
  */
  public int getScore() {
    return this.score;
  }

  /**
  */
  public void recalculateScore() {
    this.resetScore();
    
    for (PlayingCard c : this.hand) {
      this.assignScore(c);
    }
  }
  
  /**
    Returns this player's name.

    @return this player's name.
  */
  public String getName() {
    return this.name;
  }

  /**
    Returns this player's total wins.

    @return this player's total wins.
  */
  public int getWins() {
    return this.wins;
  }

  /**
    Returns true if dealer has an Ace and it's value is 11.

    @return true if dealer has an Ace and it's value is 11.
  */
  /* public int dealerSoftCheck() {
    if ()
  } */
  
  /**
    Converts <code>Player</code> to a string, including: this hand's cards, their summed score, and this player's total wins.
  */
  @Override
  public String toString() {
    String cardsInHand = "\n" + this.name + "'s Hand:\n";
    
    for (PlayingCard d : hand) {
        cardsInHand += d + " ";
      }

    cardsInHand += "    Value: " + this.score + " | Wins: " + this.wins + "\n";

      return cardsInHand;
  }  
}