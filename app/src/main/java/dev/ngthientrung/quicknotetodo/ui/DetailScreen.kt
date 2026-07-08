package dev.ngthientrung.quicknotetodo.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.ngthientrung.quicknotetodo.data.Note

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    noteId: String?,
    onBack: () -> Unit,
    viewModel: NoteViewModel,
    modifier: Modifier = Modifier
) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var isCompleted by remember { mutableStateOf(false) }
    var timestamp by remember { mutableStateOf(0L) }

    var initialTitle by remember { mutableStateOf("") }
    var initialContent by remember { mutableStateOf("") }

    var showDiscardDialog by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(noteId != null) }

    // Load note if editing
    LaunchedEffect(noteId) {
        if (noteId != null) {
            val note = viewModel.getNoteById(noteId)
            if (note != null) {
                title = note.title
                content = note.content
                isCompleted = note.isCompleted
                timestamp = note.timestamp
                initialTitle = note.title
                initialContent = note.content
            }
            isLoading = false
        }
    }

    val hasChanges = title != initialTitle || content != initialContent

    val saveNoteAndExit = {
        if (title.isNotBlank() || content.isNotBlank()) {
            val finalNote = if (noteId != null) {
                Note(
                    id = noteId,
                    title = title.trim(),
                    content = content.trim(),
                    timestamp = System.currentTimeMillis(),
                    isCompleted = isCompleted
                )
            } else {
                Note(
                    title = title.trim(),
                    content = content.trim(),
                    timestamp = System.currentTimeMillis(),
                    isCompleted = false
                )
            }

            if (noteId != null) {
                viewModel.updateNote(finalNote)
            } else {
                viewModel.insertNote(finalNote)
            }
        }
        onBack()
    }

    val handleBackNavigation = {
        if (hasChanges) {
            showDiscardDialog = true
        } else {
            onBack()
        }
    }

    // Intercept hardware system back button
    BackHandler(enabled = true, onBack = handleBackNavigation)

    if (showDiscardDialog) {
        AlertDialog(
            onDismissRequest = { showDiscardDialog = false },
            title = { Text("Lưu thay đổi?") },
            text = { Text("Bạn có thay đổi chưa lưu. Bạn có muốn lưu trước khi quay lại không?") },
            confirmButton = {
                Button(
                    onClick = {
                        showDiscardDialog = false
                        saveNoteAndExit()
                    }
                ) {
                    Text("Lưu")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDiscardDialog = false
                        onBack() // Exit without saving
                    }
                ) {
                    Text("Hủy bỏ")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (noteId == null) "Thêm ghi chú" else "Sửa ghi chú",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = handleBackNavigation) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Quay lại")
                    }
                },
                actions = {
                    IconButton(
                        onClick = saveNoteAndExit,
                        enabled = title.isNotBlank() || content.isNotBlank()
                    ) {
                        Icon(Icons.Default.Done, contentDescription = "Lưu")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp)
            ) {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    placeholder = {
                        Text(
                            "Tiêu đề",
                            style = TextStyle(
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.outline
                            )
                        )
                    },
                    textStyle = TextStyle(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = content,
                    onValueChange = { content = it },
                    placeholder = {
                        Text(
                            "Bắt đầu ghi chú...",
                            style = TextStyle(
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.outline
                            )
                        )
                    },
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }
        }
    }
}
