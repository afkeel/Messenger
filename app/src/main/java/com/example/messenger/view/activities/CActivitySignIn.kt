package com.example.messenger.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.messenger.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import android.text.TextUtils
import android.view.View
import com.example.messenger.R
import com.example.messenger.model.CUser
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.ktx.database

class CActivitySignIn : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth

        binding.btnSignIn.setOnClickListener(this)
        binding.btnRegistration.setOnClickListener(this)

    }
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnSignIn -> {
                signIn(binding.etLogin.text.toString(),
                    binding.etPassword.text.toString())
            }
            R.id.btnRegistration -> {
                createAccount(binding.etLogin.text.toString(),
                    binding.etPassword.text.toString())
            }
        }
    }
    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            start()
        }
    }
    private fun signIn(email: String, password: String) {
        if (emptyForm()) {
            return
        }
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    start()
                } else {
                    Toast.makeText(baseContext,
                        getString(R.string.sign_in_error) + task.exception?.localizedMessage,
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun createAccount(email: String, password: String) {
        if (emptyForm()) {
            return
        }
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                    start()
                } else {
                    Toast.makeText(baseContext,
                        getString(R.string.create_account_error) + task.exception?.localizedMessage,
                        Toast.LENGTH_SHORT).show()
                }

            }
    }
    private fun start() {
        startActivity(Intent(this, CActivityMessagesList::class.java))
        finish()
    }
    private fun emptyForm(): Boolean {
        var empty = true
        val email: String = binding.etLogin.text.toString()
        if (TextUtils.isEmpty(email)){
            Toast.makeText(baseContext, getString(R.string.sign_in_empty_email),
                Toast.LENGTH_SHORT).show()
            empty = false
        } else {
            val password: String = binding.etPassword.text.toString()
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(baseContext, getString(R.string.sign_in_empty_password),
                    Toast.LENGTH_SHORT).show()
                empty = false
            }
        }
        return !empty
    }
    private fun updateUI(user: FirebaseUser?) {
        val userID: String? = user?.uid
        val email: String? = user?.email
        val name = "NoName"
        val surname = "NoSurname"
        writeNewUser(userID, name, surname, email)
        //uploadFile(userID)
    }
    private fun writeNewUser(userId: String?, name: String?, surname: String?, email: String?) {

        val database = Firebase.database("https://mymessenger-d58ca-default-rtdb.europe-west1.firebasedatabase.app")
        val myRef = database.reference
        val user = CUser(name, surname, email)
        if (userId != null) {
            myRef.child("users").child(userId).child("userdata").setValue(user)
        }
    }
//    fun uploadFile(userID: String?) {
//        val data: ByteArray = ImageToByteArray(mIcStartImage)
//        val imageRef: StorageReference = mStorageRef.child("users").child(userID).child("icon_user")
//        val uploadTask: UploadTask = imageRef.putBytes(data)
//        uploadTask.addOnFailureListener(OnFailureListener { })
//            .addOnSuccessListener(OnSuccessListener<Any?> { })
//    }
}
