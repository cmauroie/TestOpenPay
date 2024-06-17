# TestOpenPay

This application was made following the guidelines of the statement provided



## App structure:

Architecturally, the application is made with **Clean Architecture** and **MVVM** 
Android App with Modular Architecture
This Android application is designed with a modular architecture, incorporating four distinct tabs, each implemented as a separate module. The project follows the MVVM (Model-View-ViewModel) pattern and Clean Architecture principles, ensuring a well-organized, maintainable, and scalable codebase. Each module encapsulates its own functionality and is integrated into the main activity within the app module. The architecture is divided into three layers: Data, Domain, and Presentation.

Among other technologies used, it should be noted that:
- **Hilt** was used as a dependency injector and is implemented with a separate module for better concern handling
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

## Profile tab:
This tab requested: include information on the most popular user, where reviews can be viewed made by the user, and images.

In this tab, a card was implemented with the image and information of the most popular user provided by the endpoint `/person/popular` 
and below is the list with the shows (movies or series) for which he or she is recognized.





in the presentation layer (App)
Among other technologies used, it should be noted that:
- **Hilt** was used as a dependency injector and is implemented with a separate module for better concern handling
- For networking and downloading of images **Retrofit** and **Picasso** were used respectively
- To store information in a local database, **Room** was implemented
- To handle asynchronization, **kotlin coroutines** were used
- For the use of the camera, **CameraX** was used
- For the use of the map, **Google Maps** was used
- Finally, to carry out the unit tests, **MockK** was used
