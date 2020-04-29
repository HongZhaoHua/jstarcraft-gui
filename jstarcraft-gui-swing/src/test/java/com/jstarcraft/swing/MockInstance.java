package com.jstarcraft.swing;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.jstarcraft.core.utility.Float2FloatKeyValue;

import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;

/**
 * 模拟实例
 * 
 * @author Birdy
 *
 */
public class MockInstance {

    /** 记录标题 */
    @NonNull
    protected String title;

    /** 记录注释 */
    @Nullable
    protected String comment;

    /** 记录标记 */
    @Nullable
    protected Int2IntOpenHashMap marks;

    /** 记录多媒体 */
    @Nullable
    protected Object2IntOpenHashMap<String> medias;

    /** 记录日期时间 */
    @Nullable
    protected LocalDateTime dateTime;

    /** 记录时区 */
    @Nullable
    protected ZoneOffset zone;

    /** 记录坐标 */
    @Nullable
    protected Float2FloatKeyValue coordinate;

    /** 记录邮编 */
    @Nullable
    protected String postal;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Int2IntOpenHashMap getMarks() {
        return marks;
    }

    public void setMarks(Int2IntOpenHashMap marks) {
        this.marks = marks;
    }

    public Object2IntOpenHashMap<String> getMedias() {
        return medias;
    }

    public void setMedias(Object2IntOpenHashMap<String> medias) {
        this.medias = medias;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public ZoneOffset getZone() {
        return zone;
    }

    public void setZone(ZoneOffset zone) {
        this.zone = zone;
    }

    public Float2FloatKeyValue getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Float2FloatKeyValue coordinate) {
        this.coordinate = coordinate;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

}
