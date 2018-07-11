# Favorite Hero

Great app for Marvel's characters fans with some cool features like searching heroes, 

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

The project was built using the Model-View-ViewModel (MVVM) presentation pattern that eases
the development by decopling the app layers by providing more testability and promoting more
reuse. The main difference between the other presentation patterns like the Model-View-Presenter (MVP) is that the MVVM has a reactive approach and
implements the Observer pattern, where the UI is automatically updated
as longer has some changes in the model. (ex: loading a model from a repository, a database update for instance).
The ViewModel is responsible to notify the UI that 

## Model

## View

## ViewModel

# Libraries

The file `buildsystem/dependencies.gradle` contains all the library dependencies used
in the project. It is organized by `region`s according to context like support library,
architecture, UI, network, testing (unit and instrumentation).

# Screenshots

TBD

# License