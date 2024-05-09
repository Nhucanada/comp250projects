package solitairecipher;

import org.junit.jupiter.api.Test;


import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class MiniTesterA2 {

    @Test
    public void AddCard_AllRef() {
        Deck3 Deck3 = new Deck3();
        Deck3.Card c1 = Deck3.new PlayingCard(Deck3.suitsInOrder[0], 1); //AC
        Deck3.Card c2 = Deck3.new PlayingCard(Deck3.suitsInOrder[0], 2); //2C
        Deck3.Card c3 = Deck3.new PlayingCard(Deck3.suitsInOrder[0], 3); //3C
        Deck3.addCard(c1);
        Deck3.addCard(c2);
        Deck3.addCard(c3);
        boolean c1ref = c1.next == c2 && c1.prev == c3;
        boolean c2ref = c2.next == c3 && c2.prev == c1;
        boolean c3ref = c3.next == c1 && c3.prev == c2;

        assertTrue(c1ref && c2ref && c3ref, "The next and prev references are not set correctly");

    }

    @Test
    public void AddCard_CheckHead() {
        Deck3 Deck3 = new Deck3();
        Deck3.Card c1 = Deck3.new PlayingCard(Deck3.suitsInOrder[0], 1); //AC
        Deck3.Card c8 = Deck3.new PlayingCard(Deck3.suitsInOrder[0], 8); //8C
        Deck3.addCard(c1);
        Deck3.addCard(c8);
        Deck3.Card head = Deck3.head;

        assertSame(head, c1, "The head is not set correctly. " +
                "addCard should add the input card to the bottom of the Deck3.\n" +
                "Expected head to be " + c1 + " but got " + head);

    }

    @Test
    public void AddCard_Circular() {
        Deck3 Deck3 = new Deck3();
        Deck3.Card c1 = Deck3.new PlayingCard(Deck3.suitsInOrder[0], 1); //AC
        Deck3.Card c2 = Deck3.new PlayingCard(Deck3.suitsInOrder[0], 2); //2C
        Deck3.Card c3 = Deck3.new PlayingCard(Deck3.suitsInOrder[0], 3); //3C
        Deck3.Card c8 = Deck3.new PlayingCard(Deck3.suitsInOrder[0], 8); //8C
        Deck3.addCard(c1);
        Deck3.addCard(c2);
        Deck3.addCard(c3);
        Deck3.addCard(c8);

        assertTrue(c1.prev == c8 && c8.next == c1, "Circular references are not correctly set up.");
    }

    @Test
    public void AddCard_NumOfCards() {
        Deck3 Deck3 = new Deck3();
        Deck3.Card c1 = Deck3.new PlayingCard(Deck3.suitsInOrder[0], 1); //AC
        Deck3.Card c2 = Deck3.new PlayingCard(Deck3.suitsInOrder[0], 2); //2C
        Deck3.Card c3 = Deck3.new PlayingCard(Deck3.suitsInOrder[0], 3); //3C
        Deck3.Card d11 = Deck3.new PlayingCard(Deck3.suitsInOrder[1], 11); //JD
        Deck3.addCard(c1);
        Deck3.addCard(c2);
        Deck3.addCard(d11);
        Deck3.addCard(c3);
        int expected = 4;
        int result = Deck3.numOfCards;

        assertEquals(expected, result, "numOfCards is not correctly updated.");
    }

    @Test
    public void AddCard_SingleCard() {
        Deck3 Deck3 = new Deck3();
        Deck3.Card c1 = Deck3.new PlayingCard(Deck3.suitsInOrder[0], 1); //AC
        Deck3.addCard(c1);

        assertTrue(c1.prev == c1 && c1.next == c1, "Card references are not correctly set up when the Deck3 contains only ONE card.");
    }

    @Test
    public void DeepCopy_CheckRefs() {
        HashSet<Deck3.Card> cardSet = new HashSet<>();
        Deck3 Deck3 = new Deck3();
        cardSet.add(Deck3.new PlayingCard(Deck3.suitsInOrder[0], 1));
        cardSet.add(Deck3.new PlayingCard(Deck3.suitsInOrder[0], 3));
        cardSet.add(Deck3.new PlayingCard(Deck3.suitsInOrder[0], 5));
        cardSet.add(Deck3.new Joker("black"));
        cardSet.add(Deck3.new PlayingCard(Deck3.suitsInOrder[1], 2));
        cardSet.add(Deck3.new PlayingCard(Deck3.suitsInOrder[2], 4));
        cardSet.add(Deck3.new PlayingCard(Deck3.suitsInOrder[3], 6));

        for(Deck3.Card c: cardSet) {
            Deck3.addCard(c);
        }

        Deck3 copy = new Deck3(Deck3); // should do a deep copy

        Deck3.Card cur = copy.head;

        for (int i = 0; i < cardSet.size(); i++) {
            assertFalse(cardSet.contains(cur),"Deep copy must create new object.");

            cur = cur.next;
        }

    }

    @Test
    public void DeepCopy_CircularNext() {
        Deck3 Deck3 = new Deck3();
        Deck3.Card[] cards = new Deck3.Card[]{
                Deck3.new PlayingCard(Deck3.suitsInOrder[0], 1),
                Deck3.new PlayingCard(Deck3.suitsInOrder[0], 3),
                Deck3.new PlayingCard(Deck3.suitsInOrder[0], 5),
                Deck3.new Joker("black"),
                Deck3.new PlayingCard(Deck3.suitsInOrder[1], 2),
                Deck3.new PlayingCard(Deck3.suitsInOrder[2], 4),
                Deck3.new PlayingCard(Deck3.suitsInOrder[3], 6)
        };

        for (Deck3.Card c : cards) {
            Deck3.addCard(c);
            System.out.println(c);
        }

        Deck3 copy = new Deck3(Deck3); // should do a deep copy

        Deck3.Card cur = copy.head;

        for (int i = 0; i < cards.length; i++) {
            assertNotNull(cur,"Either head or one of the next pointers is null.");


            // Either one is Joker and other is PlayingCard or vice versa
            assertEquals(cards[i].getClass(), cur.getClass(), "The card at the next position of ."
                        + i + " from head, has type: " + cur.getClass().getName()
                        + " but expected: " + cards[i].getClass().getName());


            // both are PlayingCard
            if (cur instanceof Deck3.PlayingCard) {
                assertEquals(cards[i].getClass(), cur.getClass(), "The card at the next position of ."
                            + i + " from head must have value: " + cards[i].getValue() + " but got: " + cur.getValue());

                // both are Joker
            } else {
                String cardColor = ((Deck3.Joker) cards[i]).getColor();
                String curColor = ((Deck3.Joker) cur).getColor();
                assertEquals(cardColor, curColor, "The joker card at the next position of ."
                            + i + " from head must have color: " + cardColor + " but got: " + curColor);

            }
            cur = cur.next;
        }

        if (cur != copy.head) {
            fail("The last card's next does not point to the head.");
        }

    }

    @Test
    public void DeepCopy_CircularPrev() {
        Deck3 Deck3 = new Deck3();
        Deck3.Card[] cards = new Deck3.Card[]{
                Deck3.new PlayingCard(Deck3.suitsInOrder[0], 1),
                Deck3.new PlayingCard(Deck3.suitsInOrder[0], 3),
                Deck3.new PlayingCard(Deck3.suitsInOrder[0], 5),
                Deck3.new Joker("black"),
                Deck3.new PlayingCard(Deck3.suitsInOrder[1], 2),
                Deck3.new PlayingCard(Deck3.suitsInOrder[2], 4),
                Deck3.new PlayingCard(Deck3.suitsInOrder[3], 6)
        };

        for (Deck3.Card c : cards) {
            Deck3.addCard(c);
        }

        Deck3 copy = new Deck3(Deck3); // should do a deep copy

        Deck3.Card cur = copy.head;
        for (int j = 0; j < cards.length; j++) {
            int i = Math.floorMod(-j, cards.length); // i goes 0, n-1, n-2, ..., 1
            assertNotNull(cur,"Either head or one of the prev pointers is null.");


            // Either one is Joker and other is PlayingCard or vice versa
            assertEquals(cards[i].getClass(), cur.getClass(), "The card at the prev position of ."
                        + j + " from head, has type: " + cur.getClass().getName()
                        + " but expected: " + cards[i].getClass().getName());


            if (cur instanceof Deck3.PlayingCard) { // both are PlayingCard
                assertEquals(cards[i].getValue(),cur.getValue(),"The card at the prev position of ."
                            + j + " from head must have value: " + cards[i].getValue() + " but got: " + cur.getValue());

            } else { // both are Joker
                String cardColor = ((Deck3.Joker) cards[i]).getColor();
                String curColor = ((Deck3.Joker) cur).getColor();
                assertEquals(cardColor, curColor, "The joker card at the prev position of ."
                        + j + " from head must have color: " + cardColor + " but got: " + curColor);

            }
            cur = cur.prev;
        }

        if (cur != copy.head) {
            fail("The last card's prev does not point to the head.");
        }
    }

    @Test
    public void LocateJoker_Test1() {
        Deck3 tDeck3 = new Deck3();
        Deck3.Card c1 = tDeck3.new PlayingCard(Deck3.suitsInOrder[0], 1); //AC
        Deck3.Card c2 = tDeck3.new PlayingCard(Deck3.suitsInOrder[0], 2); //2C
        Deck3.Card c3 = tDeck3.new PlayingCard(Deck3.suitsInOrder[0], 3); //3C
        Deck3.Card expected = tDeck3.new Joker("red");

        tDeck3.addCard(c1);
        tDeck3.addCard(c2);
        tDeck3.addCard(c3);
        tDeck3.addCard(expected);

        Deck3.Card received = tDeck3.locateJoker("red");

        assertEquals(expected, received, "The reference returned was incorrect." +
                "Expected the card " + expected.toString() + " with reference " + expected.hashCode()
                +" but instead got the card " + received + " with reference " + received.hashCode());
    }

    @Test
    public void LocateJoker_Test2() {
        Deck3 tDeck3 = new Deck3();
        Deck3.Card c1 = tDeck3.new PlayingCard(Deck3.suitsInOrder[0], 1); //AC
        Deck3.Card c2 = tDeck3.new Joker("red");
        Deck3.Card c3 = tDeck3.new PlayingCard(Deck3.suitsInOrder[0], 2); //2C
        Deck3.Card c4 = tDeck3.new PlayingCard(Deck3.suitsInOrder[0], 3); //3C

        Deck3.Card expected = tDeck3.new Joker("black");

        tDeck3.addCard(c1);
        tDeck3.addCard(c2);
        tDeck3.addCard(c3);
        tDeck3.addCard(c4);
        tDeck3.addCard(expected);

        Deck3.Card received = tDeck3.locateJoker("black");

        assertEquals(expected, received, "The reference returned was incorrect." +
                "Expected the card " + expected.toString() + " with reference " + expected.hashCode()
                +" but instead got the card " + received + " with reference " + received.hashCode());
    }

    @Test
    public void LocateJoker_Test3() {
        Deck3 tDeck3 = new Deck3();
        Deck3.Card c1 = tDeck3.new PlayingCard(Deck3.suitsInOrder[0], 1); //AC
        Deck3.Card expected = tDeck3.new Joker("red");
        Deck3.Card c2 = tDeck3.new Joker("black");
        Deck3.Card c3 = tDeck3.new PlayingCard(Deck3.suitsInOrder[0], 2); //2C
        Deck3.Card c4 = tDeck3.new PlayingCard(Deck3.suitsInOrder[0], 3); //3C

        tDeck3.addCard(c1);
        tDeck3.addCard(expected);
        tDeck3.addCard(c2);
        tDeck3.addCard(c3);
        tDeck3.addCard(c4);


        Deck3.Card received = tDeck3.locateJoker("red");

        assertEquals(expected, received, "The reference returned was incorrect." +
                "Expected the card " + expected.toString() + " with reference " + expected.hashCode()
                +" but instead got the card " + received + " with reference " + received.hashCode());
    }

    @Test
    public void LookUpCard_Test1() {

        Deck3 tDeck3 = new Deck3();
        Deck3.Card c1 = tDeck3.new PlayingCard(Deck3.suitsInOrder[0], 1); //AC
        Deck3.Card expected = tDeck3.new PlayingCard(Deck3.suitsInOrder[0], 2); //2C
        Deck3.Card c3 = tDeck3.new PlayingCard(Deck3.suitsInOrder[0], 3); //3C

        tDeck3.addCard(c1);
        tDeck3.addCard(expected);
        tDeck3.addCard(c3);

        Deck3.Card received = tDeck3.lookUpCard();

        assertEquals(expected, received, "The reference returned was incorrect." +
                "Expected the card " + expected.toString() + " with reference " + expected.hashCode()
                +" but instead got the card " + received + " with reference " + received.hashCode());

    }

    @Test
    public void LookUpCard_Test2() {
        Deck3 tDeck3 = new Deck3();
        Deck3.Card c1 = tDeck3.new PlayingCard(Deck3.suitsInOrder[0], 6); //6C
        Deck3.Card c2 = tDeck3.new PlayingCard(Deck3.suitsInOrder[1], 2); //2D
        Deck3.Card c3 = tDeck3.new PlayingCard(Deck3.suitsInOrder[2], 3); //3H
        Deck3.Card c4 = tDeck3.new PlayingCard(Deck3.suitsInOrder[2], 4); //4H
        Deck3.Card c5 = tDeck3.new PlayingCard(Deck3.suitsInOrder[2], 5); //5H
        Deck3.Card c6 = tDeck3.new PlayingCard(Deck3.suitsInOrder[2], 6); //6H
        Deck3.Card expected = tDeck3.new PlayingCard(Deck3.suitsInOrder[2], 7); //7H
        Deck3.Card c7 = tDeck3.new PlayingCard(Deck3.suitsInOrder[2], 8); //8H
        Deck3.Card c8 = tDeck3.new PlayingCard(Deck3.suitsInOrder[2], 9); //9H

        tDeck3.addCard(c1);
        tDeck3.addCard(c2);
        tDeck3.addCard(c3);
        tDeck3.addCard(c4);
        tDeck3.addCard(c5);
        tDeck3.addCard(c6);
        tDeck3.addCard(expected);
        tDeck3.addCard(c7);
        tDeck3.addCard(c8);

        Deck3.Card received = tDeck3.lookUpCard();

        assertEquals(expected, received, "The reference returned was incorrect." +
                "Expected the card " + expected.toString() + " with reference " + expected.hashCode()
                +" but instead got the card " + received + " with reference " + received.hashCode());

    }

    @Test
    public void LookUpCard_Test3() {
        Deck3 tDeck3 = new Deck3();
        Deck3.Card c1 = tDeck3.new PlayingCard(Deck3.suitsInOrder[0], 8); //6C
        Deck3.Card c2 = tDeck3.new PlayingCard(Deck3.suitsInOrder[1], 2); //2D
        Deck3.Card c3 = tDeck3.new PlayingCard(Deck3.suitsInOrder[2], 3); //3H
        Deck3.Card c4 = tDeck3.new PlayingCard(Deck3.suitsInOrder[2], 4); //4H
        Deck3.Card c5 = tDeck3.new PlayingCard(Deck3.suitsInOrder[2], 5); //5H
        Deck3.Card c6 = tDeck3.new PlayingCard(Deck3.suitsInOrder[2], 6); //6H
        Deck3.Card c7 = tDeck3.new PlayingCard(Deck3.suitsInOrder[2], 7); //7H
        Deck3.Card c8 = tDeck3.new PlayingCard(Deck3.suitsInOrder[2], 8); //8H
        Deck3.Card c9 = tDeck3.new Joker("red"); //JR

        tDeck3.addCard(c1);
        tDeck3.addCard(c2);
        tDeck3.addCard(c3);
        tDeck3.addCard(c4);
        tDeck3.addCard(c5);
        tDeck3.addCard(c6);
        tDeck3.addCard(c7);
        tDeck3.addCard(c8);
        tDeck3.addCard(c9);


        Deck3.Card received = tDeck3.lookUpCard();

        assertNull(received, "Null should be returned in case a Joker is found.");
    }

    @Test
    public void MoveCard_CheckNext1() {
        Deck3 Deck3 = new Deck3();
        Deck3.Card[] cards = new Deck3.Card[]{
                Deck3.new PlayingCard(Deck3.suitsInOrder[0], 1),
                Deck3.new PlayingCard(Deck3.suitsInOrder[0], 3),
                Deck3.new PlayingCard(Deck3.suitsInOrder[0], 5),
                Deck3.new Joker("black"),
                Deck3.new PlayingCard(Deck3.suitsInOrder[1], 2),
                Deck3.new PlayingCard(Deck3.suitsInOrder[2], 4),
                Deck3.new PlayingCard(Deck3.suitsInOrder[3], 6)
        };

        for (Deck3.Card c : cards) {
            Deck3.addCard(c);
            System.out.println(c);
        }

        Deck3.Card[] expected = new Deck3.Card[]{
                cards[0], cards[1], cards[3], cards[4],
                cards[5], cards[2], cards[6]};

        Deck3.moveCard(cards[2], 3);

        Deck3.Card cur = Deck3.head;

        for (int i = 0; i < expected.length; i++) {
            // System.out.println(cur);
            assertEquals(expected[i], cur, "Expect card: " + expected[i] + " but got: " + cur);
            cur = cur.next;
        }
    }

    @Test
    public void MoveCard_CheckNext2() {
        Deck3 Deck3 = new Deck3();
        Deck3.Card[] cards = new Deck3.Card[]{
                Deck3.new PlayingCard(Deck3.suitsInOrder[0], 1),
                Deck3.new PlayingCard(Deck3.suitsInOrder[0], 3),
                Deck3.new PlayingCard(Deck3.suitsInOrder[0], 5),
                Deck3.new Joker("black"),
                Deck3.new PlayingCard(Deck3.suitsInOrder[1], 2),
                Deck3.new PlayingCard(Deck3.suitsInOrder[2], 4),
                Deck3.new PlayingCard(Deck3.suitsInOrder[3], 6)
        };

        for (Deck3.Card c : cards) {
            Deck3.addCard(c);
        }

        Deck3.Card[] expected = new Deck3.Card[]{
                cards[0], cards[3], cards[1], cards[2],
                cards[4], cards[5], cards[6]};

        Deck3.moveCard(cards[3], 4);

        Deck3.Card cur = Deck3.head;
        for (int i = 0; i < expected.length; i++) {
            // System.out.println(cur);
            assertEquals(expected[i],cur,"Expect card: " + expected[i] + " but got: " + cur);

            cur = cur.next;
        }
    }

    @Test
    public void MoveCard_CheckPrev1() {
        Deck3 Deck3 = new Deck3();
        Deck3.Card[] cards = new Deck3.Card[]{
                Deck3.new PlayingCard(Deck3.suitsInOrder[0], 1),
                Deck3.new PlayingCard(Deck3.suitsInOrder[0], 3),
                Deck3.new PlayingCard(Deck3.suitsInOrder[0], 5),
                Deck3.new Joker("black"),
                Deck3.new PlayingCard(Deck3.suitsInOrder[1], 2),
                Deck3.new PlayingCard(Deck3.suitsInOrder[2], 4),
                Deck3.new PlayingCard(Deck3.suitsInOrder[3], 6)
        };

        for (Deck3.Card c : cards) {
            Deck3.addCard(c);
        }

        Deck3.Card[] expected = new Deck3.Card[]{
                cards[0], cards[1], cards[3], cards[4],
                cards[5], cards[2], cards[6]};

        Deck3.moveCard(cards[2], 3);

        Deck3.Card cur = Deck3.head;
        for (int j = 0; j < expected.length; j++) {
            int i = Math.floorMod(-j, expected.length); // i goes 0, n-1, n-2, ..., 1
            // System.out.println(cur);
            assertEquals(expected[i],cur,"Expect card: " + expected[i] + " but got: " + cur);

            cur = cur.prev;
        }
    }

    @Test
    public void MoveCard_CheckPrev2() {
        Deck3 Deck3 = new Deck3();
        Deck3.Card[] cards = new Deck3.Card[]{
                Deck3.new PlayingCard(Deck3.suitsInOrder[0], 1),
                Deck3.new PlayingCard(Deck3.suitsInOrder[0], 3),
                Deck3.new PlayingCard(Deck3.suitsInOrder[0], 5),
                Deck3.new Joker("black"),
                Deck3.new PlayingCard(Deck3.suitsInOrder[1], 2),
                Deck3.new PlayingCard(Deck3.suitsInOrder[2], 4),
                Deck3.new PlayingCard(Deck3.suitsInOrder[3], 6)
        };

        for (Deck3.Card c : cards) {
            Deck3.addCard(c);
        }

        Deck3.Card[] expected = new Deck3.Card[]{
                cards[0], cards[3], cards[1], cards[2],
                cards[4], cards[5], cards[6]};

        Deck3.moveCard(cards[3], 4);

        Deck3.Card cur = Deck3.head;
        for (int j = 0; j < expected.length; j++) {
            int i = Math.floorMod(-j, expected.length); // i goes 0, n-1, n-2, ..., 1
            // System.out.println(cur);
            assertEquals(expected[i],cur,"Expect card: " + expected[i] + " but got: " + cur);

            cur = cur.prev;
        }
    }

    @Test
    public void Shuffle_Empty() {
        Deck3 Deck3 = new Deck3();
        Deck3.shuffle();

        assertNull(Deck3.head,"Deck3 should be empty.");

    }

    @Test
    public void Shuffle_Example() {
        Deck3 Deck3 = new Deck3();
        // example in instruction pdf
        // AC 2C 3C 4C 5C AD 2D 3D 4D 5D RJ BJ
        Deck3.Card[] arrDeck3 = new Deck3.Card[12];
        for (int i = 0; i < 10; i++) {
            int suit = i/5;
            int rank = i%5 + 1;
            Deck3.Card card = Deck3.new PlayingCard(Deck3.suitsInOrder[suit], rank);
            arrDeck3[i] = card;
            Deck3.addCard(card);
        }
        Deck3.Card rj = Deck3.new Joker("red");
        Deck3.Card bj = Deck3.new Joker("black");
        arrDeck3[10] = rj;
        arrDeck3[11] = bj;
        Deck3.addCard(rj);
        Deck3.addCard(bj);

        int seed = 10;
        Deck3.gen.setSeed(seed);
        Deck3.shuffle();

        // expected result
        // 3C 3D AD 5C BJ 2C 2D 4D AC RJ 4C 5D

        int[] shuffledIndex = {2,7,5,4,11,1,6,8,0,10,3,9};

        // .next references
        Deck3.Card cur = Deck3.head;
        for (int i = 0; i < 12; i++) {
            Deck3.Card expected = arrDeck3[shuffledIndex[i]];
            assertEquals(expected.getValue(),cur.getValue(),"Deck3 is not correctly shuffled.\n" +
                        "Forward references are not correctly set up. " +
                        "Expected card at index " + i + " iterating using .next is " + expected + " but got " + cur);

            cur = cur.next;
        }

        // .prev references
        cur = Deck3.head.prev;
        for (int i = 11; i >=0; i--) {
            Deck3.Card expected = arrDeck3[shuffledIndex[i]];
            assertEquals(expected.getValue(),cur.getValue(),"Deck3 is not correctly shuffled.\n" +
                        "Backward references are not correctly set up. " +
                        "Expected card at index " + i + " iterating using .prev is " + expected + " but got " + cur);

            cur = cur.prev;
        }
    }

    @Test
    public void Shuffle_FullDeck3() {
        Deck3 Deck3 = new Deck3();
        // all 54 cards
        Deck3.Card[] arrDeck3 = new Deck3.Card[54];
        for (int i = 0; i < 52; i++) {
            int suit = i/13;
            int rank = i%13 + 1;
            Deck3.Card card = Deck3.new PlayingCard(Deck3.suitsInOrder[suit], rank);
            arrDeck3[i] = card;
            System.out.println(card);
            Deck3.addCard(card);
        }
        Deck3.Card rj = Deck3.new Joker("red");
        Deck3.Card bj = Deck3.new Joker("black");
        arrDeck3[52] = rj;
        arrDeck3[53] = bj;
        Deck3.addCard(rj);
        Deck3.addCard(bj);

        int seed = 10;
        Deck3.gen.setSeed(seed);
        System.out.println("done");
        Deck3.shuffle();

        // expected result
        // 7S QD 7H JH KH KD 8C 4C 9S JD KC 9C 5C QC 2S 5S 10H 10D
        // 4S 5D 6H 4D 9D 8D 3H 6D 4H 7C RJ 9H 3C 2D JC 6C 8H JS 5H
        // AH BJ 3S 6S 3D QS AS 7D 2C AD KS 10S 8S 10C QH AC 2H
        int[] shuffledIndex = {
                45, 24, 32, 36, 38, 25, 7, 3, 47, 23, 12, 8, 4, 11, 40, 43, 35, 22,
                42, 17, 31, 16, 21, 20, 28, 18, 29, 6, 52, 34, 2, 14, 10, 5, 33, 49, 30,
                26, 53, 41, 44, 15, 50, 39, 19, 1, 13, 51, 48, 46, 9, 37, 0, 27};

        // .next references
        Deck3.Card cur = Deck3.head;
        for (int i = 0; i < 54; i++) {
            Deck3.Card expected = arrDeck3[shuffledIndex[i]];
            assertEquals(expected.getValue(),cur.getValue(),"Deck3 is not correctly shuffled.\n" +
                        "Forward references are not correctly set up. " +
                        "Expected card at index " + i + " is " + expected + " but got " + cur);

            cur = cur.next;
        }

        // .prev references
        cur = Deck3.head.prev;
        for (int i = 53; i >=0; i--) {
            Deck3.Card expected = arrDeck3[shuffledIndex[i]];
            assertEquals(expected.getValue(),cur.getValue(),"Deck3 is not correctly shuffled.\n" +
                        "Backward references are not correctly set up. " +
                        "Expected card at index " + i + " iterating using .prev is " + expected + " but got " + cur);

            cur = cur.prev;
        }
    }

    @Test
    public void Shuffle_NewCard() {
        Deck3 Deck3 = new Deck3();
        // example in instruction pdf
        // AC 2C 3C 4C 5C AD 2D 3D 4D 5D RJ BJ
        Set<Deck3.Card> cardSet = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            int suit = i/5;
            int rank = i%5 + 1;
            Deck3.Card card = Deck3.new PlayingCard(Deck3.suitsInOrder[suit], rank);
            Deck3.addCard(card);
            cardSet.add(card);
        }
        Deck3.Card rj = Deck3.new Joker("red");
        Deck3.Card bj = Deck3.new Joker("black");
        Deck3.addCard(rj);
        Deck3.addCard(bj);
        cardSet.add(rj);
        cardSet.add(bj);

        int seed = 10;
        Deck3.gen.setSeed(seed);
        Deck3.shuffle();

        Deck3.Card cur = Deck3.head;
        // forward ref
        for (int i = 0; i < 12; i++) {
            assertTrue(cardSet.contains(cur),"Shuffle should not create new cards.");

            cur = cur.next;
        }
        assertEquals(Deck3.head, cur,"Deck3 is not correctly shuffled. " +
                    "Tail does not connect to head or new cards were added.");


        // backward ref
        for (int i = 11; i >= 0; i--) {
            cur = cur.prev;
        }
        assertEquals(Deck3.head, cur,"Deck3 is not correctly shuffled. " +
                    "Backward references are not correctly set up.");

    }

    @Test
    public void Shuffle_SingleCard() {
        Deck3 Deck3 = new Deck3();
        Deck3.Card c = Deck3.new Joker("red");
        Deck3.addCard(c);

        Deck3.shuffle();

        assertTrue((Deck3.head.getValue() == c.getValue() &&
                c.next.getValue() == c.getValue() && c.prev.getValue() == c.getValue()),"Deck3 is not correctly shuffled when it only has one card.");

    }

    @Test
    public void Shuffle_Three() {
        Deck3 Deck3 = new Deck3();
        // AC 2C 3C 4C 5C
        Deck3.Card[] arrDeck3 = new Deck3.Card[5];
        for (int i = 0; i < 5; i++) {
            Deck3.Card card = Deck3.new PlayingCard(Deck3.suitsInOrder[0], i+1);
            arrDeck3[i] = card;
            Deck3.addCard(card);
        }

        int seed = 250;
        Deck3.gen.setSeed(seed);
        Deck3.shuffle();
        Deck3.shuffle();
        Deck3.shuffle();

        // expected first pass
        // AC, 4C, 5C, 3C, 2C

        // expected second pass
        // 5C, AC, 4C, 2C, 3C

        // expected third pass
        // AC, 5C, 3C, 2C, 4C

        int[] shuffledIndex = {0, 4, 2, 1, 3};

        // .next references
        Deck3.Card cur = Deck3.head;
        for (int i = 0; i < 5; i++) {
            Deck3.Card expected = arrDeck3[shuffledIndex[i]];
            assertEquals(expected.getValue(),cur.getValue(),"Deck3 is not correctly shuffled.\n" +
                        "Forward references are not correctly set up. " +
                        "Expected card at index " + i + " is " + expected + " but got " + cur);

            cur = cur.next;
        }

        // .prev references
        cur = Deck3.head.prev;
        for (int i = 4; i >=0; i--) {
            Deck3.Card expected = arrDeck3[shuffledIndex[i]];
            assertEquals(expected.getValue(),cur.getValue(),"Deck3 is not correctly shuffled.\n" +
                        "Backward references are not correctly set up. " +
                        "Expected card at index " + i + " iterating using .prev is " + expected + " but got " + cur);

            cur = cur.prev;
        }
    }
}
