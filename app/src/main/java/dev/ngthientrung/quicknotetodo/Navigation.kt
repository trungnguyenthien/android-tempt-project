package dev.ngthientrung.quicknotetodo

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import dev.ngthientrung.quicknotetodo.data.DefaultNoteRepository
import dev.ngthientrung.quicknotetodo.data.NoteDatabase
import dev.ngthientrung.quicknotetodo.ui.DetailScreen
import dev.ngthientrung.quicknotetodo.ui.HomeScreen
import dev.ngthientrung.quicknotetodo.ui.NoteViewModel
import dev.ngthientrung.quicknotetodo.ui.NoteViewModelFactory

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
