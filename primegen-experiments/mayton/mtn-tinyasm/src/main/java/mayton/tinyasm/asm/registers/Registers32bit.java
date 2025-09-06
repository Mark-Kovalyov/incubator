package mayton.tinyasm.asm.registers;

public enum Registers32bit {
    // Data Registers
    eax, // Accumulator
    ebx, // Base
    ecx, // Counter
    edx, // Data

    // Pointer Registers
    eip, // Instruction Pointer
    esp, // Stack Pointer
    epb, // Base Pointer

    // Index Registers
    esi,
    edi,

    // Control Registers

    // Segment Registers
    cs,
    ds,
    ss;
}
