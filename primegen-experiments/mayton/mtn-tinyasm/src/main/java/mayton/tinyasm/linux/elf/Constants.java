package mayton.tinyasm.linux.elf;

public class Constants {
    public final int MAGIC_NUMBER_WORD = 0x7f_45_4c_46;

    public final int EI_CLASS_32_BYTE = 1; // 32x bit format
    public final int EI_CLASS_64_BYTE = 2; // 64x bit format

    public final int LITTLE_ENDIANNESS_BYTE = 1;
    public final int BIG_ENDIANNESS_BYTE = 2;

    public final int EI_VERSION_BYTE = 1; // byte

    public final int OSABI_LINUX_BYTE = 0x03;




}
