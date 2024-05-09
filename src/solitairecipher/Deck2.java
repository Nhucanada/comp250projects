/**
 * Your name here: Alexander Luo
 * Your McGill ID here: 261177261
 **/
package solitairecipher;

import java.util.Random;

public class Deck2 {
    public static String[] suitsInOrder = {"clubs", "diamonds", "hearts", "spades"};
    public static Random gen = new Random();

    public int numOfCards; // contains the total number of cards in the deck
    public Card head; // contains a pointer to the card on the top of the deck
    /*
     * TODO: Initializes a Deck object using the inputs provided
     */
    public Deck2(int numOfCardsPerSuit, int numOfSuits) {
        //Card c = new PlayingCard(suitsInOrder[0],1);
        if(numOfCardsPerSuit < 1 || numOfCardsPerSuit > 13 || numOfSuits < 1 || numOfSuits > suitsInOrder.length){
            throw new IllegalArgumentException("First input must be between 1 and 13 and Second input must be between 1 and the size of suitsInOrder.");
        }

        for (int s = 0; s < numOfSuits; s++) {
            String suit = suitsInOrder[s];
            for (int r = 1; r <= numOfCardsPerSuit; r++) {
                PlayingCard c = new PlayingCard(suit, r);
                addCard(c);
            }
        }
        addCard(new Joker("red"));
        addCard(new Joker("black"));
    }

    /*
     * TODO: Implements a copy constructor for Deck using Card.getCopy().
     * This method runs in O(n), where n is the number of cards in d.
     */
    public Deck2(Deck2 d) {
        Card currentCard = d.head;
        for(int i = 0; i < d.numOfCards; i++){
            this.addCard(currentCard.getCopy());
            currentCard = currentCard.next;
        }
    }

    /*
     * For testing purposes we need a default constructor.
     */
    public Deck2() {}

    /*
     * TODO: Adds the specified card at the bottom of the deck. This
     * method runs in $O(1)$.
     */
    public void addCard(Card c) {
        if(head == null){
            head = c;
            head.prev = c;
            head.next = c;
        }else{
            c.prev = head.prev;
            head.prev.next = c;
            head.prev = c;
            c.next = head;
        }
        this.numOfCards ++;
    }

    /*
     * TODO: Shuffles the deck using the algorithm described in the pdf.
     * This method runs in O(n) and uses O(n) space, where n is the total
     * number of cards in the deck.
     */
    public void shuffle() {
        Card[] arr = new Card[this.numOfCards];
        Card currentCard= this.head;
        Deck2 newDeck = new Deck2();

        for(int i = 0; i < this.numOfCards; i++){
            arr[i] = currentCard;
            currentCard = currentCard.next;
        }

        for(int i = numOfCards-1; i > 0; i--){
            int j = gen.nextInt(i+1);
            Card temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }


        for(int i = 0; i < this.numOfCards; i++){
            newDeck.addCard(arr[i]);
        }

        this.head = newDeck.head;
    }

    /*
     * TODO: Returns a reference to the joker with the specified color in
     * the deck. This method runs in O(n), where n is the total number of
     * cards in the deck.
     */
    public Joker locateJoker(String color) {
        Card currentCard = this.head;
        for(int i = 0; i < this.numOfCards; i++){
            if(currentCard instanceof Joker){
                if(((Joker) currentCard).getColor() == color){
                    return((Joker)currentCard);
                }
            }
            currentCard = currentCard.next;
        }
        return null;
    }

    /*
     * TODO: Moved the specified Card, p positions down the deck. You can
     * assume that the input Card does belong to the deck (hence the deck is
     * not empty). This method runs in O(p).
     */
    public void moveCard(Card c, int p) {
        if(numOfCards <= 0){
            throw new IllegalArgumentException("The deck can't be empty.");
        }
        for(int i = 0; i < p; i++){
            this.swap(c, c.next);
        }
    }

    private void swap(Card c1, Card c2){
        Card temp;
        temp = c1.next;
        c1.next = c2.next;
        c2.next = temp;

        if (c1.next != null)
            c1.next.prev = c1;
        if (c2.next != null)
            c2.next.prev = c2;

        temp = c1.prev;
        c1.prev = c2.prev;
        c2.prev = temp;

        if (c1.prev != null)
            c1.prev.next = c1;
        if (c2.prev != null)
            c2.prev.next = c2;
    }

    /*
     * TODO: Performs a triple cut on the deck using the two input cards. You
     * can assume that the input cards belong to the deck and the first one is
     * nearest to the top of the deck. This method runs in O(1)
     */
    public void tripleCut(Card firstCard, Card secondCard) {
        Card preFirst = firstCard.prev;
        Card afterSecond = secondCard.next;

        if (head.prev == secondCard) {
            secondCard.next = head;
            head.prev = secondCard;

            this.head = firstCard;
            Card tmp = preFirst;
            preFirst.next = this.head;
            this.head.prev = tmp;

        } else if (preFirst == head.prev) {
            preFirst.next = this.head;

            this.head = afterSecond;
            Card tmp = afterSecond.next;
            afterSecond.prev = this.head;
            this.head.next = tmp;

            head.prev = secondCard;
        } else {
            preFirst.next = afterSecond;
            afterSecond.prev = preFirst;

            Card tail = head.prev;
            Card tmp1 = head;

            this.head = afterSecond;

            tail.next = firstCard;
            firstCard.prev = tail;
            tmp1.prev = secondCard;
            secondCard.next = tmp1;
        }
    }
    /*
     * TODO: Performs a count cut on the deck. Note that if the value of the
     * bottom card is equal to a multiple of the number of cards in the deck,
     * then the method should not do anything. This method runs in O(n).
     */

    public void countCut() {
        Card lastCard = head.prev;
        int value = lastCard.getValue() % numOfCards;
        if (value != 0) {
            Card cutCard = head;
            for (int i = 0; i < value; i++) {
                cutCard = cutCard.next;
            }
            if (cutCard != head.prev) {

                Card newSecondToLast = cutCard.prev;
                Card oldSecondToLast = head.prev.prev;
                Card oldTail = head.prev;

                head.prev = oldSecondToLast;
                oldSecondToLast.next = head;

                oldTail.prev = newSecondToLast;
                newSecondToLast.next = oldTail;

                head = cutCard;
                head.prev = oldTail;
                oldTail.next = head;
            }
        }
    }

    /*
     * TODO: Returns the card that can be found by looking at the value of the
     * card on the top of the deck, and counting down that many cards. If the
     * card found is a Joker, then the method returns null, otherwise it returns
     * the Card found. This method runs in O(n).
     */
    public Card lookUpCard() {
        Card topCard = this.head;
        if(topCard instanceof Joker){
            return null;
        }else{
            int value = this.head.getValue();
            Card currentCard = this.head;
            for(int i = 0; i < value ; i++){
                currentCard = currentCard.next;
            }
            if (currentCard instanceof Joker) {
                return null;
            } else {
                return currentCard;
            }
        }
    }

    /*
     * TODO: Uses the Solitaire algorithm to generate one value for the keystream
     * using this deck. This method runs in O(n).
     */
    public int generateNextKeystreamValue() {
        Card card = null;
        while (card == null) {
            Joker redJoker = this.locateJoker("red");
            this.moveCard(redJoker, 1);
            Joker blackJoker = this.locateJoker("black");
            this.moveCard(blackJoker, 2);
            if (this.getPosition(redJoker) > this.getPosition(blackJoker)) {
                this.tripleCut(blackJoker, redJoker);
            } else {
                this.tripleCut(redJoker, blackJoker);
            }
            this.countCut();
            card = this.lookUpCard();
        }
        return card.getValue();
    }
    public int getPosition(Card c) {
        Card currentCard = this.head;
        for(int i = 0; i < this.numOfCards; i++){
            if(currentCard.equalTo(c)){
                return i;
            }
            currentCard = currentCard.next;
        }
        return -1;
    }


    public String toString() {
        String str = "";
        Card card = this.head;
        for(int i = 0; i < this.numOfCards; i++){
            str += card.prev.toString() + "<-" + card.toString() + "->" + card.next.toString() + ' ';
            card = card.next;
        }
        return str;
    }


    public abstract class Card {
        public Card next;
        public Card prev;

        public abstract Card getCopy();
        public abstract int getValue();

        public abstract boolean equalTo(Card c);
    }

    public class PlayingCard extends Card {
        public String suit;
        public int rank;

        public PlayingCard(String s, int r) {
            this.suit = s.toLowerCase();
            this.rank = r;
        }
        public int getRank(){
            return this.rank;
        }

        public String getSuit(){
            return this.suit;
        }

        public String toString() {
            String info = "";
            if (this.rank == 1) {
                //info += "Ace";
                info += "A";
            } else if (this.rank > 10) {
                String[] cards = {"Jack", "Queen", "King"};
                //info += cards[this.rank - 11];
                info += cards[this.rank - 11].charAt(0);
            } else {
                info += this.rank;
            }
            //info += " of " + this.suit;
            info = (info + this.suit.charAt(0)).toUpperCase();
            return info;
        }

        public PlayingCard getCopy() {
            return new PlayingCard(this.suit, this.rank);
        }

        public int getValue() {
            int i=-1;
            for (i = 0; i < suitsInOrder.length; i++) {
                if (this.suit.equals(suitsInOrder[i]))
                    break;
            }
            return this.rank + 13*i;
        }

        public boolean equalTo(Card c){
            if(c instanceof PlayingCard){
                if(((PlayingCard)c).getSuit() == this.suit && ((PlayingCard)c).getRank() == this.rank){
                    return true;
                }
            }
            return false;
        }
    }

    public class Joker extends Card {
        public String redOrBlack;

        public Joker(String c) {
            if (!c.equalsIgnoreCase("red") && !c.equalsIgnoreCase("black"))
                throw new IllegalArgumentException("Jokers can only be red or black");

            this.redOrBlack = c.toLowerCase();
        }

        public String toString() {
            //return this.redOrBlack + " Joker";
            return (this.redOrBlack.charAt(0) + "J").toUpperCase();
        }

        public Joker getCopy() {
            return new Joker(this.redOrBlack);
        }

        public int getValue() {
            return numOfCards - 1;
        }

        public String getColor() {
            return this.redOrBlack;
        }

        public boolean equalTo(Card c){
            if(c instanceof Joker){
                if(((Joker)c).getColor() == this.redOrBlack){
                    return true;
                }
            }
            return false;
        }
    }

}

