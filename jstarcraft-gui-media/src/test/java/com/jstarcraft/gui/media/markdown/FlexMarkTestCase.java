package com.jstarcraft.gui.media.markdown;

import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Test;

import com.jstarcraft.core.utility.StringUtility;
import com.vladsch.flexmark.formatter.Formatter;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;

public class FlexMarkTestCase {

    @Test
    public void test() throws Exception {

        try (InputStream stream = FlexMarkTestCase.class.getResourceAsStream("markdown.md"); InputStreamReader reader = new InputStreamReader(stream, StringUtility.CHARSET)) {
            Parser parser = Parser.builder().build();
            Formatter formatter = Formatter.builder().build();

            Node document = parser.parseReader(reader);
            String commonmark = formatter.render(document);

            System.out.println("\n\nCommonMark\n");
            System.out.println(commonmark);
        }

    }

}
