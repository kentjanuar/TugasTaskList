package com.implisit.tugastasklist

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Calendar

class task_Update : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_task_update)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        // Inisialisasi View
        val namaKegiatan = findViewById<TextView>(R.id.judulActvity)
        val inputImage = findViewById<TextView>(R.id.inputImage)
        val judulTask = findViewById<EditText>(R.id.judulTask)
        val deskripsiTask = findViewById<EditText>(R.id.deskripsiTask)
        val dateButton = findViewById<Button>(R.id.dateTask)
        val createTaskButton = findViewById<Button>(R.id.addTask)
        val imagePreview = findViewById<ImageView>(R.id.imageTask)

        // Variabel untuk menyimpan tanggal
        var selectedDate: String? = null

        val action = intent.getStringExtra("action")
        if (action != null) {
            namaKegiatan.text = action
        }

        var image = intent.getStringExtra("image")
        if (image != null) {
            val resourceId = resources.getIdentifier(image, "drawable", packageName)
            if (resourceId != 0) {
                imagePreview.setImageResource(resourceId)
            } else {
                imagePreview.setImageResource(R.drawable.error)
            }
        }
        val judul = intent.getStringExtra("judul")
        if (judul != null) {
            judulTask.setText(judul)
        }


        val deskripsi = intent.getStringExtra("deskripsi")
        if (deskripsi != null) {
            deskripsiTask.setText(deskripsi)
        }


        inputImage.setOnClickListener {
            val imageOptions = arrayOf("Image 1", "Image 2", "Image 3")

            // Tampilkan AlertDialog untuk memilih gambar
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Pilih Gambar")
                .setItems(imageOptions) { _, which ->
                    // Update selectedImage berdasarkan pilihan
                    selectedImage = when (which) {
                        0 -> R.drawable.image1
                        1 -> R.drawable.image2
                        2 -> R.drawable.image3
                        else -> null
                    }

                    // Tampilkan gambar di ImageView
                    selectedImage?.let {
                        imagePreview.setImageResource(it)
                        Toast.makeText(
                            this,
                            "Gambar ${imageOptions[which]} dipilih",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                .show()
        }

        // Listener untuk memilih tanggal
        dateButton.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog =
                DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                    selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                    Toast.makeText(this, "Tanggal dipilih: $selectedDate", Toast.LENGTH_SHORT)
                        .show()
                }, year, month, day)

            datePickerDialog.show()
        }

        createTaskButton.setOnClickListener {
            val judul = judulTask.text.toString().trim()
            val deskripsi = deskripsiTask.text.toString().trim()

            if (judul.isEmpty() || deskripsi.isEmpty() || selectedDate == null) {
                Toast.makeText(this, "Mohon lengkapi semua data!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Simpan data ke Intent
            val resultIntent = Intent().apply {
                putExtra("judul", judul)
                putExtra("deskripsi", deskripsi)
                putExtra("date", selectedDate)
                selectedImage?.let { putExtra("image", it)
                putExtra("position", intent.getIntExtra("position", -1)) // Add position

                }
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }


    }
}

private var selectedImage: Int? = null
