package com.example.to_do

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


class LoginFragment : Fragment() {

    private lateinit var caller: FragmentClickListener

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                                savedInstanceState: Bundle?): View?
    {
        val fragmentView = inflater.inflate(R.layout.fragment_login, container, false)

        val passwordText = fragmentView.findViewById<EditText>(R.id.password)
        passwordText.addTextChangedListener(object : TextWatcher {
            //TODO move this to an external object so its a bit cleaner
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //TODO Add data validation for passwords so they are atleast 6 characters long
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        fragmentView.findViewById<Button>(R.id.logoutButton).setOnClickListener()
        {
            AppData.EMAIL = fragmentView.findViewById<EditText>(R.id.username).text.toString()
            AppData.PASS = fragmentView.findViewById<EditText>(R.id.password).text.toString()
            if(AppData.EMAIL != "" && AppData.PASS != "")
                caller.onLoginButton()
        }
        fragmentView.findViewById<Button>(R.id.backButton).setOnClickListener()
        {
            caller.onCancelButton()
        }

        fragmentView.findViewById<EditText>(R.id.username).setText(AppData.EMAIL)


        return fragmentView
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(context is FragmentClickListener)
            caller = context
    }

    companion object {
        fun newInstance(): LoginFragment
        {
            return LoginFragment()
        }
    }


}