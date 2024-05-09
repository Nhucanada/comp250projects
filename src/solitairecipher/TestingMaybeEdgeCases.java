package solitairecipher;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestingMaybeEdgeCases {

    @Test
    @DisplayName("Test encode")
    public void testEncode() {
        Deck key = new Deck(13, 4);
        SolitaireCipher keyGen = new SolitaireCipher(key);
        String encodedMsg = keyGen.encode("");
        String expectedMsg = "";
        assertEquals(expectedMsg, encodedMsg);
    }
    @Test
    @DisplayName("Test encode")
    public void testEncode2() {
        Deck key = new Deck(13, 4);
        SolitaireCipher keyGen = new SolitaireCipher(key);
        String encodedMsg = keyGen.encode("JESUS help M3 i AM ju5t 4 Girl!1!");
        String expectedMsg = "NBCSAGDDPVQHHXBRJHTT";
        assertEquals(expectedMsg, encodedMsg);
    }
    @Test
    @DisplayName("Test encode")
    public void testEncode3() {
        Deck key = new Deck(13, 4);
        SolitaireCipher keyGen = new SolitaireCipher(key);
        String encodedMsg = keyGen.encode("12345678...............<>>°`~è");
        String expectedMsg = "";
        assertEquals(expectedMsg, encodedMsg);
    }
    @Test
    @DisplayName("Test encode")
    public void testEncode4() {
        Deck key = new Deck(1,1);
        SolitaireCipher keyGen = new SolitaireCipher(key);
        String encodedMsg = keyGen.encode(")(*&?%$#@!1234567890ABCDEFGHIJKLmNoPqRsTUVWXYZ0987654321!@#$%?&*()");
        String expectedMsg = "BCDEFGHIJKLMNOPQRSTUVWXYZA";
        assertEquals(expectedMsg, encodedMsg);
    }

    @Test
    @DisplayName("Test encode")
    public void testEncode5() {
        Deck key = new Deck(12, 3);
        SolitaireCipher keyGen = new SolitaireCipher(key);
        String encodedMsg = keyGen.encode("My m0m m4de s0mE b4N4n4 mUfF!ns !mK IF y0u w4N7 s0mE :)))))))))))))))");
        String expectedMsg = "QKUFYLAOUOHZSLKZKSPSWPOWTZOYQW";
        assertEquals(expectedMsg, encodedMsg);
    }

    @Test
    @DisplayName("Test decode")
    public void testDecode() {
        Deck key = new Deck(13, 4);
        SolitaireCipher keyGen = new SolitaireCipher(key);
        String decodedMsg = keyGen.decode("BLHMFNWGXXFVSCEMAN");
        String expectedMsg = "XOXOXOXOXOXOXOXOXO";
        assertEquals(expectedMsg, decodedMsg);
    }

    @Test
    @DisplayName("Test decode")
    public void testDecode2() {
        Deck key = new Deck(13, 4);
        SolitaireCipher keyGen = new SolitaireCipher(key);
        String decodedMsg = keyGen.decode("ABGGTKAWRLPHIRZSFBGATKCQPNWZR");
        String expectedMsg = "WEWILLBERCHANDSUCCESSFULWOMEN";
        assertEquals(expectedMsg, decodedMsg);
    }

    @Test
    @DisplayName("Test decode")
    public void testDecode3() {
        Deck key = new Deck(9, 2);
        SolitaireCipher keyGen = new SolitaireCipher(key);
        String decodedMsg = keyGen.decode("");
        String expectedMsg = "";
        assertEquals(expectedMsg, decodedMsg);
    }

    @Test
    @DisplayName("Test decode")
    public void testDecode4() {
        Deck key = new Deck(1, 1);
        SolitaireCipher keyGen = new SolitaireCipher(key);
        String decodedMsg = keyGen.decode("IFMMP");
        String expectedMsg = "HELLO";
        assertEquals(expectedMsg, decodedMsg);
    }

    @Test
    @DisplayName("Test decode")
    public void testDecode5() {
        Deck key = new Deck(1, 1);
        SolitaireCipher keyGen = new SolitaireCipher(key);
        String decodedMsg = keyGen.decode("XIBUBSFZPVVQUPNZMJUUMFHPPGZHPPCFS");
        String expectedMsg = "WHATAREYOUUPTOMYLITTLEGOOFYGOOBER";
        assertEquals(expectedMsg, decodedMsg);
    }
}

