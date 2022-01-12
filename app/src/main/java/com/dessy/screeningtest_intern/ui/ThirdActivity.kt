package com.dessy.screeningtest_intern.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.dessy.screeningtest_intern.UserResponse
import com.dessy.screeningtest_intern.api.RetrofitClient
import com.dessy.screeningtest_intern.databinding.ActivityThirdBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ThirdActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var binding: ActivityThirdBinding
    private lateinit var adapter: UserAdapter
    private lateinit var layoutManager: LinearLayoutManager

    private var page = 1
    private var totalPage: Int = 1
    private var isLoading = false

    var delay:Long = 2000
    val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        layoutManager = LinearLayoutManager(this)
        binding.swipeLayout.setOnRefreshListener(this)
        setupRecyclerView()
        getUsers(false)

        binding.rvUser.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = layoutManager.childCount
                val pastVisibleItem = layoutManager.findFirstVisibleItemPosition()
                val total  = adapter.itemCount
                if (!isLoading && page < totalPage){
                    if (visibleItemCount + pastVisibleItem>= total){
                        page++
                        getUsers(false)
                    }
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    private fun getUsers(isOnRefresh: Boolean) {
        isLoading = true
        if (!isOnRefresh) binding.progressBar.visibility = View.VISIBLE
        handler.postDelayed({
            val parameters = HashMap<String, String>()
            parameters["page"] = page.toString()

            RetrofitClient.instance.getUsers(parameters).enqueue(object : Callback<UserResponse> {
                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Toast.makeText(this@ThirdActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                    binding.progressBar.visibility = View.GONE
                    isLoading = false
                    binding.swipeLayout.isRefreshing = false
                }

                override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                    totalPage = response.body()?.total_pages!!

                    val listResponse = response.body()?.data
                    if (listResponse != null){
                        adapter.addList(listResponse)
                    }
                    binding.progressBar.visibility = View.GONE
                    isLoading = false
                    binding.swipeLayout.isRefreshing = false
                }
            })
        }, delay)
    }

    private fun setupRecyclerView() {
        binding.rvUser.setHasFixedSize(true)
        binding.rvUser.layoutManager = layoutManager
        adapter = UserAdapter(this)
        binding.rvUser.adapter = adapter

        binding.rvUser.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL))
    }

    override fun onRefresh() {
        adapter.clearList()
        page = 1
        getUsers(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
