/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import model.ListaDAO;
import model.Tarefa;
import model.TarefaDAO;
import view.EdicaoTarefa;
import view.VisualizacaoTarefa;

/**
 *
 * @author First Place
 */
public class Tarefas implements ActionListener {

    private view.Tarefas view = new view.Tarefas();

    private EdicaoTarefa incluirTarefa;
    private EdicaoTarefa editarTarefa;
    private VisualizacaoTarefa viewTarefa;

    private TarefaDAO daoTarefa = new TarefaDAO();
    private ListaDAO daoLista = new ListaDAO();
    
    public Tarefas() {
        view.setResizable(false);
        view.setLocationRelativeTo(null);
        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        setActions();
        
        view.setPainelListas((java.util.List) daoLista.obterTodas(), this);
    }
    
    public void show() {
        view.setVisible(true);
    }
    
    private void setActions() {
        view.getBtnAdicionarTarefa().addActionListener(this);
        view.getBtnGerenciarListas().addActionListener(this);
        view.getBtnSair().addActionListener(this);
    }
    
    private void setActionsIncluir() {
        incluirTarefa.getBtnSalvar().addActionListener(this);
        incluirTarefa.getBtnCancelar().addActionListener(this);
    }
    
    private void setActionsVizualizar() {
        viewTarefa.getBtnMover().addActionListener(this);
        viewTarefa.getBtnEditar().addActionListener(this);
        viewTarefa.getBtnExcluir().addActionListener(this);
    }
    
    private void setActionsEditar() {
        editarTarefa.getBtnSalvar().addActionListener(this);
        editarTarefa.getBtnCancelar().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        
        if (source == view.getBtnAdicionarTarefa()) {
            adicionarTarefa();
        } else if (source == view.getBtnGerenciarListas()) {
            view.hide();
            Listas app = new Listas();
            app.show();
            view.dispose();
        } else if (source == view.getBtnSair()) {
            view.dispose();
        }
        
        if (incluirTarefa != null) {
            if (source == incluirTarefa.getBtnSalvar()) {
                salvarNovaTarefa();
            } else if (source == incluirTarefa.getBtnCancelar()) {
                incluirTarefa.getJDialog().dispose();
            }
        }
        
        if (viewTarefa != null) {
            if (source == viewTarefa.getBtnMover()) {
                moverTarefa();
            } else if (source == viewTarefa.getBtnEditar()) {
                editarTarefa();
            } else if (source == viewTarefa.getBtnExcluir()) {
                excluirTarefa();
            }
        }
        
        if (editarTarefa != null) {
            if (source == editarTarefa.getBtnSalvar()) {
                salvarTarefa();
            } else if (source == editarTarefa.getBtnCancelar()) {
                editarTarefa.getJDialog().dispose();
            }
        }
    }
    
    private void salvarTarefa() {
        Tarefa tarefa = new Tarefa();
        tarefa.setId(editarTarefa.getId());
        tarefa.setNome(editarTarefa.getNome());
        tarefa.setDescricao(editarTarefa.getDescricao());
        tarefa.setLista(editarTarefa.getLista());
        
        if (tarefa.getNome().isEmpty()) {
            editarTarefa.setDescErro("Preencha o campo nome!");
        } else {
            daoTarefa.atualizar(tarefa);
            view.setPainelListas(daoLista.obterTodas(), this);
            editarTarefa.getJDialog().dispose();
        }
    }
    
    private void moverTarefa() {
        Tarefa tarefa = daoTarefa.obter(viewTarefa.getId());
        tarefa.setLista(viewTarefa.getListaSelecionada());
        daoTarefa.atualizar(tarefa);
        view.setPainelListas(daoLista.obterTodas(), this);
        viewTarefa.getJDialog().dispose();
    }
    
    public void editarTarefa() {
        editarTarefa = new EdicaoTarefa(view);
        JDialog viewEditar = editarTarefa.getJDialog();
        setActionsEditar();
        
        Tarefa tarefa = daoTarefa.obter(viewTarefa.getId());
        editarTarefa.setId(tarefa.getId());
        editarTarefa.setNome(tarefa.getNome());
        editarTarefa.setDescricao(tarefa.getDescricao());
        editarTarefa.setListas(daoLista.obterTodas(), tarefa.getLista().getId());
        
        viewTarefa.getJDialog().dispose();
        
        viewEditar.setTitle("Editar tarefa");
        viewEditar.setVisible(true);
    }
    
    private void excluirTarefa() {
        int confirm = JOptionPane.showConfirmDialog(view, "Deseja mesmo excluir esta tarefa?");
        if (confirm == JOptionPane.OK_OPTION) {
            daoTarefa.remover(viewTarefa.getId());
            view.setPainelListas(daoLista.obterTodas(), this);
            viewTarefa.getJDialog().dispose();
        }
    }
    
    private void adicionarTarefa() {
        incluirTarefa = new EdicaoTarefa(view);
        JDialog viewIncluir = incluirTarefa.getJDialog();
        setActionsIncluir();
        
        incluirTarefa.setListas(daoLista.obterTodas());
        
        viewIncluir.setTitle("Adicionar tarefa");
        viewIncluir.setVisible(true);
    }
    
    public void adicionarTarefa(int idLista) {
        incluirTarefa = new EdicaoTarefa(view);
        JDialog viewIncluir = incluirTarefa.getJDialog();
        setActionsIncluir();
        
        incluirTarefa.setListas(daoLista.obterTodas(), idLista);
        
        viewIncluir.setTitle("Adicionar tarefa");
        viewIncluir.setVisible(true);
    }
    
    public void exibirTarefa(int id) {
        viewTarefa = new VisualizacaoTarefa(view);
        JDialog viewVisualizacao = viewTarefa.getJDialog();
        setActionsVizualizar();
        
        Tarefa tarefa = daoTarefa.obter(id);
        viewTarefa.setId(tarefa.getId());
        viewTarefa.setNome(tarefa.getNome());
        viewTarefa.setDescricao(tarefa.getDescricao());
        viewTarefa.setListas(daoLista.obterTodas(), tarefa.getLista().getId());
        
        viewVisualizacao.setTitle("Tarefa");
        viewVisualizacao.setVisible(true);
    }
    
    private void salvarNovaTarefa() {
        Tarefa tarefa = new Tarefa();
        tarefa.setId(0);
        tarefa.setAtiva(true);
        tarefa.setNome(incluirTarefa.getNome());
        tarefa.setDescricao(incluirTarefa.getDescricao());
        tarefa.setLista(incluirTarefa.getLista());
        
        if (tarefa.getNome().isEmpty()) {
            incluirTarefa.setDescErro("Preencha o campo nome!");
        } else {
            daoTarefa.adicionar(tarefa);
            incluirTarefa.getJDialog().dispose();
            view.setPainelListas(daoLista.obterTodas(), this);
        }
    }
    
}
