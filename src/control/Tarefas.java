/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JFrame;
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
        
        view.setPainelListas((java.util.List) daoLista.obterTodas());
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
    }
    
    private void adicionarTarefa() {
        incluirTarefa = new EdicaoTarefa(view);
        JDialog viewIncluir = incluirTarefa.getJDialog();
        setActionsIncluir();
        
        incluirTarefa.setListas(daoLista.obterTodas());
        
        viewIncluir.setTitle("Adicionar tarefa");
        viewIncluir.setVisible(true);
    }
    
    private void adicionarTarefa(int idLista) {
        
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
            view.setPainelListas(daoLista.obterTodas());
        }
    }
    
}
