# GetYourGuide - Tempelhof

Android application to fulfill the GetYourGuide takehome project requirements: the ability to browse and post reviews for [Berlin Tempelhof Airport: The Legend of Tempelhof](https://www.getyourguide.com/berlin-l17/tempelhof-2-hour-airport-history-tour-berlin-airlift-more-t23776/).

## Testing Instructions
Open the project in Android Studio. debug using an emulator or usb device

Notes
- Toggle airplane mode to get an error response 
- Per the project instructions, the app UI "does not need to handle orientation changes." Therefore all activities are set to portrait mode and will not rotate.


## Libraries Used
- Retrofit, OkHttp, Gson - for easy http requests and to convert the json to java objects
- rxAndroid - minimally used at the moment, just for the network calls