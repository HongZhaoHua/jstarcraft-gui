package com.jstarcraft.swing.component.tip;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;

import javax.swing.JComponent;

/**
 * 文本提示
 * 
 * @author Birdy
 *
 */
public class TextTip implements Tip<String> {

    private String tip;

    public TextTip(String tip) {
        this.tip = tip;
    }

    @Override
    public String getTip() {
        return tip;
    }

    @Override
    public void paintTip(JComponent component, Graphics graphics) {
        Graphics2D graphics2d = (Graphics2D) graphics.create();
        Insets inset = component.getInsets();
        Font font = component.getFont();
        FontMetrics metrics = component.getFontMetrics(font);
        // 注意:此处使用JComponent.getBaseline在component无内容时会失效,所以使用FontMetrics.getAscent替代.
        // int y = component.getBaseline(component.getWidth(), component.getHeight());
        int x = inset.left;
        int y = metrics.getAscent();
        FontRenderContext context = graphics2d.getFontRenderContext();
        TextLayout text = new TextLayout(tip, font, context);
        graphics2d.setPaint(Color.GRAY);
        text.draw(graphics2d, x, y);
        graphics2d.dispose();
    }

}
