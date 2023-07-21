package com.balaji.openapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class InstallAppGridAdapter(private val mContext: Context,
                            private val mInstallAppList: ArrayList<AppInfo>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.install_app_grid_item, parent, false)
        return InstallAppViewHolder(v)
    }

    override fun getItemCount(): Int {
        return mInstallAppList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val appInfo = mInstallAppList[holder.adapterPosition]
        holder.itemView.findViewById<TextView>(R.id.tvAppName).text = appInfo.appname
        holder.itemView.findViewById<TextView>(R.id.tvVersionName).text = appInfo.versionName
        holder.itemView.findViewById<ImageView>(R.id.ivAppIcon).setImageDrawable(appInfo.icon)
        val button: MaterialButton = holder.itemView.findViewById(R.id.button)
        button.setOnClickListener {
            SharedPreferencesManager.putString(Keys.KEY_PACKAGE_NAME, appInfo.pname)
            notifyDataSetChanged()
        }
        val ivDone: ImageView = holder.itemView.findViewById(R.id.ivDone)

        val packageName = SharedPreferencesManager.getString(Keys.KEY_PACKAGE_NAME)
        if (appInfo.pname == packageName) {
            ivDone.visibility = View.VISIBLE
            button.visibility = View.GONE
        } else {
            ivDone.visibility = View.GONE
            button.visibility = View.VISIBLE
        }

    }

    class InstallAppViewHolder(view: View) : RecyclerView.ViewHolder(view)
}