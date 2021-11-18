package com.example.recyclviewexample


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.provider.MediaStore
import android.util.Base64.*
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_detail_view.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.lang.Exception
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*


private var output: String? = null
private var mRecorder: MediaRecorder? = null
private var state: Boolean = false
private const val REQUEST_CODE = 42

class DetailViewActivity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_view)
        img.setImageResource(intent.getIntExtra("imgId", R.drawable.ic_launcher1))
        tvNAme.setText(intent.getStringExtra("itemName"))
        tvDes.setText(intent.getStringExtra("itemDes"))
        data()

        val intent = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        registerReceiver(betteryBroadcast, intent)

         //encdodeAudio()

    }

    fun data() {
        btnstart_recording.setOnClickListener(this)
        btnstop_recording.setOnClickListener(this)
        btnCamera.setOnClickListener(this)
        btnPlay_recording.setOnClickListener(this)
    }

    private val betteryBroadcast: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, p1: Intent?) {
            if (p1?.action == "android.intent.action.BATTERY_CHANGED") {
                val batterLevel = p1.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                Log.e("hello", " $batterLevel")

                tvBroadCast?.post {
                    tvBroadCast.text = getString(R.string.battery_level) + " $batterLevel%"
                    progressBar.progress = batterLevel
                    //batterLevel.toString().plus("%")

                }

            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(betteryBroadcast)
    }

    fun stratRecord() {
        if (ContextCompat.checkSelfPermission(
                this, android.Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val myPermission = arrayOf(
                android.Manifest.permission.RECORD_AUDIO,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
            ActivityCompat.requestPermissions(this, myPermission, 0)
        } else {

            try {

                output = Environment.getExternalStorageDirectory().absolutePath + "/recording.mp3"
                mRecorder = MediaRecorder()

                mRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
                mRecorder?.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                mRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                mRecorder?.setOutputFile(output)
                mRecorder?.prepare()
                mRecorder?.start()
                state = true
                Toast.makeText(this, "Recording started!", Toast.LENGTH_SHORT).show()
            } catch (e: IllegalStateException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }


    fun stopRecording() {
        try {
            if (state) {
                mRecorder?.stop()
                mRecorder?.release()
                state = false
            } else {
                Toast.makeText(this, "You are not recording right now!", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    // encode sound here
    fun encdodeAudio() {
        //val file = File(Environment.getExternalStorageDirectory().absolutePath + "/recording.mp3")
        val path = System.getProperty("user.dir") + "\\src\\soundr.mp3"


        try {
            val encoded = Files.readAllBytes(Paths.get(path))
            println("********* Your Audio File ************* ${Arrays.toString(encoded)}")
            Log.e("hello"," ********** ${Arrays.toString(encoded)}")
        } catch (e: IOException) {

        }

    }

    fun playRecording() {
        try {
            var mediaPlayer = MediaPlayer()
            mediaPlayer.setDataSource(output)
            mediaPlayer.prepare()
            mediaPlayer.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun takePicture() {
        val picIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if (picIntent.resolveActivity(this.packageManager) != null) {
            startActivityForResult(picIntent, REQUEST_CODE)
        } else {
            Toast.makeText(applicationContext, "Unable to open Camera", Toast.LENGTH_SHORT).show()
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            val pic = data?.extras?.get("data") as Bitmap
            /// img.setImageBitmap(pic) // image taken from camera

            ///Encoding image
            val byteArrayOutputStream = ByteArrayOutputStream()
            ///val bitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_launcher1)
            pic.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            var imageByte: ByteArray = byteArrayOutputStream.toByteArray()
            var myImageStrig: String = Base64.getEncoder().encodeToString(imageByte)
            sound_recorder_heading.text = myImageStrig

            ///DEcoding Image
            imageByte = Base64.getDecoder().decode(myImageStrig)
            val decodeImage: Bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.size)
            img.setImageBitmap(decodeImage)

        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnstart_recording -> stratRecord()
            R.id.btnstop_recording -> stopRecording()
            R.id.btnPlay_recording -> playRecording()
            R.id.btnCamera -> takePicture()
        }
    }

}

