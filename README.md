# Arduino with tempsensor
## About
This is a schoolproject that aims to teach me more about IoT and how you can use an Arduino to send data to the internet.

The idea is to make an thermometer using Arduino and upload the latest temperature to a frontend via websocket.
The frontend also show statistics from the past seven days including min and max temperatures as well as an average temperature each day.

This could be used in a live scenario for a property manager to monitor and supervise the temperature in the properties.

## Reasoning
The reasoning why I choose the `DS18B20` temp sensor is that I wanted to be able to get minus degrees aswell, the sensor I first planed to use was a sensor that could meassure humidity as well as temperatures but that does not go below 0 degrees Celsius so that was not a fitting option for this project.

## Architecture
<img width="808" height="785" alt="image" src="https://github.com/user-attachments/assets/0a16ad80-b577-42ff-bd1b-d8e372c4211c" />

## What do i need to get started?
To get started you need the following:
### Hardware
* [**ARDUINO UNO R4**](https://www.kjell.com/se/produkter/el-verktyg/elektronik/arduino/utvecklingskort/arduino-uno-rev4-wifi-utvecklingskort-p88079)
* [**DS18B20 TEMP SCENSOR**](https://www.electrokit.com/en/temperatursensor-ds18b20)
* [**WIRES**](https://www.kjell.com/se/produkter/el-verktyg/elektronik/arduino/arduino-tillbehor/kopplingskablar-hane-hane-65-pack-p87212)
* [**BREADBOARD**](https://www.kjell.com/se/produkter/el-verktyg/elektronik/elektroniklabb/luxorparts-kopplingsdack-400-anslutningar-2-pack-p36283)
### Software
* [**Visual Studio Code**](https://code.visualstudio.com/download?_exp_download=fb315fc982)
* [**ARDUINO IDE**](https://support.arduino.cc/hc/en-us/articles/360019833020-Download-and-install-Arduino-IDE)
* [**NPM**](https://docs.npmjs.com/downloading-and-installing-node-js-and-npm)
* [**MONGODB**](https://www.mongodb.com/try/download/community-kubernetes-operator) (You can use a hosted version on [**ATLAS**](https://www.mongodb.com/lp/cloud/atlas/try4-reg?utm_source=google&utm_campaign=search_gs_pl_evergreen_atlas_core-high-int_prosp-brand_gic-null_emea-multi_ps-all_desktop_eng_lead&utm_term=mongodb%20atlas&utm_medium=cpc_paid_search&utm_ad=e&utm_ad_campaign_id=23926136321&adgroup=197004288389&cq_cmp=23926136321&gad_source=1&gad_campaignid=23926136321&gclid=Cj0KCQjwlNPVBhCMARIsAPZ5RqhQTaWK-RDMqxX2B0dtiPcPi6ruxhLPzyPrgLTKqLWjz4H2MDIxLGQaAoyxEALw_wcB))
## Getting started

Begin by cloning down the project using `git clone git@github.com:Temmuks/arduino-temp.git`

### Setting up the Arduino
* Start with setting up your Arduino using the following scheme
<img width="1106" height="1056" alt="image" src="https://github.com/user-attachments/assets/31378fd5-b4f2-4879-a221-318478c05bb6" />
 
* Then plug in your power source.
* Open Arduino-tempChecker.ino in your Arduino IDE
* Change name of `secrets.h.example` to `secrets.h`
* Change SECRET_SSID to your wifi name, SECRET_PASS to your wifi password and SECRET_API to your backends IP </br> (You can get this by typing ipconfig in your cmd and copy the Ipv4 adress")
* Save the `secrets.h` file and upload your code to the arduino by pressing <img width="33" height="28" alt="image" src="https://github.com/user-attachments/assets/311ae904-739d-49c0-8d2e-1cf2844672af" />
* If the physical Arduino now shows a happy face on the led display you are connected to wifi, if it shows a warning triangle you are not connected!

## Setting up the backend
* Go to `\Arduino-temp\arduino-temp-checker-server` and open this with your VSC
* Change name of `.env.example` to `.env`
* Change MONGO_URI to your mongoDB URI
* Run the application using the debugger or use `./mvnw spring-boot:run`

## Setting up the frontend
* Go to `\Arduino-temp\arduino-temp-checker-client` and open this with your VSC
* Change name of `.env.example` to `.env`
* Change VITE_API_URL to your backends IP
* use `npm install`
* use `npm run dev`
* Open your webbrowser and go to `http://localhost:5173/`

# API Endpoints
### GET endpoints
* `GET` /temp/chartdata: Returns a list of data containing Date, min temp, max temp, and average temp
  #### Statuscodes
  `200, OK` The server sucessfully handled your request. </br>
  `400, BADREQUEST` The server failed to handle your request.
  #### Response example
  ```
  [
    {
        "date": "2026-09-18",
        "min": -13,
        "max": 23,
        "average": 6.166666666666667
    },
    {
        "date": "2026-09-19",
        "min": -2,
        "max": 19,
        "average": 7.25
    },
    {
        "date": "2026-09-20",
        "min": -9,
        "max": 9,
        "average": 1.6666666666666667
    },
    {
        "date": "2026-09-21",
        "min": -11,
        "max": 26,
        "average": 2.5
    },
    {
        "date": "2026-09-22",
        "min": -17,
        "max": 24,
        "average": 4.2
    },
  ]
  ```

### POST endpoints
* `POST` /temp : Saves incomeing temperature to the database, and also converts and sends the temperature </br> to clients subscribed to `/topic/temp`
    #### Statuscodes
  `200, OK` The server sucessfully handled your request. </br>
  `400, BADREQUEST` The server failed to handle your request.
  #### Request example


  ```
  {
    "temp": 35  
  }
  ```

  #### Response example

  `200 OK`
  ```
  {
    "id": "6ab5086fa957b0e87bd87338",
    "temp": 35,
    "date": "2026-09-24"
  }
  ```
