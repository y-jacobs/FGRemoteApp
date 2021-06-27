package com.example.myremoteapp.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myremoteapp.databinding.ActivityMainBinding
import com.example.myremoteapp.model.RemoteModel
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class ViewModel: ViewModel() {
    val ip = MutableLiveData<String>()
    val port = MutableLiveData<String>()
    private var model: RemoteModel = RemoteModel()
    // private var execute: ExecutorService = Executors.newFixedThreadPool(1)
    lateinit var bind: ActivityMainBinding

    // joystick settings
    var x = 0f
    var y = 0f
    var r = 0f

    @Volatile
    var isConnected = false

    // connect the model to the flight gear
    fun connect() {
        // geting the ip ant port from user
        if (!ip.value.isNullOrEmpty() && !port.value.isNullOrEmpty()) {

            bind.buttonConnect.visibility = View.INVISIBLE
            // bind.buttonDisconnect.visibility = View.VISIBLE

            // in a thread connecting the model with the ip & port
            Thread {
                this.isConnected = model.connectFG(ip.value.toString(), port.value.toString().toInt())
            }.start()
        }
    }

    // disconnect view model
    fun disconnect() {
        if (isConnected) {
            Thread {
                model.disconnectFG()
            }
            // bind.buttonDisconnect.visibility = View.INVISIBLE
            bind.buttonConnect.visibility = View.VISIBLE
        }

    }

    // setting the joystick in the model
    fun setJoystick(newX: Float, newY:Float) {
        if (isConnected)
        {
            var a = (newX - this.x) / r
            var b = (newY - this.y) / r
            Thread {
                model.changeAileronElevator(a,b)
            }.start()
        }
    }

    // setting the throttle bar in the model
    fun setThrottle(throttle: Int) {
        if (isConnected) {
            Thread {
                val t = throttle.toFloat() / 100f
                model.changeThrottle(t)
            }.start()
        }
    }

    // setting the rudder bar in the model
    fun setRudder(rudder: Int) {
        if (isConnected) {
            Thread {
                val t = (rudder - 50).toFloat() / 50f
                model.changeRudder(t)
            }.start()
        }
    }
}