# GetYourGuide - Tempelhof

Android application to fulfill the GetYourGuide takehome project requirements: the ability to browse and post reviews for [Berlin Tempelhof Airport: The Legend of Tempelhof](https://www.getyourguide.com/berlin-l17/tempelhof-2-hour-airport-history-tour-berlin-airlift-more-t23776/).

## Testing Instructions
Open .APK on a device or [test it out in the web browser!]( todo )

Open the project in Android Studio. debug using an emulator or usb device

Notes
- Toggle airplane mode to get an error response 
- Per the project instructions, the app UI "does not need to handle orientation changes." Therefore all activities are set to portrait mode and will not rotate.
- Per the instructions, the app should "allow users to browse all of the reviews...". I didn't implement an incremental loading feature, so it can stutter a bit on the 400+ reviews.


## Libraries Used
- Retrofit, OkHttp, Gson - for easy http requests and to convert the json to java objects
- rxAndroid - minimally used at the moment, just for the network calls
- Room - persistence library. Using it, as well as an in-memory cache, to store data offline