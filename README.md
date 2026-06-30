# RoomHello 🚀

A modern, lightweight Android application demonstrating the integration of **Jetpack Compose** with **Room Database** and **Hilt Dependency Injection (v2.59.2)**
The project implements a fully reactive data flow: a greeting text is stored locally in SQLite, exposed securely through an abstraction layer as a Kotlin `Flow`, and instantly synchronized with the declarative UI state upon any user update.

---

## 🛠 Tech Stack & Architecture

* **UI Framework:** Jetpack Compose (Declarative UI)
* **Dependency Injection:** Hilt 2.59.2 (Dagger2 wrapper for Android)
* **Architecture Pattern:** MVVM (Model-View-ViewModel) + **Repository Pattern**
* **Database:** Room ORM 2.8.4 (Object-Relational Mapping)
* **Asynchronous Streams:** Kotlin Coroutines & Reactive Streams (`Flow`, `StateFlow`)

---

## 🏗 Key Features & Fixes Included

1. **Dependency Injection Module (`DatabaseModule.kt`):** Automatically provisions singletons for the Room Database, DAO, and Repository classes using `SingletonComponent`, completely eliminating boilerplate viewmodel factories.
2. **First-Launch Seeding:** Uses Room's `RoomDatabase.Callback` tied with a Hilt `Provider<GreetingDao>` proxy to safely insert default data *only* on the absolute first launch, preventing data overwrites on subsequent cold starts.
