package com.lev.filterapp

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var devicePolicyManager: DevicePolicyManager
    private lateinit var componentName: ComponentName
    private val MASTER_PIN = "123456"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        devicePolicyManager = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        componentName = ComponentName(this, MyDeviceAdminReceiver::class.java)

        val btnActivateAdmin = findViewById<Button>(R.id.btnActivateAdmin)
        val btnOpenSettings = findViewById<Button>(R.id.btnOpenSettings)

        btnActivateAdmin.setOnClickListener {
            if (!devicePolicyManager.isAdminActive(componentName)) {
                val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN).apply {
                    putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName)
                    putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "נדרש למניעת הסרת האפליקציה.")
                }
                startActivity(intent)
            } else {
                Toast.makeText(this, "הרשאת מנהל מכשיר כבר פעילה!", Toast.LENGTH_SHORT).show()
            }
        }

        btnOpenSettings.setOnClickListener {
            showPinDialog {
                Toast.makeText(this, "הקוד נכון. גישה להגדרות מורשית.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showPinDialog(onSuccess: () -> Unit) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("הכנס קוד ניהול (6 ספרות)")

        val input = EditText(this)
        input.inputType = android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_NUMBER_VARIATION_PASSWORD
        builder.setView(input)

        builder.setPositiveButton("אישור") { _, _ ->
            val pin = input.text.toString()
            if (pin == MASTER_PIN) {
                onSuccess()
            } else {
                Toast.makeText(this, "קוד שגוי!", Toast.LENGTH_LONG).show()
            }
        }
        builder.setNegativeButton("ביטול") { dialog, _ -> dialog.cancel() }
        builder.show()
    }

    override fun onBackPressed() {
        showPinDialog {
            super.onBackPressed()
        }
    }
}
