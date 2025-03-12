package com.example.textbookmanager

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class BookAdapter(private var books: List<Book>, private val context: MainActivity) :
    RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    private val db = FirebaseFirestore.getInstance()

    inner class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvAuthor: TextView = itemView.findViewById(R.id.tvAuthor)
        val tvCourse: TextView = itemView.findViewById(R.id.tvCourse)
        val tvIsbn: TextView = itemView.findViewById(R.id.tvIsbn)
        val btnEdit: Button = itemView.findViewById(R.id.btnEdit)
        val btnDelete: Button = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]
        holder.tvTitle.text = book.title
        holder.tvAuthor.text = book.author
        holder.tvCourse.text = book.course
        holder.tvIsbn.text = book.isbn

        holder.btnEdit.setOnClickListener {
            val intent = Intent(context, AddBookActivity::class.java).apply {
                putExtra("bookId", book.id)
                putExtra("isbn", book.isbn)
                putExtra("title", book.title)
                putExtra("author", book.author)
                putExtra("course", book.course)
            }
            context.startActivity(intent)
        }

        holder.btnDelete.setOnClickListener {
            db.collection("Textbooks").document(book.id).delete().addOnSuccessListener {
                context.loadBooks()
            }
        }
    }

    override fun getItemCount(): Int = books.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateBooks(newBooks: List<Book>) {
        books = newBooks
        notifyDataSetChanged()
    }
}
