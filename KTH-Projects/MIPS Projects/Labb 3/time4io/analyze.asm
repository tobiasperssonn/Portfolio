  # analyze.asm
  # This file written 2015 by F Lundevall
  # Copyright abandoned - this file is in the public domain.

	.text
main:
	li	$s0,0x30       #-Loads a starting value into $s0
loop:
	move	$a0,$s0		# copy from s0 to a0
	
	li	$v0,11		# syscall with v0 = 11 will print out
	syscall			# one byte from a0 to the Run I/O window

	addi	$s0,$s0,3	# what happens if the constant is changed?
	                       #-The constant determines the value that the registry $s0 increases by every iteration
	
	li	$t0,0x5d       #-0x5b is changed to 0x5d becouse the code now adds 3 every iteration which skips 0x5b
	bne	$s0,$t0,loop   #-Loops the above code while $s0 != $t0 
	nop			# delay slot filler (just in case) -Skapar bara en delay

stop:	j	stop		# loop forever here
	nop			# delay slot filler (just in case)

