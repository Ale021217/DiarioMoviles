package com.demoyork.a01_status2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.util.Log
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    val TAG = "Estados"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(TAG, "Método onCreate()")
        Toast.makeText(this,"Método onCreate()",Toast.LENGTH_SHORT).show()

    }
    override fun onStart() {
        super.onStart()
        Log.i(TAG, "Método onStart()")
        Toast.makeText(this,
            "Método onStart()",Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "Método onResume()")
        Toast.makeText(this,
            "Método onResume()",Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "Método onPause()")
        Toast.makeText(this,
            "Método onPause()",Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "Método onStop()")
        Toast.makeText(this,
            "Método onStop()",Toast.LENGTH_SHORT).show()
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "Método onRestart()")
        Toast.makeText(this,
            "Método onRestart()",Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "Método onDestroy()")
        Toast.makeText(this,
            "Método onDestroy()",Toast.LENGTH_SHORT).show()
    }

}