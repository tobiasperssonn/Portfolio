	addi $a0,$0,5		#Ge a0 det värde som är på lsb i den första instruktionen 
	
	add $a1,$0,$a0		#laddar in input värdet i ett register så vi kan hålla koll hur många gånger vi ska loopa i loop.
	add $a2,$0,$a0		#laddar in input värdet i ett annat register som bestämmer hur mycket vi sedan ska addera med.
	add $a3,$0,$a0		#laddar in input värdet i ett register så vi kan hålla koll hur många gånger loop har körts.
	add $v0,$0,1		#Ger v0 värdet 1 om inputen är 0, dvs !0 = 1
	
	beq $a0,$0,stop		#Om inputen är 0, hoppa direkt till slutet
	add $0,$0,$0		#nop	
	
	beq $a0,1,stop		#Om inputen är 0, hoppa direkt till slutet
	add $0,$0,$0		#nop
	
	add $v0,$0,$0		#Återställer v0 till värdet 0, ifall a0 inte är 0
	

loop:	addi $a1,$a1,-1
	beq $a1,0,main		#Hoppa till main om vi har loopat klart
	add $0,$0,$0		#nop
	
	add $v0,$v0,$a2		#Addera a2 till v0
	
	beq $0,$0,loop		#Loopa
	add $0,$0,$0		#nop
		

main:
	addi $a3,$a3,-1		#Räknar hur många gånger vi har kört loopen	
	beq $a3,2,stop		#Har a3 vairiabeln nått 2 så har vi kört klart loopen och den slutgiltiga fakulteten är hittat, den sista loopen är onödig att köra
	add $0,$0,$0		#nop
	
	add $a2,$0,$v0		#Sätter det nya värdet vi ska addera med som det värde vi räknade fram i loopen
	add $v0,$0,$0		#Resetta v0 till 0
	add $a1,$0,$a3		#Ger a1 det värde a3 har, vilket kommer göra att loopen kommer loopa 1 gång mindre än vad den gjorde sist
	
	beq $0,$0,loop		#Kör loopen igen, fast nu kommer vi addera med ett annat värde
	add $0,$0,$0		#nop
	
		
stop:	beq $0,$0,stop		#Loopa stop förevigt
	add $0,$0,$0		#nop
	
	

