package br.com.merlo;

import br.com.merlo.dao.ClienteDAO;
import br.com.merlo.dao.IClienteDAO;
import br.com.merlo.domain.Cliente;
import com.sun.source.tree.AssertTree;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class ClientTest {

    private IClienteDAO clienteDAO;

    @Test
    public void cadastrarTest () throws Exception{
        clienteDAO = new ClienteDAO();

        Cliente cliente = new Cliente();
        cliente.setCodigo("10");
        cliente.setNome("Matheus");
        Integer countCad = clienteDAO.cadastar(cliente);
        assertTrue(countCad==1);

        Cliente clienteBD = clienteDAO.buscar("10","TB_CLIENTE");
        assertNotNull(clienteBD);
        assertEquals(cliente.getCodigo(),clienteBD.getCodigo());
        assertEquals(cliente.getNome(),clienteBD.getNome());

        Integer countDel = clienteDAO.excluir(clienteBD);
        assertTrue(countDel==1);
    }

    @Test
    public void buscarTest () throws Exception {
        clienteDAO = new ClienteDAO();

        Cliente cliente = new Cliente();
        cliente.setCodigo("10");
        cliente.setNome("Matheus");
        Integer countCad = clienteDAO.cadastar(cliente);
        assertTrue(countCad == 1);

        Cliente clienteBD = clienteDAO.buscar("10","TB_CLIENTE");
        assertNotNull(clienteBD);
        assertEquals(cliente.getCodigo(),clienteBD.getCodigo());
        assertEquals(cliente.getNome(),clienteBD.getNome());

        Integer countDel = clienteDAO.excluir(clienteBD);
        assertTrue(countDel==1);
    }

    @Test
    public void excluirTest () throws Exception{
        clienteDAO = new ClienteDAO();

        Cliente cliente = new Cliente();
        cliente.setCodigo("10");
        cliente.setNome("Matheus");
        Integer countCad = clienteDAO.cadastar(cliente);
        assertTrue(countCad == 1);

        Cliente clienteBD = clienteDAO.buscar("10","TB_CLIENTE");
        assertNotNull(clienteBD);
        assertEquals(cliente.getCodigo(),clienteBD.getCodigo());
        assertEquals(cliente.getNome(),clienteBD.getNome());

        Integer countDel = clienteDAO.excluir(clienteBD);
        assertTrue(countDel == 1);
    }

    @Test
    public void buscarTodosTest () throws Exception{
        clienteDAO = new ClienteDAO();

        Cliente cliente = new Cliente();
        cliente.setCodigo("10");
        cliente.setNome("merlo");
        Integer countCad = clienteDAO.cadastar(cliente);
        assertTrue(countCad==1);

        Cliente cliente1 = new Cliente();
        cliente1.setCodigo("20");
        cliente1.setNome("matheus");
        Integer countCad2 = clienteDAO.cadastar(cliente1);
        assertTrue(countCad2==1);

        List<Cliente> list = clienteDAO.buscarTodos("TB_CLIENTE");
        assertNotNull(list);
        assertEquals(2,list.size());

        int countDel = 0;
        for (Cliente cli : list){
            clienteDAO.excluir(cli);
            countDel++;
        }
        assertEquals(list.size(),countDel);

        list = clienteDAO.buscarTodos("TB_CLIENTE");
        assertEquals(list.size(),0);
    }

    @Test
    public void atualizarTest () throws Exception {
        clienteDAO = new ClienteDAO();

        Cliente cliente = new Cliente();
        cliente.setCodigo("10");
        cliente.setNome("Matheus");
        Integer countCad = clienteDAO.cadastar(cliente);
        assertTrue(countCad == 1);

        Cliente clienteBD = clienteDAO.buscar("10","TB_CLIENTE");
        assertNotNull(clienteBD);
        assertEquals(cliente.getNome(),clienteBD.getNome());
        assertEquals(cliente.getCodigo(),clienteBD.getCodigo());

        clienteBD.setCodigo("20");
        clienteBD.setNome("merlo");
        Integer countUpdate = clienteDAO.atualizar(clienteBD);
        assertTrue(countUpdate == 1);

        Cliente clienteBD1 = clienteDAO.buscar("10","TB_CLIENTE");
        assertNull(clienteBD1);

        Cliente clienteBD2 = clienteDAO.buscar("20","TB_CLIENTE");
        assertNotNull(clienteBD2);
        assertEquals(clienteBD.getId(),clienteBD2.getId());
        assertEquals(clienteBD.getNome(),clienteBD2.getNome());
        assertEquals(clienteBD.getCodigo(),clienteBD2.getCodigo());

        List<Cliente> list = clienteDAO.buscarTodos("TB_CLIENTE");
        for (Cliente cli : list){
            clienteDAO.excluir(cli);
        }
    }
}
