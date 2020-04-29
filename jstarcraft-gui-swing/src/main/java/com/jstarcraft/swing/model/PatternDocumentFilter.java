package com.jstarcraft.swing.model;

import java.util.Objects;
import java.util.regex.Pattern;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

/**
 * 正则内容过滤器
 * 
 * <pre>
 * 作用于输入之前
 * </pre>
 * 
 * @author Birdy
 *
 */
// 参考CellEditorInputVerifier
public class PatternDocumentFilter extends DocumentFilter {

    /** 匹配内容 */
    private Pattern pattern;

    public PatternDocumentFilter(Pattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public void insertString(DocumentFilter.FilterBypass bypass, int offset, String text, AttributeSet attributes) throws BadLocationException {
        if (Objects.nonNull(text)) {
            replace(bypass, offset, 0, text, attributes);
        }
    }

    @Override
    public void remove(DocumentFilter.FilterBypass bypass, int offset, int length) throws BadLocationException {
        replace(bypass, offset, length, "", null);
    }

    @Override
    public void replace(DocumentFilter.FilterBypass bypass, int offset, int length, String text, AttributeSet attributes) throws BadLocationException {
        Document document = bypass.getDocument();
        int size = document.getLength();
        String content = document.getText(0, size);
        String before = content.substring(0, offset);
        String after = content.substring(offset + length, size);
        content = before + Objects.toString(text, "") + after;
        if (!pattern.matcher(content).matches()) {
            throw new BadLocationException(content, offset);
        }
        bypass.replace(offset, length, text, attributes);
    }

}
