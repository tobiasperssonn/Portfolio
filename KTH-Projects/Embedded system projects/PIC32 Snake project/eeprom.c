#include <stdint.h>
#include <pic32mx.h>

//The initialization procedure is taken from Section 24. in the PIC32 Family Reference Manual
void i2cInit()
{
    // Turns on the I2C
    I2C1CON = 0x8000;

    I2C1BRG = 0x0C2; // 40 Mhz from Table 24-2

    I2C1STAT = 0;   //Clears the status register
}

uint8_t eepromRead(void)
{
    //Start
    I2C1CONSET = 0x1;

    //Wait for the start sequence to finish
    while (I2C1CON & 0x1)
        ;
    
    I2C1RCV = 10100001;

    //Stop
    I2C1CONSET = 0x4;

    //Wait for the stop sequence to finish
    while (I2C1CON & 0x4)
        ;

    // Return the data that was read
    return I2C1RCV;
}

void eepromWrite(uint8_t data, uint8_t low, uint8_t high)
{
    //Start
    I2C1CONSET = 0x1;

    //Wait for the start sequence to finish
    while (I2C1CON & 0x1)
        ;
    
    // Enable receive
    //I2C1CONSET = 0x8;

    // Passes the adress where to store our data to the transmit
    // Adress to the EEPROM was found in the Basic i/o shield Reference Manual
    // Adress = 1010000, R/W bit = 0 (Write)
    I2C1TRN = 10100000;

    // Wait for the adress to transmit
    while (I2C1STAT & 0x4000)
        ;

    // Passes the data to the transmit
    I2C1TRN = data;

    while (I2C1STAT & 0x4000)
        ;
    

    //Stop
    I2C1CONSET = 0x4;

    //Wait for the stop sequence to finish
    while (I2C1CON & 0x4)
        ;
}