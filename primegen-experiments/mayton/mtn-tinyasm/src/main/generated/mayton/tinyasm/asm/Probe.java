package mayton.tinyasm.asm;

public class Probe {
    public static void main(String[] args) {
        X86 x86 = new X86.X86Builder()
                .addq()
                .cmpxchg()
                .build();
    }
}
