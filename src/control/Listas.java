/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
public class Listas implements ActionListener, MouseListener {
    
    private view.Listas view = new view.Listas();

    private EdicaoLista incluirLista;
    private EdicaoLista editarLista;
    private VisualizacaoLista viewLista;
    
    private ListaDAO daoLista = new ListaDAO();
    private TableListas tableModel = new TableListas();
    
    public Listas() {
        view.setResizable(false);
        view.setLocationRelativeTo(null);
        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        setActions();
        
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
    
    private void setActionsVisualizar() {
        viewLista.getBtnEditar().addActionListener(this);
        viewLista.getBtnExcluir().addActionListener(this);
    }
    
    private void setActionsEditar() {
        editarLista.getBtnSalvar().addActionListener(this);
        editarLista.getBtnCancelar().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == view.getBtnAdicionarLista()) {
            adicionarLista();
        } else if (source == view.getBtnTarefas()) {
            view.hide();
            Tarefas app = new Tarefas();
            app.show();
            view.dispose();
        } else if (source == view.getBtnSair()) {
            view.dispose();
        }
        
        if (incluirLista != null) {
            if (source == incluirLista.getBtnSalvar()) {
                salvarNovaLista();
            } else if (source == incluirLista.getBtnCancelar()) {
                incluirLista.getJDialog().dispose();
            }
        }
        
        if (viewLista != null) {
            if (source == viewLista.getBtnEditar()) {
                viewLista.getJDialog().dispose();
                editarLista(viewLista.getId());
            } else if (source == viewLista.getBtnExcluir()) {
                excluirLista();
            }
        }
        
        if (editarLista != null) {
            if (source == editarLista.getBtnSalvar()) {
                salvarLista();
            } else if (source == editarLista.getBtnCancelar()) {
                editarLista.getJDialog().dispose();
            }
        }
    }
    
    private void excluirLista() {
        int confirm = JOptionPane.showConfirmDialog(view, "Deseja mesmo excluir esta lista?\nTodas as tarefas relacionadas a esta listagem também serão removidas.");
        if (confirm == JOptionPane.OK_OPTION) {
            daoLista.remover(viewLista.getId());
            tableModel.limpar();
            tableModel.setListas(daoLista.obterTodas());
            viewLista.getJDialog().dispose();
        }
    }
    
    private void editarLista(int id) {
        Lista lista = daoLista.obter(id);

        editarLista = new EdicaoLista(view);
        JDialog viewEditar = editarLista.getJDialog();
        setActionsEditar();

        editarLista.setSelectOrdem(daoLista.totalListas());
        editarLista.setOrdem(lista.getOrdem());
        editarLista.setNome(lista.getNome());
        editarLista.setId(lista.getId());
        
        viewEditar.setTitle("Editar lista");
        viewEditar.setVisible(true);
    }
    
    private void salvarLista() {
        Lista lista = new Lista();
        lista.setId(editarLista.getId());
        lista.setAtiva(true);
        lista.setNome(editarLista.getNome());
        lista.setOrdem(editarLista.getOrdem());
        
        if (lista.getNome().isEmpty()) {
            editarLista.setDescErro("Preencha o campo nome!");
        } else {
            daoLista.atualizar(lista);
            tableModel.limpar();
            tableModel.setListas(daoLista.obterTodas());
            editarLista.getJDialog().dispose();
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
        setActionsVisualizar();
        
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
