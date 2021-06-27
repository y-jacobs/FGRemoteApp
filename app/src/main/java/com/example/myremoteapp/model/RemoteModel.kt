package com.example.myremoteapp.model

import java.io.PrintWriter
import java.lang.Exception
import java.net.Socket

class RemoteModel() {

    private lateinit var fg: Socket
    private lateinit var out: PrintWriter

    // connect to flight gear on tcp
    fun connectFG(ip: String, port: Int): Boolean {
        return try {
            this.fg = Socket(ip, port)
            this.out = PrintWriter(this.fg.getOutputStream(), true)
            true
        } catch (e: Exception) {
            //
            false
        }
        return true
    }

    // send joystick settings to fg
    fun changeAileronElevator(a:Float, e:Float) {
        out.print("set /controls/flight/aileron $a\n")
        out.flush()
        out.print("set /controls/flight/elevator $e\r\n")
        out.flush()
    }

    // send rudder settings to fg
    fun changeRudder(r: Float) {
        out.print("set /controls/flight/rudder $r\r\n");
        out.flush()
    }

    // send throttle settings to fg
    fun changeThrottle(t: Float) {
        out.print("set /controls/engines/current-engine/throttle $t\n");
        out.flush()
    }

    // close tcp connection
    fun disconnectFG() {
        fg.close()
    }
}