package com.jstarcraft.gui.media.markdown;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import org.junit.Test;

import com.jstarcraft.core.utility.StringUtility;
import com.vladsch.flexmark.ext.tables.TableExtractingVisitor;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.formatter.Formatter;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataSet;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.format.MarkdownTable;
import com.vladsch.flexmark.util.sequence.LineAppendableImpl;

public class FlexMarkTestCase {

    @Test
    public void test() throws Exception {
        try (InputStream stream = FlexMarkTestCase.class.getResourceAsStream("markdown.md"); InputStreamReader reader = new InputStreamReader(stream, StringUtility.CHARSET)) {
            // 设置支出表格
            DataSet option = new MutableDataSet().set(Parser.EXTENSIONS, Arrays.asList(TablesExtension.create())).toImmutable();
            Parser parser = Parser.builder(option).build();
            Formatter formatter = Formatter.builder(option).build();

            LineAppendableImpl buffer = new LineAppendableImpl(0);
            Node document = parser.parseReader(reader);
            formatter.render(document, buffer);
            System.out.println(buffer.toString());

            TableExtractingVisitor tableVisitor = new TableExtractingVisitor(option);
            MarkdownTable[] tables = tableVisitor.getTables(document);

            System.out.println(tables.length);
            for (MarkdownTable table : tables) {
                buffer = new LineAppendableImpl(0);
                table.appendTable(buffer);
                System.out.println(buffer.toString());
            }
        }

    }

}
