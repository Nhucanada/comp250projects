package solitairecipher;

import solitairecipher.Deck;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestsJuliaThoughtOf {

    public String deckToString(Deck d) {
        //setting a string to be first card of deck
        String printedDeck = d.head.toString();
        Deck.Card iterator = d.head;

        //iterating through list to get final string

        for(int i = 1; i < d.numOfCards; i++) {
            iterator = iterator.next;
            printedDeck = printedDeck + " " + iterator.toString();
        }

        return printedDeck;
    }

    //testing the first constructor with input of 4 cards per suit and 3 suits
    @Test
    public void constructor1InputsTest() {
        Deck testDeck = new Deck(4,3);
        //setting string to be first card of deck
        String printedDeck = deckToString(testDeck);

        Assertions.assertEquals("AC 2C 3C 4C AD 2D 3D 4D AH 2H 3H 4H RJ BJ", printedDeck);
    }


    //checking if the next reference of the last element of the linked list is the head of the list for the first constructor
    @Test
    public void constructor1CircularTest() {
        Deck testDeck = new Deck(2, 4);
        String printedDeck = testDeck.head.toString();
        Deck.Card iterator = testDeck.head;

        for (int i = 1; i < testDeck.numOfCards; i++) {
            iterator = iterator.next;
        }

        Deck.Card testHead = testDeck.head;
        Deck.Card testHead2 = iterator.next;

        Assertions.assertEquals(testHead, testHead2);
    }


    //checking if the prev reference of the head is the last element of the list
    @Test
    public void constructor1CircularTest2() {
        Deck testDeck = new Deck(2, 4);
        String printedDeck = testDeck.head.toString();
        Deck.Card iterator = testDeck.head;

        for (int i = 1; i < testDeck.numOfCards; i++) {
            iterator = iterator.next;
        }

        Deck.Card testTail = testDeck.head.prev;
        Deck.Card testTail2 = iterator;

        Assertions.assertEquals(testTail2, testTail);
    }


    //does the first constructor work with only one non-joker card?
    @Test
    public void constructor1OneCard() {
        Deck testerDeck = new Deck(1, 1);

        Assertions.assertEquals("AC", testerDeck.head.toString());
        Assertions.assertEquals("RJ", testerDeck.head.next.toString());
        Assertions.assertEquals("BJ", testerDeck.head.prev.toString());
    }


    //checking if the card names in the original deck match the card names in the copied deck
    @Test
    public void constructor2MatchCheck() {
        //printing out all the card in one card
        Deck origDeck = new Deck(7, 4);

        String printedOrigDeck = origDeck.head.toString();
        Deck.Card iterator = origDeck.head;

        //iterating through list to get final string
        for(int i = 1; i < origDeck.numOfCards; i++) {
            iterator = iterator.next;
            printedOrigDeck = printedOrigDeck + " " + iterator.toString();
        }

        //copying the deck and printing out all the cards in that deck
        Deck copyDeck = new Deck(origDeck);

        String printedCopyDeck = copyDeck.head.toString();
        Deck.Card iterator2 = copyDeck.head;

        //iterating through list to get final string
        for(int i = 1; i < copyDeck.numOfCards; i++) {
            iterator2 = iterator2.next;
            printedCopyDeck = printedCopyDeck + " " + iterator2.toString();
        }

        //check the content of the original list matches the content of the copied list
        Assertions.assertEquals(printedOrigDeck, printedCopyDeck);
        //2nd and 3rd tests check if the copied list is circular
        Assertions.assertEquals(iterator.next.toString(), copyDeck.head.toString());
        Assertions.assertEquals(origDeck.head.prev.toString(), iterator2.toString());

    }

    //checking if a modication to one deck will affect a copied deck (i.e. has a deep copy been created?)
    @Test
    public void constructor2DeepCopy() {
        //printing out all the card in one card
        Deck origDeck = new Deck(1, 3);

        //copying the deck and printing out all the cards in that deck
        Deck copyDeck = new Deck(origDeck);


        String printedOrigDeck = origDeck.head.toString();
        Deck.Card iterator = origDeck.head;

        //iterating through list to get final string
        for(int i = 1; i < origDeck.numOfCards; i++) {
            iterator = iterator.next;
            printedOrigDeck = printedOrigDeck + " " + iterator.toString();
        }

        Deck testerDeck = new Deck(1, 1);
        Deck.Card testerCard = testerDeck.head;

        origDeck.addCard(testerCard);


        //iterating through copied list to get final string
        String printedCopyDeck = deckToString(copyDeck);

        Assertions.assertEquals(printedOrigDeck, printedCopyDeck);
    }

    //does the second constructor work with a second deck
    @Test
    public void constructor2EmptyDeck() {
        Deck testDeck = new Deck();
        Deck copyDeck = new Deck(testDeck);

        Assertions.assertEquals(0, copyDeck.numOfCards);
        Assertions.assertEquals(null, copyDeck.head);
    }


    //checking if numOfCards is updated after addCard()
    @Test
    public void addCheck1() {
        Deck testDeck = new Deck(2,1);
        int numCards = testDeck.numOfCards;
        Deck addingDeck = new Deck(6,3);
        Deck.Card n = addingDeck.head;

        for (int i = 0; i < 4; i++) {
            n = n.next;
        }

        testDeck.addCard(n);

        Assertions.assertEquals(numCards + 1, testDeck.numOfCards);
    }

    //checking if circularity maintained after addCard()
    @Test
    public void addCheck2() {
        Deck testDeck = new Deck(2,3);
        Deck addingDeck = new Deck(6,4);
        Deck.Card n = addingDeck.head;

        testDeck.addCard(n);

        Assertions.assertEquals(n.next, testDeck.head);
        Assertions.assertEquals(n, testDeck.head.prev);

    }

    //can you call addCard() on an empty deck?
    @Test
    public void addCheck3() {
        Deck testDeck = new Deck();
        Deck addingDeck = new Deck(6,4);
        Deck.Card n = addingDeck.head;
        testDeck.addCard(n);

        Assertions.assertEquals(testDeck.head, testDeck.head.next);
        Assertions.assertEquals(testDeck.head, testDeck.head.prev);
    }

    //is the list still circular after being shuffled
    @Test
    public void shuffleCheck1() {
        Deck testDeck = new Deck(4,2);
        int num = testDeck.numOfCards;
        testDeck.shuffle();

        Assertions.assertEquals(num, testDeck.numOfCards);
        Assertions.assertEquals(testDeck.head, testDeck.head.prev.next);
    }

    //checking if Deck.locateJoker() returns a Joker of the specified colour
    @Test
    public void jokerTest() {
        Deck testDeck = new Deck(11, 4);
        testDeck.shuffle();
        Deck.Joker j = testDeck.locateJoker("red");
        String jColor = j.getColor();

        Deck.Joker j2 = testDeck.locateJoker("black");
        String j2Color = j2.getColor();

        Assertions.assertEquals("red", jColor);
        Assertions.assertEquals("black", j2Color);
    }

    //testing moveCard() for a card in the non-head position
    @Test
    public void moveCheck1() {
        Deck testDeck = new Deck(2, 2);

        testDeck.moveCard(testDeck.head.next, 2);


        Assertions.assertEquals("AC AD 2D 2C RJ BJ", deckToString(testDeck));
    }

    //testing moveCard() for a card in the head position
    @Test
    public void moveCheck2() {
        Deck testDeck = new Deck(2, 2);
        testDeck.moveCard(testDeck.head, 2);

        Assertions.assertEquals("AC 2D RJ BJ 2C AD", deckToString(testDeck));
    }

    //testing if moveCard() works for a non-head card in a bigger deck
    @Test
    public void moveCheck3() {
        Deck testDeck = new Deck(5,2);
        testDeck.moveCard(testDeck.head.next.next, 4);

        Assertions.assertEquals("AC 2C 4C 5C AD 2D 3C 3D 4D 5D RJ BJ", deckToString(testDeck));
    }

    //checking if cards in right order after triple cut
    @Test
    public void tripleCut1() {
        Deck d = new Deck();

        Deck.PlayingCard c1 = d.new PlayingCard(Deck.suitsInOrder[0], 5);
        Deck.PlayingCard c2 = d.new PlayingCard(Deck.suitsInOrder[0], 7);
        Deck.PlayingCard c3 = d.new PlayingCard(Deck.suitsInOrder[0], 9);
        Deck.Joker j1 = d.new Joker("black");
        Deck.PlayingCard m1 = d.new PlayingCard(Deck.suitsInOrder[3], 2);
        Deck.PlayingCard m2 = d.new PlayingCard(Deck.suitsInOrder[3], 3);
        Deck.PlayingCard m3 = d.new PlayingCard(Deck.suitsInOrder[3], 4);
        Deck.Joker j2 = d.new Joker("red");
        Deck.PlayingCard l1 = d.new PlayingCard(Deck.suitsInOrder[2], 11);
        Deck.PlayingCard l2 = d.new PlayingCard(Deck.suitsInOrder[2], 12);
        Deck.PlayingCard l3 = d.new PlayingCard(Deck.suitsInOrder[2], 13);

        Deck testDeck = new Deck();

        testDeck.addCard(c1);
        testDeck.addCard(c2);
        testDeck.addCard(c3);
        testDeck.addCard(j1);
        testDeck.addCard(m1);
        testDeck.addCard(m2);
        testDeck.addCard(m3);
        testDeck.addCard(j2);
        testDeck.addCard(l1);
        testDeck.addCard(l2);
        testDeck.addCard(l3);

        testDeck.tripleCut(j1, j2);

        Assertions.assertEquals("JH QH KH BJ 2S 3S 4S RJ 5C 7C 9C", deckToString(testDeck));
    }

    @Test
    public void tripleCutCheckNextPrev() {

        Deck d = new Deck();

        Deck.Card[] cards = new Deck.Card[]{
                d.new PlayingCard(Deck.suitsInOrder[0], 5),
                d.new PlayingCard(Deck.suitsInOrder[0], 7),
                d.new PlayingCard(Deck.suitsInOrder[0], 9),
                null,
                d.new PlayingCard(Deck.suitsInOrder[3], 2),
                d.new PlayingCard(Deck.suitsInOrder[3], 3),
                d.new PlayingCard(Deck.suitsInOrder[3], 4),
                null,
                d.new PlayingCard(Deck.suitsInOrder[2], 11),
                d.new PlayingCard(Deck.suitsInOrder[2], 12),
                d.new PlayingCard(Deck.suitsInOrder[2], 13)
        };

        Deck.Joker j1 = d.new Joker("black");
        Deck.Joker j2 = d.new Joker("red");

        cards[3] = j1;
        cards[7] = j2;

        for (Deck.Card c : cards) {
            d.addCard(c);
        }

        Deck.Card[] expected = new Deck.Card[]{
                cards[8], cards[9], cards[10],
                cards[3], cards[4], cards[5], cards[6], cards[7],
                cards[0], cards[1], cards[2]};


        d.tripleCut(j1, j2);

        Deck.Card curNext = d.head;

        //checking next references
        for (int i = 0; i < expected.length; i++) {
            // System.out.println(curNext);
            assertEquals(expected[i], curNext, "Expect card: " + expected[i] + " but got: " + curNext);
            curNext = curNext.next;
        }

        Deck.Card curPrev = d.head.prev;

        //checking prev references
        for (int j = expected.length - 1; j > 0; j--) {
            // System.out.println(curPrev);
            assertEquals(expected[j], curPrev, "Expect card: " + expected[j] + " but got: " + curPrev);

            curPrev = curPrev.prev;
        }

    }

    @Test
    public void countCutCheckOrder() {
        Deck d = new Deck();

        Deck.Card[] cards = new Deck.Card[]{
                d.new PlayingCard(Deck.suitsInOrder[0], 11),
                d.new PlayingCard(Deck.suitsInOrder[0], 12),
                d.new PlayingCard(Deck.suitsInOrder[0], 13),
                null,
                d.new PlayingCard(Deck.suitsInOrder[3], 2),
                d.new PlayingCard(Deck.suitsInOrder[3], 3),
                d.new PlayingCard(Deck.suitsInOrder[3], 4),
                null,
                d.new PlayingCard(Deck.suitsInOrder[2], 9),
                d.new PlayingCard(Deck.suitsInOrder[2], 7),
                d.new PlayingCard(Deck.suitsInOrder[0], 5)
        };

        Deck.Joker j1 = d.new Joker("black");
        Deck.Joker j2 = d.new Joker("red");

        cards[3] = j1;
        cards[7] = j2;

        for (Deck.Card c : cards) {
            d.addCard(c);
        }

        d.countCut();
        Assertions.assertEquals("3S 4S RJ 9H 7H JC QC KC BJ 2S 5C", deckToString(d));

    }

    @Test
    public void countCutCheckNextPrev() {

        Deck d = new Deck();

        Deck.Card[] cards = new Deck.Card[]{
                d.new PlayingCard(Deck.suitsInOrder[0], 2),
                d.new PlayingCard(Deck.suitsInOrder[0], 3),
                d.new PlayingCard(Deck.suitsInOrder[0], 4),
                null,
                d.new PlayingCard(Deck.suitsInOrder[3], 10),
                d.new PlayingCard(Deck.suitsInOrder[3], 9),
                d.new PlayingCard(Deck.suitsInOrder[3], 8),
                null,
                d.new PlayingCard(Deck.suitsInOrder[2], 11),
                d.new PlayingCard(Deck.suitsInOrder[2], 12),
                d.new PlayingCard(Deck.suitsInOrder[2], 2)
        };

        Deck.Joker j1 = d.new Joker("black");
        Deck.Joker j2 = d.new Joker("red");

        cards[3] = j1;
        cards[7] = j2;

        for (Deck.Card c : cards) {
            d.addCard(c);
        }

        Deck.Card[] expected = new Deck.Card[]{
                cards[6], cards[7], cards[8],
                cards[9], cards[0], cards[1], cards[2], cards[3],
                cards[4], cards[5], cards[10]};


        d.countCut();

        Deck.Card curNext = d.head;

        //checking next references
        for (int i = 0; i < expected.length; i++) {
            // System.out.println(curNext);
            assertEquals(expected[i], curNext, "Expect card: " + expected[i] + " but got: " + curNext);
            curNext = curNext.next;
        }

        Deck.Card curPrev = d.head.prev;

        //checking prev references
        for (int j = expected.length - 1; j > 0; j--) {
            System.out.println(curPrev);
            assertEquals(expected[j], curPrev, "Expect card: " + expected[j] + " but got: " + curPrev);

            curPrev = curPrev.prev;
        }

    }


}





