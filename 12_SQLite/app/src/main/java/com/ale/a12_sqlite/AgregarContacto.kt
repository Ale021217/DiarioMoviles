package com.example.a12_sqlite

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import DBAdapter
import com.ale.a12_sqlite.R

class AgregarContacto : AppCompatActivity() {
    private lateinit var nombreEditText: EditText
    private lateinit var apellidoEditText: EditText
    private lateinit var correoEditText: EditText
    private lateinit var telefonoEditText: EditText
    private lateinit var agregarButton: Button
    private var db: DBAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_contacto)

        nombreEditText = findViewById(R.id.nombre)
        apellidoEditText = findViewById(R.id.apellido)
        correoEditText = findViewById(R.id.correo)
        telefonoEditText = findViewById(R.id.telefono)
        agregarButton = findViewById(R.id.agregar)

        db = DBAdapter(this)

        agregarButton.setOnClickListener {
            val nombre = nombreEditText.text.toString()
            val apellidos = apellidoEditText.text.toString()
            val correo = correoEditText.text.toString()
            val telefonoStr = telefonoEditText.text.toString()

            if (nombre.isNotBlank() && apellidos.isNotBlank() && correo.isNotBlank() && telefonoStr.isNotBlank()) {
                try {
                    val telefono = telefonoStr.toLong()
                    db?.open()
                    val result = db?.insContact(nombre, apellidos, correo, telefono)

                    if (result != -1L) {
                        showToast("Contacto agregado con éxito")
                        nombreEditText.text.clear()
                        apellidoEditText.text.clear()
                        correoEditText.text.clear()
                        telefonoEditText.text.clear()
                    } else {
                        showToast("Error al agregar el contacto")
                    }

                    db?.close()
                } catch (e: NumberFormatException) {
                    showToast("El número de teléfono debe ser un valor numérico válido")
                }
            } else {
                showToast("Por favor, completa todos los campos")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
