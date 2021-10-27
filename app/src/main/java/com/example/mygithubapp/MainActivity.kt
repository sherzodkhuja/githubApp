package com.example.mygithubapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.example.mygithubapp.databinding.ActivityMainBinding
import com.example.mygithubapp.utils.Status
import com.example.mygithubapp.viewmodel.AuthViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private var clientId = "44dbc6108e828800aef9"

    @Inject
    lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        App.app.applicationComponent.inject(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener {
            loginProcess()
        }
    }

    private fun loginProcess() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(
            "https://github.com/login/oauth/authorize?client_id=$clientId&scope=repo"))
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        val uri: Uri? = intent?.data
        if (uri != null){
            val code = uri.getQueryParameter("code")
            if(code != null){
                binding.loginBtn.visibility = View.GONE
                binding.githubLogo.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
                authViewModel.getAccessToken(code).observe(this, Observer {
                    when (it.status) {
                        Status.LOADING -> {
                        }
                        Status.SUCCESS -> {
                            var intent = Intent(this, ReposActivity::class.java)
                            intent.putExtra("access token", it.data?.access_token)
                            startActivity(intent)
                        }
                        Status.ERROR -> {
                        }
                    }
                })
            }
        }
    }
}