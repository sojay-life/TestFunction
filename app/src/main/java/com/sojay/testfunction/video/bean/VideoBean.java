package com.sojay.testfunction.video.bean;

public class VideoBean {

    private String firstFrame;
    private String videoPath;
    private String lastFrame;

    public VideoBean (String firstFrame, String videoPath, String lastFrame) {
        this.firstFrame = firstFrame;
        this.videoPath = videoPath;
        this.lastFrame = lastFrame;
    }

    public void setFirstFrame(String firstFrame) {
        this.firstFrame = firstFrame;
    }

    public String getFirstFrame() {
        return firstFrame;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setLastFrame(String lastFrame) {
        this.lastFrame = lastFrame;
    }

    public String getLastFrame() {
        return lastFrame;
    }
}
