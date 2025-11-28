package mayton.bigdata;

import org.bitcoinj.base.Coin;
import org.bitcoinj.core.*;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.wallet.Wallet;

import java.io.File;

public class BitcoinjExample {

    public static void main(String[] args) {

        NetworkParameters params = TestNet3Params.get();

        File walletDir = new File("./wallet");

        WalletAppKit kit = new WalletAppKit(params, walletDir, "testwallet");

        // Start the node (SPV mode)
        kit.startAsync();
        kit.awaitRunning();

        System.out.println("BitcoinJ started. Your address:");
        System.out.println(kit.wallet().currentReceiveAddress());

        // Listen for incoming transactions
        kit.wallet().addCoinsReceivedEventListener((wallet, tx, prevBalance, newBalance) -> {
            Coin value = tx.getValueSentToMe(wallet);
            System.out.println("Received transaction!");
            System.out.println("Tx Hash: " + tx.getTxId());
            System.out.println("Value: " + value.toFriendlyString());
        });

        // Listen for outgoing transactions
        kit.wallet().addCoinsSentEventListener((wallet, tx, prevBalance, newBalance) -> {
            Coin value = tx.getValueSentFromMe(wallet);
            System.out.println("Sent transaction!");
            System.out.println("Tx Hash: " + tx.getTxId());
            System.out.println("Value: " + value.toFriendlyString());
        });
    }
}
