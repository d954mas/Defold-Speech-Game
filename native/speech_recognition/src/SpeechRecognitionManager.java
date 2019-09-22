package com.d954mas.defold.speech.recognition;

import android.app.NativeActivity;
import android.content.Context;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

import android.widget.Toast;
import com.vikramezhil.droidspeech.DroidSpeech;
import com.vikramezhil.droidspeech.OnDSListener;


public class SpeechRecognitionManager {
    private static String TAG = "SpeechRecognitionManager";
    private static Context CONTEXT;
    private static DroidSpeech droidSpeech;

    public static void init(Context context){
        CONTEXT = context;
        speechRecognitionCB1();
    }

    public static void speechRecognitionInit(){
        if (droidSpeech == null){
            droidSpeech = new DroidSpeech(CONTEXT,null);
            droidSpeech.setOnDroidSpeechListener(new OnDSListener(){
                @Override
                public void onDroidSpeechSupportedLanguages(String currentSpeechLanguage, List<String> supportedSpeechLanguages) {
                    String msg = "";
                    msg = msg + "onDroidSpeechSupportedLanguages: current:" + currentSpeechLanguage + "\nsupported:";
                    for (String s : supportedSpeechLanguages) {
                        msg += s + ", ";
                    }
                    Log.d(TAG,msg);
                }

                @Override
                public void onDroidSpeechRmsChanged(float rmsChangedValue) {
                    Log.d(TAG,"onDroidSpeechRmsChanged:" + rmsChangedValue);
                }

                @Override
                public void onDroidSpeechLiveResult(String liveSpeechResult) {
                    Log.d(TAG,"onDroidSpeechLiveResult:" + liveSpeechResult);
                }

                @Override
                public void onDroidSpeechFinalResult(String finalSpeechResult) {
                    Log.d(TAG,"onDroidSpeechFinalResult:" + finalSpeechResult);
                }

                @Override
                public void onDroidSpeechClosedByUser() {
                    Log.d(TAG,"onDroidSpeechClosedByUser");
                }

                @Override
                public void onDroidSpeechError(String errorMsg) {
                    Log.d(TAG,"onDroidSpeechError:" + errorMsg);
                }
            });
        }
    }

    public static void speechRecognitionStart(){ droidSpeech.startDroidSpeechRecognition(); }
    public static void speechRecognitionStop(){ droidSpeech.closeDroidSpeechOperations(); }
    public static void speechRecognitionSetContinuous(boolean continuous){ droidSpeech.setContinuousSpeechRecognition(continuous); }


    public static void dispose(){
        droidSpeech.closeDroidSpeechOperations();
    }

    private static native void speechRecognitionCB1();

}