package com.lkpc.android.app.glory.ui.sermon

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lkpc.android.app.glory.MainActivity
import com.lkpc.android.app.glory.R

class SermonFragment : Fragment() {

    companion object {
        fun newInstance() = SermonFragment()
    }

    private lateinit var viewModel: SermonViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val a = activity as MainActivity
        a.setActionBarTitle(R.string.title_sermon)

        return inflater.inflate(R.layout.fragment_sermon, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SermonViewModel::class.java)
        // TODO: Use the ViewModel
    }

}