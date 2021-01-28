package no.uia.ikt205.pomodoro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import no.uia.ikt205.pomodoro.util.millisecondsToDescriptiveTime

class MainActivity : AppCompatActivity() {

    lateinit var timer:CountDownTimer
    lateinit var startButton:Button
    lateinit var coutdownDisplay:TextView
    lateinit var thirtym:Button
    lateinit var sixtym:Button
    lateinit var ninetym:Button
    lateinit var hundredTwentym:Button


    var timeToCountDownInMs = 6000L
    val timeTicks = 1000L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startButton = findViewById<Button>(R.id.startCountdownButton)
        startButton.setOnClickListener(){
            startCountDown(it)
            startButton.isClickable=false
            thirtym.isClickable=false
            sixtym.isClickable=false
            ninetym.isClickable=false
            hundredTwentym.isClickable=false

        }
        //30 min knapp
        thirtym = findViewById(R.id.button30Minutes)
        thirtym.setOnClickListener(){

            timeToCountDownInMs = 1800000L
            updateCountDownDisplay(timeToCountDownInMs)


            //60 min knapp
        }
        sixtym = findViewById(R.id.button60Minutes)
        sixtym.setOnClickListener(){

            timeToCountDownInMs = 3600000L
            updateCountDownDisplay(timeToCountDownInMs)


        }
        //90 min knapp
        ninetym = findViewById(R.id.button90Minutes)
        ninetym.setOnClickListener(){

            timeToCountDownInMs = 5400000L
            updateCountDownDisplay(timeToCountDownInMs)


        }
        //120 min knapp
        hundredTwentym = findViewById(R.id.button120Minutes)
        hundredTwentym.setOnClickListener(){

            timeToCountDownInMs = 7200000L
            updateCountDownDisplay(timeToCountDownInMs)


        }

        coutdownDisplay = findViewById<TextView>(R.id.countDownView)

    }

    fun startCountDown(v: View){

        timer = object : CountDownTimer(timeToCountDownInMs,timeTicks) {
            override fun onFinish() {
                Toast.makeText(this@MainActivity,"Arbeidsøkt er ferdig", Toast.LENGTH_SHORT).show()
                thirtym.isClickable=true
                sixtym.isClickable=true
                ninetym.isClickable=true
                startButton.isClickable=true
                hundredTwentym.isClickable=true
            }

            override fun onTick(millisUntilFinished: Long) {
                updateCountDownDisplay(millisUntilFinished)
            }
        }

        timer.start()
    }

    fun updateCountDownDisplay(timeInMs:Long){
        coutdownDisplay.text = millisecondsToDescriptiveTime(timeInMs)
    }


}
