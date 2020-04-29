package com.jstarcraft.swing.model;

import java.util.List;
import java.util.regex.Pattern;

import javax.swing.InputVerifier;
import javax.swing.JComponent;

import org.jaxen.JaxenException;

import com.jstarcraft.swing.xpath.SwingAttributeNode;
import com.jstarcraft.swing.xpath.SwingComponentNode;
import com.jstarcraft.swing.xpath.SwingXPath;

/**
 * 正则内容校验器
 * 
 * <pre>
 * 作用于输入之后
 * </pre>
 * 
 * @author Birdy
 *
 */
// 参考CellEditorInputVerifier
public class PatternInputVerifier extends InputVerifier {

    /** 定位属性 */
    private SwingXPath xpath;

    /** 匹配内容 */
    private Pattern pattern;

    public PatternInputVerifier(SwingXPath xpath, Pattern pattern) {
        this.xpath = xpath;
        this.pattern = pattern;
    }

    @Override
    public boolean verify(JComponent component) {
        SwingComponentNode root = new SwingComponentNode(component);
        try {
            List<SwingAttributeNode> nodes = (List) xpath.evaluate(root);
            for (SwingAttributeNode node : nodes) {
                String content = (String) node.getProperty();
                if (!pattern.matcher(content).matches()) {
                    return false;
                }
            }
        } catch (JaxenException exception) {
            throw new RuntimeException(exception);
        }
        return true;
    }

}
