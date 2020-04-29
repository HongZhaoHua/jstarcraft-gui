package com.jstarcraft.swing.support;

/**
 * 支撑单元
 * 
 * @author Birdy
 *
 */
public interface SupportCell {

    /**
     * 添加
     */
    void attach();

    /**
     * 移除
     */
    void detach();

    /**
     * 取消
     */
    void cancel();

    /**
     * 完成
     */
    void complete();

    /**
     * 选择
     */
    boolean selected();

}
