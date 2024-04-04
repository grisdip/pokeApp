package com.ehernndez.poketest.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import com.ehernndez.poketest.R

class SecondFragment : Fragment(R.layout.fragment_second) {

    override fun onAttach(context: Context) {
        super.onAttach(context)

        Log.e("[SECOND FRAGMENT] --->", "method onAttach was called")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.e("[SECOND FRAGMENT] --->", "method onViewCreated was called")
    }

    override fun onDestroyView() {
        super.onDestroyView()

        Log.e("[SECOND FRAGMENT] --->", "method onDestroyView was called")
    }
}