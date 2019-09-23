package com.d954mas.defold.speech.recognition;

import android.app.Activity;
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
import java.util.Locale;

import android.widget.Toast;
import com.vikramezhil.droidspeech.DroidSpeech;
import com.vikramezhil.droidspeech.OnDSListener;
import org.json.JSONException;
import org.json.JSONObject;


public class SpeechRecognitionManager {
    private static final String TAG = "SpeechRecognitionManager";
    private static Context context;
    private static Activity activity;
    private static DroidSpeech droidSpeech;

    //Duplicate of ENUMS from nativeก:
    private static final int MSG_LIVE_RESULT = 1;
    private static final int MSG_FINAL_RESULT = 2;
    private static final int MSG_RMS_CHANGED = 3;
    private static final int MSG_CLOSED_BY_USER = 4;
    private static final int MSG_ERROR = 5;

    public static native void speechCallbackAddToQueue(int msg, String json);

    public static void init(Context context){
        SpeechRecognitionManager.context = context;
        SpeechRecognitionManager.activity = (Activity) context;
    }

    private static void sendSimpleMessage(int msg){
        speechCallbackAddToQueue(msg, new JSONObject().toString());
    }
    private static void sendSimpleMessage(int msg, String key1, float value1) {
        String message = null;
        try {
            JSONObject obj = new JSONObject();
            obj.put(key1, value1);
            message = obj.toString();
        } catch (JSONException e) {
            message = "{ error:'Error while converting simple message to JSON: " + e.getMessage() + "'";
        }
        speechCallbackAddToQueue(msg, message);
    }

    private static void sendSimpleMessage(int msg, String key1, String value1 ) {
        String message = null;
        try {
            JSONObject obj = new JSONObject();
            obj.put(key1, value1);
            message = obj.toString();
        } catch (JSONException e) {
            message = "{ error:'Error while converting simple message to JSON: " + e.getMessage() + "'";
        }
        speechCallbackAddToQueue(msg, message);
    }


    public static void speechRecognitionInit(){
        if (droidSpeech == null){
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    droidSpeech = new DroidSpeech(context,null);
                    droidSpeech.setPreferredLanguage("ru-RU");
                    droidSpeech.setShowRecognitionProgressView(false);
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
                          //  Log.d(TAG,"onDroidSpeechRmsChanged:" + rmsChangedValue);
                            sendSimpleMessage(MSG_RMS_CHANGED,"rms",rmsChangedValue);
                        }

                        @Override
                        public void onDroidSpeechLiveResult(String liveSpeechResult) {
                          //  Log.d(TAG,"onDroidSpeechFinalResult:" + liveSpeechResult);
                            sendSimpleMessage(MSG_LIVE_RESULT,"result",liveSpeechResult);
                        }

                        @Override
                        public void onDroidSpeechFinalResult(String finalSpeechResult) {
                          //  Log.d(TAG,"onDroidSpeechFinalResult:" + finalSpeechResult);
                            sendSimpleMessage(MSG_FINAL_RESULT,"result",finalSpeechResult);
                        }

                        @Override
                        public void onDroidSpeechClosedByUser() {
                            Log.d(TAG,"onDroidSpeechClosedByUser");
                            sendSimpleMessage(MSG_CLOSED_BY_USER);
                        }

                        @Override
                        public void onDroidSpeechError(String errorMsg) {
                            Log.d(TAG,"onDroidSpeechError:" + errorMsg);
                            sendSimpleMessage(MSG_LIVE_RESULT,"error",errorMsg);
                        }
                    });
                }
            });
        }
    }

    public static void speechRecognitionStart(){
        if (droidSpeech == null){Log.e(TAG,"speech recognition was not initialized");return;}
        activity.runOnUiThread(new Runnable() {@Override public void run() { droidSpeech.startDroidSpeechRecognition(); }});
    }
    public static void speechRecognitionStop(){
        if (droidSpeech == null){Log.e(TAG,"speech recognition was not initialized");return;}
        activity.runOnUiThread(new Runnable() {@Override public void run() { droidSpeech.closeDroidSpeechOperations(); }});
    }
    public static void speechRecognitionSetContinuous(final boolean continuous){
        if (droidSpeech == null){Log.e(TAG,"speech recognition was not initialized");return;}
        activity.runOnUiThread(new Runnable() {@Override public void run() { droidSpeech.setContinuousSpeechRecognition(continuous); }});

    }


    public static void dispose(){
        activity.runOnUiThread(new Runnable() {@Override public void run() {
            droidSpeech.closeDroidSpeechOperations();
            droidSpeech = null;
        }});
    }

}