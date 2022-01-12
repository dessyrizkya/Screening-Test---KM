package com.dessy.screeningtest_intern.ui

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dessy.screeningtest_intern.R
import com.dessy.screeningtest_intern.util.Constant
import com.dessy.screeningtest_intern.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val name = intent.getStringExtra(Constant.EXTRA_NAME).toString()

        binding.tvName.text = name

        binding.btnChooseAUser.setOnClickListener {
            val intent = Intent(this, ThirdActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onResume() {
        super.onResume()
        val sharedPref = getSharedPreferences(Constant.SHARED_PREF_USER, 0)
        val dataFromOtherAct = sharedPref.getString(Constant.KEY_NAME, getString(R.string.selected_user_name))
        binding.tvSelectedUsername.text = dataFromOtherAct
    }

    override fun onDestroy() {
        super.onDestroy()
        val sharedPref: SharedPreferences = getSharedPreferences(Constant.SHARED_PREF_USER, 0)
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(Constant.KEY_NAME, getString(R.string.selected_user_name))
        editor.apply()
    }
}