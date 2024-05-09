
package solitairecipher;

public class SolitaireCipher2 {
    public Deck2 key;

    public SolitaireCipher2(Deck2 key) {
        this.key = new Deck2(key); // deep copy of the deck
    }

    /* 
     
TODO: Generates a keystream of the given size*/
    public int[] getKeystream(int size) {
        int[] keystream = new int[size];
        for(int i = 0; i < size; i++){
            keystream[i] = this.key.generateNextKeystreamValue();}
        return keystream;}

    /* 
     
TODO: Encodes the input message using the algorithm described in the pdf.*/
    public String encode(String msg) {
        String message = "";
        for(int i = 0; i < msg.length(); i++){
            char c = msg.charAt(i);
            if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')){
                message += (c + "").toUpperCase();}}
        System.out.println(message.length());
        int[] keyStream = getKeystream(message.length());
        String msgEncoded = "";
        for(int i = 0; i < message.length(); i++){
            int unicodeValue = message.charAt(i) + keyStream[i];
            while(unicodeValue > 'Z'){
                unicodeValue -= 26;}
            msgEncoded += (char) unicodeValue;}
        System.out.println(msgEncoded);
        return msgEncoded;}

    /* 
     
TODO: Decodes the input message using the algorithm described in the pdf.*/
    public String decode(String msg) {
        String msgDecoded = "";
        int[] keyStream = getKeystream(msg.length());
        for(int i = 0; i < msg.length(); i++){
            int unicodeValue = msg.charAt(i) - keyStream[i];
            while(unicodeValue < 'A'){
                unicodeValue += 26;}
            msgDecoded += (char) unicodeValue;}
        return msgDecoded;}

}