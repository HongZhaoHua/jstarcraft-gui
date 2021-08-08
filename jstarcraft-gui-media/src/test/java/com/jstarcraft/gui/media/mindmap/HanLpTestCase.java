package com.jstarcraft.gui.media.mindmap;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.xmind.core.Core;
import org.xmind.core.ISheet;
import org.xmind.core.ITopic;
import org.xmind.core.IWorkbook;
import org.xmind.core.IWorkbookBuilder;

import com.jstarcraft.core.codec.specification.CodecDefinition;
import com.jstarcraft.core.storage.lucene.LuceneMetadata;
import com.jstarcraft.core.storage.lucene.converter.LuceneContext;
import com.jstarcraft.nlp.lucene.hanlp.HanLpIndexAnalyzer;
import com.jstarcraft.nlp.lucene.hanlp.HanLpQueryAnalyzer;

public class HanLpTestCase {

    private void bulidDocument(IndexWriter writer, LuceneMetadata metadata, ITopic root, ITopic topic) throws Exception {
        Document document = metadata.encodeDocument(new XmindTopic(root, topic));
        writer.addDocument(document);
        for (ITopic child : topic.getAllChildren()) {
            bulidDocument(writer, metadata, root, child);
        }
    }

    @Test
    public void testIndexAndSearch() throws Exception {
        LuceneContext context = new LuceneContext(CodecDefinition.instanceOf(XmindTopic.class));
        LuceneMetadata codec = new LuceneMetadata(XmindTopic.class, context);
        Path path = Paths.get("./lucene");
        FileUtils.deleteDirectory(path.toFile());

        {
            Analyzer analyzer = new HanLpIndexAnalyzer("viterbi");////////////////////////////////////////////////////
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            IndexWriter writer = new IndexWriter(FSDirectory.open(path), config);
            // XMind构建器
            IWorkbookBuilder builder = Core.getWorkbookBuilder();
            // 遍历文件
            File directory = new File("../../jstarcraft-document/knowledge");
            Iterator<File> iterator = FileUtils.iterateFiles(directory, new String[] { "xmind" }, true);
            while (iterator.hasNext()) {
                File file = iterator.next();
                System.out.println(file.getName());
                IWorkbook workbook = builder.loadFromFile(file);
                for (ISheet sheet : workbook.getSheets()) {
                    ITopic topic = sheet.getRootTopic();
                    bulidDocument(writer, codec, topic, topic);
                }
            }
            writer.commit();
            writer.close();
        }

        {
            Analyzer analyzer = new HanLpQueryAnalyzer("viterbi");
            IndexReader reader = DirectoryReader.open(FSDirectory.open(path));
            IndexSearcher isearcher = new IndexSearcher(reader);
            QueryParser parser = new MultiFieldQueryParser(new String[] { "title", "note" }, analyzer);
            Query query = parser.parse("解答");
            System.out.println(query);
//            StandardQueryParser queryParser = new StandardQueryParser(new HanLpQueryAnalyzer("viterbi")); // 在title与content中查询
//            Query query = queryParser.parse("机器", XmindTopic.TITLE);
            ScoreDoc[] hits = isearcher.search(query, 300000).scoreDocs;
            for (ScoreDoc scoreDoc : hits) {
                Document targetDoc = isearcher.doc(scoreDoc.doc);
                System.out.println(targetDoc.getField("title").stringValue());
            }
        }
    }

}
