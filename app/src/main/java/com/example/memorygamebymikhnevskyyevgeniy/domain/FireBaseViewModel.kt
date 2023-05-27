package com.example.memorygamebymikhnevskyyevgeniy.domain

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.memorygamebymikhnevskyyevgeniy.database.FireBase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class FireBaseViewModel(private val fireBase: FireBase) : ViewModel() {
    private val _valueBoolean = MutableLiveData<Boolean>()
    val valueBooleanFB: LiveData<Boolean> = _valueBoolean

    private val _realTimeDBLink = MutableLiveData<String>()
    val realTimeFB: LiveData<String> = _realTimeDBLink

    init {
        getBooleanFB()
        getLinkFB()
    }

    private fun getBooleanFB() {
        fireBase.remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.w("TAG", "getBooleanFB = ${task.result}")
                _valueBoolean.value = task.result
                Log.w("TAG", "_valueBoolean.value = ${_valueBoolean.value}")

            } else {
                Log.w("TAG", "Fetch failed")
            }
        }
    }

    private fun getLinkFB() {
        val myReference = fireBase.databaseRef.child("KeyLink")
        myReference.setValue("https://www.pinterest.com/search/pins/?q=adnroid&rs=typed")
        Log.w("TAG", "getLinkFB started")

        myReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.w("TAG", "snapshot.value ${snapshot.value}")
                _realTimeDBLink.value = snapshot.getValue<String>()
                Log.w("TAG", "_realTimeDBLink.value ${_realTimeDBLink.value}")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "Failed to read value.")
            }
        })
    }
}

