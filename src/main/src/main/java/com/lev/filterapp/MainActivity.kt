package com.lev.filterapp

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val ADMIN_PIN = "123456" // קוד ניהול בן 6 ספרות
    private lateinit var devicePolicyManager: DevicePolicyManager
    private lateinit var adminComponent: ComponentName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        devicePolicyManager = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        adminComponent = ComponentName(this, AdminReceiver::class.java)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(40, 40, 40, 40)
        }

        val title = TextView(this).apply {
            text = "לכבוד השם יתברך - מערכת סינון מתקדמת"
            textSize = 18f
            setPadding(0, 0, 0, 20)
        }
        layout.addView(title)

        val statusText = TextView(this).apply {
            text = "האפליקציה פועלת ומוגנת. תומכת מאנדרואיד 5 ומעלה."
            textSize = 14f
            setPadding(0, 0, 0, 30)
        }
        layout.addView(statusText)

        // הפעלת הרשאת מנהל מכשיר
        val btnAdmin = Button(this).apply {
            text = "הפעל הגנת מנהל מכשיר (מונע הסרה)"
            setOnClickListener {
                val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN).apply {
                    putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, adminComponent)
                    putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "נדרש כדי למנוע הסרת התקנה של מערכת הסינון לכבוד השם יתברך.")
                }
                startActivity(intent)
            }
        }
        layout.addView(btnAdmin)

        // הפעלת שירות הנגישות
        val btnAccessibility = Button(this).apply {
            text = "הפעל הרשאת סינון וחסימה (נגישות)"
            setOnClickListener {
                startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
            }
        }
        layout.addView(btnAccessibility)

        val pinLabel = TextView(this).apply {
            text = "הכנס קוד ניהול (6 ספרות) להסרה או שינויים:"
            setPadding(0, 40, 0, 10)
        }
        layout.addView(pinLabel)

        val pinInput = EditText(this).apply {
            hint = "הקש קוד בן 6 ספרות"
            inputType = android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
        layout.addView(pinInput)

        // כפתור הסרת ההגנה באמצעות קוד בלבד
        val btnRemoveAdmin = Button(this).apply {
            text = "הסרת הגנת מנהל (דורש קוד)"
            setOnClickListener {
                if (pinInput.text.toString() == ADMIN_PIN) {
                    devicePolicyManager.removeActiveAdmin(adminComponent)
                    Toast.makeText(this@MainActivity, "ההגנה הוסרה בהצלחה", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity, "קוד שגוי!", Toast.LENGTH_SHORT).show()
                }
            }
        }
        layout.addView(btnRemoveAdmin)

        setContentView(layout)
    }
}
