#if defined(DM_PLATFORM_ANDROID)

#include <dmsdk/sdk.h>
#include "speech_recognition.h"

//register callbacks from java to native
void SpeechRecognition_Initialize() {
    JNIEnv* env = djni::env();
    SpeechRecognition_RegisterNatives(env);
    jclass cls = djni::GetClass(env, "com.d954mas.defold.speech.recognition.SpeechRecognitionManager");
    jmethodID method = env->GetStaticMethodID(cls, "init", "(Landroid/content/Context;)V");
    env->CallStaticVoidMethod(cls, method, dmGraphics::GetNativeAndroidActivity());
}

void SpeechRecognition_Finalize() {
    JNIEnv* env = djni::env();
    jclass cls = djni::GetClass(env, "com.d954mas.defold.speech.recognition.SpeechRecognitionManager");
    jmethodID method = env->GetStaticMethodID(cls, "dispose", "()V");
    env->CallStaticVoidMethod(cls, method);
}

// TEST
void SpeechRecognition_cb1(JNIEnv* env, jclass clz){}

void SpeechRecognition_RegisterNatives(JNIEnv* env) {
    JNINativeMethod nativeMethods[] = {
        {"speechRecognitionCB1", "()V", (void*)&SpeechRecognition_cb1}
    };
	jclass jclass_SpeechRecognition = djni::GetClass(env, "com.d954mas.defold.speech.recognition.SpeechRecognitionManager");
    env->RegisterNatives( jclass_SpeechRecognition , nativeMethods, sizeof(nativeMethods) / sizeof(nativeMethods[0]));    
    env->DeleteLocalRef( jclass_SpeechRecognition );
}
#endif