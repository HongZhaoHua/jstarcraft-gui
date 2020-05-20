package com.jstarcraft.gui.media;

import java.awt.image.BufferedImage;

import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.opencv.opencv_core.IplImage;
import org.bytedeco.opencv.opencv_core.Mat;

/**
 * 多媒体工具
 * 
 * @author Birdy
 *
 */
public class MediaUtility {

	private static final Java2DFrameConverter java2dConverter = new Java2DFrameConverter();

	private static final OpenCVFrameConverter<IplImage> iplImageConverter = new OpenCVFrameConverter.ToIplImage();

	private static final OpenCVFrameConverter<Mat> matConverter = new OpenCVFrameConverter.ToMat();

	/**
	 * 将帧转换为图像
	 * 
	 * @param frame
	 * @return
	 */
	public static BufferedImage frame2Image(Frame frame) {
		return java2dConverter.convert(frame);
	}

	/**
	 * 将图像转换为帧
	 * 
	 * @param image
	 * @return
	 */
	public static Frame image2Frame(BufferedImage image) {
		return java2dConverter.convert(image);
	}

	public static IplImage frame2Ipl(Frame frame) {
		return iplImageConverter.convert(frame);
	}

	public static Frame ipl2Frame(IplImage image) {
		return iplImageConverter.convert(image);
	}

	public static Mat frame2Mat(Frame frame) {
		return matConverter.convert(frame);
	}

	public static Frame mat2Frame(Mat mat) {
		return matConverter.convert(mat);
	}

}
