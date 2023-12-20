package com.example.finalproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.Firebase
import com.google.firebase.auth.auth


class SignUpFragment : Fragment() {
    lateinit var viewModel: FirebaseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)
        viewModel = ViewModelProvider(this).get(FirebaseViewModel::class.java)

        val button = view.findViewById<Button>(R.id.button)
        val nameEditText = view.findViewById<EditText>(R.id.nameEditText)
        val emailEditText = view.findViewById<EditText>(R.id.emailEditText)

        button.setOnClickListener {
            val name = nameEditText.text.toString()
            val email = emailEditText.text.toString()

            Firebase.auth.createUserWithEmailAndPassword(email, "password").addOnSuccessListener {
                Toast.makeText(activity, "You're signed up!", Toast.LENGTH_LONG).show()
            }.addOnFailureListener {
                Toast.makeText(activity, "Sign up failure", Toast.LENGTH_LONG).show()
            }
        }

        return view
    }
}