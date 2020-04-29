package com.jstarcraft.swing.component.configure;

import com.jstarcraft.core.common.configuration.Configurator;

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
    Configurator getConfigurator();

    /**
     * 设置配置
     * 
     * @param configurator
     */
    void setConfigurator(Configurator configurator);

}
