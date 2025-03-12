package com.example.textbookmanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add_book.*

class AddBookActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_book)

        btnSave.setOnClickListener {
            val isbn = etIsbn.text.toString()
            val title = etTitle.text.toString()
            val author = etAuthor.text.toString()
            val course = etCourse.text.toString()

            val book = hashMapOf(
                "isbn" to isbn,
                "title" to title,
                "author" to author,
                "course" to course
            )

            db.collection("Textbooks").add(book).addOnSuccessListener {
                finish()
            }
        }
    }
}
