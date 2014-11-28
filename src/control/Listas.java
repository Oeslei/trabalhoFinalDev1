/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import model.Lista;
import model.ListaDAO;
import model.TableListas;

/**
 *
 * @author oeslei.250995
 */
public class Listas implements ActionListener {
    
    private view.Listas view = new view.Listas();
    
    public Listas() {
        view.setResizable(false);
        view.setLocationRelativeTo(null);
        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        setActions();
        
        TableListas tableModel = new TableListas();
        ListaDAO daoLista = new ListaDAO();
        
        tableModel.setListas(daoLista.obterTodas());
        view.getTableListas().setModel(tableModel);
    }
    
    public void show() {
        view.setVisible(true);
    }
    
    public void adicionarLista() {
        
    }
    
    private void setActions() {
        view.getBtnAdicionarLista().addActionListener(this);
        view.getBtnTarefas().addActionListener(this);
        view.getBtnSair().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getBtnAdicionarLista()) {
            
        } else if (e.getSource() == view.getBtnTarefas()) {
            
        } else if (e.getSource() == view.getBtnSair()) {
            view.dispose();
        }
    }
    
}
