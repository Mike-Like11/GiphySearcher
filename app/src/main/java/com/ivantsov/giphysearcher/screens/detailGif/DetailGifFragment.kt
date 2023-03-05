package com.ivantsov.giphysearcher.screens.detailGif

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.bold
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ivantsov.giphysearcher.data.model.Gif
import com.ivantsov.giphysearcher.databinding.FragmentDetailGifBinding
import com.ivantsov.giphysearcher.utils.DataState
import com.ivantsov.giphysearcher.utils.ViewModelFactory

class DetailGifFragment : Fragment() {

    companion object {
        fun newInstance(gifId: String):DetailGifFragment {
            val fragment = DetailGifFragment()
            val args = Bundle()
            args.putString("GIF_ID", gifId)
            fragment.arguments = args
            return fragment
        }
    }
    private lateinit var binding: FragmentDetailGifBinding
    private val viewModel: DetailGifViewModel by lazy {
        val factory = ViewModelFactory(
            requireActivity().application)
        ViewModelProvider(this, factory).get(DetailGifViewModel::class.java)
    }
    private val gif by lazy { requireArguments().getString("GIF_ID") }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailGifBinding.inflate(inflater)
        viewModel.gifDetail.observe(viewLifecycleOwner){
            when(it) {
                is DataState.Error -> showError()
                is DataState.Loading -> showLoading()
                is DataState.Success -> showGifDetail(it)
            }
        }
        binding.gifDetailBack.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
        binding.detailGifRetry.setOnClickListener {
            gif?.let { viewModel.getDetailedGif(it) }
        }
        gif?.let { viewModel.getDetailedGif(it) }
        return binding.root
    }
    private fun showError() {
        binding.progressDialogGifDetail.visibility = View.GONE
        binding.detailGifError.visibility = View.VISIBLE
    }

    private fun showLoading() {
        binding.progressDialogGifDetail.visibility = View.VISIBLE
        binding.detailGifError.visibility = View.GONE
    }

    private fun showGifDetail(dataState: DataState.Success<Gif>) {
        binding.progressDialogGifDetail.visibility = View.GONE
        binding.detailGifError.visibility = View.GONE
        if (dataState.result.user!=null) {
            binding.gifDetailUser.visibility = View.VISIBLE
        }
        else{
            binding.gifDetailUser.visibility = View.GONE
        }
        dataState.result.importDatetime.let {
            binding.gifDetailTime.text = SpannableStringBuilder().bold { append("Дата загрузки: ") }.append(it)
        }
        dataState.result.rating.let {
            binding.gifDetailRating.text =SpannableStringBuilder().bold { append("Рейтинг: ") }.append(it)
        }
        dataState.result.user?.avatarUrl?.let {
            Glide.with(binding.root).load(it).circleCrop().placeholder(ColorDrawable(Color.BLACK))
                .diskCacheStrategy(DiskCacheStrategy.ALL).into(binding.gifDetailUserUrl)
        }
        dataState.result.user?.username?.let {
            binding.gifDetailUserName.text = "@$it"
        }
        dataState.result.user?.displayName?.let {
            binding.gifDetailDisplayName.text = it
        }
        dataState.result.title.let {
            binding.gifDetailTitle.text = it
        }
        dataState.result.images.original?.url?.let {
            Glide.with(binding.root).asGif().load(it).placeholder(ColorDrawable(Color.BLACK))
                .diskCacheStrategy(DiskCacheStrategy.ALL).into(binding.gifDetailUrl)
        }
    }
}