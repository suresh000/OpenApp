package com.balaji.openapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.widget.Toast

class UserPresentBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action.equals(Intent.ACTION_SCREEN_OFF)) {
            Toast.makeText(context, "UserPresentBroadcastReceiver: screen OFF", Toast.LENGTH_LONG).show()
        } else if (intent.action.equals(Intent.ACTION_SCREEN_ON)) {
            Toast.makeText(context, "UserPresentBroadcastReceiver: screen ON", Toast.LENGTH_LONG).show()
            openAppIntent(context)
        } else if (intent.action.equals(Intent.ACTION_USER_PRESENT)) {
            Toast.makeText(context, "UserPresentBroadcastReceiver: User Present", Toast.LENGTH_SHORT).show()
            openAppIntent(context)
        } else if (intent.action.equals(Intent.ACTION_USER_UNLOCKED)) {
            Toast.makeText(context, "User Unlocked", Toast.LENGTH_LONG).show()
        }
    }

    private fun openAppIntent(context: Context) {
        //val mainIntent = Intent(context, MainActivity::class.java)
        //mainIntent.setClassName("com.balaji.openapp", "com.balaji.openapp.MainActivity")
        //mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        //mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        //context.startActivity(mainIntent)
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
            Toast.makeText(context, "UserPresentBroadcastReceiver: Not able to start this app", Toast.LENGTH_SHORT).show()
        }
    }
}