package com.naveen.androidroomcrud.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naveen.androidroomcrud.data.Person
import com.naveen.androidroomcrud.repository.PersonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for managing Person data and UI state
 * Follows MVVM architecture pattern
 */
class PersonViewModel : ViewModel() {
    
    private val repository = PersonRepository()
    
    // UI State
    private val _persons = MutableStateFlow<List<Person>>(emptyList())
    val persons: StateFlow<List<Person>> = _persons.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()
    
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    
    init {
        loadPersons()
    }
    
    /**
     * Load all persons from the database
     */
    private fun loadPersons() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                repository.getAllPersons().collect { personList ->
                    _persons.value = personList
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error loading persons: ${e.message}"
                _isLoading.value = false
            }
        }
    }
    
    /**
     * Search persons by query
     */
    fun searchPersons(query: String) {
        _searchQuery.value = query
        viewModelScope.launch {
            try {
                _isLoading.value = true
                if (query.isBlank()) {
                    repository.getAllPersons().collect { personList ->
                        _persons.value = personList
                        _isLoading.value = false
                    }
                } else {
                    repository.searchPersons(query).collect { personList ->
                        _persons.value = personList
                        _isLoading.value = false
                    }
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error searching persons: ${e.message}"
                _isLoading.value = false
            }
        }
    }
    
    /**
     * Insert a new person
     */
    fun insertPerson(person: Person) {
        viewModelScope.launch {
            try {
                repository.insertPerson(person)
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Error inserting person: ${e.message}"
            }
        }
    }
    
    /**
     * Update an existing person
     */
    fun updatePerson(person: Person) {
        viewModelScope.launch {
            try {
                repository.updatePerson(person)
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Error updating person: ${e.message}"
            }
        }
    }
    
    /**
     * Delete a person
     */
    fun deletePerson(person: Person) {
        viewModelScope.launch {
            try {
                repository.deletePerson(person)
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Error deleting person: ${e.message}"
            }
        }
    }
    
    /**
     * Delete a person by ID
     */
    fun deletePersonById(id: Long) {
        viewModelScope.launch {
            try {
                repository.deletePersonById(id)
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Error deleting person: ${e.message}"
            }
        }
    }
    
    /**
     * Clear all persons
     */
    fun deleteAllPersons() {
        viewModelScope.launch {
            try {
                repository.deleteAllPersons()
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Error deleting all persons: ${e.message}"
            }
        }
    }
    
    /**
     * Clear error message
     */
    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}
