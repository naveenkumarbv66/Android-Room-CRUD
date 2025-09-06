package com.naveen.androidroomcrud.repository

import com.naveen.androidroomcrud.data.Person
import com.naveen.androidroomcrud.data.DatabaseManager
import kotlinx.coroutines.flow.Flow

/**
 * Repository class that provides a clean API for data access
 * This follows the Repository pattern and abstracts the data source
 */
class PersonRepository {
    
    private val personDao = DatabaseManager.getPersonDao()
    
    /**
     * Get all persons as a Flow for reactive UI updates
     */
    fun getAllPersons(): Flow<List<Person>> = personDao.getAllPersons()
    
    /**
     * Get a person by ID
     */
    suspend fun getPersonById(id: Long): Person? = personDao.getPersonById(id)
    
    /**
     * Search persons by query string
     */
    fun searchPersons(query: String): Flow<List<Person>> = personDao.searchPersons(query)
    
    /**
     * Insert a new person
     */
    suspend fun insertPerson(person: Person): Long = personDao.insertPerson(person)
    
    /**
     * Update an existing person
     */
    suspend fun updatePerson(person: Person) = personDao.updatePerson(person)
    
    /**
     * Delete a person
     */
    suspend fun deletePerson(person: Person) = personDao.deletePerson(person)
    
    /**
     * Delete a person by ID
     */
    suspend fun deletePersonById(id: Long) = personDao.deletePersonById(id)
    
    /**
     * Delete all persons
     */
    suspend fun deleteAllPersons() = personDao.deleteAllPersons()
    
    /**
     * Get the total count of persons
     */
    suspend fun getPersonCount(): Int = personDao.getPersonCount()
    
    /**
     * Get persons by minimum salary
     */
    fun getPersonsByMinSalary(minSalary: Double) = personDao.getPersonsByMinSalary(minSalary)
    
    /**
     * Get all persons ordered by salary
     */
    fun getAllPersonsBySalary() = personDao.getAllPersonsBySalary()
    
    /**
     * Get average salary
     */
    suspend fun getAverageSalary(): Double = personDao.getAverageSalary()
    
    /**
     * Get maximum salary
     */
    suspend fun getMaxSalary(): Double = personDao.getMaxSalary()
    
    /**
     * Get minimum salary
     */
    suspend fun getMinSalary(): Double = personDao.getMinSalary()
}
