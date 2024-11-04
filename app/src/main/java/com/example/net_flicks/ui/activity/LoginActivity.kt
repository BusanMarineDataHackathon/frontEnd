package com.example.net_flicks.ui.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.net_flicks.R
import com.example.net_flicks.api.ApiService
import com.example.net_flicks.api.RetrofitClient
import com.example.net_flicks.api.model.LoginRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginUserId = findViewById<EditText>(R.id.loginUserId).text.toString()
        val loginPassword = findViewById<EditText>(R.id.loginPassword).text.toString()
        val loginButton = findViewById<Button>(R.id.loginButton)

        loginButton.setOnClickListener {
            val loginRequest = LoginRequest(loginUserId, loginPassword)

            val apiService = RetrofitClient.getClient().create(ApiService::class.java)
            val call = apiService.login(loginRequest)

            // 비동기 요청 처리
            call.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@LoginActivity, "로그인 성공", Toast.LENGTH_SHORT).show()
                        // 로그인 성공 후 필요한 작업 수행
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            "로그인 실패: ${response.message()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "네트워크 오류: ${t.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        }
    }
}