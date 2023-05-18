package com.braithiar.blackjack;

public class PlayingCard extends Card {
	  /**
	    This <code>PlayingCard</code>'s value as defined by the <code>Rank</code> enum.
	  */
	  private final Rank rank;

	  /**
	    Defines the available card ranks for use in <code>PlayingCard(Rank, Suit)</code>.
	  */
	  public enum Rank {
	    ACE ("Ace"),
			TWO ("Two"),
			THREE ("Three"),
			FOUR ("Four"),
			FIVE ("Five"),
			SIX ("Six"),
			SEVEN ("Seven"),
			EIGHT ("Eight"),
			NINE ("Nine"),
			TEN ("Ten"),
			JACK ("Jack"),
			QUEEN ("Queen"),
			KING ("King");

	    private final String rankValue;

	    private Rank(String rankValue) { this.rankValue = rankValue; }
	    private String getRankValue() { return this.rankValue; }
	  }

	  /**
	    This <code>PlayingCard</code>'s suit as defined by the <code>Suit</code> enum.
	  */
	  private final Suit suit;

	  /**
	    Defines the available suits (as unicode symbols) for use in <code>PlayingCard(Rank, Suit)</code>.
	  */
	  public enum Suit {
	    CLUBS ("\u2667"),
			SPADES ("\u2664"),
			DIAMONDS ("\u2662"),
			HEARTS ("\u2661");

	    private final String suitSymbol;

	    private Suit(String suitSymbol) { this.suitSymbol = suitSymbol; }
	    private String getSuitSymbol() { return this.suitSymbol; }
	  }

	  /**
	    Constructs a <code>PlayingCard</code> object with the specified <code>Rank</code> and <code>Suit</code>. Cards default to being face down.

	    @param rank the <code>Rank</code> of the playing card.
	    @param suit the <code>Suit</code> of the playing card.
	  */
	  public PlayingCard(Rank rank, Suit suit) {
	    super(rank.getRankValue() + " of " + suit.getSuitSymbol());
	    this.rank = rank;
	    this.suit = suit;
	  }

	  /**
	    Returns this card's <code>Rank</code>.

	    @return this card's <code>Rank</code>.
	  */
	  public Rank getRank() {
	    return this.rank;
	  }

	  /**
	    Returns this card's <code>Rank</code> as a string.

	    @retrun this card's value as a string.
	  */
	  public String getRankAsString() {
	    return this.rank.getRankValue();
	  }

	  /**
	    Gets this card's <code>Suit</code>.

	    @return this card's <code>Suit</code>.
	  */
	  public Suit getSuit() {
	    return this.suit;
	  }

	  /**
	    Returns this card's <code>Suit</code> as a unicode symbol.

	    @retrun this card's <code>Suit</code> as a unicode symbol for card suits.
	  */
	  public String getSuitAsString() {
	    return this.suit.getSuitSymbol();
	  }
	  
	  /**
	    Returns the name of the <code>PlayingCard</code> object in the form of ""<code>Rank</code> of <code>Suit</code>." Cards that are facedown (isFlipped() == false) show up as [CARD].

	    @return the name of the playing card.
	  */
	  @Override
	  public String toString() {
	    if (this.isFlipped()) {
	      return "[" + getCardName() + "]";
	    } else {
	      return "[CARD]";
	    }
	  }
	}