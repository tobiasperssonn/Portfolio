  # hexmain.asm
  # Written 2015-09-04 by F Lundevall
  # Copyright abandonded - this file is in the public domain.

	.text
main:
	li	$a0,17	 # change this to test different values

	jal	hexasc		# call hexasc
	nop			# delay slot filler (just in case)	

	move	$a0,$v0		# copy return value to argument register

	li	$v0,11		# syscall with v0 = 11 will print out
	syscall			# one byte from a0 to the Run I/O window
	
stop:	j	stop		# stop after one run
	nop			# delay slot filler (just in case)

  # You can write your own code for hexasc here
  #
hexasc:
	andi $a0,$a0,0xF	#Only look at the 4 least signifigant bits in the parameter. 0xF = 15 = 4 bits
	bge $a0,10,letters	#jump to the letters branch if the input parameter has a value above 10	
	addi $a0,$a0,0x30	#add 0x30 to the input parameter. 0x30 is the charachter 0	
	j final			#jump to the final subroutine
letters:
	addi $a0,$a0,0x37	#add 0x37 to the input parameter which puts us in the range of the ASCII letters 
	j final
final:
	andi $v0,$a0,0x7F	#Only store the 7 least signifigant bits in the return register. 0x7F = 127 = 7 bits
	jr $ra			#return to the main branch from where hexasc was called

