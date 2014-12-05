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
public class ListaDAO extends DAO {
    
    public int adicionar(Lista lista) {
        int id = -1;

        try {
            String sql = "INSERT INTO listas (ordem, nome, ativa) VALUES (?, ?, ?)";
            PreparedStatement st = (PreparedStatement) connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            st.setInt(1, lista.getOrdem());
            st.setString(2, lista.getNome());
            st.setBoolean(3, lista.isAtiva());
            st.executeUpdate();
            
            ResultSet rs = st.getGeneratedKeys();
            if (rs.next()) id = rs.getInt(1);
            
            atualizarOrdem(lista.getOrdem(), totalListas(), true, id);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

        return id;
    }
    
    public Lista obter(int id) {
        Lista lista = new Lista();

        try {
            String sql = "SELECT * FROM listas WHERE id = ? LIMIT 1";
            PreparedStatement st = (PreparedStatement) connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            
            if (rs.next()) {
                lista = getListaFromResultSet(rs);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

        return lista;
    }
    
    public List obterTodas() {
        List listas = new ArrayList();

        try {
            String sql = "SELECT * FROM listas ORDER BY ordem";
            PreparedStatement st = (PreparedStatement) connection.prepareStatement(sql);
            st.execute();
            
            ResultSet rs = st.getResultSet();
            while (rs.next()) {
                listas.add(getListaFromResultSet(rs));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

        return listas;
    }
    
    public int totalListas() {
        int result = 0;

        try {
            String sql = "SELECT COUNT(id) as total FROM listas";
            PreparedStatement st = (PreparedStatement) connection.prepareStatement(sql);
            st.execute();
            
            ResultSet rs = st.getResultSet();
            if (rs.next()) {
                result = rs.getInt("total");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

        return result;
    }
    
    private Lista getListaFromResultSet(ResultSet rs) throws SQLException {
        Lista lista = new Lista();
        
        lista.setId(rs.getInt("id"));
        lista.setOrdem(rs.getInt("ordem"));
        lista.setNome(rs.getString("nome"));
        lista.setAtiva(rs.getBoolean("ativa"));
        
        return lista;
    }
    
    public void atualizar(Lista lista) {
        try {
            String sql = "UPDATE listas SET ordem = ?, nome = ?, ativa = ? WHERE id = ?";
            PreparedStatement st = (PreparedStatement) connection.prepareStatement(sql);
            st.setInt(1, lista.getOrdem());
            st.setString(2, lista.getNome());
            st.setBoolean(3, lista.isAtiva());
            st.setInt(4, lista.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void remover(int id) {
        try {
            String sql = "DELETE FROM listas WHERE id = ?";
            PreparedStatement st = (PreparedStatement) connection.prepareStatement(sql);
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void remover(Lista lista) {
        remover(lista.getId());
    }
    
    public void atualizarOrdem(int de, int ate, boolean adicionar, int idImune) {
        try {
            String sql;
            if (adicionar) {
                sql = "UPDATE listas SET ordem = (ordem + 1) WHERE id <> ? AND ordem >= ? AND ordem <= ?";
            } else {
                sql = "UPDATE listas SET ordem = (ordem - 1) WHERE id <> ? AND ordem >= ? AND ordem <= ?";
            }
            PreparedStatement st = (PreparedStatement) connection.prepareStatement(sql);
            st.setInt(1, idImune);
            st.setInt(2, de);
            st.setInt(3, ate);
            st.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
}
