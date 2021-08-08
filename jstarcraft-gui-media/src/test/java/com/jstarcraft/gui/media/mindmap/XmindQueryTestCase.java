package com.jstarcraft.gui.media.mindmap;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.jstarcraft.core.codec.specification.CodecDefinition;
import com.jstarcraft.core.storage.lucene.LuceneEngine;
import com.jstarcraft.core.storage.lucene.LuceneMetadata;
import com.jstarcraft.core.storage.lucene.converter.LuceneContext;
import com.jstarcraft.core.utility.KeyValue;
import com.jstarcraft.core.utility.StringUtility;

import it.unimi.dsi.fastutil.floats.FloatList;

public class XmindQueryTestCase {

    @Test
    public void testSearch() throws Exception {
        // Lucene引擎
        LuceneContext context = new LuceneContext(CodecDefinition.instanceOf(XmindTopic.class));
        LuceneMetadata codec = new LuceneMetadata(XmindTopic.class, context);
        Path path = Paths.get("./lucene");

//        Analyzer analyzer = new HanLpQueryAnalyzer("viterbi");
        Analyzer analyzer = new IKAnalyzer();
        try (LuceneEngine engine = new LuceneEngine(() -> {
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            return config;
        }, path)) {
            QueryParser parser = new MultiFieldQueryParser(new String[] { "title", "note" }, analyzer);
            Query query = parser.parse("设计模式");
//            Query query = new TermQuery(new Term("title", "从众"));
            System.out.println(StringUtility.format("查询XMind主题条件:[{}]", query));
            int count = engine.countDocuments(query);
            System.out.println(StringUtility.format("查询XMind主题数量:[{}]", count));
            KeyValue<List<Document>, FloatList> documents = engine.retrieveDocuments(query, null, 0, 1000);
            for (Document document : documents.getKey()) {
                XmindTopic topic = (XmindTopic) codec.decodeDocument(document);
                String root = topic.getRoot();
                String title = topic.getTitle();
                System.out.println(StringUtility.format("文件为[{}]-主题为[{}]", root, title));
            }
        }
    }

}
