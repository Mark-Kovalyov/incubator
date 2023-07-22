package mayton.crypto;

import javax.xml.validation.Validator;
import java.util.HashMap;
import java.util.Map;

public class Enigma {

    private int rotor1;
    private int rotor2;
    private int rotor3;

    private Map<Integer,Integer> rotor1mapping = new HashMap<>();
    private Map<Integer,Integer> rotor2mapping = new HashMap<>();
    private Map<Integer,Integer> rotor3mapping = new HashMap<>();

    private static String ALPHABET = "";

    public Enigma() {

    }

    public Enigma(int rotor1, int rotor2, int rotor3) {

        this.rotor1 = rotor1;
        this.rotor2 = rotor2;
        this.rotor3 = rotor3;
    }
}
