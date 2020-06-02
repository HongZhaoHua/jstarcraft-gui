package com.jstarcraft.gui.media;

import javax.swing.JFrame;

import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.FrameRecorder;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.opencv.global.opencv_objdetect;
import org.bytedeco.opencv.opencv_core.IplImage;

/**
 * JavaCV推流器测试用例
 * 
 * @author Birdy
 *
 */
public class JavaCv2RecorderTestCase {

    public static void recordCamera(String address, double rate) throws Exception {
        Loader.load(opencv_objdetect.class);
        FrameGrabber grabber = FrameGrabber.createDefault(0);// 本机摄像头默认0，这里使用javacv的抓取器，至于使用的是ffmpeg还是opencv，请自行查看源码
        grabber.start();// 开启抓取器

        OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();// 转换器
        IplImage image = converter.convert(grabber.grab());// 抓取一帧视频并将其转换为图像，至于用这个图像用来做什么？加水印，人脸识别等等自行添加
        int width = image.width();
        int height = image.height();

        FrameRecorder recorder = FrameRecorder.createDefault(address, width, height);
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264); // avcodec.AV_CODEC_ID_H264，编码
        recorder.setFormat("flv");// 封装格式，如果是推送到rtmp就必须是flv封装格式
        recorder.setFrameRate(rate);

        recorder.start();// 开启录制器
        long startTime = 0;
        long instant = 0;
        CanvasFrame canvas = new CanvasFrame("camera", CanvasFrame.getDefaultGamma() / grabber.getGamma());
        canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas.setAlwaysOnTop(true);
        Frame rotatedFrame = converter.convert(image);// 不知道为什么这里不做转换就不能推到rtmp
        while (canvas.isVisible() && (image = converter.convert(grabber.grab())) != null) {
            rotatedFrame = converter.convert(image);
            canvas.showImage(rotatedFrame);
            if (startTime == 0) {
                startTime = System.currentTimeMillis();
            }
            instant = 1000 * (System.currentTimeMillis() - startTime);
            recorder.setTimestamp(instant);
            recorder.record(rotatedFrame);
            Thread.sleep(50);
        }
        canvas.dispose();
        recorder.stop();
        recorder.release();
        image.release();
        grabber.stop();
    }

    public static void main(String[] arguments) throws Exception {
        recordCamera("2.flv", 25);
    }

}
