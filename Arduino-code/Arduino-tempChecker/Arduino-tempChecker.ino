#include <WiFiS3.h>
#include <Arduino_LED_Matrix.h>
#include <ArduinoHttpClient.h>

#include <OneWire.h>
#include <DallasTemperature.h>

#include "frames.h"
#include "secrets.h"

//Referenser
//https://arduinomodules.info/ky-001-temperature-sensor-module/

//Wifi settings
  char ssid[] = SECRET_SSID;
  char password[] = SECRET_PASS;


//API Settings
  char api[] = SECRET_API;
  int port = 8080;
  char endpoint[] = "/temp";

// Client Inits
  WiFiClient wifi;
  HttpClient client = HttpClient(wifi, api, port);

//Sensor Settings
  #define ONE_WIRE_BUS 2
  OneWire oneWire(ONE_WIRE_BUS);
  DallasTemperature sensors(&oneWire);
  int tempC;
  
//LED Screen init
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
  //Check if wifi is connected every loop and updates the LED screen with a happy face if it is connected, else it will show a warning on the LED screen!
  if (WiFi.status() == 3) {
    matrix.loadFrame(happy);
    delay(500);
  } else {
    matrix.loadFrame(danger);
    delay(500);
  }

  tempC = sensorCheck();

  postTemp(tempC, client);

  delay(60000);
}

  //Gets the temp from the sensor
  int sensorCheck(){
    sensors.requestTemperatures();

   return sensors.getTempCByIndex(0);

}

//Sends the temperature to backend server!
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