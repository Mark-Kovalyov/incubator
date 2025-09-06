package mayton.tinyasm.linux.elf.document;

import java.util.List;

import static java.util.Collections.unmodifiableList;

public class ELFProgramHeaderTable {

    public List<ELFRoDataAndTextPair> roDataAndTextPairs;
    public List<ELFData> elfData;
    
}
