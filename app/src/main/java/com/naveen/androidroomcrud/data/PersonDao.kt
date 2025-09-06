package com.naveen.androidroomcrud.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {
    
    @Query("SELECT * FROM persons ORDER BY createdAt DESC")
    fun getAllPersons(): Flow<List<Person>>
    
    @Query("SELECT * FROM persons WHERE id = :id")
    suspend fun getPersonById(id: Long): Person?
    
    @Query("SELECT * FROM persons WHERE firstName LIKE '%' || :query || '%' OR lastName LIKE '%' || :query || '%' OR email LIKE '%' || :query || '%' ORDER BY createdAt DESC")
    fun searchPersons(query: String): Flow<List<Person>>
    
    @Query("SELECT * FROM persons WHERE salary >= :minSalary ORDER BY salary DESC")
    fun getPersonsByMinSalary(minSalary: Double): Flow<List<Person>>
    
    @Query("SELECT * FROM persons ORDER BY salary DESC")
    fun getAllPersonsBySalary(): Flow<List<Person>>
    
    @Query("SELECT AVG(salary) FROM persons")
    suspend fun getAverageSalary(): Double
    
    @Query("SELECT MAX(salary) FROM persons")
    suspend fun getMaxSalary(): Double
    
    @Query("SELECT MIN(salary) FROM persons")
    suspend fun getMinSalary(): Double
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPerson(person: Person): Long
    
    @Update
    suspend fun updatePerson(person: Person)
    
    @Delete
    suspend fun deletePerson(person: Person)
    
    @Query("DELETE FROM persons WHERE id = :id")
    suspend fun deletePersonById(id: Long)
    
    @Query("DELETE FROM persons")
    suspend fun deleteAllPersons()
    
    @Query("SELECT COUNT(*) FROM persons")
    suspend fun getPersonCount(): Int
}
