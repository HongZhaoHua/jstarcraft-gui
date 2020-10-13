package com.jstarcraft.swing.component.configure;

import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Window;

import javax.swing.JWindow;

import com.jstarcraft.core.common.option.Option;

/**
 * 可配置窗体
 * 
 * @author Birdy
 *
 */
public abstract class ConfigurableWindow extends JWindow implements Configurable {

    protected final Option option;

    public ConfigurableWindow(Option option) {
        super();
        this.option = option;
    }

    public ConfigurableWindow(Option option, Frame owner) {
        super(owner);
        this.option = option;
    }

    public ConfigurableWindow(Option option, Window owner) {
        super(owner);
        this.option = option;
    }

    public ConfigurableWindow(Option option, GraphicsConfiguration graphics) {
        super(graphics);
        this.option = option;
    }

    public ConfigurableWindow(Option option, Window owner, GraphicsConfiguration graphics) {
        super(owner, graphics);
        this.option = option;
    }

}
