package com.kuzudisli.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.google.ai.client.generativeai.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.kuzudisli.myapplication.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var generativeModel:GenerativeModel
    private lateinit var animation: Animation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        generativeModel = GenerativeModel(modelName = "gemini-pro", apiKey = Constants.API_KEY)
        initUI()


    }

    private fun initUI() {
        animation = AnimationUtils.loadAnimation(this, R.anim.dots_animation)

        binding.textLayout.setEndIconOnClickListener {
            val prompt = binding.editText.text.toString()
            binding.editText.setText("")
            binding.myTextView.text = "Loading..."
           // startAnimation(animation,binding.myTextView)
            sendPrompt(prompt)

        }
    }

    private fun sendPrompt(prompt:String) {
        CoroutineScope(Dispatchers.Main).launch {
            //stopAnimation(animation,binding.myTextView)
            val response = generativeModel.generateContent(prompt)
            binding.myTextView.text = "${response.text}\n\n"

        }
    }

    private fun startAnimation(animation:Animation,view:View) {
        view.visibility = TextView.VISIBLE
        view.startAnimation(animation)
    }

    private fun stopAnimation(animation:Animation,view:View) {
        // Animasyonu durdurun ve sıfırlayın
        animation.cancel()
        animation.reset()
        view.clearAnimation() // Animasyonu temizleyin, böylece TextView'in önceki durumuna dönmesini sağlar
        view.visibility = TextView.INVISIBLE // Eğer metni gizlemek isterseniz, bu satırı ekleyin
    }


}