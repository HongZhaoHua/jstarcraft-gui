package com.jstarcraft.swing.component.configure;

import java.awt.GraphicsConfiguration;

import javax.swing.JFrame;

import com.jstarcraft.core.common.option.Option;

/**
 * 可配置框架
 * 
 * @author 20191031
 *
 */
public abstract class ConfigurableFrame extends JFrame implements Configurable {

    protected final Option option;

    public ConfigurableFrame(Option option) {
        super();
        this.option = option;
    }

    public ConfigurableFrame(Option option, String title) {
        super(title);
        this.option = option;
    }

    public ConfigurableFrame(Option option, GraphicsConfiguration graphics) {
        super(graphics);
        this.option = option;
    }

    public ConfigurableFrame(Option option, String title, GraphicsConfiguration graphics) {
        super(title, graphics);
        this.option = option;
    }

}
