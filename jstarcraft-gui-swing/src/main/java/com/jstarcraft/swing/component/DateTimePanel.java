package com.jstarcraft.swing.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * 日期时间选择面板
 * 
 * @author Birdy
 *
 */
public class DateTimePanel extends JPanel {
    
    /** 国际化 */
    private Locale locale = Locale.CHINESE;

    /** 周起始日 */
    private DayOfWeek offset = DayOfWeek.SUNDAY;

    /** 默认最小年份 */
    private int firstYear = 1850;
    /** 默认最大年份 */
    private int lastYear = 2150;
    private int width = 200; // 界面宽度
    private int height = 200; // 界面高度

    private Color backGroundColor = Color.gray; // 底色
    // 月历表格配色----------------//
    private Color palletTableColor = Color.white; // 日历表底色
    private Color todayBackColor = Color.orange; // 今天背景色
    private Color weekFontColor = Color.blue; // 星期文字色
    private Color dayFontColor = Color.black; // 日期文字色
    private Color weekendFontColor = Color.red; // 周末文字色

    // 控制条配色------------------//
    private Color controlLineColor = Color.blue; // 控制条底色
    private Color controlTextColor = Color.white; // 控制条标签文字色

    private Color rbFontColor = Color.white; // RoundBox文字色
    private Color rbBorderColor = Color.red; // RoundBox边框色
    private Color rbButtonColor = Color.pink; // RoundBox按钮色
    private Color rbBtFontColor = Color.red; // RoundBox按钮文字色

    private LocalDateTime dateTime;

    /** 年调节器 */
    private JSpinner yearSpinner;
    /** 月调节器 */
    private JSpinner monthSpinner;
    /** 时调节器 */
    private JSpinner hourSpinner;
    /** 分调节器 */
    private JSpinner minuteSpinner;
    /** 秒调节器 */
    private JSpinner secondSpinner;
    /** 日按钮 TODO 考虑替代成JTable */
    private JButton[][] dayButtons = new JButton[6][7];

    private DateTimeEventHandler handler = new DateTimeEventHandler();

    private Collection<ChangeListener> listeners = new HashSet<>();

    private class DateTimeEventHandler implements ActionListener, ChangeListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            JButton source = (JButton) event.getSource();
            if (source.getText().length() == 0) {
                return;
            }

            dayColorUpdate(false);

            source.setForeground(todayBackColor);
            int day = Integer.parseInt(source.getText());
            LocalDateTime dateTime = getDateTime();
            dateTime = dateTime.with(TemporalAdjusters.lastDayOfMonth());
            if (day > dateTime.getDayOfMonth()) {
                day = dateTime.getDayOfMonth();
            }
            setDateTime(LocalDateTime.of(LocalDate.of(dateTime.getYear(), dateTime.getMonthValue(), day), dateTime.toLocalTime()));
        }

        @Override
        public void stateChanged(ChangeEvent event) {
            JSpinner source = (JSpinner) event.getSource();
            LocalDateTime dateTime = getDateTime();
            if (source == hourSpinner) {
                setDateTime(LocalDateTime.of(dateTime.toLocalDate(), LocalTime.of(getSelectedHour(), dateTime.getMinute(), dateTime.getSecond())));
                return;
            } else if (source == minuteSpinner) {
                setDateTime(LocalDateTime.of(dateTime.toLocalDate(), LocalTime.of(dateTime.getHour(), getSelectedMinute(), dateTime.getSecond())));
                return;
            } else if (source == secondSpinner) {
                setDateTime(LocalDateTime.of(dateTime.toLocalDate(), LocalTime.of(dateTime.getHour(), dateTime.getMinute(), getSelectedSecond())));
                return;
            }

            dayColorUpdate(false);

            if (source == yearSpinner) {
                int day = dateTime.getDayOfMonth();
                LocalDate date = LocalDate.of(getSelectedYear(), dateTime.getMonthValue(), 1);
                int length = date.lengthOfMonth();
                if (day > length) {
                    day = length;
                }
                date = LocalDate.of(getSelectedYear(), dateTime.getMonthValue(), day);
                dateTime = LocalDateTime.of(date, dateTime.toLocalTime());
                setDateTime(dateTime);
            } else {
                int day = dateTime.getDayOfMonth();
                LocalDate date = LocalDate.of(dateTime.getYear(), getSelectedMonth(), 1);
                int length = date.lengthOfMonth();
                if (day > length) {
                    day = length;
                }
                date = LocalDate.of(dateTime.getYear(), getSelectedMonth(), day);
                dateTime = LocalDateTime.of(date, dateTime.toLocalTime());
                setDateTime(dateTime);
            }
            freshWeekAndDay();
        }

    }

    public DateTimePanel(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        this.setLayout(new BorderLayout());
        this.setBorder(new LineBorder(this.backGroundColor, 2));
        this.setBackground(this.backGroundColor);

        /* 上中下布局 */
        JPanel northPanel = buildNorthPanel();
        add(northPanel, BorderLayout.NORTH);
        JPanel centerPanel = buildCenterPanel();
        add(centerPanel, BorderLayout.CENTER);
        JPanel southPanel = buildSouthPanel();
        add(southPanel, BorderLayout.SOUTH);
    }

    private JPanel buildNorthPanel() {
        LocalDateTime dateTime = getDateTime();
        int year = dateTime.getYear();
        int month = dateTime.getMonthValue();

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setBackground(controlLineColor);

        yearSpinner = new JSpinner(new SpinnerNumberModel(year, firstYear, lastYear, 1));
        yearSpinner.setPreferredSize(new Dimension(48, 20));
        yearSpinner.setName("Year");
        yearSpinner.setEditor(new JSpinner.NumberEditor(yearSpinner, "####"));
        yearSpinner.addChangeListener(handler);
        panel.add(yearSpinner);

        JLabel yearLabel = new JLabel("年");
        yearLabel.setForeground(controlTextColor);
        panel.add(yearLabel);

        monthSpinner = new JSpinner(new SpinnerNumberModel(month, 1, 12, 1));
        monthSpinner.setPreferredSize(new Dimension(35, 20));
        monthSpinner.setName("Month");
        monthSpinner.addChangeListener(handler);
        panel.add(monthSpinner);

        JLabel monthLabel = new JLabel("月");
        monthLabel.setForeground(controlTextColor);
        panel.add(monthLabel);

        return panel;
    }

    private JPanel buildCenterPanel() {
        JPanel panel = new JPanel();
        // 设置固定字体，以免调用环境改变影响界面美观
        panel.setFont(new Font("宋体", Font.PLAIN, 12));
        panel.setLayout(new GridLayout(7, 7));
        panel.setBackground(Color.white);
//        String weekNames[] = { "一", "二", "三", "四", "五", "六", "日" };

        for (int index = 0; index < 7; index++) {
            DayOfWeek dayOfWeek = offset.plus(index);
            JLabel weekLabel = new JLabel(dayOfWeek.getDisplayName(TextStyle.SHORT, locale));
            weekLabel.setHorizontalAlignment(SwingConstants.CENTER);
            if (dayOfWeek.getValue() > 5) {
                weekLabel.setForeground(weekendFontColor);
            } else {
                weekLabel.setForeground(weekFontColor);
            }
            panel.add(weekLabel);
        }

        int actionCommandId = 0;
        for (int row = 0; row < 6; row++) {
            for (int column = 0; column < 7; column++) {
                DayOfWeek dayOfWeek = offset.plus(column);
                JButton dayButton = new JButton();
                dayButton.setBorder(null);
                // 阻止绘制焦点
                dayButton.setFocusPainted(false);
                dayButton.setHorizontalAlignment(SwingConstants.CENTER);
                dayButton.setActionCommand(String.valueOf(actionCommandId));
                dayButton.addActionListener(handler);
                dayButton.setBackground(palletTableColor);
                if (dayOfWeek.getValue() > 5) {
                    dayButton.setForeground(weekendFontColor);
                } else {
                    dayButton.setForeground(dayFontColor);
                }
                dayButtons[row][column] = dayButton;
                panel.add(dayButton);
                actionCommandId++;
            }
        }

        freshWeekAndDay();

        return panel;
    }

    private JPanel buildSouthPanel() {
        LocalDateTime dateTime = getDateTime();

        int currentHour = dateTime.getHour();// 时
        int currentMin = dateTime.getMinute();// 分
        int currentSec = dateTime.getSecond();// 秒

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setBackground(controlLineColor);

        hourSpinner = new JSpinner(new SpinnerNumberModel(currentHour, 0, 23, 1));
        hourSpinner.setPreferredSize(new Dimension(35, 20));
        hourSpinner.setName("Hour");
        hourSpinner.addChangeListener(handler);
        panel.add(hourSpinner);

        JLabel hourLabel = new JLabel("时");
        hourLabel.setForeground(controlTextColor);
        panel.add(hourLabel);

        minuteSpinner = new JSpinner(new SpinnerNumberModel(currentMin, 0, 59, 1));
        minuteSpinner.setPreferredSize(new Dimension(35, 20));
        minuteSpinner.setName("Minute");
        minuteSpinner.addChangeListener(handler);
        panel.add(minuteSpinner);

        JLabel minuteLabel = new JLabel("分");
        minuteLabel.setForeground(controlTextColor);
        panel.add(minuteLabel);

        secondSpinner = new JSpinner(new SpinnerNumberModel(currentSec, 0, 59, 1));
        secondSpinner.setPreferredSize(new Dimension(35, 20));
        secondSpinner.setName("Second");
        secondSpinner.addChangeListener(handler);
        panel.add(secondSpinner);

        JLabel secondLabel = new JLabel("秒");
        secondLabel.setForeground(controlTextColor);
        panel.add(secondLabel);

        return panel;
    }

    Point getAppropriateLocation(Frame owner, Point position) {
        Point result = new Point(position);
        Point p = owner.getLocation();
        int offsetX = (position.x + width) - (p.x + owner.getWidth());
        int offsetY = (position.y + height) - (p.y + owner.getHeight());
        if (offsetX > 0) {
            result.x -= offsetX;
        }
        if (offsetY > 0) {
            result.y -= offsetY;
        }
        return result;
    }

    private int getSelectedYear() {
        return ((Integer) yearSpinner.getValue()).intValue();
    }

    private int getSelectedMonth() {
        return ((Integer) monthSpinner.getValue()).intValue();
    }

    private int getSelectedHour() {
        return ((Integer) hourSpinner.getValue()).intValue();
    }

    private int getSelectedMinute() {
        return ((Integer) minuteSpinner.getValue()).intValue();
    }

    private int getSelectedSecond() {
        return ((Integer) secondSpinner.getValue()).intValue();
    }

    /**
     * 偏移当前每周是第几天?
     * 
     * @param dayOfWeek
     * @return
     */
    private int offsetDayOfWeek(int dayOfWeek) {
        return (dayOfWeek + 7 - offset.getValue()) % 7 + 1;
    }

    private void dayColorUpdate(boolean selected) {
        LocalDateTime dateTime = getDateTime();
        int day = dateTime.getDayOfMonth();
        dateTime = dateTime.with(TemporalAdjusters.firstDayOfMonth());
        int dayOfWeek = dateTime.getDayOfWeek().getValue();
        int index = day - 2 + offsetDayOfWeek(dayOfWeek);
        int row = index / 7;
        int column = index % 7;
        if (selected) {
            dayButtons[row][column].setForeground(todayBackColor);
        } else {
            dayOfWeek = getDateTime().getDayOfWeek().getValue();
            if (dayOfWeek > 5) {
                dayButtons[row][column].setForeground(weekendFontColor);
            } else {
                dayButtons[row][column].setForeground(dayFontColor);
            }
        }
    }

    private void freshWeekAndDay() {
        LocalDateTime firstDay = getDateTime();
        firstDay = firstDay.with(TemporalAdjusters.firstDayOfMonth());
        int lastDay = firstDay.toLocalDate().lengthOfMonth();
        int dayOffset = 2 - offsetDayOfWeek(firstDay.getDayOfWeek().getValue());
        for (int row = 0; row < 6; row++) {
            for (int column = 0; column < 7; column++) {
                String text = "";
                if (dayOffset >= 1 && dayOffset <= lastDay) {
                    text = String.valueOf(dayOffset);
                }
                dayButtons[row][column].setText(text);
                dayOffset++;
            }
        }
        dayColorUpdate(true);
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        // TODO 需要触发变更
        triggerChangeListeners();
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    protected void triggerChangeListeners() {
        ChangeEvent event = new ChangeEvent(this);
        for (ChangeListener listener : listeners) {
            listener.stateChanged(event);
        }
    }

    public void attachChangeListener(ChangeListener listener) {
        listeners.add(listener);
    }

    public void detachChangeListener(ChangeListener listener) {
        listeners.remove(listener);
    }

}
