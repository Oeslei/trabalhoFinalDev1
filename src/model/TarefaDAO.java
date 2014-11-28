/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author oeslei.250995
 */
public class TarefaDAO extends DAO {
    
    public int adicionar(Tarefa tarefa) {
        int id = -1;

        try {
            String sql = "INSERT INTO tarefas (idLista, nome, descricao, ativa) VALUES (?, ?, ?, ?)";
            PreparedStatement st = (PreparedStatement) connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            st.setInt(1, tarefa.getLista().getId());
            st.setString(2, tarefa.getNome());
            st.setString(3, tarefa.getDescricao());
            st.setBoolean(4, tarefa.isAtiva());
            st.executeUpdate();
            
            ResultSet rs = st.getGeneratedKeys();
            if (rs.next()) id = rs.getInt(1);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

        return id;
    }
    
    public Tarefa obter(int id) {
        Tarefa tarefa = new Tarefa();

        try {
            String sql = "SELECT * FROM tarefas WHERE id = ? LIMIT 1";
            PreparedStatement st = (PreparedStatement) connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            
            if (rs.next()) {
                tarefa = getTarefaFromResultSet(rs);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

        return tarefa;
    }
    
    public List obterTodas() {
        List tarefas = new ArrayList();

        try {
            String sql = "SELECT * FROM tarefas";
            PreparedStatement st = (PreparedStatement) connection.prepareStatement(sql);
            st.execute();
            
            ResultSet rs = st.getResultSet();
            while (rs.next()) {
                tarefas.add(getTarefaFromResultSet(rs));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

        return tarefas;
    }
    
    private Tarefa getTarefaFromResultSet(ResultSet rs) throws SQLException {
        Tarefa tarefa = new Tarefa();
        ListaDAO daoLista = new ListaDAO();

        tarefa.setId(rs.getInt("id"));

        try {
            tarefa.setLista(daoLista.obter(rs.getInt("idLista")));
        } catch (SQLException e) {
            System.out.println(e);
        }

        tarefa.setNome(rs.getString("nome"));
        tarefa.setDescricao(rs.getString("descricao"));
        tarefa.setAtiva(rs.getBoolean("ativa"));
        
        return tarefa;
    }
    
    public void atualizar(Tarefa tarefa) {
        try {
            String sql = "UPDATE tarefas SET idLista = ?, nome = ?, descricao = ?, ativa = ? WHERE id = ?";
            PreparedStatement st = (PreparedStatement) connection.prepareStatement(sql);
            st.setInt(1, tarefa.getLista().getId());
            st.setString(2, tarefa.getNome());
            st.setString(3, tarefa.getDescricao());
            st.setBoolean(4, tarefa.isAtiva());
            st.setInt(5, tarefa.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void remover(int id) {
        try {
            String sql = "DELETE FROM tarefas WHERE id = ?";
            PreparedStatement st = (PreparedStatement) connection.prepareStatement(sql);
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void remover(Tarefa tarefa) {
        remover(tarefa.getId());
    }
    
}
