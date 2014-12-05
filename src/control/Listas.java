/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.MouseInputListener;
import model.Lista;
import model.ListaDAO;
import model.TableListas;
import view.EdicaoLista;
import view.VisualizacaoLista;

/**
 *
 * @author oeslei.250995
 */
public class Listas implements ActionListener, MouseListener {
    
    private view.Listas view = new view.Listas();

    private EdicaoLista incluirLista;
    private EdicaoLista editarLista;
    private VisualizacaoLista viewLista;
    
    private ListaDAO daoLista = new ListaDAO();
    private TableListas tableModel;
    
    public Listas() {
        view.setResizable(false);
        view.setLocationRelativeTo(null);
        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        setActions();
        
        tableModel = new TableListas();
        daoLista = new ListaDAO();
        
        tableModel.setListas(daoLista.obterTodas());
        view.getTableListas().setModel(tableModel);
        view.getTableListas().getColumnModel().getColumn(0).setMaxWidth(180);
        
        view.getTableListas().addMouseListener(this);
    }
    
    public void show() {
        view.setVisible(true);
    }
    
    public void adicionarLista() {
        incluirLista = new EdicaoLista(view);
        JDialog viewIncluir = incluirLista.getJDialog();
        setActionsIncluir();
        
        int ordem = daoLista.totalListas() + 1;
        incluirLista.setSelectOrdem(ordem);
        incluirLista.setOrdem(ordem);
        
        viewIncluir.setTitle("Adicionar lista");
        viewIncluir.setVisible(true);
    }
    
    private void setActions() {
        view.getBtnAdicionarLista().addActionListener(this);
        view.getBtnTarefas().addActionListener(this);
        view.getBtnSair().addActionListener(this);
    }
    
    private void setActionsIncluir() {
        incluirLista.getBtnSalvar().addActionListener(this);
        incluirLista.getBtnCancelar().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getBtnAdicionarLista()) {
            adicionarLista();
        } else if (e.getSource() == view.getBtnTarefas()) {
            
        } else if (e.getSource() == view.getBtnSair()) {
            view.dispose();
        } else if (e.getSource() == incluirLista.getBtnSalvar()) {
            salvarNovaLista();
        } else if (e.getSource() == incluirLista.getBtnCancelar()) {
            incluirLista.getJDialog().dispose();
        }
    }
    
    private void salvarNovaLista() {
        Lista lista = new Lista();
        lista.setId(0);
        lista.setAtiva(true);
        lista.setOrdem(incluirLista.getOrdem());
        lista.setNome(incluirLista.getNome());
        
        if (lista.getNome().isEmpty()) {
            incluirLista.setDescErro("Preencha o campo nome!");
        } else {
            daoLista.adicionar(lista);
            tableModel.limpar();
            tableModel.setListas(daoLista.obterTodas());
            incluirLista.getJDialog().dispose();
        }
    }
    
    private void visualizarLista(Lista lista) {
        viewLista = new VisualizacaoLista(view);
        JDialog view = viewLista.getJDialog();
        // setActionsVisualizar();
        
        viewLista.setNome(lista.getNome());
        viewLista.setId(lista.getId());
        
        view.setTitle("Lista");
        view.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (view.getTableListas().getSelectedRowCount() == 1) {
            visualizarLista(tableModel.obter(view.getTableListas().getSelectedRow()));
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
    
}
