package mayton.kafka;

import org.apache.commons.codec.digest.MurmurHash3;
import org.apache.commons.lang3.Validate;
import org.checkerframework.common.value.qual.IntRange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

// int  == 4 byte == 8 BCD digits (F = separator)
// long == 8 byte == 16 BDC digits

public class HTDemo {

    static Logger logger = LoggerFactory.getLogger("ht-demo");

    public static void main(String[] args) throws NoSuchAlgorithmException {
        logger.info("Start demo");
        int K = 5_000_000;
        List<Integer> array = new ArrayList<>(K);
        IntStream.range(0, K).forEach(i -> array.add(i));
        Collections.shuffle(array);
        boolean collision;
        int size = K;
        int iteration = 1;
        int MAX_DEPTH = 2;
        int EMPTY_SLOT = Integer.MIN_VALUE;
        List<Integer> collisions = new ArrayList<>();
        int inserted = 0;
        do {
            //logger.info("Start population of hashSet # {} with size = {} and keys = {}", iteration, size, K);
            int[] hashSet = new int[size];
            for (int i = 0; i < size; i++) hashSet[i] = EMPTY_SLOT;
            for (int i = 0; i < K; i++) {
                int hash = MurmurHash3.hash32(array.get(i));
                int slot = Math.abs(hash) % size;
                if (hashSet[slot] == EMPTY_SLOT) {
                    hashSet[slot] = array.get(i);
                    inserted++;
                } else {
                    //logger.info("Detected collision (slot : {}, key : {}). Add to collision list.", slot, array.get(i));
                    collisions.add(array.get(i));
                }
            }
            iteration++;
        } while(iteration == 0);
        logger.info("Populated successfully!");
        logger.info("Inserted : {}", inserted);
        logger.info("Collision list size : {}", collisions.size());
        logger.info("Hash set physical size is {} slots ({} bytes)", size, size * 4);
        logger.info("Inserted/slots ratio : {}%", 100 * inserted / size);
        logger.info("Finish");
    }
}
