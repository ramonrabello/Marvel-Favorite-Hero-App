# Favorite Hero

Cool app for Marvel's characters fans with some cool features like searching heroes, 

# Setup

TBD

# Project Structure

The project is entirely written using Kotlin language and it was created based on _feature package_ convention:
- __core:__ All classes related the the listing of super heroes.
- __heroes:__ All classes related the the listing of super heroes.
- __favorites:__ All classes related the the listing of super heroes.
- __detail:__ All classes related the the details of a super hero.
- __di:__ All classes related to Dependency Injection (DI) using Dagger 2
like `@Module`s, `@Component`s, `@ContributesAndroidInjector`s, etc.
- __core:__ All classes common to other projects like Views, Models, base adapter, base
view holder, etc.
- __view:__ Contains all classes related to the view layer like Activities, Fragments, Adapters

# Project Architecture

The project was built using the __Model-View-ViewModel (MVVM)__ presentation pattern that eases
the development by decopling the app layers by providing more testability and promoting more
reuse. The main difference between the other presentation patterns like the Model-View-Presenter (MVP) is that the MVVM has a reactive approach and
implements the Observer pattern, where the UI observes model changes that are notified by ViewModel.

# Libraries

The file `buildsystem/dependencies.gradle` contains all the library dependencies used
in the project. It is organized by `region`s according to context like support library,
architecture, UI, network, testing (unit and instrumentation).

## Kotlin
- Kotlin Standard Library 1.2.51
- Kotlin Coroutines

## Support Library 
- appcompat-v7 27.1.1
- Design 27.1.1
- cardview-v7 27.1.1
- support-fragment 27.1.1
- constraint-layout 1.1.0

## UI
- SuperRecyclerView 1.1.4
- Glide 4.7.1
- Glide Compiler 4.7.1 (for kapt)
- ReadMoreTextView 2.1.0

## Network
- Gson
- OkHttp 3
- Retrofit 2
- RxJava 2
- Okio

## Architecture
- Architecture Components (Lifecycle, Room & LiveData) 
- Dagger 2
- Room
## Testing
- JUnit 4.1.2
- Mockito 

# Screenshots
## Heroes Listing and Search
![Hero List]
(screenshots/hero-list-screen.png)

## Favorite Heroes
![Favorite Heroes]
(screenshots/favorites-screen.png)

## Hero Details
![Hero Details]
(screenshots/details-screen.png)

# License
    Copyright 2018 Ramon Rabello
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
       http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.