package br.com.merlo.dao.generic;

import br.com.merlo.domain.Cliente;
import br.com.merlo.domain.Persistente;

import java.util.List;

public interface IGenericDAO<T extends Persistente> {
    public Integer cadastar (T entity) throws Exception;
    public Integer atualizar (T entity) throws Exception;
    public T buscar (String codigo, String tableName) throws Exception;
    public List<T> buscarTodos(String tableName) throws Exception;
    public Integer excluir (T entity) throws Exception;
}
