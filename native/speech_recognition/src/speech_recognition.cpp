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

void SpeechRecognition_Start(){
    JNIEnv* env = djni::env();
    jclass cls = djni::GetClass(env, "com.d954mas.defold.speech.recognition.SpeechRecognitionManager");
    jmethodID method = env->GetStaticMethodID(cls, "speechRecognitionStart", "()V");
    env->CallStaticVoidMethod(cls, method);
}
//create speech recognition object
void SpeechRecognition_Init(){
    JNIEnv* env = djni::env();
    jclass cls = djni::GetClass(env, "com.d954mas.defold.speech.recognition.SpeechRecognitionManager");
    jmethodID method = env->GetStaticMethodID(cls, "speechRecognitionInit", "()V");
    env->CallStaticVoidMethod(cls, method);
}
void SpeechRecognition_Stop(){
    JNIEnv* env = djni::env();
    jclass cls = djni::GetClass(env, "com.d954mas.defold.speech.recognition.SpeechRecognitionManager");
    jmethodID method = env->GetStaticMethodID(cls, "speechRecognitionStop", "()V");
    env->CallStaticVoidMethod(cls, method);
}
void SpeechRecognition_SetContinuous(bool continuous){
    JNIEnv* env = djni::env();
    jclass cls = djni::GetClass(env, "com.d954mas.defold.speech.recognition.SpeechRecognitionManager");
    jmethodID method = env->GetStaticMethodID(cls, "speechRecognitionSetContinuous", "(B;)V");
    jboolean bool_value = continuous;
    env->CallStaticVoidMethod(cls, method,continuous);
}
#endif