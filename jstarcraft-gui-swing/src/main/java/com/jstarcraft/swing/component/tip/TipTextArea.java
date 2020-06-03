package com.jstarcraft.swing.component.tip;

import java.awt.Graphics;

import javax.swing.JTextArea;
import javax.swing.text.Document;

/**
 * 提示文本区域
 * 
 * @author Birdy
 *
 */
// 参考InputHintPasswordField,WatermarkInTextField,ComboBoxPlaceholder
public class TipTextArea extends JTextArea {

    protected Tip tip;

    public TipTextArea(Tip tip) {
        super();
        this.tip = tip;
    }

    public TipTextArea(Tip tip, Document document) {
        super(document);
        this.tip = tip;
    }

    public TipTextArea(Tip tip, String text) {
        super(text);
        this.tip = tip;
    }

    public TipTextArea(Tip tip, int rows, int columns) {
        super(rows, columns);
        this.tip = tip;
    }

    public TipTextArea(Tip tip, String text, int rows, int columns) {
        super(text, rows, columns);
        this.tip = tip;
    }

    public TipTextArea(Tip tip, Document document, String text, int rows, int columns) {
        super(document, text, rows, columns);
        this.tip = tip;
    }

    /**
     * 是否绘制提示
     * 
     * @return
     */
    protected boolean isNeedTip() {
        return getText().length() == 0;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if (isNeedTip()) {
            tip.paintTip(this, graphics);
        }
    }

}
