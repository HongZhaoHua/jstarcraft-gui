package com.jstarcraft.swing.component.tip;

import java.awt.Graphics;

import javax.swing.JComponent;

/**
 * 提示
 * 
 * @author Birdy
 *
 */
// 参考GhostText,InputHintPasswordField,WatermarkInTextField,ComboBoxPlaceholder
public interface Tip<T> {

    T getTip();

    void paintTip(JComponent component, Graphics graphics);

}
