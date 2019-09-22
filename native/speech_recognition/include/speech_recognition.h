#if defined(DM_PLATFORM_ANDROID)

#include <dmsdk/sdk.h>
#include "djni.h"

//initialize native extension.Register methods and etc
void SpeechRecognition_Initialize();
void SpeechRecognition_Finalize();
void SpeechRecognition_RegisterNatives(JNIEnv* env) ;

//create speech recognition
void SpeechRecognition_Init();
void SpeechRecognition_Start();
void SpeechRecognition_Stop();
void SpeechRecognition_SetContinuous(bool continuous);


#endif