
# Public API Used
The Unsplash API is a modern JSON API that surfaces all of the info you’ll need to build any experience for your users. Every image URL returned by the API can easily be manipulated to dynamically adjust the dimensions, crop, and quality of the image in realtime.

# SongSearchApp
PhotoSearchApp is a simple Android application that allows users to search and retrieve photos from Unsplash. The user can search from a variety of photo genres using the search bar at the top of the app. Once they've found an image they like, they can click on it to view more information about it and other offerings from the photographer

![Alt text](app/docs/images/search_screenshot.png?raw=true "Search Screen Screenshot") ![Alt text](app/docs/images/details_screenshot_1.png?raw=true "Details Screen Screenshot 1") ![Alt text](app/docs/images/details_screenshot_2.png?raw=true "Details Screen Screenshot 2")

# Software Development Approach
This software was developed using a Kanban approach.

# Architecture Design
The Project follows a MVVM with Repository pattern architecture. This architecture was chosen for:
- Separation of Concerns that provides a way to testing the architecture components in isolation and allows for the View classes to be updated without modifying the ViewModel classes.
- Resilience to configuration changes allows the ViewModel classes to store UI data that would otherwise be lost on screen rotation or activity lifecycle changes.
- Communication between fragments using a ViewModel class removes the need for fragments to communicate via an Activity using callbacks.

The View classes use data binding to communicate updates to their respective ViewModel classes. The ViewModel classes communicate with a Repository class using LiveData. This is then passed back to the View classes observing this LiveData. The Repository class communicates with a RESTful API using Retrofit and caches the response to a local Room database.

![Alt text](app/docs/images/mvvm_architecture.png?raw=true "MVVM Architecture")

# Libraries Used
- Hilt to provide constructor dependency injection to classes in the application
- Paging 3 to page data in from the backend
- Retrofit to provide access to the backend API endpoints
- AndroidX to provide Lifecycle, Navigation and LiveData functionality to the app
- Data binding to bind the inflated layout files to instances running in the application code.

# Further Improvements
- Implement custom tabs to load an author's portfolio on Unsplash
- Implement click functionality on the Details Fragment RecyclerView items
- Introduce local storage to persist data while the user is not connected to the internet