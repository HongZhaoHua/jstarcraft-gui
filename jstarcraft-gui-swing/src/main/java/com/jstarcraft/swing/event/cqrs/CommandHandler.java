package com.jstarcraft.swing.event.cqrs;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.table.TableModel;
import javax.swing.tree.TreeModel;

/**
 * 增删改处理器
 * 
 * @author Birdy
 *
 * @param <T>
 */
public class CommandHandler<T> implements ListDataListener, TableModelListener, TreeModelListener {

    @Override
    public void intervalAdded(ListDataEvent event) {
        ListModel model = (ListModel) event.getSource();
        for (int index = event.getIndex0(), size = event.getIndex1(); index <= size; index++) {
            model.getElementAt(index);
        }
    }

    @Override
    public void intervalRemoved(ListDataEvent event) {
        ListModel model = (ListModel) event.getSource();
        for (int index = event.getIndex0(), size = event.getIndex1(); index <= size; index++) {
            model.getElementAt(index);
        }
    }

    @Override
    public void contentsChanged(ListDataEvent event) {
        ListModel model = (ListModel) event.getSource();
        for (int index = event.getIndex0(), size = event.getIndex1(); index <= size; index++) {
            model.getElementAt(index);
        }
    }

    @Override
    public void tableChanged(TableModelEvent event) {
        TableModel model = (TableModel) event.getSource();
        switch (event.getType()) {
        case TableModelEvent.DELETE: {
            if (event.getFirstRow() == TableModelEvent.HEADER_ROW) {
                return;
            }
            int columnSize = model.getColumnCount();
            for (int rowIndex = event.getFirstRow(), rowSize = event.getLastRow(); rowIndex <= rowSize; rowIndex++) {
                for (int columnIndex = 0; columnIndex < columnSize; columnIndex++) {

                }
            }
            break;
        }
        case TableModelEvent.INSERT: {
            if (event.getFirstRow() == TableModelEvent.HEADER_ROW) {
                return;
            }
            int columnSize = model.getColumnCount();
            for (int rowIndex = event.getFirstRow(), rowSize = event.getLastRow(); rowIndex <= rowSize; rowIndex++) {
                for (int columnIndex = 0; columnIndex < columnSize; columnIndex++) {

                }
            }
            break;
        }
        case TableModelEvent.UPDATE: {
            if (event.getFirstRow() == TableModelEvent.HEADER_ROW) {
                return;
            }
            int columnSize = model.getColumnCount();
            for (int rowIndex = event.getFirstRow(), rowSize = event.getLastRow(); rowIndex <= rowSize; rowIndex++) {
                for (int columnIndex = 0; columnIndex < columnSize; columnIndex++) {

                }
            }
            break;
        }
        }
    }

    @Override
    public void treeNodesChanged(TreeModelEvent event) {
        TreeModel model = (TreeModel) event.getSource();
    }

    @Override
    public void treeNodesInserted(TreeModelEvent event) {
        TreeModel model = (TreeModel) event.getSource();
    }

    @Override
    public void treeNodesRemoved(TreeModelEvent event) {
        TreeModel model = (TreeModel) event.getSource();
    }

    @Override
    public void treeStructureChanged(TreeModelEvent event) {
        TreeModel model = (TreeModel) event.getSource();
    }

}
