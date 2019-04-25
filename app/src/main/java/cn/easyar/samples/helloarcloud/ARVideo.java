
//================================================================================================================================
//
//  Copyright (c) 2015-2018 VisionStar Information Technology (Shanghai) Co., Ltd. All Rights Reserved.
//  EasyAR is the registered trademark or trademark of VisionStar Information Technology (Shanghai) Co., Ltd in China
//  and other countries for the augmented reality technology developed by VisionStar Information Technology (Shanghai) Co., Ltd.
//
//================================================================================================================================

package cn.easyar.samples.helloarcloud;

import android.util.Log;

import cn.easyar.FunctorOfVoidFromVideoStatus;
import cn.easyar.StorageType;
import cn.easyar.VideoPlayer;
import cn.easyar.VideoStatus;
import cn.easyar.VideoType;

public class ARVideo
{
    private VideoPlayer player;
    private boolean prepared;
    private boolean found;
    private String path;

    public ARVideo()
    {
        player = new VideoPlayer();
        prepared = false;
        found = false;
    }
    public void dispose()
    {
        player.close();
    }

    public void openVideoFile(String path, int texid)
    {
        this.path = path;
        player.setRenderTexture(texid);
        player.setVideoType(VideoType.Normal);
        player.open(path, StorageType.Assets, new FunctorOfVoidFromVideoStatus() {
            @Override
            public void invoke(int status) {
                setVideoStatus(status);
            }
        });
    }

    /**
     * 获取当前播放到哪个时间
     * @return
     */
      public int getDuration(){
            int d=player.currentPosition();
            return d;
      }

    /**
     * 透明视频播放
     * @param path
     * @param texid
     */
    public void openTransparentVideoFile(String path, int texid)
    {
        this.path = path;
        player.setRenderTexture(texid);
        player.setVideoType(VideoType.TransparentSideBySide);
        player.open(path, StorageType.Assets, new FunctorOfVoidFromVideoStatus() {
            @Override
            public void invoke(int status) {
                setVideoStatus(status);
            }
        });
    }

    /**
     * 播放网上视频
     * @param url
     * @param texid
     */
    public void openStreamingVideo(String url, int texid)
    {
        this.path = url;
        player.setRenderTexture(texid);
        player.setVideoType(VideoType.Normal);
        player.open(url, StorageType.Absolute, new FunctorOfVoidFromVideoStatus() {
            @Override
            public void invoke(int status) {
                setVideoStatus(status);
            }
        });
    }

    /**
     * 获取视频状态
     * @param status
     */
    public void setVideoStatus(int status)
    {
        Log.i("HelloAR", "video: " + path + " (" + Integer.toString(status) + ")");
        if (status == VideoStatus.Ready) {
            prepared = true;
            if (found) {
                player.play();
                Log.e("查看状态", "invoke: 加载中" );
            }
        } else if (status == VideoStatus.Completed) {
            if (found) {
                player.play();
                Log.e("查看状态", "invoke: 完成" );
            }
        }
    }

    public void onFound()
    {
        found = true;
        if (prepared) {
            player.play();
        }
    }
    public void onLost()
    {
        found = false;
        if (prepared) {
            player.pause();
        }
    }
    public boolean isRenderTextureAvailable()
    {
        return player.isRenderTextureAvailable();
    }

    public void update()
    {
        player.updateFrame();
    }

//    private onTargetOnclickListener targetOnclickListener;//确定按钮被点击了的监听器
//
//
//    public void setYesOnclickListener(onTargetOnclickListener targetOnclickListener) {
//
//        this.targetOnclickListener = targetOnclickListener;
//    }
//    /**
//     * 设置确定按钮和取消被点击的接口
//     */
//    public interface onTargetOnclickListener {
//        void onTargetClick();
//    }
    }
