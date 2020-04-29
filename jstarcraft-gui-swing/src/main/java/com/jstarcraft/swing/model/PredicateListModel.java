package com.jstarcraft.swing.model;

import java.util.ArrayList;
import java.util.function.Predicate;

import javax.swing.AbstractListModel;

/**
 * 断言列表模型
 * 
 * @author Birdy
 *
 * @param <T>
 */
// 参考FilterListItems
public class PredicateListModel<T> extends AbstractListModel<T> {

    /** 断言 */
    private Predicate<T> predicate;

    /** 待匹配列表 */
    private ArrayList<T> unmatchedElements;

    /** 已匹配列表 */
    private ArrayList<T> matchedElements;

    public PredicateListModel(Predicate<T> predicate) {
        super();
        this.predicate = predicate;
        this.unmatchedElements = new ArrayList<>();
        this.matchedElements = new ArrayList<>();
    }

    @Override
    public T getElementAt(int index) {
        if (index < matchedElements.size()) {
            return matchedElements.get(index);
        } else {
            return null;
        }
    }

    @Override
    public int getSize() {
        return matchedElements.size();
    }

    public void setPredicate(Predicate<T> predicate) {
        matchedElements.clear();
        for (T element : unmatchedElements) {
            if (predicate.test(element)) {
                matchedElements.add(element);
            }
        }
        fireContentsChanged(this, 0, getSize());
    }

    public boolean attachElement(T element) {
        if (unmatchedElements.add(element)) {
            if (predicate.test(element)) {
                matchedElements.add(element);
                fireContentsChanged(this, 0, getSize());
            }
            return true;
        }
        return false;
    }

    public boolean detachElement(T element) {
        if (unmatchedElements.remove(element)) {
            if (predicate.test(element)) {
                matchedElements.remove(element);
                fireContentsChanged(this, 0, getSize());
            }
            return true;
        }
        return false;
    }

}
