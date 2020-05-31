
![Build Status](https://travis-ci.org/NalediMadlopha/taste.svg?branch=master)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

Taste
=====================

This repository contains an Android app project (Taste). Taste is an app that provides food recipes from around the world, the meals are organized in meal categories (Dessert, Seafood etc.).

Current state
--------------------

The app currently has two screens, one that displays a list of meal categorys and the other that displays a list of meals. The screens are both implemented using Android activities, this can be optimized to use the Android Navigation component for effiecency.

Architecture:
--------------------

This project follows common architecture principles and makes use of the recommended app architecture (https://bit.ly/2TVOAAa).


Remote Data Source
------------------

Taste receives its data from [TheMealDb](https://themealdb.com), an open, crowd-sourced database of Recipes from around the world. To connect to the APIs, the app makes use of Retrofit which is a type-safe HTTP client for Android and Java. Retrofit makes it easy to consume JSON or XML data which is parsed.

Model
------

The app persists the data from the remote data source local on the device using Room. Room persistence library provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite.

Repository
---------

The app has a repository layer which acts as a abstract data source. It is the single source of truth for the data used in the app. This layers sources the data from the remote data source as well as the local database and provides the view model layer with necessary data. It provides the benefit of swapping out the underlying data sources without having to change the implementation of the app.

ViewModel
----------

The ViewModels provides the data for specific UI components (fragment or activity), and contains data-handling business logic to communicate with the model.

UI
--

The app makes use of activities to display the data to the user. This has to be optimized to use the Android Navigation component for efficiency.

Dependency Injection
--------------------

Taste makes use of the Dagger dependency injection library to simiplify dependency management among the different components of the app.

CI/CD
------

This project makes use of Travis-CI (https://travis-ci.org/) a continous integration and delivery platform for testing and deployment.
