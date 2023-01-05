package com.example.onlinecourses.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import com.example.onlinecourses.R
import com.google.firebase.auth.FirebaseAuth

class RegisterFragment : Fragment() {

    private var edtPhone: EditText? = null
    private var btnRegister: Button? = null
    private var txtLogin: TextView? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var number:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_register, container, false)

        edtPhone = view.findViewById(R.id.edtRegisterPhone)
        btnRegister = view.findViewById(R.id.btnRegister)
        txtLogin = view.findViewById(R.id.txtLogin)
        number = edtPhone?.text?.trim().toString()
        auth = FirebaseAuth.getInstance()

        txtLogin?.setOnClickListener {
           val anotherActivity = LoginFragment()
           val fragmentTransaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
           fragmentTransaction.replace(R.id.frame, anotherActivity)
           fragmentTransaction.commit()

        }
        return view
    }
}