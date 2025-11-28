package mayton.bigdata;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.bitcoinj.core.*;
import org.bitcoinj.params.MainNetParams;

public class ParseTransactionExample {
    public static void main(String[] args) throws DecoderException {

        String txHex = "0200000001..."; // your raw hex

        NetworkParameters params = MainNetParams.get();

        byte[] bytes = Hex.decodeHex(txHex);

        Transaction tx = new Transaction(params, bytes);


        System.out.println("TxID: " + tx.getTxId());
        System.out.println("Inputs: " + tx.getInputs().size());
        System.out.println("Outputs: " + tx.getOutputs().size());

        tx.getOutputs().forEach(out ->
                System.out.println("Output: " + out.getValue().toFriendlyString())
        );
    }
}

