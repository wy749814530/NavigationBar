package com.mcustom.navigationbar.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.mcustom.navigationbar.R
import kotlinx.android.synthetic.main.fragment_b.*

/**
 * @WYU-WIN
 * @date 2020/7/14 0014.
 * description：
 */
class FragmnetB : Fragment() {
    lateinit var rootView: View
    var textView: TextView? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_b, container, false)
        textView = rootView.findViewById(R.id.textView)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btnDaoHang.setOnClickListener {
        }
    }

    override fun onResume() {
        super.onResume()
        Log.i(javaClass.simpleName, "=========  onResume  ======")

    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i(javaClass.simpleName, "=========  onDestroyView  ======")
    }

    fun refresh() {
        Log.i(javaClass.simpleName, "=========  refresh $isDetached $isAdded $isInLayout ======")
        if (isAdded) {
            Toast.makeText(context, javaClass.simpleName + " 数据刷新了", Toast.LENGTH_SHORT).show()
        }
    }
}