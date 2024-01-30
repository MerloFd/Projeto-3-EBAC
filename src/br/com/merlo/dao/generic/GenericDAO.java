package br.com.merlo.dao.generic;

import br.com.merlo.dao.JDBC.ConnectionFactory;
import br.com.merlo.domain.Cliente;
import br.com.merlo.domain.Persistente;
import br.com.merlo.domain.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class GenericDAO <T extends Persistente> implements IGenericDAO<T> {
    @Override
    public Integer cadastar(T entity) throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;
        try {
            connection = ConnectionFactory.getConnection();
            if (entity instanceof Cliente){
                String sql = getSqlInsertCliente();
                stm=connection.prepareStatement(sql); //compila o comando
                adicionaParametrosInsertCliente(stm, (Cliente) entity);
            } else if (entity instanceof Produto) {
                String sql = getSqlInsertProduto();
                stm=connection.prepareStatement(sql); //compila o comando
                adicionaParametrosInsertProduto(stm, (Produto) entity);
            }
            return stm.executeUpdate();
        } catch (Exception e){
            throw e;
        } finally {
            closeConnection(connection,stm,null);
        }
    }

    private void adicionaParametrosInsertProduto(PreparedStatement stm, Produto produto) throws SQLException {
        stm.setString(1, produto.getNome());
        stm.setString(2,produto.getCodigo());
        stm.setDouble(3,produto.getValor());
        stm.setString(4,produto.getFornecedor());
    }

    private String getSqlInsertProduto() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO TB_PRODUTO (ID, NOME, CODIGO, VALOR, FORNECEDOR)");
        sb.append("VALUES (NEXTVAL('SQ_CLIENTE'),?,?,?,?)");
        return sb.toString();
    }

    @Override
    public Integer atualizar(Persistente entity) throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;
        try {
            connection = ConnectionFactory.getConnection();
            if (entity instanceof Cliente){
                String sql = getSqlUpdateCliente();
                stm=connection.prepareStatement(sql);
                adicionaParametrosUpdateCliente(stm,(Cliente) entity);
            } else if (entity instanceof Produto) {
                String sql = getSqlUpdateProduto();
                stm=connection.prepareStatement(sql);
                adicionaParametrosUpdateProduto(stm,(Produto) entity);
            }
            return stm.executeUpdate();
        } catch (Exception e){
            throw e;
        } finally {
            closeConnection(connection,stm,null);
        }
    }

    private void adicionaParametrosUpdateProduto(PreparedStatement stm, Produto produto) throws SQLException {
        stm.setString(1,produto.getNome());
        stm.setString(2,produto.getCodigo());
        stm.setDouble(3,produto.getValor());
        stm.setString(4,produto.getFornecedor());

    }

    private String getSqlUpdateProduto() {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE TB_PRODUTO");
        sb.append("SET NOME =?, CODIGO=?,VALOR=?, FORNECEDOR=?");
        sb.append("WHERE ID=?");
        return sb.toString();
    }

    @Override
    public T buscar(String codigo, String tableName) throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        T entity = null;
        try {
            connection = ConnectionFactory.getConnection();
            String sql = getSqlSelect(tableName); //a table fornecida pelo app declara qual table vai ser manipulada
            stm=connection.prepareStatement(sql);
            adicionaParametrosSelect(stm,codigo);
            rs = stm.executeQuery();

            if (tableName.equalsIgnoreCase("TB_CLIENTE")){
                if (rs.next()){
                    entity = (T) new Cliente();
                    Long id = rs.getLong("ID");
                    String nome = rs.getString("NOME");
                    String cod = rs.getString("CODIGO");
                    ((Cliente) entity).setId(id);
                    ((Cliente) entity).setNome(nome);
                    ((Cliente) entity).setCodigo(cod);
                }
            } else if (tableName.equalsIgnoreCase("TB_PRODUTO")) {
                if (rs.next()){
                    entity = (T) new Produto();
                    Long id = rs.getLong("ID");
                    String nome = rs.getString("NOME");
                    String cod = rs.getString("CODIGO");
                    Double valor = rs.getDouble("VALOR");
                    String fornecedor = rs.getString("FORNECEDOR");
                    ((Produto) entity).setId(id);
                    ((Produto) entity).setNome(nome);
                    ((Produto) entity).setCodigo(cod);
                    ((Produto) entity).setValor(valor);
                    ((Produto) entity).setFornecedor(fornecedor);
                }
            }

        } catch (Exception e){
            throw e;
        } finally {
            closeConnection(connection,stm,rs);
        }
        return entity;
    }

    @Override
    public List<T> buscarTodos(String tableName) throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;
        List<T> list = new ArrayList<>();
        ResultSet rs = null;
        Cliente cliente = null;
        try {
            connection = ConnectionFactory.getConnection();
            String sql = getSqlSelectAll(tableName);
            stm=connection.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()){ //loop para pegar todos os results da list
                cliente = new Cliente();
                Long id = rs.getLong("ID");
                String nome = rs.getString("NOME");
                String cod = rs.getString("CODIGO");
                cliente.setId(id);
                cliente.setNome(nome);
                cliente.setCodigo(cod);
                list.add((T) cliente);
            }
        } catch (Exception e){
            throw e;
        } finally {
            closeConnection(connection,stm,rs);
        }
        return list;
    }

    @Override
    public Integer excluir(Persistente entity) throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;
        try {
            connection = ConnectionFactory.getConnection();
            if (entity instanceof Cliente){
                String sql = getSqlDeleteCliente();
                stm=connection.prepareStatement(sql);
                adicionaParametrosDeleteCliente(stm,(Cliente) entity);
            } else if (entity instanceof Produto){
                String sql = getSqlDeleteProduto();
                stm=connection.prepareStatement(sql);
                adicionaParametrosDeleteProduto(stm,(Produto) entity);
            }
            return stm.executeUpdate();
        } catch (Exception e){
            throw e;
        } finally {
            closeConnection(connection,stm,null);
        }
    }

    private void closeConnection(Connection connection, PreparedStatement stm, ResultSet rs) {
        try {
            if (rs != null && !rs.isClosed()){
                rs.close();
            }
            else if (stm !=null && !stm.isClosed()){
                stm.close();
            }
            else if (connection !=null && !connection.isClosed()){
                connection.close();
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    private void adicionaParametrosDeleteProduto(PreparedStatement stm, Produto produto) throws SQLException {
        stm.setString(1,produto.getCodigo());
    }

    private String getSqlDeleteProduto() {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE * FROM TB_PRODUTO");
        sb.append("WHERE CODIGO = ?");
        return sb.toString();
    }

    private void adicionaParametrosDeleteCliente(PreparedStatement stm, Cliente cliente) throws SQLException {
        stm.setString(1,cliente.getCodigo());
    }

    private String getSqlDeleteCliente() {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE * FROM TB_CLIENTE");
        sb.append("WHERE CODIGO = ?");
        return sb.toString();
    }

    private void adicionaParametrosInsertCliente(PreparedStatement stm, Cliente cliente) throws SQLException {
        stm.setString(1, cliente.getNome());
        stm.setString(2,cliente.getCodigo());
    }

    private String getSqlInsertCliente() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO TB_CLIENTE (ID, NOME, CODIGO)");
        sb.append("VALUES (NEXTVAL('SQ_CLIENTE'),?,?)");
        return sb.toString();
    }

    private String getSqlSelectAll(String tableName) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM " + tableName);
        return sb.toString();
    }


    private String getSqlSelect(String tableName) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM " + tableName + "WHERE CODIGO =?");
        return sb.toString();
    }

    private void adicionaParametrosSelect(PreparedStatement stm, String codigo) throws SQLException {
        stm.setString(1,codigo);
    }

    private String getSqlUpdateCliente() {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE TB_CLIENTE ");
        sb.append("SET NOME =?, CODIGO=?");
        sb.append("WHERE ID=?");
        return sb.toString();
    }

    private void adicionaParametrosUpdateCliente(PreparedStatement stm, Cliente cliente) throws SQLException {
        stm.setString(1, cliente.getNome());
        stm.setString(2,cliente.getCodigo());
        stm.setLong(3,cliente.getId());
    }

}
