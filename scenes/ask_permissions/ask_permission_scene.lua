local COMMON = require "libs.common"
local SM = require "libs.sm.sm"

local BaseScene = require "libs.sm.scene"

local TAG = "ASK_PERMISSION_SCENE"
local ANDROID_PERMISSIONS = {"android.permission.RECORD_AUDIO"}
local IS_ANDROID = sys.get_sys_info().system_name == "Android"

---@class AskPermissionScene:Scene
local Scene = BaseScene:subclass("AskPermissionScene")
function Scene:initialize()
    BaseScene.initialize(self, "AskPermissionScene", "/ask_permission#proxy", "ask_permission:/scene_controller")
end

function Scene.have_permissions()
    local have = true
    if IS_ANDROID then
        for _,permission in ipairs(ANDROID_PERMISSIONS)do
            have = have and android_permissions.is_permission_granted("android.permission.RECORD_AUDIO")
            if not have then COMMON.w("no permission:" .. permission) end
        end
    end
    return have
end

function Scene.ask_permission()
    if IS_ANDROID then
        COMMON.i("Ask permissions",TAG)
        android_permissions.request_permissions(ANDROID_PERMISSIONS, function (results)
            for permission, grant_result in pairs(results) do
                if grant_result == android_permissions.PERMISSION_GRANTED then
                   -- print("Get permission:" .. permission,TAG) --print not worked here. can't save to log file.
                elseif grant_result == android_permissions.PERMISSION_DENIED then
                   -- print("No permission:" .. permission,TAG)
                end
            end
             COMMON.RX.MainScheduler:schedule(function()
                 --replace game scene with menu
                 if Scene.have_permissions() then SM:show("GameScene",{level = "prototype"}) end
            end,0.01)
        end)
      --  COMMON.i("Have permissions:" .. tostring(Scene.have_permissions()))
    end
end

return Scene