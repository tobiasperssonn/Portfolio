  # labwork.S
  # Written 2015-2017 by F Lundevall
  # Skeleton file for IS1200/IS1500 lab 1.
  # The macros PUSH and POP are in the public domain.
  # Please add your own code at the end of the file.

  #
  # Please keep the two macros PUSH and POP unchanged
  #
.global enable_interrupt 
  #
  # Please add your own code below this line
  #
.text 

enable_interrupt:
	ei
	jr $ra
	nop
	

