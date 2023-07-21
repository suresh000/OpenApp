package com.balaji.openapp

import android.app.UiModeManager
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var emptyTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        emptyTextView = findViewById(R.id.emptyText)

        if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.P){
            if (!Settings.canDrawOverlays(this)) {
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
                )
                startActivity(intent)
            }
        }

        val serviceIntent = Intent(this, MyForegroundService::class.java)
        startForegroundService(serviceIntent)
        Toast.makeText(this, "Foreground Service start: Main", Toast.LENGTH_SHORT).show()

        var gridCount = 2
        val uiModeManager = getSystemService(UI_MODE_SERVICE) as UiModeManager
        gridCount = if (uiModeManager.currentModeType == Configuration.UI_MODE_TYPE_TELEVISION) {
            //startService(serviceIntent)
            4
        } else {
            //startForegroundService(serviceIntent)
            2
        }

        val installApps: ArrayList<AppInfo> = AppUtil.getInstalledApps(this, false)
        if (installApps.size > 0) {
            emptyTextView.visibility = View.GONE
            val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
            //recyclerView.layoutManager = LinearLayoutManager(this)
            //val adapter = InstallAppLinearAdapter(installApps)
            recyclerView.layoutManager = GridLayoutManager(this, gridCount,
                GridLayoutManager.VERTICAL, false)
            val adapter = InstallAppGridAdapter(this, installApps)
            recyclerView.adapter = adapter
        } else {
            emptyTextView.visibility = View.VISIBLE
        }
    }
}