package com.jstarcraft.gui.media;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber.Exception;

public class JavaCv9ScreenTestCase {

    public static void captureScreen() throws Exception {
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();// 获取当前屏幕大小
        // 读取屏幕

        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber("desktop");

        // FFmpegFrameGrabber grabber = new FFmpegFrameGrabber("title=窗口标题名称");//获取windows某个窗口画面，不支持中文，必须是窗口标题名称，而不是程序进程名称
        grabber.setFormat("gdigrab");// 基于gdigrab的输入格式
        // grabber.setFrameRate(30);//经过验证，这个帧率设置是无效的，请使用下面的帧率设置方法

        grabber.setOption("framerate", "60");// 证确设置帧率方法，直接设置60帧每秒的高帧率
        grabber.setOption("offset_x", "0");// 截屏起始点X

        grabber.setOption("offset_y", "0");// 截屏起始点Y

        grabber.setImageWidth(screenSize.width);// 截取的画面宽度

        grabber.setImageHeight(screenSize.height);// 截取的画面高度

        grabber.setOption("draw_mouse", "0");// 隐藏鼠标
        // grabber.setOption("draw_mouse", "1");//绘制鼠标

        grabber.start();

        CanvasFrame canvas = new CanvasFrame("屏幕预览");// 新建一个窗口
        canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas.setAlwaysOnTop(true);
        Frame frame = null;

        long timeDump = System.currentTimeMillis();// 记录一下时间，用来简单计算一下平均帧率
        long lastTimeDump;
        // 抓取屏幕画面
        for (int i = 0; (frame = grabber.grab()) != null; i++) {
            // 显示画面
            canvas.showImage(frame);

            lastTimeDump = System.currentTimeMillis();
            if (lastTimeDump - timeDump > 10000) {// 录屏10秒，超过10秒退出并计算平均帧率
                long inteval = ((lastTimeDump - timeDump) / 1000);
                System.out.println("总耗时：" + inteval + "秒，总帧数：" + i + ",平均帧率：" + i / inteval);
                break;
            }
        }
        grabber.stop();
        canvas.dispose();
    }

    public static void main(String[] args) throws Exception {
        captureScreen();
    }

}
