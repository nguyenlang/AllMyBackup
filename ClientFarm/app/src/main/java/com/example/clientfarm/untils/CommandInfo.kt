package com.example.clientfarm.untils

public class CommandInfo{
    public var type:CommandType? = null
    public var parameter:String =""
}

public enum class CommandType{
    OPENVIDEO,
    SUBCRIBE,
    COMMENT,
    LIKE,
    SHARE
}

public class VideoInfo{
    public var videoUrl: String =""
    public var videoDuration: Long = 0
    public var timeToPlay: Long = 0
}