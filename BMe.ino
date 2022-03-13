#ifndef UNIT_TEST
#include <ESP8266WiFi.h>
#endif
#include <Wire.h>
#include <SPI.h>
#include <Adafruit_Sensor.h>
#include <Adafruit_BME280.h>
#include <IRremoteESP8266.h>
#include <IRrecv.h>
#include <IRutils.h>
#include <WiFiClient.h>

const char* ssid = "madhu12";
const char* pass = "gulabjam";
const char* host = "192.168.43.1";

#define BME_SCK 13
#define BME_MISO 12
#define BME_MOSI 11
#define BME_CS 10

#define SEALEVELPRESSURE_HPA (1013.25)

Adafruit_BME280 bme; // I2C
//Adafruit_BME280 bme(BME_CS); // hardware SPI
//Adafruit_BME280 bme(BME_CS, BME_MOSI, BME_MISO, BME_SCK); // software SPI

unsigned long delayTime;
const uint16_t kRecvPin = 14;
String check;
IRrecv irrecv(kRecvPin);

decode_results results;
WiFiClient client;
void setup() {

  Serial.begin(9600);
  Serial.println();
  Serial.print("conecting to: ");
  Serial.println(ssid);
  Serial.println("Try to connect to server: ");
  Serial.println(host);


  delay(1000);
  WiFi.begin(ssid, pass);

  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    Serial.print(".");
    irrecv.enableIRIn();  // Start the receiver
    while (!Serial)  // Wait for the serial connection to be establised.
      delay(50);
    Serial.println();
    Serial.print("IRrecvDemo is now running and waiting for IR message on Pin ");
    Serial.println(kRecvPin);
  }

  Serial.println();
  Serial.print("My IP: ");
  Serial.println(WiFi.localIP());
  Serial.print("Connecting to server");
  while (!client.connect(host, 3175)) {
    Serial.print(".");

    yield();
  }
  Serial.println();
  Serial.print("Conected to IP: ");
  Serial.println(host);
  client.println("Sensor");


  Serial.println(F("BME280 test"));
  bool status;
  // default settings
  // (you can also pass in a Wire library object like &Wire2)
  status = bme.begin(0X76);
  if (!status) {
    Serial.println("Could not find a valid BME280 sensor, check wiring!");
    while (1);
  }

  Serial.println("-- Default Test --");
  delayTime = 1000;
  Serial.println();
}


void loop() {

  if (!client.connected())
  {
    while (!client.connect(host, 3175)) {
      Serial.print(".");

      yield();
    }
  }
  if (check != "power")
  {

    String Values = "Values," + String(bme.readTemperature()) + "," + (bme.readPressure() / 100.0F) + "," + bme.readHumidity();
    Serial.println(Values);
    client.println(Values);


    //printValues();
    delay(100);
  }

  if (irrecv.decode(&results)) {
    decodeIR ( ) ;
    irrecv.resume();  // Receive the next value
  }

  String line = client.readStringUntil('\n');
  //Serial.print("Line:  " + line);
}


void printValues() {
  Serial.print("Temperature = ");
  Serial.print(bme.readTemperature());
  Serial.println(" *C");

  Serial.print("Pressure = ");

  Serial.print(bme.readPressure() / 100.0F);
  Serial.println(" hPa");

  Serial.print("Approx. Altitude = ");
  Serial.print(bme.readAltitude(SEALEVELPRESSURE_HPA));
  Serial.println(" m");

  Serial.print("Humidity = ");
  Serial.print(bme.readHumidity());
  Serial.println(" %");

  Serial.println();
}

void decodeIR ( )                // This will tell us which key Is pressed.

{
  if (results.value == 0x1FE48B7)
  {
    String keypad = "keypad," + String("power");
    check = "power";
    Serial.println(keypad);
    client.println(keypad);
    Serial.println("Remote_POWER");

    delay(2000);
    while (!client.connect(host, 3176)) {
      // Serial.print(".");

      yield();
    }
  }
  else
  {

    Serial.println();
    Serial.print("Conected to IP: ");
    Serial.println(host);
    client.println("Sensor");
    if (results.value == 0x1FE58A7)
    {

      client.println( "Remote_MODE");

      Serial.println("Remote_MODE");
      delay(2000);
      while (!client.connect(host, 3175)) {
        Serial.print(".");

        yield();
      }
      check = "notpower";
      Serial.println("conneccted now");
    }
    else if (results.value == 0x1FE7887)
    { client.println("Remote_MUTE");
      Serial.println("Remote_MUTE");
    }
    else if (results.value == 0x1FE807F)
    { client.println ("Remote_PAUSE") ;
      Serial.println ("Remote_PAUSE") ;
    }
    else if (results.value == 0x1FE40BF)
    {
      client.println ("Remote_REVERSE") ;
      Serial.println ("Remote_REVERSE") ;
    }
    else if (results.value == 0x1FEC03F)
    {
      client.println ("Remote_FORWARD") ;
      Serial.println ("Remote_FORWARD") ;
    }
    else if (results.value == 0x1FE20DF)
    {
      client.println ("Remote_EQUAL") ;
      Serial.println ("Remote_EQUAL") ;
    }
    else if (results.value == 0x1FEA05F)

    { client.println ("Remote_VOLUME-") ;
      Serial.println ("Remote_VOLUME-") ;
    }
    else if (results.value == 0x1FE609F)

    { client.println ("Remote_VOLUME+") ;
      Serial.println("Remote_VOLUME+");
    }
    else if (results.value == 0x1FEE01F)
    {
      client.println ("Remote_0") ;
      Serial.println ("Remote_0") ;
    }
    else if (results.value == 0x1FE50AF )
    {
      client.println ("Remote_1") ;
      Serial.println ("Remote_1") ;
    }

    else if (results.value == 0x1FED827)
    {
      client.println ("Remote_2" ) ;
      Serial.println ("Remote_2" ) ;

    }
    else if (results.value == 0x1FEF807)
    {
      client.println ("Remote_3") ;
      Serial.println ("Remote_3") ;

    }
    else if (results.value == 0x1FE30CF)
    {
      client.println( "Remote_4" ) ;
      Serial.println ( "Remote_4" ) ;

    }
    else if (results.value == 0x1FEB04F)
    {
      client.println ("Remote_5") ;
      Serial.println ("Remote_5") ;
    }
    else if (results.value == 0x1FE708F)
    {
      client.println ("Remote_6");
      Serial.println ("Remote_6");
    }

    else if (results.value == 0x1FE00FF)
    {
      client.println("Remote_7") ;
      Serial.println ("Remote_7") ;
    }
    else if (results.value == 0x1FEF00F)
    {
      client.println ("Remote_8") ;
      Serial.println ("Remote_8") ;
    }

    else if (results.value == 0x1FE9867)
    {
      client.println ("Remote_9") ;
      Serial.println ("Remote_9") ;
    }

    else if (results.value == 0x1FE10EF)
    {
      client.println ("Remote_REPEAT") ;
      Serial.println ("Remote_REPEAT") ;
    }

    else if (results.value == 0x1FE906F)
    {
      client.println ("Remote_U/SD") ;
      Serial.println ("Remote_U/SD") ;
    }
    else {

    }
  }
}

