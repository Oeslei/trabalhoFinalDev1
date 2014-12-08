/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import model.ListaDAO;
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

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        
        if (source == view.getBtnAdicionarTarefa()) {
            
        } else if (source == view.getBtnGerenciarListas()) {
            view.hide();
            Listas app = new Listas();
            app.show();
            view.dispose();
        } else if (source == view.getBtnSair()) {
            view.dispose();
        }
    }
    
}
