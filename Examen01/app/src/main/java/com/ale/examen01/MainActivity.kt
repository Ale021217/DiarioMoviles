import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ale.examen01.R

class MainActivity : AppCompatActivity() {
    private lateinit var codigoEditText: EditText
    private lateinit var descripcionEditText: EditText
    private lateinit var categoriaEditText: EditText
    private lateinit var minimoEditText: EditText
    private lateinit var maximoEditText: EditText
    private lateinit var descontinuadoRadioGroup: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        codigoEditText = findViewById(R.id.codigoEditText)
        descripcionEditText = findViewById(R.id.descripcionEditText)
        categoriaEditText = findViewById(R.id.categoriaEditText)
        minimoEditText = findViewById(R.id.minimoEditText)
        maximoEditText = findViewById(R.id.maximoEditText)
        descontinuadoRadioGroup = findViewById(R.id.descontinuadoRadioGroup)

        val aceptarButton: Button = findViewById(R.id.button2)
        aceptarButton.setOnClickListener {
            // Realizar validaciones
            if (validarCampos()) {
                // Mostrar mensaje de registro exitoso
                mostrarMensaje("Producto registrado")

                // Limpiar o establecer campos por defecto
                limpiarCampos()
            }
        }
    }

    // Método para realizar validaciones
    private fun validarCampos(): Boolean {
        // Agrega tus condiciones de validación aquí
        val codigo = codigoEditText.text.toString()
        val descripcion = descripcionEditText.text.toString()
        val categoria = categoriaEditText.text.toString()
        val minimo = minimoEditText.text.toString()
        val maximo = maximoEditText.text.toString()

        // Por ejemplo, verifica si los campos no están vacíos y los valores numéricos son mayores a cero
        if (codigo.isEmpty() || descripcion.isEmpty() || categoria.isEmpty() || minimo.isEmpty() || maximo.isEmpty()) {
            mostrarMensaje("Por favor, complete todos los campos.")
            return false
        }

        val minimoValor = minimo.toInt()
        val maximoValor = maximo.toInt()

        if (minimoValor <= 0 || maximoValor <= 0) {
            mostrarMensaje("Los valores mínimos y máximos deben ser mayores a cero.")
            return false
        }

        // Agrega más condiciones de validación según tus requisitos

        return true
    }

    // Método para mostrar mensajes Toast
    private fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    // Método para limpiar los campos
    private fun limpiarCampos() {
        codigoEditText.text.clear()
        descripcionEditText.text.clear()
        categoriaEditText.text.clear()
        minimoEditText.text.clear()
        maximoEditText.text.clear()
        descontinuadoRadioGroup.clearCheck()
    }
}
