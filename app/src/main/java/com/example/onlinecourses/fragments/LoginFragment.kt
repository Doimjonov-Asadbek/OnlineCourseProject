package com.example.onlinecourses.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.onlinecourses.R
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class LoginFragment : Fragment() {

    private var login: Button? = null
    private var txtRegister: TextView? = null
    private var edtLoginPhone: EditText? = null

    private lateinit var auth: FirebaseAuth
    lateinit var storedVerificationId:String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private var sharedPreferences: SharedPreferences? = null
    private var number = ""
    private var phone = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view:View = inflater.inflate(R.layout.fragment_login, container, false)

        login = view.findViewById(R.id.btnLogin)
        edtLoginPhone = view.findViewById(R.id.edtLoginPhone)
        txtRegister = view.findViewById(R.id.txtRegister)
        auth = FirebaseAuth.getInstance()
        sharedPreferences = activity?.getSharedPreferences("number", Context.MODE_PRIVATE)
        number = sharedPreferences?.getString("number","").toString()

        txtRegister?.setOnClickListener {
            val anotherActivity = RegisterFragment()
            val fragmentTransaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frame, anotherActivity)
            fragmentTransaction.commit()
        }

        startFirebaseLogin()

        login?.setOnClickListener {
            val number = edtLoginPhone?.text.toString()
            if(number.isNotEmpty()){
                sendVerificationCode (number)
                sharedPreferences?.edit()?.putString("number", number)?.apply()
            }else{
                edtLoginPhone?.error = "This place should be busy"
            }
        }
        return view
    }

    private fun startFirebaseLogin() {
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Toast.makeText(activity?.applicationContext, "Complete", Toast.LENGTH_LONG).show()
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(activity?.applicationContext, "Failed", Toast.LENGTH_LONG).show()
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                super.onCodeSent(verificationId, token)
                storedVerificationId = verificationId
                resendToken = token
                val bundle = Bundle()
                bundle.putString("storedVerificationId",storedVerificationId)
                bundle.putString("number", edtLoginPhone?.text.toString())
                val anotherActivity = OTPfragment()
                anotherActivity.arguments = bundle
                val fragmentTransaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.frame, anotherActivity)
                fragmentTransaction.commit()
            }
        }
    }

    private fun sendVerificationCode(number: String?) {
        auth = FirebaseAuth.getInstance()
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number.toString())
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this.requireActivity())
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

}