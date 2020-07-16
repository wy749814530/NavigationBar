package com.mcustom.navigationbar.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.mcustom.navigationbar.R

/**
 * @WYU-WIN
 * @date 2020/7/14 0014.
 * description：
 */
class FragmnetE : Fragment() {
    var textView: TextView? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_e, container, false)
        textView = rootView.findViewById(R.id.textView)
        return rootView
    }

    override fun onResume() {
        super.onResume()
        textView!!.text = "FragmnetE"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i(javaClass.simpleName, "=========  onDestroyView  ======")
    }

    fun refresh() {
        Log.i(javaClass.simpleName, "=========  refresh  ======")
        if (!isDetached) {
            Toast.makeText(context, javaClass.simpleName + " 数据刷新了", Toast.LENGTH_SHORT)
                .show()
        }
    }
}