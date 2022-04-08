package com.jstarcraft.swing.component.configure;

import com.jstarcraft.core.common.configuration.Option;

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
    Option getConfiguration();

    /**
     * 设置配置
     * 
     * @param option
     */
    void setConfiguration(Option option);

}
