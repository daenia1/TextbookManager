package com.example.textbookmanager

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BookAdapter
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerViewBooks)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = BookAdapter(mutableListOf(), this)
        recyclerView.adapter = adapter

        findViewById<FloatingActionButton>(R.id.btnAddBook).setOnClickListener {
            startActivity(Intent(this, AddBookActivity::class.java))
        }

        loadBooks()
    }

    fun loadBooks() {
        db.collection("Textbooks").get().addOnSuccessListener { result ->
            val books = result.map { document ->
                Book(
                    document.id,
                    document.getString("isbn") ?: "",
                    document.getString("title") ?: "",
                    document.getString("author") ?: "",
                    document.getString("course") ?: ""
                )
            }
            adapter.updateBooks(books)
        }
    }
}