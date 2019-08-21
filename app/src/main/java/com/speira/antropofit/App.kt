package com.speira.antropofit

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.remoteconfig.FirebaseRemoteConfig

class App : Application() {

    companion object {

        private val TAG: String? = App::class.java.simpleName

    }

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        val firebaseRemoteConfig: FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        val remoteConfigDefaults: HashMap<String, Any> = HashMap()
        remoteConfigDefaults[UpdateValidator.UPDATE_REQUIRED]
        remoteConfigDefaults[UpdateValidator.UPDATE_REQUIRED] = false
        remoteConfigDefaults[UpdateValidator.CURRENT_VERSION] = "1.0.0"
        remoteConfigDefaults[UpdateValidator.GOOGLE_PLAY_URL] = "https://play.google.com/store/apps/details?id=com.arquea.tottusnsg"
        firebaseRemoteConfig.setDefaults(remoteConfigDefaults)
        firebaseRemoteConfig.fetch(60).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.e(TAG, "Configuración remota obtenida")
                firebaseRemoteConfig.activateFetched()
            }
        }
    }
}