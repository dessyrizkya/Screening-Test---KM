package com.dessy.screeningtest_intern.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.dessy.screeningtest_intern.util.Constant
import com.dessy.screeningtest_intern.R
import com.dessy.screeningtest_intern.databinding.ActivityFirstBinding

class FirstActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityFirstBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCheck.setOnClickListener(this)
        binding.btnNext.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_check -> {
                val text = binding.tvPalindrome.text
                if (text.isEmpty()) {
                    Toast.makeText(this, "Field Palindrome is Empty", Toast.LENGTH_SHORT).show()
                } else {
                    if(isPalindrome(text.toString().trim().lowercase().split(" "))) {
                        Toast.makeText(this, "isPalindrome", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "not palindrome", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            R.id.btn_next -> {
                val name = binding.tvName.text.toString().trim()
                val intent = Intent(this, SecondActivity::class.java)
                intent.putExtra(Constant.EXTRA_NAME, name)
                startActivity(intent)
            }
        }
    }

    private fun isPalindrome(list: List<String>): Boolean{
        var text = ""

        for (str in list) {
            text +=str
        }

        return text == text.reversed()
    }
}