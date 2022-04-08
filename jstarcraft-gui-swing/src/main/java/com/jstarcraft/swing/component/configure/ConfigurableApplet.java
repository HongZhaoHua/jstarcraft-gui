package com.jstarcraft.swing.component.configure;

import javax.swing.JApplet;

import com.jstarcraft.core.common.configuration.Option;

/**
 * 可配置小程序
 * 
 * @author Birdy
 *
 */
public abstract class ConfigurableApplet extends JApplet implements Configurable {

    protected final Option option;

    public ConfigurableApplet(Option option) {
        super();
        this.option = option;
    }

}
