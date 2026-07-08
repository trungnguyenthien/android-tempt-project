package dev.ngthientrung.quicknotetodo.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

interface NoteRepository {
    val allNotes: Flow<List<Note>>
    suspend fun getNoteById(id: String): Note?
    suspend fun insertNote(note: Note)
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(note: Note)
}

class DefaultNoteRepository(private val noteDao: NoteDao) : NoteRepository {
    override val allNotes: Flow<List<Note>> = noteDao.getAllNotes()

    override suspend fun getNoteById(id: String): Note? = withContext(Dispatchers.IO) {
        noteDao.getNoteById(id)
    }

    override suspend fun insertNote(note: Note): Unit = withContext(Dispatchers.IO) {
        noteDao.insertNote(note)
    }

    override suspend fun updateNote(note: Note): Unit = withContext(Dispatchers.IO) {
        noteDao.updateNote(note)
    }

    override suspend fun deleteNote(note: Note): Unit = withContext(Dispatchers.IO) {
        noteDao.deleteNote(note)
    }
}
