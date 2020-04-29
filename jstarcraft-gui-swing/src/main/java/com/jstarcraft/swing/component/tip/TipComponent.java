package com.jstarcraft.swing.component.tip;

import java.awt.Graphics;

import javax.swing.JComponent;

public class TipComponent extends JComponent {

    protected Tip tip;

    public TipComponent(Tip tip) {
        super();
        this.tip = tip;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        tip.paintTip(this, graphics);
    }

}
