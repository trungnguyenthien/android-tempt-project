package com.example.quicknotetodo

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.quicknotetodo.data.DefaultNoteRepository
import com.example.quicknotetodo.data.NoteDatabase
import com.example.quicknotetodo.ui.DetailScreen
import com.example.quicknotetodo.ui.HomeScreen
import com.example.quicknotetodo.ui.NoteViewModel
import com.example.quicknotetodo.ui.NoteViewModelFactory

@Composable
fun MainNavigation() {
    val context = LocalContext.current
    val database = remember { NoteDatabase.getDatabase(context) }
    val repository = remember { DefaultNoteRepository(database.noteDao()) }
    val noteViewModel: NoteViewModel = viewModel(
        factory = NoteViewModelFactory(repository)
    )

    val backStack = rememberNavBackStack(Home)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<Home> {
                HomeScreen(
                    onAddNote = { backStack.add(Detail(null)) },
                    onEditNote = { noteId -> backStack.add(Detail(noteId)) },
                    viewModel = noteViewModel,
                    modifier = Modifier.fillMaxSize()
                )
            }
            entry<Detail> { key ->
                DetailScreen(
                    noteId = key.noteId,
                    onBack = { backStack.removeLastOrNull() },
                    viewModel = noteViewModel,
                    modifier = Modifier.fillMaxSize()
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}
