# Customer App

An Android application developed as part of the Mobile Developer Challenge. The app features five main screens and implements Clean Architecture with various design patterns and best practices in Android development.

## Techs/Libraries

- **Kotlin** - Version 1.9.0
- **Material Components** - Version 1.12.0
- **AndroidX Core** - Version 1.13.1
- **AndroidX AppCompat** - Version 1.7.0
- **AndroidX Activity** - Version 1.9.1
- **AndroidX ConstraintLayout** - Version 2.1.4
- **AndroidX Fragment** - Version 1.8.2
- **AndroidX Lifecycle** - Version 2.8.4
- **Hilt** - Version 2.48.1
- **Coroutines** - Version 1.7.3
- **Retrofit** - Version 2.10.0
- **Gson** - Version 2.10.1
- **Glide** - Version 4.15.0
- **Firebase Database KTX** - Version 21.0.0
- **Firebase Auth KTX** - Version 23.0.0
- **Mockito** - Version 4.7.0
- **Mockito Inline** - Version 4.7.0
- **Mockito Kotlin** - Version 5.1.0
- **MockK** - Version 1.13.3

## Project Structure

- **Common**: Common utilities and resources shared across modules.
- **Data**: Data sources, repositories, and data models.
- **Di**: Dependency injection setup (Dagger Hilt).
- **Domain**: Business logic, use cases, and domain models.
- **Ui**: UI components, including activities and fragments.

## Activities

- **Splash Activity**: Entry screen of the app.
- **Login Activity**: For phone number authentication.
- **Login Verification Activity**: For code verification.
- **Client Create Activity**: Form for creating a new client.
- **Home Activity**: Displays a welcome message.

## Plugins

- **Android Application Plugin**: Version 8.5.2
- **Kotlin Android Plugin**: Version 1.9.0
- **Kotlin Kapt Plugin**: Version 1.9.0
- **Dagger Hilt Android Plugin**: Version 2.48.1
- **Google Services Plugin**: Version 4.4.2

## Google Services Configuration

Each environment (dev, qa, prod) has its own `google-services.json` file containing Firebase-specific configuration. These files should not be uploaded to the repository due to sensitive information.

Place the `google-services.json` files in the following paths:
- Dev: `app/src/dev/google-services.json`
- QA: `app/src/qa/google-services.json`
- Prod: `app/src/prod/google-services.json`

## Cloning the Project

To clone the repository, use the following command:

```bash
git clone https://github.com/martinprodeveloper/CustomerApp.git