package com.example.onlinecourses.bottomBar

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.onlinecourses.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DashboardFragment : Fragment() {

    private var monthly:View? = null
    private var yearly:View? = null
    private var money:TextView? = null
    private var phoneNumber:TextView? = null
    private lateinit var builder:AlertDialog.Builder
    private var db = Firebase.firestore
    private var id = FirebaseAuth.getInstance().currentUser!!.uid
    private var sharedPreferences: SharedPreferences? = null
    private var number = ""

    private var cosMonth = 0
    private var cosYear = 0
    private var buy = 0
    private var costMonth = ""
    private var costYear = ""
    private var coin = ""
    private var equalMonth = 0
    private var equalYear = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        monthly = view.findViewById(R.id.viewMonthly)
        yearly = view.findViewById(R.id.viewYearly)
        builder = AlertDialog.Builder(activity)
        money =view.findViewById(R.id.textMoney)
        phoneNumber = view.findViewById(R.id.phoneNumber)

        getUsersData()
        getAdminData()

        sharedPreferences = activity?.getSharedPreferences("number", Context.MODE_PRIVATE)
        number = sharedPreferences?.getString("number","").toString()

        phoneNumber?.text = number

        monthly?.setOnClickListener {
            if (money?.text.toString().toInt() > 99.99){
                showAlertDialogMonth()
            }else{
                builder.setTitle("Alert!")
                    .setMessage("You don't have enough money in your account.Want to throw money?")
                    .setCancelable(false)
                    .setPositiveButton("Ok"){ dialog, _ ->
                        dialog.dismiss()
            }
                .setNegativeButton("No"){ dialog, _ ->
                    dialog.cancel()
                }
                .show()
            }
        }

        yearly?.setOnClickListener {
            if (money?.text.toString().toInt() > 799.99){
                showAlertDialogYear()
            }else{
                builder.setTitle("Alert!")
                    .setMessage("You don't have enough money in your account.Want to throw money?")
                    .setCancelable(false)
                    .setPositiveButton("Ok"){ dialog, _ ->
                        dialog.dismiss()
                    }
                    .setNegativeButton("No"){ dialog, _ ->
                        dialog.cancel()
                    }
                    .show()
            }
        }
        return view
    }

    private fun getUsersData() {
        val ref = db.collection("Users").document(id)
        ref.get().addOnSuccessListener {
            if (it != null){
                coin = it.data?.get("money")?.toString().toString()
                money?.text = coin

                buy = coin.toInt()
            }
        }
            .addOnFailureListener {
                Toast.makeText(activity, "Failed!", Toast.LENGTH_SHORT).show()
            }
    }

    private fun getAdminData() {
        val admin = db.collection("Admin").document("Admin")
        admin.get().addOnSuccessListener {
            if (it != null){
                costMonth = it.data?.get("cost")?.toString().toString()
                costYear = it.data?.get("costYear")?.toString().toString()

                cosMonth = costMonth.toInt()
                cosYear = costYear.toInt()
            }
        }
    }

    private fun showAlertDialogYear() {
        builder.setTitle("Purchase")
            .setMessage("Do you really want to buy this?")
            .setCancelable(true)
            .setPositiveButton("Yes") { dialog, _ ->
                yearly?.setBackgroundResource(R.drawable.back_view)
                dialog.dismiss()

                getUsersData()

                equalYear = buy - cosYear

                val mapUpdate = mapOf(
                    "money" to equalYear
                )
                db.collection("Users").document(id).update(mapUpdate)
                    .addOnSuccessListener {
                        Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show()
                    }

                money?.text = equalYear.toString()
            }
            .setNegativeButton("No"){dialog, _ ->
                dialog.cancel()
            }
            .show()
    }

    private fun showAlertDialogMonth() {
        builder.setTitle("Purchase")
            .setMessage("Do you really want to buy this?")
            .setCancelable(true)
            .setPositiveButton("Yes") { dialog, _ ->
                monthly?.setBackgroundResource(R.drawable.back_view)
                dialog.dismiss()

                getUsersData()

                equalMonth = buy - cosMonth

                val mapUpdate = mapOf(
                    "money" to equalMonth
                )
                db.collection("Users").document(id).update(mapUpdate)
                    .addOnSuccessListener {
                        Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show()
                    }

                money?.text = equalMonth.toString()
            }
            .setNegativeButton("No"){dialog, _ ->
                dialog.cancel()
            }
            .show()
    }
}