package com.jstarcraft.swing.component.calendar;

import java.time.LocalDate;
import java.util.EventObject;

import javax.swing.JLabel;

import com.jstarcraft.core.common.instant.CalendarDate;
import com.jstarcraft.core.common.instant.CalendarType;
import com.jstarcraft.core.common.instant.LunarDate;

/**
 * 阴历微调面板
 * 
 * @author Birdy
 *
 */
// 参考MouseWheel与HtmlSpinnerEditor
public class LunarCalendarSpinnerPanel extends CalendarSpinnerPanel {

    public LunarCalendarSpinnerPanel(LocalDate date) {
        super();

        CalendarDate calendar = CalendarType.Lunar.getCalendarDate(date);
        CalendarDate maximum = CalendarType.Lunar.getMaximumDate();
        CalendarDate minimum = CalendarType.Lunar.getMinimumDate();
        setSpinnerBoundary(this.yearSpinner, minimum.getYear(), maximum.getYear());
        // 月转索引
        int leap = LunarDate.getLeapMonth(calendar.getYear());
        int monthSize = leap > 0 ? 13 : 12;
        setSpinnerBoundary(this.monthSpinner, 1, monthSize);
        int daySize = LunarDate.getDaySize(calendar.getYear(), calendar.isLeap(), calendar.getMonth());
        setSpinnerBoundary(this.daySpinner, 1, daySize);

        this.yearSpinner.addChangeListener(this::adjustSpinnerView);
        this.monthSpinner.addChangeListener(this::adjustSpinnerView);
        this.daySpinner.addChangeListener(this::adjustSpinnerView);

        this.yearSpinner.setValue(calendar.getYear());
        int month = calendar.getMonth();
        int index = calendar.isLeap() || (leap > 0 && month > leap) ? month + 1 : month;
        this.monthSpinner.setValue(index);
        this.daySpinner.setValue(calendar.getDay());

        this.yearSpinner.addChangeListener(this::adjustSpinnerModel);
        this.monthSpinner.addChangeListener(this::adjustSpinnerModel);
        this.daySpinner.addChangeListener(this::adjustSpinnerModel);
    }

    @Override
    protected void adjustSpinnerModel(EventObject event) {
        // 忽略边界变化
        if (isValueChange()) {
            if (event.getSource() == yearSpinner) {
                int year = (int) yearSpinner.getValue();
                // 索引转月
                int leap = LunarDate.getLeapMonth(year);
                int index = (int) monthSpinner.getValue();
                int monthSize = leap > 0 ? 13 : 12;
                setSpinnerBoundary(monthSpinner, 1, monthSize);
                if (index > monthSize) {
                    monthSpinner.setValue(monthSize);
                }
                int month = leap > 0 && index > leap ? index - 1 : index;
                int daySize = LunarDate.getDaySize(year, leap > 0 && leap == index - 1, month);
                int day = (int) daySpinner.getValue();
                setSpinnerBoundary(daySpinner, 1, daySize);
                if (day > daySize) {
                    daySpinner.setValue(daySize);
                }
            }
            if (event.getSource() == monthSpinner) {
                int year = (int) yearSpinner.getValue();
                // 索引转月
                int leap = LunarDate.getLeapMonth(year);
                int index = (int) monthSpinner.getValue();
                int month = leap > 0 && index > leap ? index - 1 : index;
                int daySize = LunarDate.getDaySize(year, leap > 0 && leap == index - 1, month);
                int day = (int) daySpinner.getValue();
                setSpinnerBoundary(daySpinner, 1, daySize);
                if (day > daySize) {
                    daySpinner.setValue(daySize);
                }
            }
        }
    }

    @Override
    protected void adjustSpinnerView(EventObject event) {
        // 忽略边界变化
        if (isValueChange()) {
            if (event.getSource() == yearSpinner) {
                JLabel yearLabel = (JLabel) yearSpinner.getEditor();
                int year = (int) yearSpinner.getValue();
                yearLabel.setText(year + "年");
            }
            if (event.getSource() == monthSpinner) {
                int year = (int) yearSpinner.getValue();
                JLabel monthLabel = (JLabel) monthSpinner.getEditor();
                // 索引转月
                int leap = LunarDate.getLeapMonth(year);
                int index = (int) monthSpinner.getValue();
                int month = leap > 0 && index > leap ? index - 1 : index;
                monthLabel.setText((leap > 0 && leap == index - 1 ? "闰" : "") + month + "月");
            }
            if (event.getSource() == daySpinner) {
                JLabel dayLabel = (JLabel) daySpinner.getEditor();
                int day = (int) daySpinner.getValue();
                dayLabel.setText(day + "日");
            }
        }
    }

    @Override
    public void setCalendarDate(LocalDate date) {
        CalendarDate calendar = CalendarType.Lunar.getCalendarDate(date);
        yearSpinner.setValue(calendar.getYear());
        int leap = LunarDate.getLeapMonth(calendar.getYear());
        // 月转索引
        int month = calendar.getMonth();
        int index = calendar.isLeap() || (leap > 0 && month > leap) ? month + 1 : month;
        monthSpinner.setValue(index);
        daySpinner.setValue(calendar.getDay());
    }

    @Override
    public LocalDate getCalendarDate() {
        int year = ((Number) yearSpinner.getValue()).intValue();
        int leap = LunarDate.getLeapMonth(year);
        // 索引转月
        int index = ((Number) monthSpinner.getValue()).intValue();
        int month = leap > 0 && index > leap ? index - 1 : index;
        int day = ((Number) daySpinner.getValue()).intValue();
        CalendarDate calendar = new LunarDate(year, leap > 0 && leap == index - 1, month, day);
        return calendar.getDate();
    }

    @Override
    public CalendarType getCalendarType() {
        return CalendarType.Lunar;
    }

}
