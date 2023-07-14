package com.zckj.compose.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zckj.compose.data.Student
import com.zckj.compose.ui.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    var openDialog by mutableStateOf(false)
    var name by mutableStateOf("name")
    val students = mutableListOf<MutableState<Student>>()

    init {
        (0..22).forEach {
            val studentState = mutableStateOf(Student(name, it.toString()))
            students.add(studentState)
        }
    }

    fun updateStudentName(student: Student, newName: String) {
        student.name = newName
    }

    private val _screenEvent = MutableSharedFlow<String>()
    val screenEvent = _screenEvent.asSharedFlow()

    fun sendScreen(screen: String) {
        viewModelScope.launch {
            _screenEvent.emit(screen)
        }
    }
}