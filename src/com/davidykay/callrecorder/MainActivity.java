package com.davidykay.callrecorder;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
      
  private String PATH_NAME;
  private MediaRecorder mRecorder; 

  ////////////////////////////////////////
  // Activity Lifecycle
  ////////////////////////////////////////

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Init Model
    
    //PATH_NAME = new FileDescriptor("this/is/my/path");
    //File file = new File(getExternalFilesDir(null), "transcript.wav");
    //PATH_NAME = file.getAbsolutePath();
    PATH_NAME = "/sdcard/transcript.3gp";

    mRecorder = new MediaRecorder();
    try {
      initRecorder(PATH_NAME);
    } catch (IOException e) {      
      e.printStackTrace();
      throw new RuntimeException("Couldn't init Recorder with path: " + PATH_NAME);
    }

    
    // Init View
    setContentView(R.layout.main);

    final Button recordButton = (Button) findViewById(R.id.button_record);
    recordButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        startRecord();
      }
    });
    
    final Button playButton = (Button) findViewById(R.id.button_play);
    playButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        //startRecord();
      }
    });
    final Button pauseButton = (Button) findViewById(R.id.button_pause);
    pauseButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        //startRecord();
      }
    });
    final Button stopButton = (Button) findViewById(R.id.button_stop);
    stopButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        stopRecord();
      }
    });
  }
  
  ////////////////////////////////////////
  // Audio Recorder
  ////////////////////////////////////////

  @Override
  protected void onDestroy() {
    super.onDestroy();
	  
    cleanupRecorder();
  }

  public boolean initRecorder(String path) throws IOException {
    // make sure the directory we plan to store the recording in exists
    File directory = new File(path).getParentFile();
    if (!directory.exists() && !directory.mkdirs()) {
      throw new IOException("Path to file could not be created.");
    }

    mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
    mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
    mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

    mRecorder.setOutputFile(path);
    try {
      mRecorder.prepare();
      return true;
    } catch (IllegalStateException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return false;
  }

  public void startRecord() {
    mRecorder.start();   // Recording is now started
  }

  public void stopRecord() {
    mRecorder.stop();
  }

  public void cleanupRecorder() {
    mRecorder.reset();   // You can reuse the object by going back to setAudioSource() step
    mRecorder.release(); // Now the object cannot be reused
  }

}
