# doordashlite
DoorDash Interview App

## Folder Structure
- /data
  - /data_sources
    - All files relating to external data sources (APIs)
  - /models
    - Classes modeling data returned from data sources
    - NOTE: These models are complete, but not all data elements are used in the app
- /ui
  - /adapters
    - All files relating to RecyclerView adapters
  - /presenters
    - All presenters including interfaces and base classes
  - /views
    - All views (Activities) including interfaces and base classes
- /util
  - All files for utility functions and constants


## Architecture Notes
This app uses a MVP design structure, defined as follows:

### Model
Simple class containing necessary data for display or functionality, usually fetched from remote data sources

### View
The Android Activity class, in charge of displaying data to the user. The view manages the capture user input. but does not handle that input.

### Presenter 
The presenter is in charge of fetching the appropriate models for the view to use. It also handles user input that it receives from the view.

The importance of this separation allows each element to function without knowing the logic of the other elements. It also tends to be clean, maintainable, and testable.


## Why MVP (vs others)?
I am aware of other design patterns (MVC, MVVM). In general I find MVP to be a better solution than MVC as it increases modularity via Presenter vs. Controller.
MVVM seems like a great solution, especially using Android's LiveData and ViewModel components. However, it's not a pattern that I have used professionally and did not want to try implementing something for the first time during this assignment. In the future, this would probably be something I would be keen to learn and implement.


## 3rd Party Libraries:
### Retrofit
Retrofit is a simple solution to making API calls and modeling them into appropriate objects.

### Glide
Glide is a simple async image loading library (with caching)

### Dagger2
Dagger2 is a dependency injection framework. NOTE: I only used this for the RemoteDataSource to provide a simple example of somewhere DI would be useful. As this project is not too complex, I didn’t think that there was a dire need for this. However, it serves as a good example of where to use DI in large, more complex projects.

### Butterknife
Butterknife removes boilerplate for linking views to corresponding member variables via annotations. Requires a higher minimum Android version (24)

### RxJava
RxJava provides a reactive framework for Java / Android. One of the main benefits is streamlining complex operations and simplifying synchronous and asynchronous functions. For example, RxJava allows you to easily call multiple functions on a background thread, join them, and handle the results on the main thread.


## Why Java (vs Kotlin)?
For the same reason I avoided MVVM, I didn’t want to try to write Kotlin for the first time when making this app. While I’ve played around with the language and have read up on it, I haven’t used it professionally. I love the idea of Kotlin, and have dealt with an Objective-C to Swift migration, so it’s something I’m excited to use in the future.


## Testing Notes
I chose to focus my unit tests on the important classes of the project: RemoteDataSource and DiscoverPresenter.

The RemoteDataSource handles fetching and parsing the data from the API, using Retrofit. In order to test this, I mocked the API response, so the test covers the code path through Retrofit, including the GSON parsing that creates the appropriate object.

The Presenters are simple to test, but important in that they appropriately link the View and the Models. I mocked the RemoteDataSource (which is tested separately) and made sure that the appropriate calls from the View will in turn call the appropriate functions in the View with the correct data.

I chose not to do UI tests as in terms of importance, they are higher up than core unit tests. With more time it would be cool to include tests for the Views.

## Final Notes
I hope my code is functional, simple, and readable. My goal was to show general design principles and good coding practices without overcomplicating the task at hand.
