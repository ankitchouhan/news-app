# news-app
[![Kotlin Version](https://img.shields.io/badge/kotlin-1.3.71-blue.svg)](http://kotlinlang.org/)

A news listing app built with Android Jetpack.

Sample app which uses news api to get latest news and built to illustrate best development practices with Android Jetpack.

Libraries Used
---------------

* [Room][1] - Access your app's SQLite database with in-app objects and compile-time checks.
* [LiveData][2] - Build data objects that notify views when the underlying database changes.
* [Paging library][3] - Load and display small chunks of data at a time.
* [ViewModel][4] - Store UI-related data that isn't destroyed on app rotations. Easily schedule
     asynchronous tasks for optimal execution.
* [Glide][5] for image loading
* [Retrofit][6] Type-safe HTTP client for Android and Java

[1]: https://developer.android.com/topic/libraries/architecture/room
[2]: https://developer.android.com/topic/libraries/architecture/livedata
[3]: https://developer.android.com/topic/libraries/architecture/paging
[4]: https://developer.android.com/topic/libraries/architecture/viewmodel
[5]: https://bumptech.github.io/glide/
[6]: https://square.github.io/retrofit/

Android Studio IDE setup
------------------------
* Use the latest version of Android Studio.
* **Add your [News API][7] Key in `Constants.kt` file to make news fetch work.**

[7]: https://newsapi.org/

Discussions
-----------
If you've found an error in this sample, please file an issue: https://github.com/ankitchouhan/news-app/issues
