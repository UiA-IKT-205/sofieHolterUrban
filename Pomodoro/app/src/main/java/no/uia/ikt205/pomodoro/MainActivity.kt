package no.uia.ikt205.pomodoro

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import no.uia.ikt205.pomodoro.util.millisecondsToDescriptiveTime

class MainActivity : AppCompatActivity() {

    private lateinit var timer:CountDownTimer
    private lateinit var timerPause:CountDownTimer
    private lateinit var startButton:Button
    private lateinit var countdownDisplay:TextView
    private lateinit var seekBar: SeekBar
    private lateinit var antallRepetisjoner: EditText
    var timeToCountDownInMs = 0L
    var timeToCountDownInMsPause = 0L
    val timeTicks = 1000L
    val timeTicksPause = 1000L
    var countdownRunning=false
    var pauseCountdownRun=false
    var repetisjon = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // seekbar for arbeidstid i minutter
        antallRepetisjoner = findViewById(R.id.repetisjoner)
        seekBar = findViewById(R.id.angiArbeidMinutter)
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                timeToCountDownInMs = progress * 60000L
                updateCountDownDisplay(timeToCountDownInMs)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                updateCountDownDisplay(timeToCountDownInMs)
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                updateCountDownDisplay(timeToCountDownInMs)
            }

        })
        // seekbar for pausetid angitt i minutter
        seekBar = findViewById(R.id.angiPauseMinutter)
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                timeToCountDownInMsPause = progress * 60000L
                updateCountDownDisplay(timeToCountDownInMsPause)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                updateCountDownDisplay(timeToCountDownInMsPause)
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                updateCountDownDisplay(timeToCountDownInMsPause)
            }
        })

        //startknapp starter nedtelling når arbeid, pause og repetisjoner er angitt
        startButton = findViewById(R.id.startCountdownButton)
        startButton.setOnClickListener {
            if (!countdownRunning && !pauseCountdownRun){
                repetisjon = antallRepetisjoner.text.toString().toInt()
                startCountDown(it)
                return@setOnClickListener}
            Toast.makeText(this@MainActivity,"Arbeidsøkt pågår", Toast.LENGTH_SHORT).show()
        }

        countdownDisplay = findViewById(R.id.countDownView)
    }

    fun startCountDown(v: View){

        timer = object : CountDownTimer(timeToCountDownInMs,timeTicks) {
            override fun onFinish() {
                Toast.makeText(this@MainActivity,"Arbeidsøkt ferdig", Toast.LENGTH_SHORT).show()
                if (!pauseCountdownRun && repetisjon >0){
                    startCountDownPause(v)
                }
                repetisjon --
                countdownRunning = false
            }

            override fun onTick(millisUntilFinished: Long) {
                updateCountDownDisplay(millisUntilFinished)
                countdownRunning =true
                pauseCountdownRun =false
            }
        }

        timer.start()
    }

    fun startCountDownPause(v: View){
        timerPause = object : CountDownTimer(timeToCountDownInMsPause,timeTicksPause) {
            override fun onFinish() {
                Toast.makeText(this@MainActivity,"Pause ferdig", Toast.LENGTH_SHORT).show()
                if (!countdownRunning && repetisjon > 0)
                    startCountDown(v)
                pauseCountdownRun = false
            }

            override fun onTick(millisUntilFinished: Long) {
                updateCountDownDisplay(millisUntilFinished)
                pauseCountdownRun = true
                countdownRunning = false
            }
        }

        timerPause.start()
    }
    fun updateCountDownDisplay(timeInMs:Long){
        countdownDisplay.text = millisecondsToDescriptiveTime(timeInMs)
    }
}