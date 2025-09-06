# Android Room CRUD with SQLCipher Encryption

A complete Android application demonstrating modern Room database implementation with SQLCipher encryption and smart fallback, following MVVM architecture, Jetpack Compose UI, and Coroutines.

## 🚀 Features

### 🔐 Security
- **SQLCipher Encryption**: All database data is encrypted using SQLCipher
- **Smart Fallback**: Graceful handling of encryption failures
- **Secure Key Management**: Configurable encryption keys (production-ready setup needed)

### 🏗️ Architecture
- **MVVM Pattern**: Clean separation of concerns with ViewModel and Repository
- **Repository Pattern**: Abstracted data access layer
- **Singleton Database Manager**: Easy global database access
- **Coroutines**: Asynchronous operations with Flow for reactive UI

### 📱 UI/UX
- **Jetpack Compose**: Modern declarative UI
- **Material Design 3**: Beautiful and consistent design
- **Real-time Search**: Live search functionality
- **Form Validation**: Comprehensive input validation
- **Error Handling**: User-friendly error messages
- **Loading States**: Smooth loading indicators

### 💾 Database Features
- **Room Database**: Type-safe database access
- **CRUD Operations**: Complete Create, Read, Update, Delete functionality
- **Database Migration**: Seamless schema evolution with data preservation
- **Type Converters**: Automatic Date handling
- **Search Functionality**: Multi-field search capabilities
- **Salary Analytics**: Average, min, max salary calculations
- **Salary Filtering**: Filter persons by minimum salary
- **Salary Sorting**: Sort persons by salary (ascending/descending)

## 🛠️ Technical Stack

- **Language**: Kotlin
- **UI**: Jetpack Compose
- **Database**: Room with SQLCipher
- **Architecture**: MVVM
- **Async**: Coroutines + Flow
- **DI**: Manual dependency management (no external DI framework)

## 📦 Dependencies

```kotlin
// Room Database
implementation("androidx.room:room-runtime:2.6.1")
implementation("androidx.room:room-ktx:2.6.1")
kapt("androidx.room:room-compiler:2.6.1")

// SQLCipher for encryption
implementation("net.zetetic:android-database-sqlcipher:4.5.4")

// ViewModel and Lifecycle
implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.9.3")

// Coroutines
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0")

// Compose
implementation("androidx.compose.material3:material3")
implementation("androidx.activity:activity-compose:1.10.1")
```

## 🏃‍♂️ Getting Started

### Prerequisites
- Android Studio Arctic Fox or later
- Android SDK 24+ (API level 24)
- Kotlin 2.0.21+

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd AndroidRoomCRUD
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an existing project"
   - Navigate to the project folder

3. **Sync Project**
   - Android Studio will automatically sync the project
   - Wait for all dependencies to download

4. **Run the App**
   - Connect an Android device or start an emulator
   - Click the "Run" button or press `Shift + F10`

## 📱 Usage

### Global Database Access

The app provides simple database access from anywhere in the application:

```kotlin
// 1. Initialize database (done in MainActivity)
DatabaseManager.initialize(context)

// 2. Access database anywhere
val personDao = DatabaseManager.getPersonDao()

// 3. Use the DAO directly
val persons = personDao.getAllPersons()
```

### Using the Repository

```kotlin
// Create repository instance
val repository = PersonRepository()

// Get all persons
val persons = repository.getAllPersons()

// Insert new person
val newPerson = Person(
    firstName = "John",
    lastName = "Doe",
    email = "john@example.com",
    phone = "1234567890",
    age = 30,
    address = "123 Main St"
)
repository.insertPerson(newPerson)
```

### Using the ViewModel

```kotlin
// In your Composable
val viewModel: PersonViewModel = viewModel()

// Observe data
val persons by viewModel.persons.collectAsState()
val isLoading by viewModel.isLoading.collectAsState()

// Perform operations
viewModel.insertPerson(person)
viewModel.updatePerson(person)
viewModel.deletePerson(person)
```

## 🗂️ Project Structure

```
app/src/main/java/com/naveen/androidroomcrud/
├── data/
│   ├── Person.kt                 # Entity class
│   ├── PersonDao.kt             # Data Access Object
│   ├── AppDatabase.kt           # Room database with SQLCipher
│   ├── Converters.kt            # Type converters
│   └── DatabaseManager.kt       # Singleton for global access
├── repository/
│   └── PersonRepository.kt      # Repository pattern implementation
├── viewmodel/
│   └── PersonViewModel.kt       # ViewModel with UI state
├── ui/screens/
│   ├── PersonListScreen.kt      # Main list screen
│   └── AddEditPersonScreen.kt   # Add/Edit form screen
└── MainActivity.kt              # Main activity with navigation
```

## 🔧 Configuration

### Encryption Key

**⚠️ Security Warning**: The current encryption key is hardcoded for demonstration purposes. In production, implement secure key management:

```kotlin
// In AppDatabase.kt - Current (Development)
private const val ENCRYPTION_KEY = "MySecretKey123!@#"

// Production recommendation:
// - Use Android Keystore
// - Implement key derivation
// - Store keys securely
```

### Database Migration

The app includes migration support for schema changes. Here's an example of the migration from version 1 to 2 that adds a salary column:

```kotlin
// Migration from version 1 to 2: Add salary column
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Add salary column with default value of 0.0
        database.execSQL("ALTER TABLE persons ADD COLUMN salary REAL NOT NULL DEFAULT 0.0")
    }
}
```

**Migration Features:**
- **Data Preservation**: Existing data is preserved during migration
- **Default Values**: New columns get sensible default values
- **Zero Downtime**: Migration happens automatically on app startup
- **Rollback Support**: Fallback to destructive migration if needed

## 🧪 Testing

### Unit Tests
- Run unit tests: `./gradlew test`
- Test files are located in `src/test/java/`

### Instrumented Tests
- Run instrumented tests: `./gradlew connectedAndroidTest`
- Test files are located in `src/androidTest/java/`

## 🚀 Performance Features

- **Lazy Loading**: Efficient list rendering with LazyColumn
- **State Management**: Optimized state updates with StateFlow
- **Memory Management**: Proper lifecycle management
- **Database Optimization**: Indexed queries and efficient operations

## 🔒 Security Features

- **Data Encryption**: All data encrypted at rest
- **Input Validation**: Comprehensive form validation
- **SQL Injection Protection**: Parameterized queries
- **Error Handling**: Secure error messages

## 📊 Database Schema

```sql
CREATE TABLE persons (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    firstName TEXT NOT NULL,
    lastName TEXT NOT NULL,
    email TEXT NOT NULL,
    phone TEXT NOT NULL,
    age INTEGER NOT NULL,
    address TEXT,
    salary REAL NOT NULL DEFAULT 0.0,
    createdAt INTEGER,
    updatedAt INTEGER
);
```

**Schema Evolution:**
- **Version 1**: Original schema without salary
- **Version 2**: Added salary column with default value 0.0
- **Migration**: Automatic data preservation during schema updates

## 🎯 Key Features Explained

### 1. SQLCipher Integration
- Automatic encryption/decryption
- Zero configuration setup
- Fallback handling for encryption failures

### 2. MVVM Architecture
- **Model**: Person entity and database
- **View**: Jetpack Compose screens
- **ViewModel**: Business logic and state management

### 3. Repository Pattern
- Single source of truth for data
- Easy testing and mocking
- Clean separation of concerns

### 4. Reactive UI
- Flow-based data streams
- Automatic UI updates
- Loading and error states

## 🐛 Troubleshooting

### Common Issues

1. **Database not initialized**
   ```kotlin
   // Make sure to call this in MainActivity
   DatabaseManager.initialize(this)
   ```

2. **Encryption errors**
   - Check SQLCipher integration
   - Verify encryption key
   - Check device compatibility

3. **Build errors**
   - Clean and rebuild project
   - Check Kotlin version compatibility
   - Verify all dependencies

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🙏 Acknowledgments

- Android Room team for the excellent database library
- SQLCipher team for encryption capabilities
- Jetpack Compose team for modern UI toolkit
- Material Design team for design guidelines

## 📞 Support

For questions or issues:
- Create an issue in the repository
- Check the troubleshooting section
- Review the code comments for implementation details

---

**Happy Coding! 🚀**
