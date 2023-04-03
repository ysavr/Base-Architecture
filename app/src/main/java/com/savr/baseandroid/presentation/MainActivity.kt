package com.savr.baseandroid.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.savr.baseandroid.R
import com.savr.baseandroid.common.utils.EndlessRecyclerViewScrollListener
import com.savr.baseandroid.common.utils.gone
import com.savr.baseandroid.common.utils.toast
import com.savr.baseandroid.common.utils.visible
import com.savr.baseandroid.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()

    private lateinit var movieAdapter: MovieAdapter
    private var scrollListener: EndlessRecyclerViewScrollListener? = null
    private var onScroll = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initEvent()
    }

    private fun initView() {
        movieAdapter = MovieAdapter()
        movieAdapter.setOnItemClickListener {
            toast(it.title)
        }
        binding.rvMovie.adapter = movieAdapter

        scrollListener = object : EndlessRecyclerViewScrollListener(
            binding.rvMovie.layoutManager as LinearLayoutManager
        ) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                onScroll = true
                movieAdapter.addLoadingItem()
                viewModel.getDiscoverMovie()
            }
        }

        binding.rvMovie.addOnScrollListener(scrollListener as EndlessRecyclerViewScrollListener)

        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
            onScroll = false
            scrollListener?.resetState()
            movieAdapter.clear()
            viewModel.resetPagination()
            viewModel.getDiscoverMovie()
        }
    }

    private fun initEvent() {
        viewModel.getDiscoverMovie()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movieState.collect {
                    when (it) {
                        MovieState.Empty -> Unit
                        is MovieState.Error -> {
                            Timber.e(it.message)
                            if (!onScroll) {
                                binding.layoutStatus.visible()
                                binding.ivStatus.setImageResource(R.drawable.ic_error_connection)
                                binding.tvStatus.text = it.message
                            }
                        }
                        MovieState.Loading -> {
                            if (!onScroll) {
                                binding.shimmerViewMovie.visible()
                                binding.rvMovie.gone()
                                binding.layoutStatus.gone()
                            }
                        }
                        is MovieState.Success -> {
                            binding.shimmerViewMovie.gone()
                            binding.layoutStatus.gone()
                            if (onScroll) {
                                onScroll = false
                                movieAdapter.removeLoadingItem()
                            } else {
                                if (it.data.isEmpty()) {
                                    binding.ivStatus.setImageResource(R.drawable.ic_no_data)
                                    binding.tvStatus.text = "No data found"
                                } else {
                                    binding.layoutStatus.gone()
                                }
                            }
                            binding.rvMovie.visible()
                            movieAdapter.submitList(it.data.toList())
                        }
                    }
                }
            }
        }
    }
}
