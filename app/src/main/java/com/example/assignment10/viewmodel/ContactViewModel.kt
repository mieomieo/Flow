package com.example.assignment10.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.assignment10.database.dao.ContactDao
import com.example.assignment10.model.Contact
import com.example.assignment10.repository.ContactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(private val contactRepository: ContactRepository) : ViewModel() {

    private var _allContactsLiveData: MutableLiveData<List<Contact>> = MutableLiveData()
    val allContactsLiveData: LiveData<List<Contact>>
        get() = _allContactsLiveData

    fun insertContact(contact: Contact) = viewModelScope.launch {
        contactRepository.insertContact(contact)
    }

    fun updateContact(contact: Contact) = viewModelScope.launch {
        contactRepository.updateContact(contact)
    }

    fun deleteContact(contact: Contact) = viewModelScope.launch {
        contactRepository.deleteContact(contact)
    }

    fun getAllContact(): LiveData<List<Contact>> = contactRepository.getAllContact()


}