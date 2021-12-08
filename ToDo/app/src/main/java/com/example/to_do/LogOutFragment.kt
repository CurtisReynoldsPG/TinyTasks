package com.example.to_do

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class LogOutFragment : Fragment() {

    private lateinit var caller: FragmentClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragmentView = inflater.inflate(R.layout.fragment_log_out, container, false)

        fragmentView.findViewById<Button>(R.id.backButton).setOnClickListener(){caller.onCancelButton()}
        fragmentView.findViewById<Button>(R.id.logoutButton).setOnClickListener(){caller.onLogoutButton()}

        fragmentView.findViewById<TextView>(R.id.name).setText(AppData.EMAIL)
        return fragmentView
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(context is FragmentClickListener)
            caller = context
    }

    companion object {
        fun newInstance(): LogOutFragment
        {
            return LogOutFragment()
        }
    }
}