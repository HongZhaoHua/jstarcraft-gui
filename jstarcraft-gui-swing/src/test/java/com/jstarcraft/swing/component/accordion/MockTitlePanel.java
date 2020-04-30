package com.jstarcraft.swing.component.accordion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

import com.jstarcraft.swing.component.DataPanel;

public class MockTitlePanel extends DataPanel<Integer, Integer> {

    private static final Border border = new MatteBorder(1, 1, 1, 1, new Color(0, 0, 0));

    private JLabel titleLabel;

    public MockTitlePanel() {
        super(new BorderLayout());

        this.titleLabel = new JLabel();
        this.add(this.titleLabel, BorderLayout.CENTER);
        this.setBorder(border);
    }

    @Override
    public Integer getData() {
        return Integer.valueOf(titleLabel.getText());
    }

    @Override
    public void setData(Integer data) {
        titleLabel.setText(String.valueOf(data));
    }

}
