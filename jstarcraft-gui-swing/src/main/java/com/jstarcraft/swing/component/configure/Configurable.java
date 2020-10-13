package com.jstarcraft.swing.component.configure;

import com.jstarcraft.core.common.option.Option;

/**
 * 可配置
 * 
 * @author Birdy
 *
 */
public interface Configurable {

    /**
     * 获取配置
     * 
     * @return
     */
    Option getOption();

    /**
     * 设置配置
     * 
     * @param option
     */
    void setOption(Option option);

}
