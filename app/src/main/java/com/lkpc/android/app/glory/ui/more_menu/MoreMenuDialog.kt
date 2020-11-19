package com.lkpc.android.app.glory.ui.more_menu

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.lkpc.android.app.glory.R
import com.lkpc.android.app.glory.ui.news.NewsActivity
import com.lkpc.android.app.glory.ui.note.NoteListActivity
import com.lkpc.android.app.glory.ui.qr_code.QrCodeGeneratorActivity
import kotlinx.android.synthetic.main.more_menu_layout.*


class MoreMenuDialog: DialogFragment() {
    companion object {
        const val TAG = "MoreMenuDialog"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.more_menu_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        more_menu_1.setOnClickListener {
            dismiss()
            val i = Intent(activity, QrCodeGeneratorActivity::class.java)
            startActivity(i)
        }

        more_menu_2.setOnClickListener {
            dismiss()
            val i = Intent(activity, NoteListActivity::class.java)
            startActivity(i)
        }

        more_menu_3.setOnClickListener {
            dismiss()
            val i = Intent(activity, NewsActivity::class.java)
            startActivity(i)
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

        (activity as DialogInterface.OnDismissListener).onDismiss(dialog)
    }

    override fun onResume() {
        super.onResume()

        val window = dialog!!.window
        window!!.setGravity(Gravity.BOTTOM or Gravity.END)
        val p = window.attributes
        p.width = resources.getDimensionPixelSize(R.dimen.more_menu_width)
        p.y = resources.getDimensionPixelSize(R.dimen.design_bottom_navigation_height)
        p.x = resources.getDimensionPixelSize(R.dimen.more_menu_margin_end)
        window.attributes = p
        window.setBackgroundDrawableResource(R.drawable.more_menu_bg)
    }
}