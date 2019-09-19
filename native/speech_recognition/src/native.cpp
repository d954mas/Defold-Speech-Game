// myextension.cpp
// Extension lib defines
#define EXTENSION_NAME speech_recognition
#define LIB_NAME "speech_recognition"
#define MODULE_NAME "speech_recognition"
// include the Defold SDK
#include <dmsdk/sdk.h>
#include "speech_recognition.h"

#if defined(DM_PLATFORM_ANDROID)

static const luaL_reg Module_methods[] = {
    {0, 0}
};

static void LuaInit(lua_State* L){
	int top = lua_gettop(L);
	// Register lua names
	luaL_register(L, MODULE_NAME, Module_methods);
	lua_pop(L, 1);
	assert(top == lua_gettop(L));
}


dmExtension::Result InitializeMyExtension(dmExtension::Params* params) {
    SpeechRecognition_Initialize();
    LuaInit(params->m_L);
    printf("Registered %s Extension\n", MODULE_NAME);
    return dmExtension::RESULT_OK;
}

dmExtension::Result FinalizeMyExtension(dmExtension::Params* params) {
    SpeechRecognition_Finalize();
    return dmExtension::RESULT_OK;
}

#else

dmExtension::Result InitializeMyExtension(dmExtension::Params* params) { return dmExtension::RESULT_OK;}

dmExtension::Result FinalizeMyExtension(dmExtension::Params* params) { return dmExtension::RESULT_OK;}
#endif

DM_DECLARE_EXTENSION(EXTENSION_NAME, LIB_NAME, NULL, NULL, InitializeMyExtension, 0, 0, FinalizeMyExtension)
