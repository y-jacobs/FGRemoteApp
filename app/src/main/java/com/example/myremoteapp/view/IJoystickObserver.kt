package com.example.myremoteapp.view

interface IJoystickObserver {
    fun onChange(x:Float, y:Float)
    fun onCreateJoystick(x: Float, y: Float, r: Float)
}