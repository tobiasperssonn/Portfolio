  # labwork.S
  # Written 2015-2017 by F Lundevall
  # Skeleton file for IS1200/IS1500 lab 1.
  # The macros PUSH and POP are in the public domain.
  # Please add your own code at the end of the file.

  #
  # Please keep the two macros PUSH and POP unchanged
  #
.macro	PUSH reg
	addi	$sp,$sp,-4
	sw	\reg,0($sp)
.endm

.macro	POP reg
	lw	\reg,0($sp)
	addi	$sp,$sp,4
.endm
.global hexasc,delay,time2string 
  #
  # Please add your own code below this line
  #
.text 
hexasc:
	andi $a0,$a0,0xF	
	bge $a0,10,letters	
	addi $a0,$a0,0x30	
	j final		
	nop	
letters:
	addi $a0,$a0,0x37	
	j final
	nop
final:
	andi $v0,$a0,0x7F	
	jr $ra
	nop	

delay:
	PUSH "$ra"
	li $t0,0
	li $t2,120		#Adjust this constant
	jal whileloop
	nop
	
whileloop:
	ble $a0,$t0,finaldelay
	li $t1,0
	addi $a0,$a0,-1
	jal forloop
	nop
	j whileloop
	nop

forloop:
	bge $t1,$t2,whileloop
	addi $t1,$t1,1
	j forloop
	nop

finaldelay:
	POP "$ra"
	jr $ra
	nop

time2string:
	PUSH "$t0"		#Pushing a couple of registers that i will alter, and that the program used earlier
	PUSH "$t1"
	PUSH "$t2"
	PUSH "$ra"
	
	andi $a1,$a1,0xFFFF	#Isolate the 16 lsb of the $a1 register
	
				#Shift the numbers in the $a1 and only use the 4 lsb inorder to devide them in min and sec
	srl $t0,$a1,12		#Minute digit 1
	andi $t0,$t0,0xF
	
	srl $t1,$a1,8		#Minute digit 2
	andi $t1,$t1,0xF
	
	srl $t2,$a1,4		#Second digit 1
	andi $t2,$t2,0xF
	
	andi $t3,$a1,0xF	#Second digit 2
	
	move $t4,$a0		#Register containing the memory adress
	
	move $a0,$t0		#Convert minute digit 1 to ASCII
	jal hexasc
	nop
	sb $v0,0($t4)		#Store minute digit 1 in memory 
	
	move $a0,$t1		#Convert minute digit 2 to ASCII
	jal hexasc
	nop
	sb $v0,1($t4)		#Store minute digit 2 in memory with an offset of 1 bytes (the ASCII values each take up 1 byte of space at most)
	
	li $t5,0x3A		#Load the colon into a register
	sb $t5,2($t4)		#store the colon in memory
	
	move $a0,$t2		#Convert second digit 1 to ASCII
	jal hexasc
	nop
	sb $v0,3($t4)		#Store second digit 1 in memory 
	
	move $a0,$t3		#Convert second digit 2 to ASCII
	jal hexasc
	nop
	sb $v0,4($t4)		#Store second digit 2 in memory
	
	li $t6,0x00		#Load the null byte into a register
	sb $t6,5($t4)		#store the null byte in memory
	
	POP "$ra"		#Restoring the registers i pushed to the value they had before this subroutine was jumped to 
	POP "$t2"
	POP "$t1"
	POP "$t0"
	
	jr $ra
	nop
	

