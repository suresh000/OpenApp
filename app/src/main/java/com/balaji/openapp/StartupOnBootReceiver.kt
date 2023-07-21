package com.balaji.openapp

import android.app.UiModeManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class StartupOnBootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        try {
            val uiModeManager =
                context.getSystemService(AppCompatActivity.UI_MODE_SERVICE) as UiModeManager
            if (Intent.ACTION_BOOT_COMPLETED == intent.action ||
                Intent.ACTION_REBOOT == intent.action ||
                Intent.ACTION_SHUTDOWN == intent.action
            ) {
                val serviceIntent = Intent(context, MyForegroundService::class.java)
                context.startForegroundService(serviceIntent)
                Toast.makeText(context, "Foreground Service start-1", Toast.LENGTH_SHORT).show()
                /*if (uiModeManager.currentModeType == Configuration.UI_MODE_TYPE_TELEVISION) {
                    context.startForegroundService(serviceIntent)
                    Toast.makeText(context, "Foreground Service start-2", Toast.LENGTH_SHORT).show()
                } else {
                    context.startForegroundService(serviceIntent)
                    Toast.makeText(context, "Foreground Service start-3", Toast.LENGTH_SHORT).show()
                }*/
            } else {
                if (uiModeManager.currentModeType == Configuration.UI_MODE_TYPE_TELEVISION) {
                    val serviceIntent = Intent(context, MyForegroundService::class.java)
                    context.startForegroundService(serviceIntent)
                    Toast.makeText(context, "Foreground Service start-2", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(
                context,
                "Exception(StartupOnBootReceiver): in onReceive",
                Toast.LENGTH_SHORT
            ).show()
        } finally {
            openAppIntent(context)
        }
    }

    private fun openAppIntent(context: Context) {
        var packageName = SharedPreferencesManager.getString(Keys.KEY_PACKAGE_NAME)
        if (TextUtils.isEmpty(packageName)) {
            packageName = "com.balaji.openapp"
        }
        val launchIntent = context.packageManager.getLaunchIntentForPackage(packageName)
        launchIntent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            context.startActivity(launchIntent)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(
                context,
                "StartupOnBootReceiver: Not able to start this app",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}