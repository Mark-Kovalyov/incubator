package mayton.tinyasm.linux.elf.document;

import javax.annotation.concurrent.Immutable;

@Immutable
public class ELFDocument {

    public ELFHeader elfHeader;
    public ELFProgramHeaderTable ELFProgramHeaderTable;
    public ELFSectionHeaderTable sectionHeaderTable;

}
