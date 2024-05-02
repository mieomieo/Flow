package com.example.assignment10.activities

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment10.adapter.ContactAdapter
import com.example.assignment10.databinding.ActivityMainBinding
import com.example.assignment10.model.Contact
import com.example.assignment10.viewmodel.ContactViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

//    private val contactViewModel: ContactViewModel by lazy {
//        ViewModelProvider(
//            this,
//            ContactViewModel.ContactViewModelFactory(this.application)
//        )[ContactViewModel::class.java]
//    }
    private val contactViewModel: ContactViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initEvents()
        initControls()
    }

    private fun initControls() {
        val adapter = ContactAdapter(this@MainActivity, onItemClick, onItemDelete)
        binding.rvContacts.setHasFixedSize(true)
        binding.rvContacts.layoutManager = LinearLayoutManager(this)
        binding.rvContacts.adapter = adapter
        contactViewModel.apply {
            allContactsLiveData.observe(this@MainActivity, Observer { list ->
                adapter.setContacts(list)
                Log.e("Check size", "onCreate: ${list.size}")
            })
            getAllContact().observe(this@MainActivity, Observer {
                adapter.setContacts(it)
            })
        }

    }

    private fun initEvents() {
        binding.btFloat.setOnClickListener {
            val intent = Intent(this, AddContactActivity::class.java)
            startActivity(intent)

        }

    }



    fun generateRandomEmail(): String {
        val alphabet = "abcdefghijklmnopqrstuvwxyz"
        val nameLength = (5..6).random()
        val randomName = (1..nameLength)
            .map { alphabet.random() }
            .joinToString("")

        return "$randomName@gmail.com"
    }

    fun generateRandomPhoneNumber(): String {
        val countryCode = "+84"
        val areaCode = "${Random.nextInt(100, 1000)}"
        val prefix = "${Random.nextInt(100, 1000)}"
        val lineNumber = "${Random.nextInt(1000, 10000)}"
        return "$countryCode$areaCode$prefix$lineNumber"
    }

    fun generateRandomName(): String {
        val alphabet = "abcdefghijklmnopqrstuvwxyz"
        val nameLength = (5..6).random()
        return (1..nameLength)
            .map { alphabet.random() }
            .joinToString("")
    }

    private val onItemClick: (Contact) -> Unit = {
        val intent = Intent(this@MainActivity, DetailContactActivity::class.java)
        intent.putExtra("DETAIL_CONTACT", it)
        startActivity(intent)
    }
    private val onItemDelete: (Contact) -> Unit = {
        contactViewModel.deleteContact(it)
    }
}