package com.naveen.androidroomcrud.examples

import com.naveen.androidroomcrud.data.DatabaseManager
import com.naveen.androidroomcrud.data.Person
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

/**
 * Example class demonstrating how to use the database from anywhere in the application
 * This shows the simple steps to access the Room database globally
 */
class DatabaseUsageExample {
    
    /**
     * Example 1: Simple database access
     * This is the easiest way to access the database from anywhere
     */
    fun simpleDatabaseAccess() {
        // Step 1: Get the DAO directly
        val personDao = DatabaseManager.getPersonDao()
        
        // Step 2: Use the DAO in a coroutine
        CoroutineScope(Dispatchers.IO).launch {
            // Get all persons
            val allPersons = personDao.getAllPersons()
            
            // Insert a new person
            val newPerson = Person(
                firstName = "John",
                lastName = "Doe",
                email = "john@example.com",
                phone = "1234567890",
                age = 30,
                address = "123 Main St",
                salary = 75000.0
            )
            val insertedId = personDao.insertPerson(newPerson)
            
            // Update a person
            val updatedPerson = newPerson.copy(
                firstName = "Jane",
                updatedAt = Date()
            )
            personDao.updatePerson(updatedPerson)
            
            // Delete a person
            personDao.deletePerson(updatedPerson)
        }
    }
    
    /**
     * Example 2: Using the Repository pattern
     * This is the recommended approach for complex operations
     */
    fun repositoryPatternExample() {
        val repository = com.naveen.androidroomcrud.repository.PersonRepository()
        
        CoroutineScope(Dispatchers.IO).launch {
            // All operations through repository
            val persons = repository.getAllPersons()
            val count = repository.getPersonCount()
            
            // Search functionality
            val searchResults = repository.searchPersons("John")
        }
    }
    
    /**
     * Example 3: Direct database access for custom queries
     * Use this when you need custom database operations
     */
    fun customDatabaseOperations() {
        val database = DatabaseManager.getDatabase()
        val personDao = database.personDao()
        
        CoroutineScope(Dispatchers.IO).launch {
            // Custom operations
            val personCount = personDao.getPersonCount()
            val specificPerson = personDao.getPersonById(1L)
            
            // Search with custom query
            val searchResults = personDao.searchPersons("gmail")
        }
    }
}

/**
 * Extension functions for even simpler database access
 */
object DatabaseExtensions {
    
    /**
     * Quick insert function
     */
    suspend fun insertPersonQuick(
        firstName: String,
        lastName: String,
        email: String,
        phone: String,
        age: Int,
        address: String = "",
        salary: Double = 0.0
    ): Long {
        val person = Person(
            firstName = firstName,
            lastName = lastName,
            email = email,
            phone = phone,
            age = age,
            address = address,
            salary = salary
        )
        return DatabaseManager.getPersonDao().insertPerson(person)
    }
    
    /**
     * Quick search function
     */
    fun searchPersonsQuick(query: String) = 
        DatabaseManager.getPersonDao().searchPersons(query)
    
    /**
     * Quick delete function
     */
    suspend fun deletePersonQuick(id: Long) {
        DatabaseManager.getPersonDao().deletePersonById(id)
    }
}

/**
 * Usage examples in different scenarios:
 * 
 * 1. In a Fragment:
 *    DatabaseManager.getPersonDao().getAllPersons()
 * 
 * 2. In a Service:
 *    DatabaseManager.getPersonDao().insertPerson(person)
 * 
 * 3. In a BroadcastReceiver:
 *    DatabaseManager.getPersonDao().deletePersonById(id)
 * 
 * 4. In any class:
 *    DatabaseExtensions.insertPersonQuick("John", "Doe", "john@example.com", "1234567890", 30)
 */
