package mayton.tinyasm.linux.elf.document;

public class ELFHeader {
    public int magic;
    public int clazz;
    public int endian;
    public int version;
    public int os;
    public int abiVersion;
    public int eType;
    public int machine;
    public int eVersion;
    public int eEntry;
    public int ePhoff;
    public int eShoff;
    public int eFlaggs;
    public int eEhsize;
    public int ePhentsize;
    public int ePhnum;
    public int eShentsize;
    public int eShnum;
    public int eShstrndx;

    @Override
    public String toString() {
        return "ELFHeader{" +
                "magic=" + String.format("%08X", magic) +
                ", clazz=" + clazz +
                ", endian=" + endian +
                ", version=" + version +
                ", os=" + os +
                ", abiVersion=" + abiVersion +
                ", eType=" + eType +
                ", machine=" + machine +
                ", eVersion=" + eVersion +
                ", eEntry=" + eEntry +
                ", ePhoff=" + ePhoff +
                ", eShoff=" + eShoff +
                ", eFlaggs=" + eFlaggs +
                ", eEhsize=" + eEhsize +
                ", ePhentsize=" + ePhentsize +
                ", ePhnum=" + ePhnum +
                ", eShentsize=" + eShentsize +
                ", eShnum=" + eShnum +
                ", eShstrndx=" + eShstrndx +
                '}';
    }
}
