package com.lev.filterapp

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast

class AppBlockerService : AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event?.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            val packageName = event.packageName?.toString() ?: return
            val className = event.className?.toString() ?: ""

            // חסימת הגדרות מערכת, מנהל יישומים, ודפי ביטול מנהל מכשיר
            if (packageName.contains("settings") || 
                packageName.contains("packageinstaller") || 
                className.contains("DeviceAdminAdd")) {
                
                showBlockNotice()
                goHome()
            }

            // חסימת YouTube או אפליקציות אסורות
            if (packageName.contains("google.android.youtube")) {
                showBlockNotice()
                goHome()
            }

            // חסימת הגדרות נקודה חמה / טתרינג
            if (className.contains("TetherSettings") || className.contains("Hotspot")) {
                Toast.makeText(this, "הנקודה חמה נחסמה לכבוד השם יתברך", Toast.LENGTH_LONG).show()
                goHome()
            }
        }
    }

    private fun showBlockNotice() {
        Toast.makeText(this, "האפליקציה נחסמה לכבוד השם יתברך", Toast.LENGTH_LONG).show()
    }

    private fun goHome() {
        val homeIntent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_HOME)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(homeIntent)
    }

    override fun onInterrupt() {}
}
