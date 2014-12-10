/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author First Place
 */
public class ListaDAOTest {
    
    public ListaDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of remover method, of class ListaDAO.
     */
    @Test
    public void testRemover() {
        System.out.println("remover");
        
        ListaDAO daoLista = new ListaDAO();
        Lista lista = new Lista();
        lista.setNome("testRemover");
        lista.setOrdem(1000);
        lista.setAtiva(true);
        int idLista = daoLista.adicionar(lista);
        
        TarefaDAO daoTarefa = new TarefaDAO();
        Tarefa tarefa = new Tarefa();
        tarefa.setNome("testRemover");
        tarefa.setDescricao("Test of remover method, of class ListaDAO.");
        tarefa.setLista(daoLista.obter(idLista));
        tarefa.setAtiva(true);
        int idTarefa = daoTarefa.adicionar(tarefa);
        
        daoLista.remover(idLista);
        
        if (idTarefa == daoTarefa.obter(idTarefa).getId()) {
            fail("A tarefa relacionada a listagem não foi removida, gerando inconsistência no banco de dados!");
        }
    }

    /**
     * Test of atualizarOrdem method, of class ListaDAO.
     */
    @Test
    public void testAtualizarOrdem() {
        System.out.println("atualizarOrdem");

        ListaDAO daoLista = new ListaDAO();

        Lista lista1 = new Lista();
        lista1.setNome("Lista 1");
        lista1.setOrdem(1);
        lista1.setAtiva(true);
        int idLista1 = daoLista.adicionar(lista1);
        
        Lista lista2 = new Lista();
        lista2.setNome("Lista 2");
        lista2.setOrdem(2);
        lista2.setAtiva(true);
        int idLista2 = daoLista.adicionar(lista2);
        
        Lista lista3 = daoLista.obter(idLista2);
        lista3.setOrdem(1);
        daoLista.atualizar(lista3);
        
        assertEquals(daoLista.obter(idLista1).getOrdem(), 2);
        assertEquals(daoLista.obter(idLista2).getOrdem(), 1);
        
        Lista lista4 = daoLista.obter(idLista2);
        lista4.setOrdem(2);
        daoLista.atualizar(lista4);
        
        assertEquals(daoLista.obter(idLista1).getOrdem(), 1);
        assertEquals(daoLista.obter(idLista2).getOrdem(), 2);
        
        daoLista.remover(idLista1);
        daoLista.remover(idLista2);
    }
    
}
