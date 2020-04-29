package com.jstarcraft.swing.component.accordion;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jstarcraft.swing.component.CellPanel;

/**
 * 伸缩面板
 * 
 * @author Birdy
 *
 */
// 参考AccordionPanel,ExpandablePanel
public class TogglePanel extends JPanel {

    /** 标题标签 */
    private JPanel titlePanel;

    /** 内容面板 */
    private JPanel contentPanel;

    public TogglePanel(JPanel title, JPanel content, boolean toggle) {
        super(new BorderLayout());

        this.titlePanel = title;
        this.titlePanel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent evnet) {
                setToggle(!contentPanel.isVisible());
            }

        });
        this.contentPanel = content;
        this.contentPanel.setVisible(toggle);

        this.add(this.titlePanel, BorderLayout.NORTH);
        this.add(this.contentPanel, BorderLayout.CENTER);
    }

    /**
     * 获取伸缩状态
     * 
     * @return
     */
    public boolean isToggle() {
        return contentPanel.isVisible();
    }

    /**
     * 设置伸缩状态
     * 
     * @param toggle
     */
    public void setToggle(boolean toggle) {
        if (contentPanel.isVisible() != toggle) {
            // 关键代码
            contentPanel.setVisible(toggle);
            revalidate();
            // TODO 考虑改为状态变更事件
            // 通知JScrollPanel滚动到指定位置
            EventQueue.invokeLater(() -> contentPanel.scrollRectToVisible(contentPanel.getBounds()));
        }
    }

}
