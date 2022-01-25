package com.example.moviesjetpackcompose.presentation.tvShowDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moviesjetpackcompose.presentation.BaseApplication
import com.example.moviesjetpackcompose.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class TvShowDetailsFragment : Fragment() {

    @Inject
    lateinit var application: BaseApplication

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

}