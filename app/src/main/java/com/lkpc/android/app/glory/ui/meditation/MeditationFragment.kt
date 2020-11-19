package com.lkpc.android.app.glory.ui.meditation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lkpc.android.app.glory.MainActivity
import com.lkpc.android.app.glory.R
import com.lkpc.android.app.glory.entity.BaseContent
import kotlinx.android.synthetic.main.fragment_meditation.*

class MeditationFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_meditation, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // page title
        val a = activity as MainActivity
        a.setActionBarTitle(R.string.title_meditation)

        rv_meditation.layoutManager = LinearLayoutManager(activity)
        rv_meditation.adapter = MeditationAdapter()

        // data observation
        val viewModel: MeditationViewModel by viewModels()
        val observer = Observer<List<BaseContent?>> { data ->
            if (rv_meditation != null) {
                val adapter = rv_meditation.adapter as MeditationAdapter
                if (adapter.isLoading) {
                    (adapter.meditations as MutableList<BaseContent?>).removeAt(adapter.meditations.size - 1)
                    adapter.isLoading = false
                }
                adapter.meditations = data
                adapter.notifyDataSetChanged()
            }
        }
        viewModel.getData().observe(activity as LifecycleOwner, observer)

        // scroll listener
        rv_meditation.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val adapter = rv_meditation.adapter as MeditationAdapter
                if (!rv_meditation.canScrollVertically(1) && !adapter.isLoading) {
                    (adapter.meditations as MutableList).add(null)
                    adapter.notifyItemInserted(adapter.meditations.size - 1)
                    rv_meditation.scrollToPosition(adapter.meditations.size - 1)

                    viewModel.addData(adapter.itemCount - 1)

                    adapter.isLoading = true
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()

        Log.d("Meditation", "onResume")
    }
}