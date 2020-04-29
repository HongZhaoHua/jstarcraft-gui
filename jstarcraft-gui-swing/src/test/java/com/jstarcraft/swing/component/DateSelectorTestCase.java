package com.jstarcraft.swing.component;

import java.awt.BorderLayout;
import java.time.LocalDateTime;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

public class DateSelectorTestCase extends JFrame {

    private JPanel contentPane;

    public static void main(String[] args) {
        DateSelectorTestCase frame = new DateSelectorTestCase();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public DateSelectorTestCase() {
        setBounds(100, 100, 350, 200);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JButton button = new DateSelector("yyyy年MM月dd日  HH时mm分ss秒", LocalDateTime.of(2020, 3, 1, 0, 0, 0));
        contentPane.add(button, BorderLayout.CENTER);
    }

}
