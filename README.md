# TestOpenPay


# Demo

- Video: https://drive.google.com/file/d/19rSpBeT2q1DuOCPN4XP5u4TIbit6jBkm/view?usp=drive_link

- Gif: https://drive.google.com/file/d/19rSpBeT2q1DuOCPN4XP5u4TIbit6jBkm/view?usp=drive_link


## App overview:

Made with **Clean Architecture** and **MVVM** 
Android App with Modular Architecture
This Android application is designed with a modular architecture, incorporating four distinct tabs, each implemented as a separate module. The project follows the MVVM (Model-View-ViewModel) pattern and Clean Architecture principles, ensuring a well-organized, maintainable, and scalable codebase. Each module encapsulates its own functionality and is integrated into the main activity within the app module. The architecture is divided into three layers: Data, Domain, and Presentation.

Among other technologies used, it should be noted that:
- dependency injector **Hilt** was used is implemented with a separate module for better concern handling
- For networking and downloading of images **Retrofit** and **Glide** were used respectively
- To store information in a local database, **Room** was implemented
- To handle asynchronization, **kotlin coroutines** were used
- For the use of the camera, **MediaStore.ACTION_IMAGE_CAPTURE** was used
- For the use of the map, **Google Maps** was used
- For the use of the location, **FusedLocationProviderClient** was used
- For the use of the location every 5 minutes, **Service** was used
- For the ui tab1 y tab2, **Jetpaclk Compose** was used
- For the ui tab3 y tab4, **layout xml** was used
- Finally, to carry out the unit tests, **MockK** was used

Modules
App Module

Description: This is the main module of the application, responsible for integrating all the feature modules and managing the navigation between different tabs. It contains the main activity that hosts the tabs.
Components:
MainActivity - The primary activity that contains a Fragment for handling the navigation between tabs.

Tab 1 (Module -> popularperson)
Tab 2 (Module -> movies)
Tab 3 (Module -> map)
Tab 4 (Module -> photo)

- Emphasizing that all four tabs adhere to the same architecture

Description: This module encapsulates all the functionality for every tab.
Layers:

- **Data Layer**: Handles data operations, including network requests and local database interactions.

- **Repositories**
Data sources
Entities

- **Domain Layer**: Contains the business logic of the app, including use cases and domain models.
Use cases
Domain models

- **Presentation Layer**: Manages the UI and user interactions using ViewModel.

ViewModel
Views (Fragments, Adapters)
View (Jetpack compose)

## Popular Person tab:
This tab requested: include information on the most popular user, where reviews can be viewed made by the user, and images.

In this tab, a card was implemented with the image and information of the most popular user provided by the endpoint `/person/popular` 
and below is the list with the shows (movies or series) for which he or she is recognized.

**The tab 1 is configured with Room for data storage**, enabling seamless online and offline functionality. This allows users to load and view movies even when their device is not connected to the internet, providing a continuous and reliable user experience.

## Case with internet connected:

| Profile tab loading                           | Profile tab loaded state |
|-----------------------------------------------|-------------------------|
| <img width="321" alt="Screen Shot 2024-06-17 at 2 48 57 PM" src="https://github.com/cmauroie/TestOpenPay/assets/6970907/f6aa51f0-9684-45ba-8360-7ffc2270c4cc"> | <img width="321" alt="Screen Shot 2024-06-17 at 2 52 41 PM" src="https://github.com/cmauroie/TestOpenPay/assets/6970907/2c14326f-26b2-4295-a5f4-8f27dd4d7745"> |


 ## Movies tab:
This tab requested: Load the list of all movies separated with the most popular movies, the most rated and the best recommendations.

In this tab, 3 calls to services were implemented:
- Popular movies: `/movie/popular`
- Top rated movies: `/movie/top_rated`
- Recommended movies: `/movie/{movie_id}/recommendations`

**The tab 2 is configured with Room for data storage**, enabling seamless online and offline functionality. This allows users to load and view movies even when their device is not connected to the internet, providing a continuous and reliable user experience.

- ## Case without internet connected:

| Movie tab first time without internet       | Movie tab loaded state |
|-----------------------------------------------|-------------------------|
| <img width="319" alt="Screen Shot 2024-06-17 at 3 04 28 PM" src="https://github.com/cmauroie/TestOpenPay/assets/6970907/d6b6ac9c-f2e8-4899-bf51-14e337b1101b"> | <img width="321" alt="Screen Shot 2024-06-17 at 3 06 49 PM" src="https://github.com/cmauroie/TestOpenPay/assets/6970907/7b83657d-fa8f-436a-94e6-65a40d016a4a"> |


## Map tab:

**The tab 3 is not configured with Room for data storage**
This tab requested: Consume from the Firebase console (Cloud Firestore) and display the locations on a Map, additionally showing the
storage date.
Add the device location to a Firebase console to persist (Cloud Firestore) every 5 minutes and notify the user using NotificationCompat.


In the menu map include two buttons:

Start report location (button):

When start button is selected it checks/requests the location permission that the user grants to the application. In this case notifications are enabled
When all permissions are granted. The app start a service to work task in background and the **FusedLocationProviderClient** is started to provide the location every 5 minutos, when the app receive the location, this is service receives the location every 5 minutes it is sent to the map fragment through a launcher that allows you to configure a callback for when the application is onResumen.

When this location is received it is sent to the firebase store and displayed on the map.

When the location is received and the application has been destroyed, the location is registered in the firebase store but the locations are not saved in room, this was thinking that the tests with room were evaluated with the first 2 tabs. For this scenario, the app will register locations in the Firebase store in the foreground and background, and the points on the map are shown with the date but only updated while the app is in the foreground.

Stop report location (button):

When stop button is selected all location reporting processes stop. In this case notifications are disabled

| Map tap with location sent to firebase firestore  | Map tab notificaion enabled |
|-----------------------------------------------|-------------------------|
<img width="1574" alt="Screen Shot 2024-06-17 at 9 20 22 AM" src="https://github.com/cmauroie/TestOpenPay/assets/6970907/7f65d601-09bc-454f-bdb0-ba94b43672dd"> | <img width="317" alt="Screen Shot 2024-06-17 at 3 31 56 PM" src="https://github.com/cmauroie/TestOpenPay/assets/6970907/4a1ae248-5148-4713-8064-607eeca8e35f"> |


## Photos tab:

**The tab 4 is not configured with Room for data storage**(Due to time issues, it was not possible to integrate the room.)
This tab requested: Capture or select one or more images from the device's gallery and upload them to Firebase Storage.

This tab includes 4 buttons (TAKE PHOTO, GALLERY, SAVE PHOTO, SAVE PHOTOS).

The take photo button(TOMAR FOTO) allows you to validate the camera permissions and, if approved, allows you to use the device's camera to capture an image. When the image is captured, it is displayed on the screen on the left.

The save photo button(GUARDAR FOTO), when selected, sends the image captured from the device's camera to Firebase storage and it is stored.

The GALLERY button (GALERIA) allows you to enter the device's gallery and select one or more images. When the user selects the images, the number of selected photos is displayed on the screen.

The save photo button(GUARDAR FOTOS), when selected, sends the selected images from the device gallery to Firebase storage and these remain stored.




Finally, if the user presses the "Save" button the selected images will be uploaded to Firebase Storage.

| Photo take from camera                             | Images selected from Gallery           | Images in Firebase Storage                                       |
|----------------------------------------------|----------------------------------------------|--------------------------------------------------------|
| <img width="314" alt="Screen Shot 2024-06-17 at 3 49 18 PM" src="https://github.com/cmauroie/TestOpenPay/assets/6970907/337f94ec-9505-45a8-a045-4cd92db55625"> | <img width="308" alt="Screen Shot 2024-06-17 at 3 51 55 PM" src="https://github.com/cmauroie/TestOpenPay/assets/6970907/2dc249d2-52b7-4549-83b6-5e09974539c5"> | <img width="1166" alt="Screen Shot 2024-06-17 at 4 03 37 PM" src="https://github.com/cmauroie/TestOpenPay/assets/6970907/05953836-202c-49e7-91b7-cb47650209cc"> |

## Unit test (Popular Person tab):

The unit tests were done only in the popular persona module, these included tests for:

- Usecase
- Remote datasource
- Local datasource
- ViewModel

| Unit test implemented  |
|-----------------------------------------------|
<img width="1711" alt="Screen Shot 2024-06-17 at 1 03 05 PM" src="https://github.com/cmauroie/TestOpenPay/assets/6970907/20f06872-580b-46a8-a934-a363021ff137"> |


Among other technologies used, it should be noted that:

- **Hilt** was used as a dependency injector and is implemented with a separate module for better concern handling
- For networking and downloading of images **Retrofit** and **Glide** were used respectively
- To store information in a local database, **Room** was implemented
- To handle asynchronization, **kotlin coroutines** were used
- For the use of the camera, **CameraX** was used
- For the use of the map, **Google Maps** was used
- For tab 1 and tab 2, **Jetpack Compose** was used in fragment container
- Finally, to carry out the unit tests, **MockK** was used
