package com.example.myremoteapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.myremoteapp.R
import com.example.myremoteapp.databinding.ActivityMainBinding
import com.example.myremoteapp.viewmodel.ViewModel

class MainActivity : AppCompatActivity(), IJoystickObserver {

    private lateinit var bind: ActivityMainBinding
    private lateinit var viewmodel: ViewModel
    private lateinit var joystick: Joystick

    // @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // set view from layout
        setContentView(R.layout.activity_main)

        // initiating view model
        viewmodel = ViewModelProvider(this).get(ViewModel::class.java)

        // initiating binding
        bind =  DataBindingUtil.setContentView( this, R.layout.activity_main)
        bind.apply {
            this.lifecycleOwner = this@MainActivity
            this.viewModel = viewmodel
        }
        viewmodel.bind=bind

        // initiating the joystick
        joystick = Joystick(this)

        // setup for the seek bars, vertical throttle bar, both will start from "0"
        bind.seekBarThrottle.rotation = 270F
        bind.seekBarRudder.progress = 50
        bindRudder()
        bindThrottle()
    }

    // bind movement of seek bar to rudder
    private fun bindRudder() {
        bind.seekBarRudder.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (seekBar != null) {
                    viewmodel.setRudder(seekBar.progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                if (seekBar != null) {
                    viewmodel.setRudder(seekBar.progress)
                }
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if (seekBar != null) {
                    viewmodel.setRudder(seekBar.progress)
                }
            }
        }
        )
    }

    // bind movement of seek bar to throttle
    private fun bindThrottle() {
        bind.seekBarThrottle.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (seekBar != null) {
                    viewmodel.setThrottle(seekBar.progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                if (seekBar != null) {
                    viewmodel.setThrottle(seekBar.progress)
                }
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if (seekBar != null) {
                    viewmodel.setThrottle(seekBar.progress)
                }
            }
        }
        )
    }

    override fun onChange(x: Float, y: Float) {
        viewmodel.setJoystick(x,y)
    }

    // set joystick interface
    override fun onCreateJoystick(x: Float, y: Float, r: Float) {
        viewmodel.x=x
        viewmodel.y=y
        viewmodel.r=r
    }

}

