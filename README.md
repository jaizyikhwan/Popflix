# <img src="app/src/main/res/drawable/logo_project2.png" width="32"> PopFlix

![CircleCI](https://circleci.com/gh/jaizyikhwan/Popflix.svg?style=shield)

PopFlix is an Android application built with Kotlin Jetpack Compose that allows users to discover and explore movies using TheMovieDB API. This project fully leverages Kotlin's capabilities while promoting a collaborative learning experience.

## Features

- Display a list of popular, now playing, top-rated, and upcoming movies
- Show movie details including synopsis, rating, and release year
- Search for movies by title
- Add movies to the favorites list (Dynamic Feature Module)
- Light and dark mode

## Tech Stack

- **Jetpack Compose** - Modern declarative UI
- **Koin** - Dependency Injection
- **Retrofit** - HTTP client for API requests
- **Room** - Local database
- **Coil** - Image loading
- **SqlCipher** - Database security
- **ProGuard** - Minimal obfuscation
- **CI/CD with CircleCI** - Automated build and deployment

## Installation and Setup

1. **Clone the Repository**

```bash
git clone https://github.com/jaizyikhwan/Popflix.git
```

2. **Open the project in Android Studio**

3. **Generate Your API Key on TheMovieDB**

- Visit TheMovieDB.
- Sign up or log in.
- Navigate to the API section in your account and generate a new API key.

4. **Place the API Key in `local.properties`**

In the project root, create (or update) a file named local.properties and add the following line:

```bash
API_KEY=YOUR_API_KEY_HERE
```

5. **Run the application on an emulator or a physical device**

## Disclaimer

This project is part of a submission for the [Dicoding](https://www.dicoding.com/) certification capstone project.  
You may use this code as a reference, but **you are not allowed to copy, redistribute, or submit it as your own work**.