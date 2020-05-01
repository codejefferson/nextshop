package com.nextshop.ui.scheme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import com.nextshop.ui.ParentActivity

class SchemeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, ParentActivity::class.java))
        finish()
    }
}
