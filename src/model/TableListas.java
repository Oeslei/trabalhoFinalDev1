/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.*;
import javax.swing.table.*;

/**
 *
 * @author oeslei.250995
 */
public class TableListas extends AbstractTableModel {
    
    private final int COL_ORDEM = 0;
    private final int COL_NOME = 1;
    
    private List listas;
    
    public TableListas() {
        listas = new ArrayList();
    }
    
    public TableListas(List listas) {
        this();
        this.setListas(listas);
    }
    
    public void setListas(List listas) {
        listas.addAll(listas);
    }
    
    @Override
    public int getRowCount() {
        return listas.size();
    }
    
    @Override
    public int getColumnCount() {
        return 2;
    }
    
    @Override
    public String getColumnName(int column) {
        switch (column) {
            case COL_ORDEM:
                return "Ordem";
            case COL_NOME:
                return "Nome";
            default:
                return "";
        }
    }
    
    @Override
    public Class getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case COL_ORDEM:
                return Integer.class;
            case COL_NOME:
                return String.class;
            default:
                return String.class;
        }
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Lista lista = (Lista) listas.get(rowIndex);
        
        switch (columnIndex) {
            case COL_ORDEM:
                return lista.getOrdem();
            case COL_NOME:
                return lista.getNome();
            default:
                return "";
        }
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Lista lista = (Lista) listas.get(rowIndex);
        
        switch (columnIndex) {
            case COL_ORDEM:
                lista.setOrdem(Integer.parseInt(aValue.toString()));
            case COL_NOME:
                lista.setNome(aValue.toString());
        }
        
        fireTableDataChanged();
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
    
    public void adicionarLista(Lista lista) {
        listas.add(lista);
        fireTableDataChanged();
    }
    
    public void limpar() {
        listas.clear();
        fireTableDataChanged();
    }
    
    public Lista obter(int linha) {
        return (Lista) listas.get((linha + 1));
    }
    
}
