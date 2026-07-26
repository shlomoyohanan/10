package com.lev.filterapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // יצירת מסך פשוט שיררא שהאפליקציה עובדת
        val textView = TextView(this).apply {
            text = "FilterApp פועלת בהצלחה!"
            textSize = 22f
            setPadding(50, 50, 50, 50)
        }
        setContentView(textView)
    }
}
