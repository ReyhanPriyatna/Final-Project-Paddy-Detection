package com.reyhanagus.deteksipadi.ui.history

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.reyhanagus.deteksipadi.databinding.ActivityHistoryResultDetectionBinding
import com.reyhanagus.deteksipadi.ui.main.MainActivity
import java.text.SimpleDateFormat
import java.util.*

class HistoryResultDetectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryResultDetectionBinding
    private lateinit var resultTitle: TextView
    private lateinit var resultDate: TextView
    private lateinit var resultImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryResultDetectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        resultTitle = binding.tvResultTitle
        resultDate = binding.tvResultDate
        resultImage = binding.ivResultImage

        val intent = intent
        val title = intent.getStringExtra("result_name")
        val date = intent.getStringExtra("result_date")
        val image = intent.getStringExtra("result_image")

        // fungsi untuk mengubah format tanggal
        val dateString = date
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val date2 = inputFormat.parse(dateString)
        val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val formattedDate = outputFormat.format(date2)

        resultTitle.text = "Terindikasi terkena " + title
        resultDate.text = formattedDate
        Glide.with(this)
            .load(image)
            .into(resultImage)

        binding.backButton.setOnClickListener {
            val resultIntent = Intent()
            setResult(RESULT_OK, resultIntent)
            finish()
        }

        binding.homeButton.setOnClickListener {
            val intent = Intent(this@HistoryResultDetectionActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}