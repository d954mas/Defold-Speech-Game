speech_recognition = {}

function speech_recognition.init() end
function speech_recognition.start() end
function speech_recognition.stop() end
---@param continuous boolean
function speech_recognition.set_continuous(continuous) end
---@param callback function (instance,number, table)
function speech_recognition.set_callback(callback) end
