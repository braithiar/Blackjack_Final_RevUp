package com.braithiar.blackjack;

import java.util.ArrayList;
import java.util.Collections;

/**
  */
public class Deck {
  /**
    Defines a standard deck size without Jokers: 52.
  */
  protected static final int DECK_SIZE = 52;
  
  /**
    ArrayList holding the <code>PlayingCard</code> objects for this deck.
  */
  private ArrayList<PlayingCard> deck;

  /**
    The total number of cards drawn from this deck.
  */
  private int numCardsUsed;

  /**
    Constructs a <code>Deck</code> object of size <code>DECK_SIZE</code> with <code>PlayingCard</code>s having rank & suit as defined in a standard deck of cards.
  */
  public Deck() {
    this.deck = new ArrayList<>(Deck.DECK_SIZE);
    this.numCardsUsed = 0;

    for (PlayingCard.Suit s : PlayingCard.Suit.values()) {
      for (PlayingCard.Rank r : PlayingCard.Rank.values()) {
        this.deck.add(new PlayingCard(r, s));
      }
    }
  }
  
  /**
    Draws a card from the top of this deck.

    @return the drawn card.
  */
  public PlayingCard draw() {
    if (this.numCardsUsed < Deck.DECK_SIZE) {
      int index = (deck.size() - 1);
      
      ++this.numCardsUsed;

      deck.get(index).flip();
      
      return deck.remove(index);
    } else {
      throw buildEmptyDeckException();
    }
  }

  /**
    TODO
  */
  //public PlayingCard[] draw(int numToDraw){}
  
  /**
    TODO
  */
  //public PlayingCard[] drawRandom(int numToDraw){}
 
  /**
    Shuffles the deck using <code>Collections.shuffle</code>.
  */
  public void shuffle() {
    Collections.shuffle(this.deck);
  }

  /**
    Returns the remaining number of cards in this deck.

    @return this deck's remaining number of cards.
  */
  public int getCardsRemaing() {
    return Deck.DECK_SIZE - this.numCardsUsed;
  }
  
  /**
    Deals <code>numCards</code> to each player in <code>players</code>. Each card will be flipped to be face up, as per the rules of blackjack, except for the dealer's first card. It will remain hidden until <code>dealerReveal()</code> is used.

    @param players an array of players to be dealt to.
    @param numCards the number of cards to be dealt to each player.
  */
  public void deal(Player[] players, int numCards) {
    if (numCards > 0 && numCards < this.getCardsRemaing()) {
      for (Player p : players) {
        PlayingCard card = this.draw();

        if (p.isDealer()) {
          if (p.getHandSize() < 1) {
            card.flip();
          }        
        }
        
        p.addToHand(card);
      } 

      this.deal(players, numCards - 1);
      
    } else if (numCards == 0) {
      return;
    } else {
        throw buildIllegalNumberOfCardsException(numCards);
    }
  }
  
  private static final IndexOutOfBoundsException buildEmptyDeckException() {
    return new IndexOutOfBoundsException("There were no more cards to draw from the deck!");
  }

  private static final IllegalArgumentException buildIllegalNumberOfCardsException(int num) {
    return new IllegalArgumentException(num + " is not a valid number of cards. It must be greater than 0 and less than the integer returned by getCardsRemaining().");
  }
}