package com.speira.antropofit

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), UpdateValidator.OnUpdateNeededListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        UpdateValidator.with(this).onUpdateNeededListener(this).check()
    }

    override fun onUpdateNeeded(updateURL: String) {
        val dialog = AlertDialog.Builder(this).setTitle("")
            .setMessage("")
            .setPositiveButton("Actualizar") { _, _ ->
                goToPlayStore(updateURL)
            }.create()
        dialog.show()
    }

    private fun goToPlayStore(updateURL: String) {

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(updateURL))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)

    }
}
