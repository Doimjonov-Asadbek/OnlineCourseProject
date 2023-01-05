package com.example.onlinecourses.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.onlinecourses.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore

class OTPfragment : Fragment() {

    private var edtOtpPhone: EditText? = null
    private var btnVerify: Button? = null
    private var auth:FirebaseAuth = FirebaseAuth.getInstance()
    private var sharedPreferences: SharedPreferences? = null
    private var password = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view:View = inflater.inflate(R.layout.fragment_o_t_p_fragment, container, false)

        sharedPreferences = activity?.getSharedPreferences("Clone", Context.MODE_PRIVATE)
        password = sharedPreferences?.getString("password","").toString()
        edtOtpPhone = view.findViewById(R.id.edtOtpPhone)
        btnVerify = view.findViewById(R.id.btnVerify)
        val args = this.arguments
        val verificationCode = args?.get("storedVerificationId").toString()

        btnVerify?.setOnClickListener {
            val otp = edtOtpPhone?.text.toString()
            val credential = PhoneAuthProvider.getCredential(verificationCode, otp)
            signInWithPhone(credential)
            sharedPreferences?.edit()?.putString("password", verificationCode)?.apply()
        }
        return view
    }

    private fun signInWithPhone(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    saveFireStore()
                    val anotherActivity = NameFragment()
                    val fragmentTransaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.frame, anotherActivity)
                    fragmentTransaction.commit()
                } else {
                    Toast.makeText(activity, "Incorrect OTP", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun saveFireStore() {
        val uid = auth.currentUser?.uid
        val db = FirebaseFirestore.getInstance()
        val arg = this.arguments
        val number = arg?.get("number").toString()

        val users = hashMapOf(
            "number" to number,
            "name" to "",
            "surname" to "",
            "money" to ""
        )

        if (uid != null){
            db.collection("Users").document(uid).set(users)
        }
    }
}