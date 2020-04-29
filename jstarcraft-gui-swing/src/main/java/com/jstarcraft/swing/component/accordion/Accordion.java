package com.jstarcraft.swing.component.accordion;

/**
 * 折叠
 * 
 * @author Birdy
 *
 */
public interface Accordion {

    /**
     * 获取格子数量
     * 
     * @return
     */
    int getCellSize();

    /**
     * 判断某个格子的伸缩状态
     * 
     * @param index
     * @return
     */
    boolean isCellToggle(int index);

    /**
     * 设置某个格子的伸缩状态
     * 
     * @param index
     * @param toggle
     */
    void setCellToggle(int index, boolean toggle);

}
