package com.example.mygithubapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.mygithubapp.adapter.ReposAdapter
import com.example.mygithubapp.databinding.ActivityReposBinding
import com.example.mygithubapp.models.Repository
import com.example.mygithubapp.utils.Status
import com.example.mygithubapp.viewmodel.AuthViewModel
import javax.inject.Inject


class ReposActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReposBinding
    private lateinit var reposAdapter: ReposAdapter
    lateinit var list: ArrayList<Repository>

    @Inject
    lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        App.app.applicationComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityReposBinding.inflate(layoutInflater)
        setContentView(binding.root)

        list = ArrayList()
        val accessToken = intent.getStringExtra("access token")
        authViewModel.getRepositories(accessToken ?: "").observe(this, Observer {
            when (it.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    list.addAll(it.data ?: emptyList())
                    reposAdapter = ReposAdapter(
                        it.data ?: emptyList(),
                        object : ReposAdapter.OnItemClickListener {
                            override fun onItemClick(repository: Repository) {
                                val intent = Intent(
                                    Intent.ACTION_VIEW, Uri.parse(repository.html_url)
                                )
                                startActivity(intent)
                            }
                        })
                    binding.rv.adapter = reposAdapter

                    binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            binding.search.clearFocus()
                            var newList: ArrayList<Repository> = ArrayList()
                            for (i in list.indices) {
                                if (list[i].name.toLowerCase().startsWith(
                                        query.toString().toLowerCase()
                                    )
                                ) {
                                    newList.add(list[i])
                                }
                            }

                            reposAdapter = ReposAdapter(
                                newList,
                                object : ReposAdapter.OnItemClickListener {
                                    override fun onItemClick(
                                        repository: Repository
                                    ) {
                                        val intent = Intent(
                                            Intent.ACTION_VIEW, Uri.parse(repository.html_url)
                                        )
                                        startActivity(intent)
                                    }

                                })

                            binding.rv.adapter = reposAdapter


                            return false
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            var newList: ArrayList<Repository> = ArrayList()
                            for (i in list.indices) {
                                if (list[i].name.toLowerCase().startsWith(
                                        newText.toString().toLowerCase()
                                    )
                                ) {
                                    newList.add(list[i])
                                }
                            }

                            reposAdapter = ReposAdapter(
                                newList,
                                object : ReposAdapter.OnItemClickListener {
                                    override fun onItemClick(
                                        repository: Repository
                                    ) {
                                        val intent = Intent(
                                            Intent.ACTION_VIEW, Uri.parse(repository.html_url)
                                        )
                                        startActivity(intent)
                                    }

                                })

                            binding.rv.adapter = reposAdapter
                            return false
                        }

                    })
                }
                Status.ERROR -> {
                }
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}