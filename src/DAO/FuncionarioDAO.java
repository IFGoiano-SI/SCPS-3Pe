package DAO;

import Entidades.Funcionario;
import Entidades.Endereco;
import Entidades.Usuario;
import Conexao.ConexaoDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para a entidade Funcionario.
 */
public class FuncionarioDAO implements DAO<Funcionario> {

    public FuncionarioDAO() {
    }

    @Override
    public int inserir(Funcionario funcionario) {
        String sql = "INSERT INTO funcionario (nome, cargo, telefone, email, id_endereco, id_usuario) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = new ConexaoDB().getConexao();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getCargo());
            stmt.setString(3, funcionario.getTelefone());
            stmt.setString(4, funcionario.getEmail());
            stmt.setInt(5, funcionario.getEndereco().getId());
            stmt.setInt(6, funcionario.getUsuario().getIdUsuario());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // Retorna o ID gerado
                } else {
                    throw new SQLException("Falha ao inserir funcionario, nenhum ID obtido.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir funcionário: " + e.getMessage());
            return -1; // Retorna -1 em caso de erro
        }
    }

    @Override
    public boolean atualizar(Funcionario funcionario) {
        String sql = "UPDATE funcionario SET nome = ?, cargo = ?, telefone = ?, email = ?, id_endereco = ?, id_usuario = ? WHERE id_funcionario = ?";
        try (Connection connection = new ConexaoDB().getConexao();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getCargo());
            stmt.setString(3, funcionario.getTelefone());
            stmt.setString(4, funcionario.getEmail());
            stmt.setInt(5, funcionario.getEndereco().getId());
            stmt.setInt(6, funcionario.getUsuario().getIdUsuario());
            stmt.setInt(7, funcionario.getIdFuncionario());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar funcionário: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean remover(int id) {
        String sql = "DELETE FROM funcionario WHERE id_funcionario = ?";
        try (Connection connection = new ConexaoDB().getConexao();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao remover funcionário: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Funcionario buscarPorId(int id) {
        String sql = "SELECT * FROM funcionario WHERE id_funcionario = ?";
        try (Connection connection = new ConexaoDB().getConexao();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Endereco endereco = new EnderecoDAO().buscarPorId(rs.getInt("id_endereco"));
                    Usuario usuario = new UsuarioDAO().buscarPorId(rs.getInt("id_usuario"));
                    return new Funcionario(
                            rs.getInt("id_funcionario"),
                            rs.getString("nome"),
                            rs.getString("telefone"),
                            rs.getString("email"),
                            endereco,
                            rs.getString("cargo"),
                            usuario,
                            rs.getTimestamp("created_at").toLocalDateTime(),
                            rs.getTimestamp("updated_at").toLocalDateTime()
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar funcionário por ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Funcionario> listarTodos() {
        String sql = "SELECT * FROM funcionario";
        List<Funcionario> funcionarios = new ArrayList<>();
        try (Connection connection = new ConexaoDB().getConexao();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Endereco endereco = new EnderecoDAO().buscarPorId(rs.getInt("id_endereco"));
                Usuario usuario = new UsuarioDAO().buscarPorId(rs.getInt("id_usuario"));
                funcionarios.add(new Funcionario(
                        rs.getInt("id_funcionario"),
                        rs.getString("nome"),
                        rs.getString("telefone"),
                        rs.getString("email"),
                        endereco,
                        rs.getString("cargo"),
                        usuario,
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar todos os funcionários: " + e.getMessage());
        }
        return funcionarios;
    }
}