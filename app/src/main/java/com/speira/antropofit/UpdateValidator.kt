package com.speira.antropofit

import android.content.Context
import android.util.Log
import com.google.firebase.remoteconfig.FirebaseRemoteConfig

class UpdateValidator(
    private val context: Context,
    private val onUpdateNeededListener: OnUpdateNeededListener
) {

    companion object {

        val TAG: String = UpdateValidator::class.java.simpleName
        const val UPDATE_REQUIRED: String = "update_required"
        const val CURRENT_VERSION: String = "current_version"
        const val GOOGLE_PLAY_URL: String = "google_play_url"

        fun with(context: Context): Builder = Builder(context)

        class Builder(private val context: Context) {

            private lateinit var onUpdateNeededListener: OnUpdateNeededListener

            fun onUpdateNeededListener(onUpdateNeededListener: OnUpdateNeededListener): Builder {
                this.onUpdateNeededListener = onUpdateNeededListener
                return this
            }

            private fun build(): UpdateValidator = UpdateValidator(context, onUpdateNeededListener)

            fun check(): UpdateValidator {
                val updateValidator = build()
                updateValidator.check()
                return updateValidator
            }
        }

    }

    interface OnUpdateNeededListener {
        fun onUpdateNeeded(updateURL: String)
    }


    fun check() {
        val firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

        Log.e("REMOTE", firebaseRemoteConfig.toString())
        Log.e("GAAA", "ANTES DE UPDATE_REQUIRED")
        if (firebaseRemoteConfig.getBoolean(UPDATE_REQUIRED)) {
            Log.e("GAAA", "ENTRE A DE UPDATE_REQUIRED")
            val currentVersion = firebaseRemoteConfig.getString(CURRENT_VERSION).trim()
            val appVersion = getAppVersion().trim()
            val updateURL = firebaseRemoteConfig.getString(GOOGLE_PLAY_URL).trim()
            Log.e("GAAA", "$appVersion $updateURL")
            if (appVersion != currentVersion) {
                onUpdateNeededListener.onUpdateNeeded(updateURL)
            }
        }
    }

    private fun getAppVersion(): String {
        var version = ""
        try {
            version = context.packageManager.getPackageInfo(context.packageName, 0).versionName
            version = version.replace("[a-zA-Z]|-".toRegex(), "")
        } catch (e: Exception) {
            Log.e(TAG, e.message)
        }
        return version
    }

}