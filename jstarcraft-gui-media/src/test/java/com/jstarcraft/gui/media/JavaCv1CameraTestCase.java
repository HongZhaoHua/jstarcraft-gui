package com.jstarcraft.gui.media;

import javax.swing.JFrame;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameGrabber;

public class JavaCv1CameraTestCase {

    public static void main(String[] arguments) throws Exception, InterruptedException {
        try (OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0)) {
            // 开始获取摄像头数据
            grabber.start();
            CanvasFrame canvas = new CanvasFrame("摄像头");
            canvas.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            canvas.setAlwaysOnTop(true);
            while (canvas.isDisplayable()) {
                // 获取摄像头图像(按帧)
                Frame fame = grabber.grab();
                canvas.showImage(fame);
                Thread.sleep(50);
            }
            // 停止获取摄像头数据
            grabber.stop();
        }
    }

}
