package com.braithiar.blackjack;

/**
<code>Card</code> is an abstracted class that specifies the minimum state and behaviors of a <code>Card</code>.
*/
public abstract class Card {
/**
  This <code>Card</code>'s name.
*/
private final String cardName;

/**
  Default value is <code>false</code>, as the card starts face down.
*/
private boolean faceUp;

/**
  Constructor for invocation by subclass constructors. <code>faceUP</code> defaults to false.
  @param name the name of the <code>Card</code>.
*/
protected Card(String name) { 
  this.cardName = name;
  this.faceUp = false; 
}

/**
  Returns the name of the card as set in <code>Card(String)</code>.
  @return the name of the card.
*/
public String getCardName() {
  return cardName;
}

/**
  Gets the current flip status of the card.
  @return <code>true</code> if the card is face up.
*/
public boolean isFlipped() {
  return faceUp;
}

/**
  Cycles the card's flip state.
*/
public void flip() {
  this.faceUp = this.faceUp ? false : true;
}
}