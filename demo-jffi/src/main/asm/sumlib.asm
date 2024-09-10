section .data

section .text
    global sum

; Calling convention for x86_64 user level functions
; %rdi, %rsi, %rdx, %rcx, %r8 ,%r9, ( stack ... ) 
sum:
    mov rax, rdi
    add rax, rsi
    ret
