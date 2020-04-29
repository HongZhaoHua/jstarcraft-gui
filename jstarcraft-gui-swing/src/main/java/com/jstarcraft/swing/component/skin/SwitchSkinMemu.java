package com.jstarcraft.swing.component.skin;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.lang.reflect.Modifier;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import com.jstarcraft.core.utility.PackageUtility.PackageScanner;
import com.jstarcraft.core.utility.StringUtility;
import com.jstarcraft.swing.SwingUtility;

/**
 * 切换皮肤菜单
 * 
 * @author Birdy
 *
 */
public class SwitchSkinMemu extends JMenu {

    static {
        // 扫描JTattoo皮肤
        PackageScanner scanner = new PackageScanner("com.jtattoo.plaf");
        for (Class clazz : scanner.getClazzCollection()) {
            if (LookAndFeel.class.isAssignableFrom(clazz) && !Modifier.isAbstract(clazz.getModifiers())) {
                String name = clazz.getSimpleName();
                name = name.substring(0, name.indexOf("LookAndFeel"));
                // 装载JTattoo皮肤
                UIManager.installLookAndFeel(name, clazz.getName());
            }
        }
    }

    private ButtonGroup switchGroup = new ButtonGroup();

    private Component[] skinComponents;

    public SwitchSkinMemu(Component... components) {
        this(StringUtility.EMPTY, components);
    }

    public SwitchSkinMemu(String text, Component... components) {
        super(text);
        this.skinComponents = components;

        String skin = UIManager.getLookAndFeel().getClass().getName();
        for (LookAndFeelInfo information : UIManager.getInstalledLookAndFeels()) {
            String name = information.getName();
            String clazz = information.getClassName();
            JRadioButtonMenuItem item = new JRadioButtonMenuItem(name, clazz.equals(skin));
            item.setActionCommand(clazz);
            item.setHideActionText(false);
            item.addActionListener(this::actionPerformed);
            switchGroup.add(item);
            this.add(item);
        }
    }

    protected void actionPerformed(ActionEvent event) {
        ButtonModel model = switchGroup.getSelection();
        String skin = model.getActionCommand();
        for (Component component : skinComponents) {
            SwingUtility.setComponentSkin(component, skin);
        }
    }

}
