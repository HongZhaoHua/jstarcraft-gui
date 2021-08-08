package com.jstarcraft.gui.media.mindmap;

import java.util.List;

import org.xmind.core.INotes;
import org.xmind.core.IPlainNotesContent;
import org.xmind.core.ITopic;

import com.jstarcraft.core.storage.lucene.annotation.LuceneConfiguration;
import com.jstarcraft.core.storage.lucene.annotation.LuceneIndex;
import com.jstarcraft.core.storage.lucene.annotation.LuceneSort;
import com.jstarcraft.core.storage.lucene.annotation.LuceneStore;

/**
 * XMind主题
 * 
 * @author Birdy
 *
 */
@LuceneConfiguration(id = "id")
public class XmindTopic {

    public static final String ID = "id";

    public static final String TITLE = "title";

    public static final String NOTE = "note";

    /** 标识 */
    @LuceneIndex
    @LuceneSort
    @LuceneStore
    private String id;

    /** 标题 */
    @LuceneIndex(analyze = true)
    @LuceneStore
    private String title;

    /** 根 */
    @LuceneStore
    private String root;

    /** 类型 */
    @LuceneStore
    private String type;

    /** 注释 */
    @LuceneIndex(analyze = true)
    @LuceneStore
    private String note;

    /** 路径 */
    @LuceneIndex
    @LuceneStore
    private String[] path;

    protected XmindTopic() {
    }

    public XmindTopic(ITopic root, ITopic topic) {
        this.root = root.getTitleText();
        this.id = topic.getId();
        this.title = topic.getTitleText();
        this.type = topic.getType();
        INotes note = topic.getNotes();
        if (note != null) {
            IPlainNotesContent plain = (IPlainNotesContent) note.getContent("plain");
            if (plain != null) {
                this.note = plain.getTextContent();
            }
        }
        List<ITopic> path = topic.getPath().toTopicList();
        int size = path.size();
        this.path = new String[size];
        for (int index = 0; index < size; index++) {
            this.path[index] = path.get(index).getId();
        }
    }

    public String getRoot() {
        return root;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getNote() {
        return note;
    }

    public String[] getPath() {
        return path;
    }

}
