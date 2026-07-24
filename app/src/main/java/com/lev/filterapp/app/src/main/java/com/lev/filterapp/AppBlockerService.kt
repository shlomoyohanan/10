package com.lev.filterapp

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast

class AppBlockerService : AccessibilityService() {

    private val blockedPackages = listOf(
        "com.google.android.youtube" // דוגמה לחסימת יוטיוב
    )

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        if (event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            val packageName = event.packageName?.toString()
            if (packageName != null && blockedPackages.contains(packageName)) {
                performGlobalAction(GLOBAL_ACTION_HOME)
                Toast.makeText(this, "האפליקציה נחסמה לכבוד השם יתברך", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onInterrupt() {}
}
