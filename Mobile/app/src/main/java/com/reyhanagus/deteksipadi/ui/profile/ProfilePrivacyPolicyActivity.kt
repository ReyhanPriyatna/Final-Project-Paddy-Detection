package com.reyhanagus.deteksipadi.ui.profile

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.reyhanagus.deteksipadi.databinding.ActivityProfilePrivacyPolicyBinding

class ProfilePrivacyPolicyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfilePrivacyPolicyBinding
    private lateinit var backButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfilePrivacyPolicyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        backButton = binding.backButton
        backButton.setOnClickListener {
            val resultIntent = Intent()
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}