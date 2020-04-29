package com.jstarcraft.swing.component.calendar;

import java.awt.EventQueue;
import java.time.LocalDate;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.junit.Assert;
import org.junit.Test;

import com.jstarcraft.core.common.instant.CalendarDate;
import com.jstarcraft.core.common.instant.SolarDate;

public class SolarCalendarSpinnerPanenlTestCase {

    @Test
    public void test() throws Exception {
        SolarCalendarSpinnerPanel panel = new SolarCalendarSpinnerPanel(LocalDate.of(2020, 2, 29));

        JFrame frame = new JFrame("testSolarDateSpinnerPanenl");
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        // 适配大小
        frame.pack();
        // 窗体居中
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        Thread.sleep(5000L);
        EventQueue.invokeAndWait(() -> {
            CalendarDate date = new SolarDate(2010, 2, 28);
            panel.setCalendarDate(date.getDate());
            Assert.assertEquals(date.getDate(), panel.getCalendarDate());
        });
        Thread.sleep(5000L);
    }

}
