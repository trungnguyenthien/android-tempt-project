package com.example.quicknotetodo

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object Home : NavKey

@Serializable
data class Detail(val noteId: String? = null) : NavKey
