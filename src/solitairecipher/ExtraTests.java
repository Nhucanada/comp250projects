package solitairecipher;

import org.junit.jupiter.api.*;
import java.util.Arrays;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class ExtraTests {

    // utils

    public String Deck3ToString(Deck3 Deck3) {
        String[] result = new String[Deck3.numOfCards];
        Deck3.Card currentCard = Deck3.head;
        for (int i = 0; i < result.length; i++) {
            result[i] = currentCard.toString();
            currentCard = currentCard.next;
        }
        return Arrays.toString(result);
    }

    public Deck3 stringToDeck3(String cardList) {
        Deck3 newDeck3 = new Deck3();
        newDeck3.numOfCards = 0;
        String[] cardStrings = cardList.split(" ");
        Deck3.Card[] cards = new Deck3.Card[cardStrings.length];
        for (int i = 0; i < cardStrings.length; i++) {
            String cardStr = cardStrings[i];
            if (cardStr.equals("RJ")) {
                cards[i] = newDeck3.new Joker("red");
            } else if (cardStr.equals("BJ")) {
                cards[i] = newDeck3.new Joker("black");
            } else {
                String suit = "";
                int rank = 0;

                suit = switch (cardStr.charAt(cardStr.length() - 1)) {
                    case 'C' -> "clubs";
                    case 'H' -> "hearts";
                    case 'D' -> "diamonds";
                    case 'S' -> "spades";
                    default -> suit;
                };
                String rankStr = cardStr.substring(0, cardStr.length() - 1);
                rank = switch (rankStr) {
                    case "A" -> 1;
                    case "J" -> 11;
                    case "Q" -> 12;
                    case "K" -> 13;
                    default -> Integer.parseInt(rankStr);
                };
                cards[i] = newDeck3.new PlayingCard(suit, rank);
            }
        }
        for (Deck3.Card card : cards) {
            newDeck3.addCard(card);
        }
        return newDeck3;
    }

    public boolean[] testLinkage(String expectedFirst, String expectedLast, Deck3 testDeck3) {
        boolean[] result = new boolean[4];

        // Check next pointers
        Deck3.Card finalCard = testDeck3.head;
        for (int i = 0; i < testDeck3.numOfCards - 1; i++) {
            finalCard = finalCard.next;
        }
        result[0] = (expectedLast.equals(finalCard.toString()));

        // Check previous pointers
        Deck3.Card firstCard = finalCard;
        for (int i = 0; i < testDeck3.numOfCards - 1; i++) {
            firstCard = firstCard.prev;
        }
        result[1] = (expectedFirst.equals(firstCard.toString()));

        // Check that the Deck3 is circular
        result[2] = expectedLast.equals(firstCard.prev.toString());
        result[3] = expectedFirst.equals(finalCard.next.toString());

        return result;
    }

    // tests

    @Test
    @DisplayName("Test Deck3 exception handling")
    public void testDeck3ExceptionHandling() {
        // Check that numOfCardsPerSuit throws error if not within range [1,13]
        assertThrows(IllegalArgumentException.class,
                () -> {
                    Deck3 testDeck3 = new Deck3(500, 3);
                }, "should throw an exception if numOfCardsPerSuit is not in range [1,13]");
        assertThrows(IllegalArgumentException.class,
                () -> {
                    Deck3 testDeck3 = new Deck3(500, Deck3.suitsInOrder.length + 1);
                }, "should throw an exception if numOfSuits is not in range [1, suitsInOrder.length]");
    }

    @Test
    @DisplayName("Test Deck3 creation")
    public void testDeck3Creation() {
        Deck3 testDeck3 = new Deck3(4,3);
        String expectedDeck3 = "[AC, 2C, 3C, 4C, AD, 2D, 3D, 4D, AH, 2H, 3H, 4H, RJ, BJ]";
        assertTrue(Deck3ToString(testDeck3).equals(expectedDeck3), "Deck3 doesn't match expected");
    }

    @Test
    @DisplayName("Test numOfCards field")
    public void testDeck3CreationFields() {
        /* A new Deck3 with 2 suits and 4 cards each should have a total of 10 cards
           (8 suit cards, 2 jokers) */
        Deck3 testDeck3 = new Deck3(4,2);
        int expectedNumOfCards = 10;
        assertEquals(expectedNumOfCards, testDeck3.numOfCards, "numOfCards not initialized properly");
    }

    @Test
    @DisplayName("Test Deck3 linkage")
    public void testDeck3Linkage() {
        Deck3 testDeck3 = new Deck3(4, 3);
        Deck3.Card head = testDeck3.head;

        boolean[] linkageTest = testLinkage("AC", "BJ", testDeck3);
        assertTrue(linkageTest[0], "next fields not working properly");
        assertTrue(linkageTest[1], "previous fields not working");
        assertTrue(linkageTest[2], "firstCard.prev should be the end of the Deck3");
        assertTrue(linkageTest[3], "finalCard.next should be the head of the Deck3");

    }

    @Test
    @DisplayName("Test Deck3 copy constructor")
    public void testDeck3CopyConstructor() {
        Deck3 testDeck3 = new Deck3(3,3);
        Deck3 copyDeck3 = new Deck3(testDeck3);
        assertEquals(Deck3ToString(testDeck3), Deck3ToString(copyDeck3), "copy was not the same as original");
    }

    @Test
    @DisplayName("Test Deck3 copy constructor deep copy")
    public void testDeck3CopyConstructorDeepCopy() {
        Deck3 testDeck3 = new Deck3(3,3);
        Deck3 copyDeck3 = new Deck3(testDeck3);
        // the head of copyDeck3 should be a different object than the original
        assertFalse(testDeck3.head.equals(copyDeck3.head), "copy was not a deepcopy");
    }

    @Test
    @DisplayName("Test addCard")
    public void testAddCard() {
        Deck3 testDeck3 = new Deck3(1,1);
        Deck3.Card cardToAdd = new Deck3().new PlayingCard("clubs", 4);
        testDeck3.addCard(cardToAdd);
        assertEquals("[AC, RJ, BJ, 4C]", Deck3ToString(testDeck3), "card was not added" +
                " OR numOfCards field was not updated");
    }

    @Test
    @DisplayName("Test addCard linkage")
    public void testAddCardLinkage() {
        Deck3 testDeck3 = new Deck3(1,1);
        Deck3.Card cardToAdd = new Deck3().new PlayingCard("clubs", 4);
        Deck3.Card oldTail = testDeck3.head.prev;
        testDeck3.addCard(cardToAdd);
        assertEquals(cardToAdd, testDeck3.head.prev, "head.prev should be the new card");
        assertEquals(testDeck3.head, testDeck3.head.prev.next, "head.prev.next should be the head");
        assertEquals(oldTail, testDeck3.head.prev.prev, "head.prev.prev should be the old tail");
        assertEquals(cardToAdd, testDeck3.head.prev.prev.next, "head.prev.prev.next should be the new card");
    }

    @Test
    @DisplayName("Test shuffle")
    public void testShuffle() {
        Deck3 testDeck3 = new Deck3(5,2);
        Random oldGen = Deck3.gen;
        Deck3.gen = new Random(10);
        testDeck3.shuffle();
        Deck3.gen = oldGen;
        String expectedDeck3 = "[3C, 3D, AD, 5C, BJ, 2C, 2D, 4D, AC, RJ, 4C, 5D]";
        assertEquals(expectedDeck3, Deck3ToString(testDeck3), "Deck3 didn't shuffle as expected");
    }

    @Test
    @DisplayName("Test shuffle linkage")
    public void testShuffleLinkage() {
        Deck3 testDeck3 = new Deck3(4,3);
        Random oldGen = Deck3.gen;
        Deck3.gen = new Random(1);
        testDeck3.shuffle();
        Deck3.gen = oldGen;

        boolean[] linkageTest = testLinkage("2D", "4H", testDeck3);
        assertTrue(linkageTest[0], "next fields not working properly after shuffle");
        assertTrue(linkageTest[1], "previous fields not working properly after shuffle");
        assertTrue(linkageTest[2], "circular link from head.prev to tail broken after shuffle");
        assertTrue(linkageTest[3], "circular link from tail.next to head broken after shuffle");
    }

    @Test
    @DisplayName("Test locateJoker")
    public void testLocateJoker() {
        Deck3 testDeck3 = new Deck3(1,1);
        Deck3.Joker redJoker = (Deck3.Joker) testDeck3.head.next;
        Deck3.Joker blackJoker = (Deck3.Joker) testDeck3.head.next.next;
        assertEquals(redJoker, testDeck3.locateJoker("red"), "didn't find the red joker");
        assertEquals(blackJoker, testDeck3.locateJoker("black"), "didn't find the black joker");
    }

    @Test
    @DisplayName("Test locateJoker no joker")
    public void testLocateJokerNoJoker() {
        Deck3 testDeck3 = stringToDeck3("AC AC AC AC AC");
        assertNull(testDeck3.locateJoker("red"));
        assertNull(testDeck3.locateJoker("black"));
    }

    @Test
    @DisplayName("Test moveCard non-head case")
    public void testMoveCardNonHead() {
        Deck3 testDeck3 = new Deck3(2,2);
        testDeck3.moveCard(testDeck3.head.next, 2);
        String expectedDeck3 = "[AC, AD, 2D, 2C, RJ, BJ]";
        assertEquals(expectedDeck3, Deck3ToString(testDeck3), "card did not move down properly");
    }

    @Test
    @DisplayName("Test moveCard non-head case linkage")
    public void testMoveCardNonHeadLinkage() {
        Deck3 testDeck3 = new Deck3(2,2);
        testDeck3.moveCard(testDeck3.head.next, 7);

        boolean[] linkageTest = testLinkage("AC", "BJ", testDeck3);
        assertTrue(linkageTest[0], "next fields not working properly after moveCardNonHead");
        assertTrue(linkageTest[1], "previous fields not working properly after moveCardNonHead");
        assertTrue(linkageTest[2], "circular link from head.prev to tail broken after moveCardNonHead");
        assertTrue(linkageTest[3], "circular link from tail.next to head broken after moveCardNonHead");
    }

    /* If we call moveCard with a p value greater than the remaining spots below on the list, we need to
       move back to the top, skipping the head, and then move down more.*/

    @Test
    @DisplayName("Test moveCard with large p value")
    public void testMoveCardLargePValue() {
        Deck3 testDeck3 = new Deck3(2,2);
        testDeck3.moveCard(testDeck3.head.next, 7);
        String expectedDeck3 = "[AC, AD, 2D, 2C, RJ, BJ]";
        assertEquals(expectedDeck3, Deck3ToString(testDeck3), "card did not move down properly (large p value)");
    }

    @Test
    @DisplayName("Test moveCard head case")
    public void testMoveCardHead() {
        Deck3 testDeck3 = new Deck3(2,2);
        testDeck3.moveCard(testDeck3.head, 2);
        String expectedDeck3 = "[AC, 2D, RJ, BJ, 2C, AD]";
        assertEquals(expectedDeck3, Deck3ToString(testDeck3));
    }

    @Test
    @DisplayName("Test moveCard head case linkage")
    public void testMoveCardHeadLinkage() {
        Deck3 testDeck3 = new Deck3(2,2);
        testDeck3.moveCard(testDeck3.head, 2);

        boolean[] linkageTest = testLinkage("AC", "AD", testDeck3);
        assertTrue(linkageTest[0], "next fields not working properly after moveCardHead");
        assertTrue(linkageTest[1], "previous fields not working properly after moveCardHead");
        assertTrue(linkageTest[2], "circular link from head.prev to tail broken after moveCardHead");
        assertTrue(linkageTest[3], "circular link from tail.next to head broken after moveCardHead");
    }

    @Test
    @DisplayName("Test tripleCut")
    public void testTripleCut() {
        Deck3 testDeck3 = stringToDeck3("AC 4C 7C 10C KC 3D 6D 9D QD 3C 6C BJ 9C QC 2D 5D 8D JD 2C RJ 5C 8C JC AD 4D 7D 10D KD");
        testDeck3.tripleCut(testDeck3.locateJoker("black"), testDeck3.locateJoker("red"));
        String expectedDeck3 = Deck3ToString(stringToDeck3("5C 8C JC AD 4D 7D 10D KD BJ 9C QC 2D 5D 8D JD 2C RJ AC 4C 7C 10C KC 3D 6D 9D QD 3C 6C"));
        assertEquals(expectedDeck3, Deck3ToString(testDeck3), "triple cut didn't work properly");
    }

    @Test
    @DisplayName("Test tripleCut linkage")
    public void testTripleCutLinkage() {
        Deck3 testDeck3 = new Deck3(2,2);
        testDeck3.tripleCut(testDeck3.head.next, testDeck3.head.next.next.next);

        boolean[] linkageTest = testLinkage("RJ", "AC", testDeck3);
        assertTrue(linkageTest[0], "next fields not working properly after tripleCut");
        assertTrue(linkageTest[1], "previous fields not working properly after tripleCut");
        assertTrue(linkageTest[2], "circular link from head.prev to tail broken after tripleCut");
        assertTrue(linkageTest[3], "circular link from tail.next to head broken after tripleCut");
    }

    @Test
    @DisplayName("Test countCut")
    public void testCountCut() {
        Deck3 testDeck3 = stringToDeck3("5C 8C JC AD 4D 7D 10D KD BJ 9C QC 2D 5D 8D JD 2C RJ AC 4C 7C 10C KC 3D 6D 9D QD 3C 6C");
        testDeck3.countCut();
        String expectedDeck3 = Deck3ToString(stringToDeck3("10D KD BJ 9C QC 2D 5D 8D JD 2C RJ AC 4C 7C 10C KC 3D 6D 9D QD 3C 5C 8C JC AD 4D 7D 6C"));
        assertEquals(expectedDeck3, Deck3ToString(testDeck3), "countCut didn't work properly");
    }

    @Test
    @DisplayName("Test countCut linkage")
    public void testCountCutLinkage() {
        Deck3 testDeck3 = new Deck3(2,2);
        Deck3.PlayingCard cardToAdd = new Deck3().new PlayingCard("spades", 1);
        testDeck3.addCard(cardToAdd);
        testDeck3.countCut();

        boolean[] linkageTest = testLinkage("BJ", "AS", testDeck3);
        assertTrue(linkageTest[0], "next fields not working properly after countCut");
        assertTrue(linkageTest[1], "previous fields not working properly after countCut");
        assertTrue(linkageTest[2], "circular link from head.prev to tail broken after tripleCut");
        assertTrue(linkageTest[3], "circular link from tail.next to head broken after tripleCut");
    }

    @Test
    @DisplayName("Test lookUpCard")
    public void testLookUpCard() {
        Deck3 testDeck3 = new Deck3(5,2);
        Random oldGen = Deck3.gen;
        Deck3.gen = new Random(10);
        testDeck3.shuffle();
        Deck3.gen = oldGen;
        String expectedCard = "5C";
        assertEquals(expectedCard, testDeck3.lookUpCard().toString(), "didn't find the expected card");
    }

    @Test
    @DisplayName("Test lookUpCard joker")
    public void testLookUpCardJoker() {
        Deck3 testDeck3 = stringToDeck3("3C AC AD RJ AC AD AC AD");
        assertNull(testDeck3.lookUpCard(), "make sure to return null if you find a joker");
    }

    @Test
    @DisplayName("Test generateNextKeystreamValue")
    public void testGenerateNextKeystreamValue() {
        Deck3 testDeck3 = stringToDeck3("AC 4C 7C 10C KC 3D 6D 9D QD BJ 3C 6C 9C QC 2D 5D 8D JD RJ 2C 5C 8C JC AD 4D 7D 10D KD");
        assertEquals(11, testDeck3.generateNextKeystreamValue());
    }

    @Test
    @DisplayName("Test getKeyStream")
    public void testGetKeyStream() {
        Deck3 keyDeck = stringToDeck3("AC 4C 7C 10C KC 3D 6D 9D QD BJ 3C 6C 9C QC 2D 5D 8D JD RJ 2C 5C 8C JC AD 4D 7D 10D KD");
        SolitaireCipher keyGen = new SolitaireCipher(keyDeck);
        int[] keyStream = keyGen.getKeystream(12);
        String expectedKeys = Arrays.toString(new int[]{11, 9, 23, 7, 10, 25, 11, 11, 7, 8, 9, 3});
        assertEquals(expectedKeys, Arrays.toString(keyStream));
    }

    @Test
    @DisplayName("Test encode")
    public void testEncode() {
        Deck3 keys = stringToDeck3("AC 4C 7C 10C KC 3D 6D 9D QD BJ 3C 6C 9C QC 2D 5D 8D JD RJ 2C 5C 8C JC AD 4D 7D 10D KD");
        SolitaireCipher keyGen = new SolitaireCipher(keys);
        String encodedMsg = keyGen.encode("IsthatyouBob");
        String expectedMsg = "TBQOKSJZBJXE";
        assertEquals(expectedMsg, encodedMsg);
    }

    @Test
    @DisplayName("Test decode")
    public void testDecode() {
        Deck3 keyss = stringToDeck3("AC 4C 7C 10C KC 3D 6D 9D QD BJ 3C 6C 9C QC 2D 5D 8D JD RJ 2C 5C 8C JC AD 4D 7D 10D KD");
        SolitaireCipher keyGen = new SolitaireCipher(keyss);
        String decodedMsg = keyGen.decode("TBQOKSJZBJXE");
        String expectedMsg = "ISTHATYOUBOB";
        assertEquals(expectedMsg, decodedMsg);
    }

    @Test
    @DisplayName("Test moving card to original position")
    public void testMoveCardOriginal() {
        Deck3 testDeck3 = new Deck3(2, 2);
        testDeck3.moveCard(testDeck3.head.next, 5);
        String expectedDeck3 = "[AC, 2C, AD, 2D, RJ, BJ]";

        assertEquals(expectedDeck3, Deck3ToString(testDeck3), "card did not move down properly (back to original position)");

        boolean[] linkageTest = testLinkage("AC", "BJ", testDeck3);
        assertTrue(linkageTest[0], "next fields not working properly after moveCardNonHead");
        assertTrue(linkageTest[1], "previous fields not working properly after moveCardNonHead");
        assertTrue(linkageTest[2], "circular link from head.prev to tail broken after moveCardNonHead");
        assertTrue(linkageTest[3], "circular link from tail.next to head broken after moveCardNonHead");

    }

    /*
     * Moving the card only one position down
     */
    @Test
    @DisplayName("Test moving card just one position down")
    public void testMoveCardOne() {
        Deck3 testDeck3 = new Deck3(2, 2);
        testDeck3.moveCard(testDeck3.head.next, 1);
        String expectedDeck3 = "[AC, AD, 2C, 2D, RJ, BJ]";

        assertEquals(expectedDeck3, Deck3ToString(testDeck3), "card did not move down properly (1 pos down)");
        boolean[] linkageTest = testLinkage("AC", "BJ", testDeck3);

        assertTrue(linkageTest[0], "next fields not working properly after moveCardNonHead");
        assertTrue(linkageTest[1], "previous fields not working properly after moveCardNonHead");
        assertTrue(linkageTest[2], "circular link from head.prev to tail broken after moveCardNonHead");
        assertTrue(linkageTest[3], "circular link from tail.next to head broken after moveCardNonHead");

    }


    @Test
    @DisplayName("Test tripleCut with empty section before first card")
    public void testTripleCutEmptyFirst() {
        Deck3 testDeck3 = stringToDeck3("RJ AC 2C 3D QD BJ 2D 4C");
        testDeck3.tripleCut(testDeck3.head, testDeck3.head.prev.prev.prev);
        String expectedDeck3 = "[2D, 4C, RJ, AC, 2C, 3D, QD, BJ]";
        assertEquals(expectedDeck3, Deck3ToString(testDeck3));

        boolean[] linkageTest = testLinkage("2D", "BJ", testDeck3);
        assertTrue(linkageTest[0], "next fields not working properly after tripleCut");
        assertTrue(linkageTest[1], "previous fields not working properly after tripleCut");
        assertTrue(linkageTest[2], "circular link from head.prev to tail broken after tripleCut");
        assertTrue(linkageTest[3], "circular link from tail.next to head broken after tripleCut");
    }


    @Test
    @DisplayName("Test tripleCut with empty section after second card")
    public void testTripleCutEmptyEnd() {
        Deck3 testDeck3 = stringToDeck3("AC 2C RJ 3D QD BJ");
        testDeck3.tripleCut(testDeck3.head.next.next, testDeck3.head.prev);
        String expectedDeck3 = "[RJ, 3D, QD, BJ, AC, 2C]";
        assertEquals(expectedDeck3, Deck3ToString(testDeck3));

        boolean[] linkageTest = testLinkage("RJ", "2C", testDeck3);
        assertTrue(linkageTest[0], "next fields not working properly after tripleCut");
        assertTrue(linkageTest[1], "previous fields not working properly after tripleCut");
        assertTrue(linkageTest[2], "circular link from head.prev to tail broken after tripleCut");
        assertTrue(linkageTest[3], "circular link from tail.next to head broken after tripleCut");

    }

    @Test
    @DisplayName("Test tripleCut with empty section before and after first card")
    public void testTripleCutEmptyEnds() {
        Deck3 testDeck3 = stringToDeck3("RJ 3D QD AC 2C BJ");
        testDeck3.tripleCut(testDeck3.head, testDeck3.head.prev);
        String expectedDeck3 = "[RJ, 3D, QD, AC, 2C, BJ]";
        assertEquals(expectedDeck3, Deck3ToString(testDeck3));

        boolean[] linkageTest = testLinkage("RJ", "BJ", testDeck3);
        assertTrue(linkageTest[0], "next fields not working properly after tripleCut");
        assertTrue(linkageTest[1], "previous fields not working properly after tripleCut");
        assertTrue(linkageTest[2], "circular link from head.prev to tail broken after tripleCut");
        assertTrue(linkageTest[3], "circular link from tail.next to head broken after tripleCut");

    }

    @Test
    @DisplayName("Test generateNextKeystreamValueJokerCase")
    public void testGenerateNextKeystreamValueJokerCase() {
        Deck3 testDeck3 = stringToDeck3("9C QC 2D 5D 8D JD 2C JC AC 4C 7C 10C KC 3D 6D 9D QD 3C RJ 6C AD 4D 7D BJ 10D KD 5C 8C");
        Deck3 trueValueDeck3 = stringToDeck3("10D KD BJ 9C QC 2D 5D 8D JD 2C JC AC 4C 7C 10C KC 3D 6D 9D QD 3C 5C 8C RJ AD 4D 7D 6C");
        int trueValue = trueValueDeck3.generateNextKeystreamValue();
        assertEquals(trueValue, testDeck3.generateNextKeystreamValue());
    }

    @Test
    @DisplayName("Test moveCard when input is the head and p value is large")
    public void testMoveHeadLargePValue() {
        Deck3 testDeck3 = new Deck3(2, 2);
        testDeck3.moveCard(testDeck3.head, 8);
        String expectedDeck3 = "[AC, RJ, BJ, 2C, AD, 2D]";

        assertEquals(expectedDeck3, Deck3ToString(testDeck3), "cards did not move to bottom properly (head + large p)");
        boolean[] linkageTest = testLinkage("AC", "2D", testDeck3);

        assertTrue(linkageTest[0], "next fields not working properly after testMoveHeadLargePValue");
        assertTrue(linkageTest[1], "previous fields not working properly after testMoveHeadLargePValue");
        assertTrue(linkageTest[2], "circular link from head.prev to tail broken after testMoveHeadLargePValue");
        assertTrue(linkageTest[3], "circular link from tail.next to head broken after testMoveHeadLargePValue");
    }

    @Test
    @DisplayName("Test countCut do nothing")
    public void testCountCutDoNothing() {
        Deck3 DdDeck3 = stringToDeck3("AC 2C 3C 4C 6C 5C");
        String expectedDeck3dd = Deck3ToString(DdDeck3);
        DdDeck3.countCut();
        assertEquals(expectedDeck3dd, Deck3ToString(DdDeck3));
    }
    }

