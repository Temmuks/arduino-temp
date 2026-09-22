#include <WiFiS3.h>
#include <Arduino_LED_Matrix.h>
#include <ArduinoHttpClient.h>

#include <OneWire.h>
#include <DallasTemperature.h>

#include "frames.h"
#include "secrets.h"

//Wifi settings
char ssid[] = SECRET_SSID;
char password[] = SECRET_PASS;


//API Settings
char api[] = SECRET_API;
int port = 8080;
char endpoint[] = "/test";

WiFiClient wifi;
HttpClient client = HttpClient(wifi, api, port);

//Sensor Settings
  #define ONE_WIRE_BUS 2
  OneWire oneWire(ONE_WIRE_BUS);
  DallasTemperature sensors(&oneWire);
  int tempC;
  

ArduinoLEDMatrix matrix;

void setup() {
  // put your setup code here, to run once:

  Serial.begin(9600);

  sensors.begin();
  matrix.begin();

  Serial.println("Connecting to Wifi...");

  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    Serial.println("...");
    delay(1000);
  }

  Serial.println("Connected!");
  matrix.loadFrame(happy);

  delay(1000);
}

void loop() {
  //Check if wifi is connected every loop
  if (WiFi.status() == 3) {
    matrix.loadFrame(happy);
    delay(500);
  } else {
    matrix.loadFrame(danger);
    delay(500);
  }

  tempC = sensorCheck();

  postTemp(tempC, client);

  delay(5000);
}

  int sensorCheck(){
    sensors.requestTemperatures();

   return sensors.getTempCByIndex(0);

  }

void postTemp(int newTemp, HttpClient client) {
  String postData = String("{\"temp\":") + newTemp + "}";
  Serial.println("Sending postData...");

  client.beginRequest();
  client.post(endpoint);

  client.sendHeader("Content-Type", "application/json");
  client.sendHeader("Content-Length", postData.length());

  client.beginBody();
  client.print(postData);
  client.endRequest();

  int statusCode = client.responseStatusCode();
  String response = client.responseBody();

  Serial.print("Status code: ");
  Serial.println(statusCode);
  Serial.print("Response: ");
  Serial.println(response);
}