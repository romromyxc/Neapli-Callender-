package com.example.nepalicalendar

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.Toast

class MainActivity : Activity() {

    private val overlayPermissionReqCode = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val startBtn = Button(this).apply {
            text = "Enable Transparent Bottom Calendar"
            textSize = 16f
            setOnClickListener { checkAndStartOverlay() }
        }
        setContentView(startBtn)
    }

    private fun checkAndStartOverlay() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            )
            startActivityForResult(intent, overlayPermissionReqCode)
        } else {
            startOverlayService()
        }
    }

    private fun startOverlayService() {
        startService(Intent(this, OverlayService::class.java))
        Toast.makeText(this, "Dock enabled at bottom of display", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == overlayPermissionReqCode) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.canDrawOverlays(this)) {
                startOverlayService()
            } else {
                Toast.makeText(this, "Permission required for transparent mode", Toast.LENGTH_LONG).show()
            }
        }
    }
}
