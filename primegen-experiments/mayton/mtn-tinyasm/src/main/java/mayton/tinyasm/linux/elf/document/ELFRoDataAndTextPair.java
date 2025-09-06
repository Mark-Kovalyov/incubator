package mayton.tinyasm.linux.elf.document;

public class ELFRoDataAndTextPair extends ELFSegment {
    private ELFRoData elfRoData;
    private ELFText elfText;

    public ELFRoData getElfRoData() {
        return elfRoData;
    }

    public void setElfRoData(ELFRoData elfRoData) {
        this.elfRoData = elfRoData;
    }

    public ELFText getElfText() {
        return elfText;
    }

    public void setElfText(ELFText elfText) {
        this.elfText = elfText;
    }
}
