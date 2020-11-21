package com.example.popotarou.dontpicktup

import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log

class AlarmService : Service(),SensorEventListener {

    private val threshold: Float = 15f //閾値
    private var mp: MediaPlayer? = null  //音声ファイルを再生するMEdia player
    private var oValue: Array<Float> = arrayOf(0f,0f,0f) //eventの３つの値を保持するために利用
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //センサーマネージャーを取得
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        //利用したいせんさーを取得
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        //リスナー登録
        sensorManager.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorManager.unregisterListener(this)

    }
    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null) return
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            val speed = Math.abs(event.values[0] - oValue[0]) +
                    Math.abs(event.values[1] - oValue[1]) +
                    Math.abs(event.values[2] - oValue[2])

            Log.d("speed","$speed")
            if (speed > threshold){
                Log.d("voice","start!!!!!!")
                mp = MediaPlayer.create(applicationContext,R.raw.voice)
                Log.d("mp","$mp")
                mp?.start()

            }
            oValue[0] = event.values[0]
            oValue[1] = event.values[1]
            oValue[2] = event.values[2]

        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }


}