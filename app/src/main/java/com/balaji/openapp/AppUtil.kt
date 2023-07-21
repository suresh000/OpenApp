package com.balaji.openapp

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo


class AppUtil {

    companion object {

        @JvmStatic
        fun getInstalledApps(context: Context, getSysPackages: Boolean): ArrayList<AppInfo> {
            val res = ArrayList<AppInfo>()
            res.clear()
            //    val packs: List<PackageInfo> = context.packageManager.getInstalledPackages(PackageManager.GET_META_DATA)
            val packs: List<PackageInfo> = context.packageManager.getInstalledPackages(0)
            for (i in packs.indices) {
                val p: PackageInfo = packs[i]
                val ai: ApplicationInfo = p.applicationInfo
                if (!getSysPackages && p.versionName == null) {
                    continue
                }
                if ((ai.flags and ApplicationInfo.FLAG_SYSTEM) == 0) {
                    if (p.packageName != "com.balaji.openapp") {
                        val newInfo = AppInfo()
                        newInfo.appname = ai.loadLabel(context.packageManager).toString()
                        newInfo.pname = p.packageName
                        newInfo.versionName = p.versionName
                        newInfo.versionCode = p.versionCode
                        newInfo.icon = ai.loadIcon(context.packageManager)
                        res.add(newInfo)
                    }
                }
            }
            return res
        }

    }

}