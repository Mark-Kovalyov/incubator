package mayton.crypto;

import org.apache.commons.lang3.tuple.Pair;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.jcajce.provider.BouncyCastlePQCProvider;
import org.bouncycastle.pqc.jcajce.spec.DilithiumParameterSpec;
import org.bouncycastle.pqc.jcajce.spec.FalconParameterSpec;
import org.bouncycastle.pqc.jcajce.spec.SPHINCSPlusParameterSpec;

import java.security.*;
import java.security.spec.AlgorithmParameterSpec;
import java.util.*;

public class DemoQuantumResistance {

    public static void enumerateAll() {
        Security.addProvider(new BouncyCastlePQCProvider());

        for (Provider p : Security.getProviders()) {
            if (!p.getName().contains("BC")) continue;
            System.out.println("Provider: " + p.getName());
            Map<String, Set<String>> byType = new TreeMap<>();
            for (Provider.Service s : p.getServices()) {
                byType.computeIfAbsent(s.getType(), k -> new TreeSet<>()).add(s.getAlgorithm());
            }
            for (Map.Entry<String, Set<String>> e : byType.entrySet()) {
                System.out.println("  " + e.getKey() + ":");
                for (String alg : e.getValue()) {
                    System.out.println("    " + alg);
                }
            }
            System.out.println();
        }
    }



    public static KeyPair generateKeyPair(KeyPairGenerator kg, AlgorithmParameterSpec spec) throws Exception {
        kg.initialize(spec);
        KeyPair kp = kg.generateKeyPair();
        PrivateKey privkey = kp.getPrivate();
        PublicKey pubkey = kp.getPublic();

        System.out.printf("Private Key (%d bit): %s\n", privkey.getEncoded().length*8,  "...");//org.bouncycastle.util.encoders.Hex.toHexString(privkey.getEncoded()));
        System.out.printf("Public Key: (%d bits): %s\n", pubkey.getEncoded().length*8,  "...");//org.bouncycastle.util.encoders.Hex.toHexString(pubkey.getEncoded()));

        return kp;
    }

    public static void main( String[] args ) throws Exception {

        Security.addProvider(new BouncyCastlePQCProvider());
        enumerateAll();


        KeyPairGenerator dilithium = KeyPairGenerator.getInstance("Dilithium", "BCPQC");
        KeyPairGenerator falcon = KeyPairGenerator.getInstance("Falcon", "BCPQC");
        KeyPairGenerator sphinxplus = KeyPairGenerator.getInstance("SPHINCSPLUS", "BCPQC");

        List<Pair> algorithms_params = Arrays.asList(
            Pair.of(dilithium, DilithiumParameterSpec.dilithium2),
            Pair.of(dilithium,  DilithiumParameterSpec.dilithium3),
            Pair.of(dilithium,  DilithiumParameterSpec.dilithium5),
            Pair.of(falcon,  FalconParameterSpec.falcon_512),
            Pair.of(falcon,  FalconParameterSpec.falcon_1024),
            Pair.of(sphinxplus,  SPHINCSPlusParameterSpec.haraka_128f)
        );

        List<String> algorithmNames = Arrays.asList("Dilithium", "Falcon", "SPHINCSPLUS");

        for(Pair<KeyPairGenerator, AlgorithmParameterSpec> pair : algorithms_params) {
            KeyPairGenerator kpg = pair.getLeft();
            AlgorithmParameterSpec spec = pair.getRight();
            System.out.println("------- Generating key pair for " + kpg.getAlgorithm() + " with spec " + spec.getClass().getSimpleName() + " -------");
            generateKeyPair(kpg, spec);
        }

        KeyPair keyPair = generateKeyPair(dilithium, DilithiumParameterSpec.dilithium2);

        String mesage = "The quick brown fox jumps over the lazy dog";

        // Sign data

        Signature signature = Signature.getInstance("Dilithium2", "BCPQC");
        signature.initSign(keyPair.getPrivate());
        signature.update(mesage.getBytes());
        byte[] signatureBytes = signature.sign();

        // Verify signature
        signature.initVerify(keyPair.getPublic());
        signature.update(mesage.getBytes());
        boolean isValid = signature.verify(signatureBytes);
        System.out.println("Signature valid: " + isValid);

    }
}
