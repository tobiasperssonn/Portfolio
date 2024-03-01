  # timetemplate.asm
  # Written 2015 by F Lundevall
  # Copyright abandonded - this file is in the public domain.

.macro	PUSH (%reg)
	addi	$sp,$sp,-4
	sw	%reg,0($sp)
.end_macro

.macro	POP (%reg)
	lw	%reg,0($sp)
	addi	$sp,$sp,4
.end_macro

	.data
	.align 2
mytime:	.word 0x5957
timstr:	.ascii "text more text lots of text\0"
	.text
main:
	# print timstr
	la	$a0,timstr
	li	$v0,4
	syscall
	nop
	# wait a little
	li	$a0,1000
	jal	delay
	nop
	# call tick
	la	$a0,mytime
	jal	tick
	nop
	# call your function time2string
	la	$a0,timstr
	la	$t0,mytime
	lw	$a1,0($t0)
	jal	time2string
	nop
	# print a newline
	li	$a0,10
	li	$v0,11
	syscall
	nop
	# go back and do it all again
	j	main
	nop
# tick: update time pointed to by $a0
tick:	lw	$t0,0($a0)	# get time
	addiu	$t0,$t0,1	# increase
	andi	$t1,$t0,0xf	# check lowest digit
	sltiu	$t2,$t1,0xa	# if digit < a, okay
	bnez	$t2,tiend
	nop
	addiu	$t0,$t0,0x6	# adjust lowest digit
	andi	$t1,$t0,0xf0	# check next digit
	sltiu	$t2,$t1,0x60	# if digit < 6, okay
	bnez	$t2,tiend
	nop
	addiu	$t0,$t0,0xa0	# adjust digit
	andi	$t1,$t0,0xf00	# check minute digit
	sltiu	$t2,$t1,0xa00	# if digit < a, okay
	bnez	$t2,tiend
	nop
	addiu	$t0,$t0,0x600	# adjust digit
	andi	$t1,$t0,0xf000	# check last digit
	sltiu	$t2,$t1,0x6000	# if digit < 6, okay
	bnez	$t2,tiend
	nop
	addiu	$t0,$t0,0xa000	# adjust last digit
tiend:	sw	$t0,0($a0)	# save updated result
	jr	$ra		# return
	nop

  # you can write your code for subroutine "hexasc" below this line
  #
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
	li $t2,220		#Adjust this constant
	j whileloop
	nop
	
whileloop:
	ble $a0,0,finaldelay	#if the argument ms is less then or equal to 0, dont run the while loop
	li $t1,0		#the i variable in the for loop
	addi $a0,$a0,-1
	j forloop
	nop
	j whileloop
	nop

forloop:
	bge $t1,$t2,whileloop
	addi $t1,$t1,1
	j forloop
	nop

finaldelay:
	jr $ra
	nop

time2string:
	PUSH ($ra)		#Pushing a couple of registers that i will alter
	
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
	
	POP ($ra)		#Restoring the registers i pushed to the value they had before this subroutine was jumped to 
	
	jr $ra
	nop
	
