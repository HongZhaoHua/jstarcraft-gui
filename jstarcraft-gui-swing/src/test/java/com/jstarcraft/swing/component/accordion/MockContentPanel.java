package com.jstarcraft.swing.component.accordion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

import com.jstarcraft.core.utility.KeyValue;
import com.jstarcraft.swing.component.DataPanel;

public class MockContentPanel extends DataPanel<KeyValue<String, Integer>, String> {

    private static final Border border = new MatteBorder(1, 1, 1, 1, new Color(0, 0, 0));

    private JLabel contentLabel;

    public MockContentPanel() {
        super(new BorderLayout());

        this.contentLabel = new JLabel();
        this.add(this.contentLabel, BorderLayout.CENTER);
        this.setBorder(border);
    }

    @Override
    public String getData() {
        return contentLabel.getText();
    }

    @Override
    public void setData(KeyValue<String, Integer> data) {
        contentLabel.setText(data.getKey());
        Dimension dimension = contentLabel.getPreferredSize();
        dimension.width *= data.getValue();
        dimension.height *= data.getValue();
        setPreferredSize(dimension);
    }

}
