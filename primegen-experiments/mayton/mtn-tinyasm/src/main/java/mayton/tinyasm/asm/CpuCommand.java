package mayton.tinyasm.asm;

import javax.annotation.concurrent.Immutable;
import java.util.Arrays;

@Immutable
public class CpuCommand {

    public final String label;
    public final String mnemonic;
    public final Object[] operands;

    public CpuCommand(String mnemonic, Object... operands) {
        this.label = null;
        this.mnemonic = mnemonic;
        this.operands = operands;
    }

    public CpuCommand(String label, String mnemonic, Object... operands) {
        this.label = label;
        this.mnemonic = mnemonic;
        this.operands = operands;

    }

    @Override
    public String toString() {
        return label == null ? String.format("%s %s", mnemonic, operands) : String.format("%s %s %s", label, mnemonic, operands);
    }
}
