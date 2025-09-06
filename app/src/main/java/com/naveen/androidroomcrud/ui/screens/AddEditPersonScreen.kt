package com.naveen.androidroomcrud.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.naveen.androidroomcrud.data.Person
import com.naveen.androidroomcrud.viewmodel.PersonViewModel
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditPersonScreen(
    person: Person? = null,
    onNavigateBack: () -> Unit,
    viewModel: PersonViewModel = viewModel()
) {
    val isEditMode = person != null
    val errorMessage by viewModel.errorMessage.collectAsState()
    
    // Form state
    var firstName by remember { mutableStateOf(person?.firstName ?: "") }
    var lastName by remember { mutableStateOf(person?.lastName ?: "") }
    var email by remember { mutableStateOf(person?.email ?: "") }
    var phone by remember { mutableStateOf(person?.phone ?: "") }
    var age by remember { mutableStateOf(person?.age?.toString() ?: "") }
    var address by remember { mutableStateOf(person?.address ?: "") }
    var salary by remember { mutableStateOf(person?.salary?.toString() ?: "0.0") }
    
    var showValidationError by remember { mutableStateOf(false) }
    var validationMessage by remember { mutableStateOf("") }
    
    // Clear error message when screen is first composed
    LaunchedEffect(Unit) {
        viewModel.clearErrorMessage()
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditMode) "Edit Person" else "Add Person") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Error Message
            errorMessage?.let { message ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
                ) {
                    Text(
                        text = message,
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
            
            // Validation Error
            if (showValidationError) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
                ) {
                    Text(
                        text = validationMessage,
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
            
            // Form Fields
            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text("First Name *") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = firstName.isBlank()
            )
            
            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text("Last Name *") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = lastName.isBlank()
            )
            
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email *") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = email.isBlank() || !isValidEmail(email)
            )
            
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Phone *") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = phone.isBlank()
            )
            
            OutlinedTextField(
                value = age,
                onValueChange = { age = it },
                label = { Text("Age *") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = age.isBlank() || age.toIntOrNull() == null || age.toInt() <= 0
            )
            
            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = { Text("Address") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2,
                maxLines = 4
            )
            
            OutlinedTextField(
                value = salary,
                onValueChange = { salary = it },
                label = { Text("Salary") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = salary.isBlank() || salary.toDoubleOrNull() == null || salary.toDouble() < 0
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Save Button
            Button(
                onClick = {
                    if (validateForm(firstName, lastName, email, phone, age, salary)) {
                        val personToSave = if (isEditMode) {
                            person!!.copy(
                                firstName = firstName.trim(),
                                lastName = lastName.trim(),
                                email = email.trim(),
                                phone = phone.trim(),
                                age = age.toInt(),
                                address = address.trim(),
                                salary = salary.toDouble(),
                                updatedAt = Date()
                            )
                        } else {
                            Person(
                                firstName = firstName.trim(),
                                lastName = lastName.trim(),
                                email = email.trim(),
                                phone = phone.trim(),
                                age = age.toInt(),
                                address = address.trim(),
                                salary = salary.toDouble()
                            )
                        }
                        
                        if (isEditMode) {
                            viewModel.updatePerson(personToSave)
                        } else {
                            viewModel.insertPerson(personToSave)
                        }
                        
                        onNavigateBack()
                    } else {
                        showValidationError = true
                        validationMessage = "Please fill in all required fields with valid data."
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(
                    text = if (isEditMode) "Update Person" else "Add Person",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

private fun validateForm(
    firstName: String,
    lastName: String,
    email: String,
    phone: String,
    age: String,
    salary: String
): Boolean {
    return firstName.isNotBlank() &&
            lastName.isNotBlank() &&
            email.isNotBlank() &&
            isValidEmail(email) &&
            phone.isNotBlank() &&
            age.isNotBlank() &&
            age.toIntOrNull() != null &&
            age.toInt() > 0 &&
            salary.isNotBlank() &&
            salary.toDoubleOrNull() != null &&
            salary.toDouble() >= 0
}

private fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}
