package com.example.notificationsapp.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.notificationsapp.R

class MainActivity : AppCompatActivity() {
    private var viewModel = MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = BaseFragmentFactory()
        super.onCreate(savedInstanceState)
        viewModel.setCount(0)
        setContentView(R.layout.activity_main)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        if(intent!=null){
            val notificationNum = intent.getIntExtra("notificationNum", 0)
            viewModel.setCount(notificationNum)
        }
    }
}