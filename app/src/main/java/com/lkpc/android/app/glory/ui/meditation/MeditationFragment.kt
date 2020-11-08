package com.lkpc.android.app.glory.ui.meditation

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lkpc.android.app.glory.MainActivity
import com.lkpc.android.app.glory.R

class MeditationFragment : Fragment() {

    companion object {
        fun newInstance() = MeditationFragment()
    }

    private lateinit var viewModel: MeditationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val a = activity as MainActivity
        a.setActionBarTitle(R.string.title_meditation)

        return inflater.inflate(R.layout.fragment_meditation, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MeditationViewModel::class.java)
        // TODO: Use the ViewModel
    }

}