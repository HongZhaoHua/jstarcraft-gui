package com.jstarcraft.gui.media.mindmap;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;
import org.xmind.core.Core;
import org.xmind.core.ISheet;
import org.xmind.core.ITopic;
import org.xmind.core.IWorkbook;
import org.xmind.core.IWorkbookBuilder;

import com.jstarcraft.core.codec.specification.CodecDefinition;
import com.jstarcraft.core.storage.lucene.LuceneEngine;
import com.jstarcraft.core.storage.lucene.LuceneMetadata;
import com.jstarcraft.core.storage.lucene.converter.LuceneContext;
import com.jstarcraft.core.utility.StringUtility;
import com.jstarcraft.nlp.lucene.hanlp.HanLpIndexAnalyzer;

public class XmindIndexTestCase {

    private void bulidDocument(LuceneEngine engine, LuceneMetadata metadata, ITopic root, ITopic topic) {
        Document document = metadata.encodeDocument(new XmindTopic(root, topic));
        engine.createDocument(topic.getId(), document);
        for (ITopic child : topic.getAllChildren()) {
            bulidDocument(engine, metadata, root, child);
        }
    }

    @Test
    public void testIndex() throws Exception {
        // Lucene引擎
        LuceneContext context = new LuceneContext(CodecDefinition.instanceOf(XmindTopic.class));
        LuceneMetadata codec = new LuceneMetadata(XmindTopic.class, context);
        Path path = Paths.get("./lucene");
        FileUtils.deleteDirectory(path.toFile());

//        Analyzer analyzer = new HanLpIndexAnalyzer("viterbi");
        Analyzer analyzer = new IKAnalyzer();
        try (LuceneEngine engine = new LuceneEngine(() -> {
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            return config;
        }, path)) {
            // XMind构建器
            IWorkbookBuilder builder = Core.getWorkbookBuilder();
            File directory = new File("../../jstarcraft-document");
            Iterator<File> iterator = FileUtils.iterateFiles(directory, new String[] { "xmind" }, true);
            while (iterator.hasNext()) {
                File file = iterator.next();
                System.out.println(StringUtility.format("索引XMind文件名称:[{}]", file.getName()));
                IWorkbook workbook = builder.loadFromFile(file);
                for (ISheet sheet : workbook.getSheets()) {
                    ITopic topic = sheet.getRootTopic();
                    bulidDocument(engine, codec, topic, topic);
//                    System.out.println(topic.getTitleText());
//                    for (IRelationship relationship : sheet.getRelationships()) {
//                        System.out.println("relationship is " + relationship.getTitleText());
//                        System.out.println(relationship.getEnd1Id());
//                        System.out.println(relationship.getEnd2Id());
//                    }
                }
            }
            int count = engine.countDocuments(new MatchAllDocsQuery());
            System.out.println(StringUtility.format("索引XMind主题数量:[{}]", count));
        }
    }

}
