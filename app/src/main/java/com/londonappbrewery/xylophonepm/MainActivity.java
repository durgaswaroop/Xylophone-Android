package com.londonappbrewery.xylophonepm;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.Button;

import com.londonappbrewery.xylophonepm.utils.KeyTone;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

//    // Helpful Constants
//    private final int NR_OF_SIMULTANEOUS_SOUNDS = 7;
//    private final float LEFT_VOLUME = 1.0f;
//    private final float RIGHT_VOLUME = 1.0f;
//    private final int NO_LOOP = 0;
//    private final int PRIORITY = 0;
//    private final float NORMAL_PLAY_RATE = 1.0f;
//
//    // TODO: Add member variables here
//    private int mCSoundId;
//    private int mDSoundId;
//    private int mESoundId;
//    private int mFSoundId;
//    private int mGSoundId;
//    private int mASoundId;
//    private int mBSoundId;

    String TAG = "Xylophone";
    boolean recordingFlag = false;
    List<Integer> buttonsClicked = new ArrayList<>();
    List<KeyTone> keyTones = new ArrayList<>();

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button playButton = (Button) findViewById(R.id.button_play);
        Button recordStopButton = (Button) findViewById(R.id.button_record_stop);

        playButton.setOnClickListener(View -> {
            Log.d(TAG, "Playbutton clicked ");
//            keyTones.forEach(keyTone -> {
//                button.performClick();
//            });


            for (int i = 0; i < keyTones.size(); i++) {
                KeyTone currentTone = keyTones.get(i);
                Button button = (Button) findViewById(currentTone.getButtonId());
                button.performClick();

                long endTime = currentTone.getEndTime();
                long startTime = currentTone.getStartTime();
                if (endTime > 0) {
                    try {
                        Thread.sleep(endTime - startTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        recordStopButton.setOnClickListener((View) -> {
            Log.d(TAG, "record stop clicked ");
            if (recordingFlag) {
                recordStopButton.setText(R.string.record);
                recordStopButton.setBackgroundColor(getResources().getColor(R.color.green));
                recordingFlag = false;
//                Log.d(TAG, "Buttons clicked: " + buttonsClicked);
                Log.d(TAG, "Keytones: " + keyTones);
            } else {
                recordStopButton.setText(R.string.stop);
                recordStopButton.setBackgroundColor(getResources().getColor(R.color.red));
                recordingFlag = true;
//                buttonsClicked = new ArrayList<>();
                keyTones = new ArrayList<>();
            }
        });


        // Set listeners
        final SparseIntArray keyNoteMap = new SparseIntArray();
        keyNoteMap.put(R.id.a_key, R.raw.note6_a);
        keyNoteMap.put(R.id.b_key, R.raw.note7_b);
        keyNoteMap.put(R.id.c_key, R.raw.note1_c);
        keyNoteMap.put(R.id.d_key, R.raw.note2_d);
        keyNoteMap.put(R.id.e_key, R.raw.note3_e);
        keyNoteMap.put(R.id.f_key, R.raw.note4_f);
        keyNoteMap.put(R.id.g_key, R.raw.note5_g);

        // Mapped notes to keys
        for (int i = 0; i < keyNoteMap.size(); i++) {
            final int key = keyNoteMap.keyAt(i);
            Button keyButton = (Button) findViewById(key);
            keyButton.setOnClickListener(view -> {
                if (recordingFlag) {
                    Log.d("Xylophone", "Recording Flag on");
                    Log.d("Xylophone", "Adding Id " + view.getId());
//                    buttonsClicked.add(view.getId());

                    long currentTime = new Date().getTime();
                    keyTones.add(new KeyTone(key, currentTime, -100000L));

                    int keyTonesSize = keyTones.size();
                    if (keyTonesSize >= 2) {
                        keyTones.get(keyTonesSize - 2).setEndTime(currentTime);
                    }
                }
                MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), keyNoteMap.get(key));
                mediaPlayer.start();
                Log.d("Xylo", key + " started");
                mediaPlayer.setOnCompletionListener(MediaPlayer::release);
            });
        }


////        // TODO: Create a new SoundPool
//        final SoundPool soundPool = new SoundPool.Builder().build();
//
//        //
////        // TODO: Load and get the IDs to identify the sounds
//        mCSoundId = soundPool.load(getApplicationContext(), R.raw.note1_c, 1);
//        mDSoundId = soundPool.load(getApplicationContext(), R.raw.note2_d, 1);
//        mESoundId = soundPool.load(getApplicationContext(), R.raw.note3_e, 1);
//        mFSoundId = soundPool.load(getApplicationContext(), R.raw.note4_f, 1);
//        mGSoundId = soundPool.load(getApplicationContext(), R.raw.note5_g, 1);
//        mASoundId = soundPool.load(getApplicationContext(), R.raw.note6_a, 1);
//        mBSoundId = soundPool.load(getApplicationContext(), R.raw.note7_b, 1);
//
//        Button button = (Button) findViewById(R.id.a_key);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                soundPool.play(mASoundId, LEFT_VOLUME, RIGHT_VOLUME, PRIORITY, NO_LOOP, NORMAL_PLAY_RATE);
//            }
//        });


    }

    // TODO: Add the play methods triggered by the buttons


}
