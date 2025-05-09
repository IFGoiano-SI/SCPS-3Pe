package DAO;

import Entidades.Endereco;
import Conexao.ConexaoDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para a entidade Endereco.
 */
public class EnderecoDAO implements DAO<Endereco> {

    @Override
    public int inserir(Endereco endereco) {
        String sql = "INSERT INTO endereco (rua, numero, bairro, cidade, estado, cep) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = new ConexaoDB().getConexao();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, endereco.getRua());
            stmt.setString(2, endereco.getNumero());
            stmt.setString(3, endereco.getBairro());
            stmt.setString(4, endereco.getCidade());
            stmt.setString(5, endereco.getEstado());
            stmt.setString(6, endereco.getCep());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // Retorna o ID gerado
                } else {
                    throw new SQLException("Falha ao inserir endereco, nenhum ID obtido.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir endereço: " + e.getMessage());
            return -1; // Retorna -1 em caso de erro
        }
    }

    @Override
    public boolean atualizar(Endereco endereco) {
        String sql = "UPDATE endereco SET rua = ?, numero = ?, bairro = ?, cidade = ?, estado = ?, cep = ? WHERE id_endereco = ?";
        try (Connection connection = new ConexaoDB().getConexao();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, endereco.getRua());
            stmt.setString(2, endereco.getNumero());
            stmt.setString(3, endereco.getBairro());
            stmt.setString(4, endereco.getCidade());
            stmt.setString(5, endereco.getEstado());
            stmt.setString(6, endereco.getCep());
            stmt.setInt(7, endereco.getId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar endereço: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean remover(int id) {
        String sql = "DELETE FROM endereco WHERE id_endereco = ?";
        try (Connection connection = new ConexaoDB().getConexao();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao remover endereço: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Endereco buscarPorId(int id) {
        String sql = "SELECT * FROM endereco WHERE id_endereco = ?";
        try (Connection connection = new ConexaoDB().getConexao();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Endereco(
                            rs.getInt("id_endereco"),
                            rs.getString("rua"),
                            rs.getString("numero"),
                            rs.getString("bairro"),
                            rs.getString("cidade"),
                            rs.getString("estado"),
                            rs.getString("cep")
                    );
                } else {
                    throw new IllegalArgumentException("Endereco com ID " + id + " não encontrado.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar endereço por ID: " + e.getMessage());
            throw new RuntimeException("Erro ao buscar endereco com ID " + id, e);
        }
    }

    @Override
    public List<Endereco> listarTodos() {
        String sql = "SELECT * FROM endereco";
        List<Endereco> enderecos = new ArrayList<>();
        try (Connection connection = new ConexaoDB().getConexao();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                enderecos.add(new Endereco(
                        rs.getInt("id_endereco"),
                        rs.getString("rua"),
                        rs.getString("numero"),
                        rs.getString("bairro"),
                        rs.getString("cidade"),
                        rs.getString("estado"),
                        rs.getString("cep")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar todos os endereços: " + e.getMessage());
        }
        return enderecos;
    }
}