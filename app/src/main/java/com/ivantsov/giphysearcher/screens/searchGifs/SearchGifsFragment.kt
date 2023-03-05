package com.ivantsov.giphysearcher.screens.searchGifs

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ivantsov.giphysearcher.R
import com.ivantsov.giphysearcher.data.model.Gif
import com.ivantsov.giphysearcher.databinding.FragmentSearchGifsBinding
import com.ivantsov.giphysearcher.screens.detailGif.DetailGifFragment
import com.ivantsov.giphysearcher.utils.DataState
import com.ivantsov.giphysearcher.utils.ViewModelFactory

class SearchGifsFragment : Fragment() {

    private val viewModel: SearchGifsViewModel by lazy {
        val factory = ViewModelFactory(
            requireActivity().application
        )
        ViewModelProvider(this, factory).get(SearchGifsViewModel::class.java)
    }
    private lateinit var binding: FragmentSearchGifsBinding
    private lateinit var adapter: SearchGifsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchGifsBinding.inflate(inflater)
        viewModel.searchedGifs.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Error -> showError()
                is DataState.Loading -> showLoading()
                is DataState.Success -> showGifList(it)
            }
        }
        binding.gifSearch.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.setSearchTitle(newText ?: "")
                return true
            }
        })
        viewModel.getSearchedGifs()
        binding.searchGifRetry.setOnClickListener {
            viewModel.getSearchedGifs()
        }
        return binding.root
    }

    private fun showError() {
        binding.gifList.visibility = View.GONE
        binding.gifListLoading.visibility = View.GONE
        binding.searchGifError.visibility = View.VISIBLE
    }

    private fun showLoading() {
        binding.gifList.visibility = View.GONE
        binding.searchGifError.visibility = View.GONE
        binding.gifListLoading.visibility = View.VISIBLE
    }

    private fun showGifList(dataState: DataState.Success<List<Gif>>) {
        binding.gifListLoading.visibility = View.GONE
        binding.searchGifError.visibility = View.GONE
        binding.gifList.visibility = View.VISIBLE
        adapter = SearchGifsAdapter(dataState.result.toMutableList(), ::onItemClick)
        val orientation = this.resources.configuration.orientation
        binding.gifList.layoutManager = if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        } else {
            StaggeredGridLayoutManager(4, LinearLayoutManager.VERTICAL)
        }
        binding.gifList.adapter = adapter
    }

    private fun onItemClick(gifId: String) {
        parentFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.fragment_container_view, DetailGifFragment.newInstance(gifId))
            .addToBackStack(null)
            .commit()
    }
}