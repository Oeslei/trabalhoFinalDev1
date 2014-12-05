/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import model.Lista;
import model.ListaDAO;
import model.TableListas;
import view.EdicaoLista;
import view.VisualizacaoLista;

/**
 *
 * @author oeslei.250995
 */
public class Listas implements ActionListener {
    
    private view.Listas view = new view.Listas();

    private EdicaoLista incluirLista;
    private EdicaoLista editarLista;
    private EdicaoLista viewLista;
    
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
    
}
