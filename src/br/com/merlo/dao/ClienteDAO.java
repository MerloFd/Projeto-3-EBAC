package br.com.merlo.dao;

import br.com.merlo.dao.generic.GenericDAO;
import br.com.merlo.domain.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO extends GenericDAO <Cliente> implements IClienteDAO {
}
