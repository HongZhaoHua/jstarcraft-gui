package com.jstarcraft.swing.component.calendar;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.EventObject;

import javax.swing.JLabel;

import com.jstarcraft.core.common.instant.CalendarDate;
import com.jstarcraft.core.common.instant.CalendarType;
import com.jstarcraft.core.common.instant.SolarDate;

/**
 * 阳历微调面板
 * 
 * @author Birdy
 *
 */
// 参考MouseWheel与HtmlSpinnerEditor
public class SolarCalendarSpinnerPanel extends CalendarSpinnerPanel {

    public SolarCalendarSpinnerPanel(LocalDate date) {
        super();

        CalendarDate calendar = CalendarType.Solar.getCalendarDate(date);
        CalendarDate maximum = CalendarType.Solar.getMaximumDate();
        CalendarDate minimum = CalendarType.Solar.getMinimumDate();
        setSpinnerBoundary(this.yearSpinner, minimum.getYear(), maximum.getYear());
        setSpinnerBoundary(this.monthSpinner, 1, 12);
        setSpinnerBoundary(this.daySpinner, 1, YearMonth.of(calendar.getYear(), calendar.getMonth()).lengthOfMonth());

        this.yearSpinner.addChangeListener(this::adjustSpinnerView);
        this.monthSpinner.addChangeListener(this::adjustSpinnerView);
        this.daySpinner.addChangeListener(this::adjustSpinnerView);

        this.yearSpinner.setValue(calendar.getYear());
        this.monthSpinner.setValue(calendar.getMonth());
        this.daySpinner.setValue(calendar.getDay());

        this.yearSpinner.addChangeListener(this::adjustSpinnerModel);
        this.monthSpinner.addChangeListener(this::adjustSpinnerModel);
        this.daySpinner.addChangeListener(this::adjustSpinnerModel);
    }

    @Override
    protected void adjustSpinnerModel(EventObject event) {
        // 忽略边界变化
        if (isValueChange()) {
            if (event.getSource() == yearSpinner || event.getSource() == monthSpinner) {
                int year = (int) yearSpinner.getValue();
                int month = (int) monthSpinner.getValue();
                int day = (int) daySpinner.getValue();

                int daySize = YearMonth.of(year, month).lengthOfMonth();
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
                JLabel monthLabel = (JLabel) monthSpinner.getEditor();
                int month = (int) monthSpinner.getValue();
                monthLabel.setText(month + "月");
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
        CalendarDate calendar = CalendarType.Solar.getCalendarDate(date);
        yearSpinner.setValue(calendar.getYear());
        monthSpinner.setValue(calendar.getMonth());
        daySpinner.setValue(calendar.getDay());
    }

    @Override
    public LocalDate getCalendarDate() {
        int year = ((Number) yearSpinner.getValue()).intValue();
        int month = ((Number) monthSpinner.getValue()).intValue();
        int day = ((Number) daySpinner.getValue()).intValue();
        CalendarDate calendar = new SolarDate(year, month, day);
        return calendar.getDate();
    }

    @Override
    public CalendarType getCalendarType() {
        return CalendarType.Solar;
    }

}
