package com.buyou.voicetools;

import android.Manifest;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.buyou.voicetools.audio.AudioPalyer;
import com.buyou.voicetools.audio.AudioPalyer.AudioPlayListener;
import com.buyou.voicetools.audio.IMRecordController;
import com.buyou.voicetools.audio.RecordListener;
import com.tbruyelle.rxpermissions2.RxPermissions;

public class MainActivity extends AppCompatActivity implements AudioPlayListener {
    private String TAG ="录音";
    Button recordBtn;
    IMRecordController imRecordController;
    private AudioPalyer mAudioPalyer;
    final RxPermissions rxPermissions = new RxPermissions(this);
    private boolean isPlay=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recordBtn=findViewById(R.id.recordBtn);
        ImageView play=findViewById(R.id.play);
        Button playBtn=findViewById(R.id.playBtn);
        TextView voice_url=findViewById(R.id.voice_url);
        rxPermissions
            .request(Manifest.permission.RECORD_AUDIO)
            .subscribe(granted -> {
                if (granted) { // Always true pre-M
                    // I can control the camera now
                } else {
                    // Oups permission denied
                }
            });
        imRecordController= new IMRecordController(this);
        imRecordController.setRecordListener(new RecordListener() {
            @Override
            public void onRecordStart() {
                recordBtn.setText("松开发送");
            }

            @Override
            public void onRecordCancel() {
                recordBtn.setText("按住说话");
            }

            @Override
            public void onRecordSuccess(String filePath, int timeLen) {
                recordBtn.setText("按住说话");
                Log.e(TAG,filePath);
                voice_url.setText(filePath);
            }
        });
        recordBtn.setOnTouchListener(imRecordController);

        mAudioPalyer = new AudioPalyer();
        mAudioPalyer.setAudioPlayListener(this);


        play.setImageResource(R.drawable.audio_animation);
        AnimationDrawable animationDrawable = (AnimationDrawable) play.getDrawable();
        playBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isPlay){
                    isPlay=true;
                    mAudioPalyer.play(voice_url.getText().toString());
                    animationDrawable.start();
                }else {
                    isPlay=false;
                    mAudioPalyer.stop();
                    animationDrawable.stop();
                }


            }
        });
    }

    @Override
    public void onPreparing() {

    }

    @Override
    public void onPrepared() {

    }

    @Override
    public void onBufferingUpdate(int percent) {

    }

    @Override
    public void onError() {

    }

    @Override
    public void onCompletion() {

    }

    @Override
    public void onSeekComplete() {

    }
}
