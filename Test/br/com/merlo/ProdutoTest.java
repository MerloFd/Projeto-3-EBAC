package br.com.merlo;

import br.com.merlo.dao.ClienteDAO;
import br.com.merlo.dao.IClienteDAO;
import br.com.merlo.dao.IProdutoDAO;
import br.com.merlo.dao.ProdutoDAO;
import br.com.merlo.dao.generic.GenericDAO;
import br.com.merlo.dao.generic.IGenericDAO;
import br.com.merlo.domain.Cliente;
import br.com.merlo.domain.Produto;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ProdutoTest {

    private IProdutoDAO produtoDAO;
    @Test
    public void cadastrarTest () throws Exception{
        produtoDAO = new ProdutoDAO();

        Produto produto = new Produto();
        produto.setCodigo("10");
        produto.setNome("Matheus");
        produto.setValor(800d);
        produto.setFornecedor("Marcos");
        Integer countCad = produtoDAO.cadastar(produto);
        assertTrue(countCad==1);

        Produto produtoBD = produtoDAO.buscar("10","TB_PRODUTO");
        assertNotNull(produtoBD);
        assertEquals(produto.getCodigo(),produtoBD.getCodigo());
        assertEquals(produto.getNome(),produtoBD.getNome());
        assertEquals(produto.getValor(),produtoBD.getValor());
        assertEquals(produto.getFornecedor(),produtoBD.getFornecedor());

        Integer countDel = produtoDAO.excluir(produtoBD);
        assertTrue(countDel==1);
    }

    @Test
    public void buscarTest () throws Exception {
        produtoDAO = new ProdutoDAO();

        Produto produto = new Produto();
        produto.setCodigo("10");
        produto.setNome("Matheus");
        produto.setValor(800d);
        produto.setFornecedor("Marcos");
        Integer countCad = produtoDAO.cadastar(produto);
        assertTrue(countCad==1);

        Produto produtoBD = produtoDAO.buscar("10","TB_PRODUTO");
        assertNotNull(produtoBD);
        assertEquals(produto.getCodigo(),produtoBD.getCodigo());
        assertEquals(produto.getNome(),produtoBD.getNome());
        assertEquals(produto.getValor(),produtoBD.getValor());
        assertEquals(produto.getFornecedor(),produtoBD.getFornecedor());

        Integer countDel = produtoDAO.excluir(produtoBD);
        assertTrue(countDel==1);
    }

    @Test
    public void excluirTest () throws Exception {
        produtoDAO = new ProdutoDAO();

        Produto produto = new Produto();
        produto.setCodigo("10");
        produto.setNome("Matheus");
        produto.setValor(800d);
        produto.setFornecedor("Marcos");
        Integer countCad = produtoDAO.cadastar(produto);
        assertTrue(countCad==1);

        Produto produtoBD = produtoDAO.buscar("10","TB_PRODUTO");
        assertNotNull(produtoBD);
        assertEquals(produto.getCodigo(),produtoBD.getCodigo());
        assertEquals(produto.getNome(),produtoBD.getNome());
        assertEquals(produto.getValor(),produtoBD.getValor());
        assertEquals(produto.getFornecedor(),produtoBD.getFornecedor());

        Integer countDel = produtoDAO.excluir(produtoBD);
        assertTrue(countDel==1);
    }

    @Test
    public void buscarTodosTest () throws Exception{
        produtoDAO = new ProdutoDAO();

        Produto produto = new Produto();
        produto.setCodigo("10");
        produto.setNome("Matheus");
        produto.setValor(800d);
        produto.setFornecedor("Marcos");
        Integer countCad = produtoDAO.cadastar(produto);
        assertTrue(countCad==1);

        Produto produto1 = new Produto();
        produto.setCodigo("11");
        produto.setNome("Merlo");
        produto.setValor(1200d);
        produto.setFornecedor("Marcos");
        Integer countCad2 = produtoDAO.cadastar(produto1);
        assertTrue(countCad2==1);

        List<Produto> list = produtoDAO.buscarTodos("TB_PRODUTO");
        assertNotNull(list);
        assertEquals(2,list.size());

        int countDel = 0;
        for (Produto prd : list){
            produtoDAO.excluir(prd);
            countDel++;
        }
        assertEquals(list.size(),countDel);

        list = produtoDAO.buscarTodos("TB_PRODUTO");
        assertEquals(list.size(),0);
    }

    @Test
    public void atualizarTest () throws Exception {
        produtoDAO = new ProdutoDAO();

        Produto produto = new Produto();
        produto.setCodigo("10");
        produto.setNome("Matheus");
        produto.setValor(800d);
        produto.setFornecedor("Marcos");
        Integer countCad = produtoDAO.cadastar(produto);
        assertTrue(countCad==1);

        Produto produtoBD = produtoDAO.buscar("10","TB_PRODUTO");
        assertNotNull(produtoBD);
        assertEquals(produto.getCodigo(),produtoBD.getCodigo());
        assertEquals(produto.getNome(),produtoBD.getNome());
        assertEquals(produto.getValor(),produtoBD.getValor());
        assertEquals(produto.getFornecedor(),produtoBD.getFornecedor());

        produtoBD.setCodigo("20");
        produtoBD.setNome("merlo");
        Integer countUpdate = produtoDAO.atualizar(produtoBD);
        assertTrue(countUpdate == 1);

        Produto produtoBD1 = produtoDAO.buscar("10","TB_PRODUTO");
        assertNull(produtoBD1);

        Produto produtoBD2 = produtoDAO.buscar("20","TB_PRODUTO");
        assertNotNull(produtoBD2);
        assertEquals(produtoBD.getId(),produtoBD2.getId());
        assertEquals(produtoBD.getNome(),produtoBD2.getNome());
        assertEquals(produtoBD.getCodigo(),produtoBD2.getCodigo());

        List<Produto> list = produtoDAO.buscarTodos("TB_PRODUTO");
        for (Produto prd : list){
            produtoDAO.excluir(prd);
        }
    }

}
