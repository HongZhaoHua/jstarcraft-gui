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

    protected final Configurator option;

    public ConfigurableApplet(Configurator option) {
        super();
        this.option = option;
    }

}
