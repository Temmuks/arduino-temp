#include <WiFiS3.h>
#include <Arduino_LED_Matrix.h>
#include <ArduinoHttpClient.h>

#include "frames.h"
#include "secrets.h"

char ssid[] = SECRET_SSID;
char password[] = SECRET_PASS;
char api[] = SECRET_API;
int port = 8080;

WiFiClient wifi;
HttpClient client = HttpClient(wifi, api, port);

ArduinoLEDMatrix matrix;

void setup() {
  // put your setup code here, to run once:

  Serial.begin(9600);

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

  postTemp(50, client);

  delay(2000);
}


void postTemp(int newTemp, HttpClient client) {
  String postData = String("{\"temp\":") + newTemp + "}";
  Serial.println("Sending postData...");

  client.beginRequest();
  client.post("/test");

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