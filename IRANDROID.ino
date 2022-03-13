#ifndef UNIT_TEST
#include <ESP8266WiFi.h>
#endif
#include <WiFiClient.h>
#include <IRremoteESP8266.h>
#include <IRrecv.h>
#include <IRutils.h>

const char* ssid = "madhu12";
const char* pass = "gulabjam";
const char* host = "192.168.43.1";

const uint16_t kRecvPin = 14;

IRrecv irrecv(kRecvPin);

decode_results results;

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
  while (!client.connect(host, 3176)) {
    Serial.print(".");

    yield();
  }
  Serial.println();
  Serial.print("Conected to IP: ");
  Serial.println(host);

  client.println("Sensor");
  client.setTimeout(200);
}

void loop() {


  String line = client.readStringUntil('\n');
  Serial.print("Line:  " + line);

  if (irrecv.decode(&results)) {
    decodeIR ( ) ;
    irrecv.resume();  // Receive the next value
  }


  if (!client.connected())
  {
    while (!client.connect(host, 3176)) {
      Serial.print(".");

      yield();
    }
  }



}

void decodeIR ( )                // This will tell us which key Is pressed.

{

  switch (results.value)

  {
    case 0x1FE48B7:

      client.println( "Remote_POWER");
      Serial.println("Remote_POWER");

      break;
    case 0x1FE58A7:

      client.println( "Remote_MODE");
      Serial.println("Remote_MODE");
      break;
    case 0x1FE7887:
      client.println("Remote_MUTE");
      Serial.println("Remote_MUTE");
      break;
    case 0x1FE807F:
      client.println ("Remote_PAUSE") ;
      Serial.println ("Remote_PAUSE") ;
      break;
    case 0x1FE40BF:
      client.println ("Remote_REVERSE") ;
      Serial.println ("Remote_REVERSE") ;
      break ;
    case 0x1FEC03F:

      client.println ("Remote_FORWARD") ;
      Serial.println ("Remote_FORWARD") ;

      break;
    case 0x1FE20DF:

      client.println ("Remote_EQUAL") ;
      Serial.println ("Remote_EQUAL") ;
      break;
    case 0x1FEA05F:
      client.println ("Remote_VOLUME-") ;
      Serial.println ("Remote_VOLUME-") ;
      break ;
    case 0x1FE609F:
      client.println ("Remote_VOLUME+") ;
      Serial.println("Remote_VOLUME+");

      break;
    case 0x1FEE01F :
      client.println ("Remote_0") ;
      Serial.println ("Remote_0") ;

      break;
    case 0x1FE50AF :

      client.println ("Remote_1") ;
      Serial.println ("Remote_1") ;

      break;
    case 0x1FED827:

      client.println ("Remote_2" ) ;
      Serial.println ("Remote_2" ) ;

      break;
    case 0x1FEF807:
      client.println ("Remote_3") ;
      Serial.println ("Remote_3") ;

      break;
    case 0x1FE30CF:
      client.println( "Remote_4" ) ;
      Serial.println ( "Remote_4" ) ;

      break;
    case 0x1FEB04F:

      client.println ("Remote_5") ;
      Serial.println ("Remote_5") ;

      break;
    case 0x1FE708F :

      client.println ("Remote_6");
      Serial.println ("Remote_6");

      break;
    case 0x1FE00FF:
      client.println("Remote_7") ;
      Serial.println ("Remote_7") ;

      break;
    case 0x1FEF00F:

      client.println ("Remote_8") ;
      Serial.println ("Remote_8") ;

      break;
    case 0x1FE9867:

      client.println ("Remote_9") ;
      Serial.println ("Remote_9") ;

      break;
    case 0x1FE10EF:

      client.println ("Remote_REPEAT") ;
      Serial.println ("Remote_REPEAT") ;

      break;
    case 0x1FE906F:

      client.println ("Remote_U/SD") ;
      Serial.println ("Remote_U/SD") ;

      break;
    default:

      break;
  }
}

