package com.example.todo.viewModel

// from ViewModel to UI
sealed class UiEvent {

    object popBackStack: UiEvent()
    data class navigate(val route: String): UiEvent()
    data class showSnackBar(
        val message: String,
        val action: String? = null
    ): UiEvent()

}