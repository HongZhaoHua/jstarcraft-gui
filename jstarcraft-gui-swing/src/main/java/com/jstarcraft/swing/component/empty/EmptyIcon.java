package com.jstarcraft.swing.component.empty;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

/**
 * 空图标
 * 
 * @author Birdy
 *
 */
// TODO 考虑整合到SwingUtility
public class EmptyIcon implements Icon {

    @Override
    public void paintIcon(Component component, Graphics graphics, int x, int y) {
    }

    @Override
    public int getIconWidth() {
        return 0;
    }

    @Override
    public int getIconHeight() {
        return 0;
    }

}