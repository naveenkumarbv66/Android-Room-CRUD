package com.naveen.androidroomcrud

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.naveen.androidroomcrud.data.DatabaseManager
import com.naveen.androidroomcrud.data.Person
import com.naveen.androidroomcrud.ui.screens.AddEditPersonScreen
import com.naveen.androidroomcrud.ui.screens.PersonListScreen
import com.naveen.androidroomcrud.ui.theme.AndroidRoomCRUDTheme
import com.naveen.androidroomcrud.viewmodel.PersonViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize the database
        DatabaseManager.initialize(this)
        
        enableEdgeToEdge()
        setContent {
            AndroidRoomCRUDTheme {
                PersonManagerApp()
            }
        }
    }
}

@Composable
fun PersonManagerApp() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.PersonList) }
    var selectedPerson by remember { mutableStateOf<Person?>(null) }
    
    when (currentScreen) {
        is Screen.PersonList -> {
            PersonListScreen(
                onNavigateToAddEdit = { person ->
                    selectedPerson = person
                    currentScreen = Screen.AddEditPerson
                }
            )
        }
        is Screen.AddEditPerson -> {
            AddEditPersonScreen(
                person = selectedPerson,
                onNavigateBack = {
                    selectedPerson = null
                    currentScreen = Screen.PersonList
                }
            )
        }
    }
}

sealed class Screen {
    object PersonList : Screen()
    object AddEditPerson : Screen()
}

@Preview(showBackground = true)
@Composable
fun PersonManagerAppPreview() {
    AndroidRoomCRUDTheme {
        PersonManagerApp()
    }
}