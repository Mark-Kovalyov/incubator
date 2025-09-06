package mayton.tinyasm.linux.elf.document;

import java.util.Collections;
import java.util.List;

public class ELFSectionHeaderTable {

    private List<ELFSegment> elfSegmentList;

    public List<ELFSegment> getElfSegmentList() {
        return Collections.unmodifiableList(elfSegmentList);
    }
}
