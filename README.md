## Top StackOverflow Users

- [Overview](#overview)
- [Architecture](#architecture)
- [3rd party libraries](#3rd-party-libraries)
- [Known issues](#known-issues)

## Overview

It is a simple StackOverflow client that shows the top 20 users along with their details.
The application is design to be used on phones and on tablets. On phones, we have a simple
list and after selecting the user a screen with details pops up. 
On tablets, the application uses a master/detail approach.

### Features
+ Displaying list of users.
+ Displaying user details page with necessary information.
+ Can be used on phones and tablets.

## Architecture

### Packaging
The code is structured using "package by feature" approach. The main reason for such packaging
is to imitate app domain structure into the code. 

### Model
Main business logic lives in the `Model` layer. `Model` is responsible for downloading data from `Repository` and combine them with network status.
`Repository` is implemented on the top of Retrofit library that is configured to work with RxJava.

###### Reactive flow
RxJava was used to combine two main stream that `Model` logic defines. Network state and data from the endpoint. The reason behind
using RxJava here was not multithreading but working with multiple streams.

### View
Thanks to Android Data Binding mechanism `Activity` or `Fragment` acts as a Dummy View. It does not contain any presentation or render logic. `View` is only responsible for getting an instance of `ViewModel` and bind it to generated representation of xml layout(`Binding`). In some scenarios it also needs to do addition work but only related to gluing everything together or specific Android Framework stuff. 

### ViewModel
`ViewModel` is build on top of Android Architecture Components. It gives simplicity when it comes to screen rotation as it survives `Fragment` or `Activity` re-creation. Main responsibility of a `ViewModel` is to react to specific
`Model` data stream and change state of UI. Updating `ViewState` is automatically translated to UI due data binding mechanism.

### Dependency Inversion
There is no dependency injection framework used. For the simplicity of this application constructor injection(with default values) is the main approach to
build lose coupling and testable code. Dagger 2 or Koin frameworks would not solve any problems.
It would only introduce unnecessary complexity.

## 3rd party libraries

- Retrofit2 (Networking).
- RxJava2 (Working with streams).
- Glide (Downloading and caching images).
- Joda-Time for Android (Working with dates).
- Android Data Binding (MVVM helper).

## Known issues

- Sometimes NetworkMonitor emits incorrect state when app is in the background.
- Lack of UI/Espresso tests for user journeys.