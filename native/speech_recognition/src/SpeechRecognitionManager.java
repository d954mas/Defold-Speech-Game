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

import android.widget.Toast;


public class SpeechRecognitionManager {
    private static String TAG = "SpeechRecognitionManager";
    private static Context CONTEXT;

    public static void init(Context context){
        CONTEXT = context;
        speechRecognitionCB1();
    }

    public static void dispose(){}

    private static native void speechRecognitionCB1();

}