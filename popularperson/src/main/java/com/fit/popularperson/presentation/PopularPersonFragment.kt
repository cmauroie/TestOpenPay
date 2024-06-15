package com.fit.popularperson.presentation

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fit.popularperson.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularPersonFragment : Fragment() {

    companion object {
        fun newInstance() = PopularPersonFragment()
    }

    private val viewModel: PopularPersonViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.test()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_popular_person, container, false)
    }
}