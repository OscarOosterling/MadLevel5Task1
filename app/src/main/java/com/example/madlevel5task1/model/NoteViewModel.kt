package com.example.madlevel5task1.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.madlevel5task1.database.NoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class NoteViewModel(application: Application) :AndroidViewModel(application) {
    private val noteRepository = NoteRepository(application.applicationContext)
    private val mainScope = CoroutineScope(Dispatchers.Main)
    val note = noteRepository.getNotepad()
    val error = MutableLiveData<String>()
    val succes = MutableLiveData<Boolean>()

    fun updateNote(title:String,text:String){
    val newNote = Note(
    id = note.value?.id,
    title = title,
    lastUpdated = Date(),
    text = text
    )
        if(isNoteValid(newNote)){
            mainScope.launch { withContext(Dispatchers.IO){
                noteRepository.updateNotepad(newNote)
            }
            succes.value = true
            }
        }
    }
    private fun isNoteValid(note:Note):Boolean{
        return when{
            note.title.isBlank()->{
                error.value = "Title must not be empty"
                false
            }else->true
        }
    }
}