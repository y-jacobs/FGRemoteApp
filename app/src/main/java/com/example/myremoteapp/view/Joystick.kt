package com.example.myremoteapp.view

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import java.lang.Math.sqrt
import kotlin.math.pow

class Joystick : SurfaceView,SurfaceHolder.Callback, View.OnTouchListener {
    lateinit var listener: IJoystickObserver
    var centerX = 0f
    var centerY = 0f
    var rBase = 0f
    var rKnob = 0f

    // constructors
    constructor(context: Context?) : super(context) {
        holder.addCallback(this)
        setOnTouchListener(this)
        if (context is IJoystickObserver) {
            listener = context
        }
    }

    constructor(context: Context?, att: AttributeSet?) : super(context, att) {
        holder.addCallback(this)
        setOnTouchListener(this)
        if (context is IJoystickObserver) {
            listener = context
        }
    }

    constructor(context: Context?, att: AttributeSet?, style: Int) : super(context, att, style) {
        holder.addCallback(this)
        setOnTouchListener(this)
        if (context is IJoystickObserver) {
            listener = context
        }
    }

    // drawing joystick on create
    override fun surfaceCreated(holder: SurfaceHolder) {
        setupDimensions()
        listener.onCreateJoystick(centerX,centerY,rBase)
        drawJoystick(centerX,centerY)
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

    override fun surfaceDestroyed(holder: SurfaceHolder) {}


    // bind movement of joystick
    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        if (v != null && v == this) {
            if (event.action != MotionEvent.ACTION_UP) {
                // calculate distance from centre
                val dist =  sqrt((event.x - centerX).toDouble().pow(2.0) +
                        (event.y - centerY).toDouble().pow(2.0)).toFloat()
                if (dist < rBase)
                {
                    // draw change and notify model
                    drawJoystick(event.x, event.y)
                    listener.onChange(event.x, event.y)
                }
                // movement will be calc relative to the size of the joystick
                else
                {
                    val ratio = rBase / dist
                    val newX = centerX + (event.x - centerX) * ratio
                    val newY = centerY + (event.y - centerY) * ratio
                    // draw change and notify model
                    drawJoystick(newX, newY)
                    listener.onChange(newX, newY)
                }
            } else {
                //return the joystick to center
                drawJoystick(centerX, centerY)
                listener.onChange(centerX, centerY)
            }
        }
        return true
    }


    private fun drawJoystick(x: Float, y:Float) {
        if(holder.surface.isValid) {
            // create white canvas
            val canvas = this.holder.lockCanvas()
            val paintColor = Paint()
            canvas.drawColor(Color.WHITE)
            //create base circle
            paintColor.setARGB(255, 205, 240, 234)
            canvas.drawCircle(centerX, centerY, rBase, paintColor)
            //create knob circle
            paintColor.setARGB(255, 196, 144, 228)
            canvas.drawCircle(x, y, rKnob, paintColor)

            holder.unlockCanvasAndPost(canvas)
        }
    }

    // set ratios
    private fun setupDimensions() {
        centerX = width.toFloat() / 2
        centerY = height.toFloat() / 2
        rBase = (width.coerceAtMost(height) / 3).toFloat();
        rKnob = (width.coerceAtMost(height) / 5).toFloat();
    }
}