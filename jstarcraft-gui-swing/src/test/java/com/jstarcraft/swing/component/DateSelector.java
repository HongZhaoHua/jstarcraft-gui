package com.jstarcraft.swing.component;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class DateSelector extends JButton {

    private JDialog dialog;

    private DateTimePanel chooser;

    private DateTimeFormatter formatter;

    private ChangeListener listener = new ChangeListener() {

        @Override
        public void stateChanged(ChangeEvent event) {
            LocalDateTime dateTime = chooser.getDateTime();
            setText(formatter.format(dateTime));
        }

    };

    public DateSelector(String format, LocalDateTime dateTime) {
        this.chooser = new DateTimePanel(dateTime);
        this.chooser.attachChangeListener(listener);
        this.formatter = DateTimeFormatter.ofPattern(format);
        this.setText(formatter.format(dateTime));
        this.setBorder(null);
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                showDateChooser();
            }

        });
    }

    private JDialog buildDialog(Frame owner) {
        JDialog dialog = new JDialog(owner, "日期时间选择", true);
        dialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        dialog.getContentPane().add(chooser, BorderLayout.CENTER);
        dialog.pack();
        return dialog;
    }

    void showDateChooser() {
        Frame frame = (Frame) SwingUtilities.getWindowAncestor(DateSelector.this);
        if (dialog == null || dialog.getOwner() != frame) {
            dialog = buildDialog(frame);
        }
//        dialog.setLocation(getAppropriateLocation(owner, position));

        // 相对屏幕居中
//        dialog.setLocationRelativeTo(null);
        // 相对JFrame居中
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

}
