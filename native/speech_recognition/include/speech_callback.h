#pragma once

#include <dmsdk/sdk.h>

enum MESSAGE_ID{
    MSG_LIVE_RESULT =      1,
    MSG_FINAL_RESULT =     2,
    MSG_RMS_CHANGED =      3,
    MSG_CLOSED_BY_USER =   4,
    MSG_ERROR =            5
};

struct Speech_Callback{
    Speech_Callback() : m_L(0), m_Callback(LUA_NOREF), m_Self(LUA_NOREF) {}
    lua_State* m_L;
    int m_Callback;
    int m_Self;
};

struct CallbackData{
    MESSAGE_ID msg;
    char* json;
};

#if defined(DM_PLATFORM_ANDROID)

void SpeechCallback_Set(lua_State* L, int pos);
void SpeechCallback_Initialize();
void SpeechCallback_Finalize();
void SpeechCallback_Update();
void SpeechCallback_AddToQueue(MESSAGE_ID msg, const char*json);
void SpeechCallback_AddToQueue(MESSAGE_ID msg, const char*json);

#endif
