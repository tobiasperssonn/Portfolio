//Definerar konstanter
const int lightSensorPin = A0;
const int speakerPin = 11;

//Definerar variablar
int lightSensorValue = 0;
int randNum = 0;

//Funktion som bestämmer vilken pin som ska outputa spänning beroende på vilket nummer tärningen har slagit.
void throwDice(int num) {
  //1
  if(num == 1) {
    digitalWrite(6, HIGH);
  }

  //2
  else if(num == 2) {
    digitalWrite(10, HIGH);
    digitalWrite(7, HIGH);
  }

  //3
  else if(num == 3) {
    digitalWrite(7, HIGH);
    digitalWrite(6, HIGH);
    digitalWrite(10, HIGH);   
  }

  //4
  else if(num == 4) {
    digitalWrite(9, HIGH);
    digitalWrite(7, HIGH);
    digitalWrite(10, HIGH);
    digitalWrite(5, HIGH);     
  }

  //5
  else if(num == 5) {
    digitalWrite(9, HIGH);
    digitalWrite(7, HIGH);
    digitalWrite(10, HIGH);
    digitalWrite(5, HIGH);
    digitalWrite(6, HIGH); 
  }

  //6
  else {
    digitalWrite(9, HIGH);
    digitalWrite(8, HIGH);
    digitalWrite(7, HIGH);
    digitalWrite(10, HIGH);
    digitalWrite(4, HIGH);
    digitalWrite(5, HIGH);
  }
}

//Funktion som ställer in att alla pins ska ha spänningen 0V
void clearDice() {
  digitalWrite(10, LOW);
  digitalWrite(4, LOW);
  digitalWrite(5, LOW);
  digitalWrite(6, LOW);
  digitalWrite(7, LOW);
  digitalWrite(8, LOW);
  digitalWrite(9, LOW);
}

//Funktion som bestämer vilka frekvensser av toner som högtalaren ska spela up beroende på de slaggna nummret.
void speakerTone(int num) {
  if(num == 6) {
    tone(speakerPin, 2000);
    delay(150);
    noTone(speakerPin);

    tone(speakerPin, 3000);
    delay(150);
    noTone(speakerPin);

    tone(speakerPin, 4000);
    delay(150);
    noTone(speakerPin);

    tone(speakerPin, 5000);
    delay(150);
    noTone(speakerPin);
  }
  else {
    tone(speakerPin, 2000);
    delay(150);
    noTone(speakerPin);

    tone(speakerPin, 1000);
    delay(150);
    noTone(speakerPin);
  }
}

void setup() {
  //Etablerar seriel kontakt mellan datorn och arduino kortet via usb kabeln med en "baud rate" på 9600
  Serial.begin(9600);

  //Definerar att de pinsen som ledsen är kopplade till är till för att outputa spänning.
  pinMode(4, OUTPUT);
  pinMode(5, OUTPUT);
  pinMode(6, OUTPUT);
  pinMode(7, OUTPUT);
  pinMode(8, OUTPUT);
  pinMode(9, OUTPUT);
  pinMode(10, OUTPUT);
  pinMode(11, OUTPUT);

}

void loop() {

  //Läser in sensor värdet från fototransistorn
  lightSensorValue = analogRead(lightSensorPin);
  delay(5);

  //Debugging information
  Serial.print("Light value: ");
  Serial.println(lightSensorValue);

  //Ifall transistorn blir blockerad från ljuset
  if(lightSensorValue < 100)
  {
    //Slumpar fram ett nummer mellan 1-6
    randNum = random(1,7);

    //Kallar på funktionen som sedan får ledsen att visa detta slumpade nummer
    throwDice(randNum);
    //Kallar på funktionen som spelar upp ljud i högtalarn
    speakerTone(randNum);

    //Vänta ett litet tag innan ett nytt nummer kan kastas
    delay(1000);

    //Återställer alla leds
    clearDice();
  }
}
