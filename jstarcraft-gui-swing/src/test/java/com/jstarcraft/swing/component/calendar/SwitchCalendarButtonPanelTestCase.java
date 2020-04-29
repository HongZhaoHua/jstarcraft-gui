package com.jstarcraft.swing.component.calendar;

import java.time.LocalDate;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.junit.Test;

public class SwitchCalendarButtonPanelTestCase {

    @Test
    public void test() throws Exception {
        LocalDate date = LocalDate.of(2020, 3, 10);
        SwitchCalendarButtonPanel panel = new SwitchCalendarButtonPanel(new LunarCalendarSpinnerPanel(date), new SolarCalendarSpinnerPanel(date));

        JFrame frame = new JFrame();
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        // 适配大小
        frame.pack();
        // 窗体居中
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        Thread.sleep(5000L);
    }

}
