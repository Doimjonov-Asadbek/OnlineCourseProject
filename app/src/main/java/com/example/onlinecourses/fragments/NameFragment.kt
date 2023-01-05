package com.example.onlinecourses.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.onlinecourses.EnterActivity
import com.example.onlinecourses.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class NameFragment : Fragment() {

    private lateinit var edtName: EditText
    private lateinit var edtSurname: EditText
    private lateinit var btnSubmit: Button
    private var db = Firebase.firestore
    private var id = FirebaseAuth.getInstance().currentUser!!.uid

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_name, container, false)

        edtName = view.findViewById(R.id.edtName)
        edtSurname = view.findViewById(R.id.edtSurname)
        btnSubmit = view.findViewById(R.id.btnSubmit)

        getFirestoreData()

        btnSubmit.setOnClickListener {

            secure()

            val firstName = edtName.text.toString().trim()
            val lastName = edtSurname.text.toString().trim()

            val mapUpdate = mapOf(
                "name" to firstName,
                "surname" to lastName
            )

            db.collection("Users").document(id).update(mapUpdate)
                .addOnSuccessListener {
                    val intent = Intent(activity, EnterActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                }
                .addOnFailureListener {
                    Toast.makeText(activity, "Update failed!", Toast.LENGTH_SHORT).show()
                }
        }
        return view
    }

    private fun secure() {
        if (edtName.text.toString().isEmpty() && edtSurname.text.toString().isEmpty()){
            edtName.error = "This place should be busy"
            edtSurname.error = "This place should be busy"
        }
        else if (edtName.text.toString().isEmpty()){
            edtName.error = "This place should be busy"
        }
        else if (edtSurname.text.toString().isEmpty()){
            edtSurname.error = "This place should be busy"
        }
    }

    private fun getFirestoreData() {
        val ref = db.collection("Users").document(id)
        ref.get().addOnSuccessListener {
            if (it != null){
                val name = it.data?.get("name")?.toString()
                val surName = it.data?.get("surname")?.toString()

                edtName.setText(name)
                edtSurname.setText(surName)
            }
        }
            .addOnFailureListener {
                Toast.makeText(activity, "Failed!", Toast.LENGTH_SHORT).show()
            }
    }
}