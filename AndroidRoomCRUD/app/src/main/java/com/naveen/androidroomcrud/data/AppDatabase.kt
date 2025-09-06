package com.naveen.androidroomcrud.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

@Database(
    entities = [Person::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    
    abstract fun personDao(): PersonDao
    
    companion object {
        private const val DATABASE_NAME = "person_database"
        private const val ENCRYPTION_KEY = "MySecretKey123!@#" // In production, use secure key management
        
        @Volatile
        private var INSTANCE: AppDatabase? = null
        
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = createDatabase(context)
                INSTANCE = instance
                instance
            }
        }
        
        private fun createDatabase(context: Context): AppDatabase {
            val passphrase = SQLiteDatabase.getBytes(ENCRYPTION_KEY.toCharArray())
            val factory = SupportFactory(passphrase)
            
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DATABASE_NAME
            )
            .openHelperFactory(factory)
            .addMigrations(MIGRATION_1_2) // Add proper migration
            .fallbackToDestructiveMigration() // Fallback for development
            .build()
        }
        
        // Migration from version 1 to 2: Add salary column
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Add salary column with default value of 0.0
                database.execSQL("ALTER TABLE persons ADD COLUMN salary REAL NOT NULL DEFAULT 0.0")
            }
        }
    }
}
