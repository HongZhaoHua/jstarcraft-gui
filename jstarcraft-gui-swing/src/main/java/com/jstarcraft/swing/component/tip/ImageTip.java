package com.jstarcraft.swing.component.tip;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.JComponent;

/**
 * 图片提示
 * 
 * @author Birdy
 *
 */
public class ImageTip implements Tip<Image> {

    private Image tip;

    public ImageTip(Image tip) {
        this.tip = tip;
    }

    @Override
    public Image getTip() {
        return tip;
    }

    @Override
    public void paintTip(JComponent component, Graphics graphics) {
        Graphics2D graphics2d = (Graphics2D) graphics.create();
        Insets inset = component.getInsets();
        Font font = component.getFont();
        FontMetrics metrics = component.getFontMetrics(font);
        // 注意:此处使用JComponent.getBaseline在component无内容时会失效,所以使用FontMetrics.getAscent替代.
        // int height = component.getBaseline(component.getWidth(), component.getHeight());
        int height = metrics.getAscent() + metrics.getDescent();
        int width = (int) (tip.getWidth(null) * 1F * height / tip.getHeight(null));
        graphics2d.drawImage(tip, inset.left, inset.top, width, height, component);
        graphics2d.dispose();
    }

}
