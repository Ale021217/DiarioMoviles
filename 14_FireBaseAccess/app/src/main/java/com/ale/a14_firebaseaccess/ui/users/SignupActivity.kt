package com.ale.a14_firebaseaccess.ui.users

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ale.a14_firebaseaccess.R
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.EditText
import android.widget.Button
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Date

class SignupActivity : AppCompatActivity() {
    var auth = FirebaseAuth.getInstance()
    var db = FirebaseFirestore.getInstance()

    private lateinit var txtRNombre: EditText
    private lateinit var txtREmail: EditText
    private lateinit var txtRContra: EditText
    private lateinit var txtRreContra: EditText
    private lateinit var btnRegistrarU: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        txtRNombre = findViewById(R.id.txtRNombre)
        txtREmail = findViewById(R.id.txtREmail)
        txtRContra = findViewById(R.id.txtRContra)
        txtRreContra = findViewById(R.id.txtRreContra)
        btnRegistrarU = findViewById(R.id.btnRegistrarU)

        btnRegistrarU.setOnClickListener {
            registrarUsuario()
        }
    }

    private fun registrarUsuario() {
        val nombre = txtRNombre.text.toString()
        val email = txtREmail.text.toString()
        val contra = txtRContra.text.toString()
        val reContra = txtRreContra.text.toString()

        if (nombre.isEmpty() || email.isEmpty() || contra.isEmpty() || reContra.isEmpty()) {
            Toast.makeText(this, "Favor de llenar todos los campos", Toast.LENGTH_SHORT).show()
        } else {
            if (contra == reContra) {
                auth.createUserWithEmailAndPassword(email, contra)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val dt: Date = Date()
                            val user = hashMapOf(
                                "idemp" to task.result?.user?.uid,
                                "usuario" to nombre,
                                "email" to email,
                                "ultAcceso" to dt.toString(),
                            )

                            // Obtener el CustomerID
                            obtenerCustomerIdSegunRegistroUsuario(nombre, email)
                                .addOnCompleteListener { customerIdTask ->
                                    if (customerIdTask.isSuccessful) {
                                        val CustomerId = customerIdTask.result
                                        if (CustomerId != null) {
                                            // Agregar CustomerID al usuario
                                            user["CustomerId"] = CustomerId

                                            // Resto del código para agregar a la colección datosUsuarios...
                                            db.collection("datosUsuarios")
                                                .add(user)
                                                .addOnSuccessListener { documentReference ->
                                                    // Register the data into the local storage
                                                    val prefe = this.getSharedPreferences("appData", Context.MODE_PRIVATE)
                                                    val editor = prefe.edit()

                                                    // Set editor fields with the new values
                                                    editor.putString("email", email.toString())
                                                    editor.putString("contra", contra.toString())
                                                    editor.putString("CustomerId", CustomerId.toString())

                                                    // Write app data
                                                    editor.apply()

                                                    Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show()

                                                    Intent().let {
                                                        setResult(Activity.RESULT_OK)
                                                        finish()
                                                    }
                                                }
                                                .addOnFailureListener { e ->
                                                    Toast.makeText(this, "Error al registrar usuario", Toast.LENGTH_SHORT).show()
                                                }
                                        } else {
                                            // Manejar el caso en que no se encuentra CustomerID
                                            Toast.makeText(this, "No se encontró el CustomerID", Toast.LENGTH_SHORT).show()
                                        }
                                    } else {
                                        // Manejar el caso en que la obtención de CustomerID falla
                                        Toast.makeText(this, "Error al obtener el CustomerID", Toast.LENGTH_SHORT).show()
                                    }
                                }
                        } else {
                            // Manejar el caso en que el registro de usuario falla
                            Toast.makeText(this, "Error al registrar usuario", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun obtenerCustomerIdSegunRegistroUsuario(nombre: String, email: String): Task<String?> {
        return db.collection("Customers")
            .whereEqualTo("ContactName", nombre)
            .whereEqualTo("ContactEmail", email)
            .get()
            .continueWith { task ->
                if (task.isSuccessful) {
                    val result = task.result?.documents?.firstOrNull()
                    result?.getString("CustomerID") // Ajusta esto según la estructura de tu base de datos
                } else {
                    null
                }
            }
    }
}
