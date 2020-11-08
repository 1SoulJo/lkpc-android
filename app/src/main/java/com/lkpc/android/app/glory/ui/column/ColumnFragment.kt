package com.lkpc.android.app.glory.ui.column

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lkpc.android.app.glory.MainActivity
import com.lkpc.android.app.glory.R

class ColumnFragment : Fragment() {

    companion object {
        fun newInstance() = ColumnFragment()
    }

    private lateinit var viewModel: ColumnViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val a = activity as MainActivity
        a.setActionBarTitle(R.string.title_column)

        return inflater.inflate(R.layout.fragment_column, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ColumnViewModel::class.java)
        // TODO: Use the ViewModel
    }

}