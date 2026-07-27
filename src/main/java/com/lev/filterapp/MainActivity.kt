package com.lev.filterapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // יצירת מסך פשוט שמוודא שהאפליקציה עובדת
        val textView = TextView(this).apply {
            text = "FilterApp פועלת בהצלחה!\n(בית המקדש השני)"
            textSize = 22f
            setPadding(50, 50, 50, 50)
        }
        setContentView(textView)
    }
}
