package com.reyhanagus.deteksipadi.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.reyhanagus.deteksipadi.adapter.DetectDiseaseHistoryAdapter
import com.reyhanagus.deteksipadi.databinding.ActivityMainBinding
import com.reyhanagus.deteksipadi.network.ApiConfig
import com.reyhanagus.deteksipadi.network.responses.GetHistoryResponseItem
import com.reyhanagus.deteksipadi.ui.detection.DetectionActivity
import com.reyhanagus.deteksipadi.ui.history.HistoryActivity
import com.reyhanagus.deteksipadi.ui.history.HistoryResultDetectionActivity
import com.reyhanagus.deteksipadi.ui.profile.ProfileActivity
import com.reyhanagus.deteksipadi.ui.recomendation.RecomendationActivity
import com.reyhanagus.deteksipadi.viewmodel.MainViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var helloName: TextView
    private lateinit var detectCounter: TextView
    private lateinit var historyResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        helloName = binding.helloUser
        detectCounter = binding.jumlahTerdeteksi

        // Inisialisasi ViewModel
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.init(this)

        viewModel.name.observe(this) { name ->
            helloName.text = "Halo, $name!"
        }

        setupRecyclerView()

        // Set button listener
        val profileButton = binding.rectangleProfile
        profileButton.setOnClickListener {
            val intent = Intent(this@MainActivity, ProfileActivity::class.java)
            startActivity(intent)
        }

        val recomendationButton = binding.cariButton
        recomendationButton.setOnClickListener {
            val intent = Intent(this@MainActivity, RecomendationActivity::class.java)
            startActivity(intent)
        }

        val historyButton = binding.lihatLebih
        historyButton.setOnClickListener {
            val intent = Intent(this@MainActivity, HistoryActivity::class.java)
            startActivity(intent)
        }

        val detectionButton = binding.fab
        detectionButton.setOnClickListener {
            val intent = Intent(this@MainActivity, DetectionActivity::class.java)
            startActivity(intent)
        }

        // Activity result launcher
        historyResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == RESULT_OK) {
                // Refresh activity
            }
        }
    }

    // fungsi untuk menampilkan data history penyakit
    fun setupRecyclerView() {
        // Mendapatkan data list dari API
        val token = "Bearer " + viewModel.token
        val client = ApiConfig.getApiService().getHistoryDisease(token)
        client.enqueue(object : Callback<List<GetHistoryResponseItem>> {
            override fun onResponse(
                call: Call<List<GetHistoryResponseItem>>,
                response: Response<List<GetHistoryResponseItem>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    val detectDiseaseItems = responseBody as List<GetHistoryResponseItem>
                    val sortedList = detectDiseaseItems.sortedByDescending { it.detectionDate }
                    val detectAdapter = DetectDiseaseHistoryAdapter(
                        sortedList,
                        object : DetectDiseaseHistoryAdapter.OnAdapterClickListener {
                            override fun onItemClicked(detectDisease: GetHistoryResponseItem) {
                                val intent = Intent(
                                    this@MainActivity,
                                    HistoryResultDetectionActivity::class.java
                                )
                                intent.putExtra("result_name", detectDisease.detectionResult)
                                intent.putExtra("result_image", detectDisease.imageUrl)
                                intent.putExtra("result_date", detectDisease.detectionDate)
                                historyResultLauncher.launch(intent)
                            }
                        })
                    detectCounter.text = detectDiseaseItems.size.toString()

                    binding.rvDeteksi.apply {
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        adapter = detectAdapter
                    }
                }
            }

            override fun onFailure(call: Call<List<GetHistoryResponseItem>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Gagal mendapatkan data", Toast.LENGTH_SHORT)
                    .show()
            }

        })
    }

    // Exit app when back button pressed
    override fun onBackPressed() {
        finishAffinity()
    }
}