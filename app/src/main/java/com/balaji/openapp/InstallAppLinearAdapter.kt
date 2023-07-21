package com.balaji.openapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView

class InstallAppLinearAdapter(private val mInstallAppList: ArrayList<AppInfo>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var isSelected = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.install_app_linear_item, parent, false)
        return InstallAppViewHolder(v)
    }

    override fun getItemCount(): Int {
        return mInstallAppList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val appInfo = mInstallAppList[holder.adapterPosition]
        holder.itemView.findViewById<TextView>(R.id.tvAppName).text = appInfo.appname
        holder.itemView.findViewById<TextView>(R.id.tvVersionName).text = appInfo.versionName
        holder.itemView.findViewById<ImageView>(R.id.ivAppIcon).roundedImageView(appInfo.icon)
        val packageName = SharedPreferencesManager.getString(Keys.KEY_PACKAGE_NAME)
        val switchCompact = holder.itemView.findViewById<SwitchCompat>(R.id.switchView)
        switchCompact.isChecked = appInfo.pname == packageName
        switchCompact.setOnCheckedChangeListener { buttonView, isChecked ->
            isSelected = isChecked
            if (isChecked) {
                SharedPreferencesManager.putString(Keys.KEY_PACKAGE_NAME, appInfo.pname)
            } else {
                SharedPreferencesManager.putString(Keys.KEY_PACKAGE_NAME, "")
            }
        }
    }

    class InstallAppViewHolder(view: View) : RecyclerView.ViewHolder(view)
}