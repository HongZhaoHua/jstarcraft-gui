package com.jstarcraft.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Iterator;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jstarcraft.core.utility.StringUtility;

/**
 * Swing工具
 * 
 * @author Birdy
 */
public class SwingUtility {

	private static final Logger logger = LoggerFactory.getLogger(SwingUtility.class);

	/**
	 * 设置组件皮肤
	 * 
	 * @param skin
	 */
	public static void setComponentSkin(Component component, String skin) {
		String lookAndFeel = UIManager.getLookAndFeel().getClass().getName();
		if (!skin.equals(lookAndFeel)) {
			try {
				UIManager.setLookAndFeel(skin);
				SwingUtilities.updateComponentTreeUI(component);
			} catch (Exception exception) {
				String message = StringUtility.format("组件{}切换皮肤{}异常", component.getName(), skin);
				logger.error(message, exception);
				Toolkit.getDefaultToolkit().beep();
			}
		}
	}

	/**
	 * 遍历器转枚举器
	 * 
	 * @param <T>
	 * @param iterator
	 * @return
	 */
	public static <T> Enumeration<T> iterator2Enumeration(final Iterator<T> iterator) {
		return new Enumeration<T>() {

			@Override
			public boolean hasMoreElements() {
				return iterator.hasNext();
			}

			@Override
			public T nextElement() {
				return iterator.next();
			}

		};
	}

	/**
	 * 将图像转缓冲
	 * 
	 * @param image
	 * @return
	 */
	public static BufferedImage image2Buffer(Image image) {
		int width = image.getWidth(null);
		int height = image.getHeight(null);
		BufferedImage buffer = image2Buffer(image, width, height);
		return buffer;
	}

	/**
	 * 将图像转缓冲
	 * 
	 * @param image
	 * @param width
	 * @param height
	 * @return
	 */
	public static BufferedImage image2Buffer(Image image, int width, int height) {
		BufferedImage buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics graphics = buffer.getGraphics();
		graphics.drawImage(image, 0, 0, null);
		graphics.dispose();
		return buffer;
	}

	/**
	 * 获取文本宽高
	 * 
	 * @param text
	 * @param font
	 * @return
	 */
	public static Dimension getTextDimension(String text, Font font) {
		Rectangle2D rectangle = font.getStringBounds(text, new FontRenderContext(AffineTransform.getScaleInstance(1D, 1D), false, false));
		int width = (int) Math.ceil(rectangle.getWidth());
		int height = (int) Math.ceil(rectangle.getHeight());
		return new Dimension(width, height);
	}

	/**
	 * 获取透明图片
	 * 
	 * @param width
	 * @param height
	 * @param transparency
	 * @return
	 */
	public static BufferedImage getTransparencyImage(int width, int height, final int transparency) {
		GraphicsConfiguration configuration = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		return configuration.createCompatibleImage(width, height, transparency);
	}

	/**
	 * 将文本转图片
	 * 
	 * @param text
	 * @param font
	 * @return
	 */
	public static BufferedImage text2Image(String text, Font font, Color color) {
		int width = 0, height = 0;
		try (ByteArrayInputStream stream = new ByteArrayInputStream(text.getBytes(StringUtility.CHARSET)); InputStreamReader iterator = new InputStreamReader(stream, StringUtility.CHARSET); BufferedReader buffer = new BufferedReader(iterator)) {
			String line;
			while ((line = buffer.readLine()) != null) {
				Dimension dimension = getTextDimension(line, font);
				width = Math.max(width, dimension.width);
				height = height + dimension.height;
			}
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
		BufferedImage image = getTransparencyImage(width, height, BufferedImage.TRANSLUCENT);
		Graphics graphics = image.getGraphics();
		graphics.setColor(color);
		graphics.setFont(font);
		FontMetrics metrics = graphics.getFontMetrics(font);
		int x = 0;
		int y = metrics.getAscent();
		try (ByteArrayInputStream stream = new ByteArrayInputStream(text.getBytes(StringUtility.CHARSET)); InputStreamReader iterator = new InputStreamReader(stream, StringUtility.CHARSET); BufferedReader buffer = new BufferedReader(iterator)) {
			String line;
			while ((line = buffer.readLine()) != null) {
				graphics.drawString(line, x, y);
				y += font.getSize();
			}
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
		graphics.dispose();
		return image;
	}

	/**
	 * 获取偏移所在行
	 * 
	 * @param component
	 * @return
	 */
	public static int getRowAtOffset(JTextComponent component, int offset) {
		Element root = component.getDocument().getDefaultRootElement();
		int line = root.getElementIndex(offset);
		return line + 1;
	}

	/**
	 * 获取偏移所在列
	 * 
	 * @param component
	 * @return
	 */
	public static int getColumnAtOffset(JTextComponent component, int offset) {
		Element root = component.getDocument().getDefaultRootElement();
		int line = root.getElementIndex(offset);
		return offset - root.getElement(line).getStartOffset() + 1;
	}

	/**
	 * 获取光标所在行
	 * 
	 * @param component
	 * @return
	 */
	public static int getRowAtCaret(JTextComponent component) {
		int position = component.getCaretPosition();
		return getRowAtOffset(component, position);
	}

	/**
	 * 获取光标所在列
	 * 
	 * @param component
	 * @return
	 */
	public static int getColumnAtCaret(JTextComponent component) {
		int position = component.getCaretPosition();
		return getColumnAtOffset(component, position);
	}

	/**
	 * 获取坐标所在行
	 * 
	 * @param component
	 * @return
	 */
	public static int getRowAtPoint(JTextComponent component, Point point) {
		int position = component.viewToModel(point);
		// Java 11: int position = component.viewToModel2D(point);
		return getRowAtOffset(component, position);
	}

	/**
	 * 获取坐标所在列
	 * 
	 * @param component
	 * @return
	 */
	public static int getColumnAtPoint(JTextComponent component, Point point) {
		int position = component.viewToModel(point);
		// Java 11: int position = component.viewToModel2D(point);
		return getColumnAtOffset(component, position);
	}

}
