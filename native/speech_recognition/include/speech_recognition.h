#if defined(DM_PLATFORM_ANDROID)

#include <dmsdk/sdk.h>
#include "djni.h"

void SpeechRecognition_Initialize();
void SpeechRecognition_Finalize();
void SpeechRecognition_RegisterNatives(JNIEnv* env) ;
#endif