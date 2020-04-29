package com.jstarcraft.swing.component.configure;

import javax.swing.JApplet;

import com.jstarcraft.core.common.configuration.Configurator;

/**
 * 可配置小程序
 * 
 * @author Birdy
 *
 */
public abstract class ConfigurableApplet extends JApplet implements Configurable {

    protected final Configurator configurator;

    public ConfigurableApplet(Configurator configurator) {
        super();
        this.configurator = configurator;
    }

}
