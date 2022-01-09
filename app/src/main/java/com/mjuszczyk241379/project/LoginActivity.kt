package com.mjuszczyk241379.project

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.mjuszczyk241379.project.data.AppDatabase
import com.mjuszczyk241379.project.data.Code
import com.mjuszczyk241379.project.data.CodeRepository
import com.mjuszczyk241379.project.data.Password
import com.mjuszczyk241379.project.databinding.ActivityLoginBinding
import com.mjuszczyk241379.project.ui.main.CodeViewModel
import com.mjuszczyk241379.project.ui.main.PageViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var codeViewModel: CodeViewModel
    private lateinit var code: Code

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        codeViewModel = ViewModelProvider(this).get(CodeViewModel::class.java)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.unlockBtn.setOnClickListener {
            val testCode = binding.unlockCode.text.toString()
            if (testCode == code.code){
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, R.string.wrong_code, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()

        codeViewModel.code.observe(this, {
            code = if (it == null) {
                val tmpCode = Code(1, "0000")
                codeViewModel.insert(tmpCode)
                tmpCode
            } else {
                it
            }
        })
    }

}