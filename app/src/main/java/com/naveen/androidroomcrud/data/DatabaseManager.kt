package com.naveen.androidroomcrud.data

import android.content.Context

/**
 * Singleton class to manage database access throughout the application
 * This provides a simple way to access the database from anywhere in the app
 */
object DatabaseManager {
    
    @Volatile
    private var database: AppDatabase? = null
    
    /**
     * Initialize the database with application context
     * Call this in Application class or MainActivity
     */
    fun initialize(context: Context) {
        if (database == null) {
            synchronized(this) {
                if (database == null) {
                    database = AppDatabase.getDatabase(context)
                }
            }
        }
    }
    
    /**
     * Get the database instance
     * Make sure to call initialize() first
     */
    fun getDatabase(): AppDatabase {
        return database ?: throw IllegalStateException("Database not initialized. Call initialize() first.")
    }
    
    /**
     * Get PersonDao for direct database operations
     * This is the simplest way to access database from anywhere
     */
    fun getPersonDao(): PersonDao {
        return getDatabase().personDao()
    }
    
    /**
     * Close the database connection
     * Call this when the app is being destroyed
     */
    fun closeDatabase() {
        database?.close()
        database = null
    }
}
