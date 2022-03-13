#include <ESP8266WiFi.h>
#include <WiFiClient.h>

const char* ssid = "madhu12";
const char* pass = "gulabjam";
const char* host = "192.168.43.1";
     
const int buttonPin = 4; 
int buttonState = 0;
const int buttonPin1 = 5; 
int buttonState1 = 0; 
const int buttonPin2 = 16; 
int buttonState2 = 0; 
const int buttonPin3 = 14; 
int buttonState3 = 0; 
const int buttonPin4 = 12; 
int buttonState4 = 0; 
const int buttonPin5 = 13; 
int buttonState5 = 0;          
WiFiClient client;
void setup() {
  Serial.begin(115200);
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
  }

  Serial.println();
  Serial.print("My IP: ");
  Serial.println(WiFi.localIP());
 
  pinMode(buttonPin,INPUT);
  pinMode(buttonPin1,INPUT);
  pinMode(buttonPin2,INPUT);
  pinMode(buttonPin3,INPUT);
  pinMode(buttonPin4,INPUT);
  pinMode(buttonPin5,INPUT);
  
  Serial.print("Connecting to server");
  while (!client.connect(host, 3175)) {
    Serial.print(".");

    yield();
  }
   client.println("Button");

  
}

void loop() {
  Serial.println();
  Serial.print("Conected to IP: ");
  Serial.println(host);

 

  //Serial.println("Sending string to server: ");
 
  //client.println("RFID");
  //delay(1000);
  String line = client.readStringUntil('\n');
  Serial.print("Line:  "+line);
  buttonState = digitalRead(buttonPin);
  buttonState1 = digitalRead(buttonPin1);
  buttonState2 = digitalRead(buttonPin2);
  buttonState3 = digitalRead(buttonPin3);
  buttonState4 = digitalRead(buttonPin4);
  buttonState5 = digitalRead(buttonPin5);

  if (buttonState == HIGH) {
   client.println("Button1_On");
   Serial.println("Button1_On");
    //delay(100);
  }
  else {
    client.println("Button1_Off");
   // delay(100);
  }
   if (buttonState1 == HIGH) {
   client.println("Button2_On");
    //delay(100);
  }
  else {
    client.println("Button2_Off");
   // delay(100);
  }
   if (buttonState2 == HIGH) {
   client.println("Button3_On");
   // delay(100);
  }
  else {
    client.println("Button3_Off");
    //delay(100);
  }
   if (buttonState3 == HIGH) {
   client.println("Button4_On");
    //delay(100);
  }
  else {
    client.println("Button4_Off");
    //delay(100);
  }
   if (buttonState4 == HIGH) {
   client.println("Button5_On");
    //delay(100);
  }
  else {
    client.println("Button5_Off");
    //delay(100);
  }
   if (buttonState5 == HIGH) {
   client.println("Button6_On");
    //delay(100);
  }
  else {
    client.println("Button6_Off");
    //delay(100);
  }
}
