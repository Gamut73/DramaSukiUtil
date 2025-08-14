This is a Kotlin Multiplatform project but currently just targets Desktop.

It's a simple Utility application that scrapes a given url from the DramaSuki site and the queries 
TMDB for the movie details.

# Libraries used:
- Desktop DB: xerial sqlite-jdbc
- Scrapping: jsoup (html parser) + selenium (web driver)
- Movie details: TMDB API client
- Image loading: coil

# How to run
1. Clone the repository
2. Open the project in IntelliJ IDEA
3. Run the desktopMain `main.kt` file
or `desktopRun -DmainClass=org.artificery.dramasukiutil.MainKt --quiet`