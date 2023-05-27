package com.example.memorygamebymikhnevskyyevgeniy.database

import com.example.memorygamebymikhnevskyyevgeniy.R
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

class FireBase {
    val remoteConfig = Firebase.remoteConfig
    val databaseRef = Firebase.database.reference
    private val configSettings = remoteConfigSettings {
        minimumFetchIntervalInSeconds = 3600
    }

    init {
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
    }

    companion object {
        private var fireBase: FireBase? = null
        val instance: FireBase
            get() {
                if (fireBase == null) {
                    fireBase = FireBase()
                }
                return fireBase!!
            }
    }
}