package DAO;

import Entidades.OrdemServico;
import Entidades.Cliente;
import Entidades.Funcionario;
import Conexao.ConexaoDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para a entidade OrdemServico.
 */
public class OrdemServicoDAO implements DAO<OrdemServico> {

    public OrdemServicoDAO() {
    }

    @Override
    public int inserir(OrdemServico ordemServico) {
        String sql = "INSERT INTO ordem_servico (data_abertura, data_conclusao, cliente_id, funcionario_id, descricao, valor_pago) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setTimestamp(1, Timestamp.valueOf(ordemServico.getDataAbertura()));
            stmt.setTimestamp(2, ordemServico.getDataConclusao() != null ? Timestamp.valueOf(ordemServico.getDataConclusao()) : null);
            stmt.setInt(3, ordemServico.getCliente().getIdCliente());
            stmt.setInt(4, ordemServico.getFuncionario().getIdFuncionario());
            stmt.setString(5, ordemServico.getDescricao());
            stmt.setDouble(6, ordemServico.getValorPago());

            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public boolean atualizar(OrdemServico ordemServico) {
        String sql = "UPDATE ordem_servico SET data_abertura = ?, data_conclusao = ?, status = ?, id_cliente = ?, id_funcionario = ?, descricao = ?, valor_pago = ?, ativo = ?  WHERE id_ordem = ?";
        try (PreparedStatement stmt = new ConexaoDB().getConexao().prepareStatement(sql)) {
            stmt.setTimestamp(1, Timestamp.valueOf(ordemServico.getDataAbertura()));
            stmt.setTimestamp(2, ordemServico.getDataConclusao() != null ? Timestamp.valueOf(ordemServico.getDataConclusao()) : null);
            stmt.setString(3, ordemServico.getStatus().name()); // Converte o enum Status para String usando name()
            stmt.setInt(4, ordemServico.getCliente().getIdCliente());
            stmt.setInt(5, ordemServico.getFuncionario().getIdFuncionario());
            stmt.setString(6, ordemServico.getDescricao()); // Adicionado o campo descrição no SQL
            stmt.setDouble(7, ordemServico.getValorPago()); // Adicionado o campo valor_pago no SQL
            stmt.setInt(8, ordemServico.getAtivo()); // Adicionado o campo ativo no SQL
            stmt.setInt(9, ordemServico.getIdOrdem());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar ordem de serviço: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean remover(int id) {
        String sql = "DELETE FROM ordem_servico WHERE id_ordem = ?";
        try (PreparedStatement stmt = new ConexaoDB().getConexao().prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao remover ordem de serviço: " + e.getMessage());
            return false;
        }
    }

    @Override
    public OrdemServico buscarPorId(int id) {
        String sql = "SELECT * FROM ordem_servico WHERE id_ordem = ? AND ativo = 1";
        try (PreparedStatement stmt = new ConexaoDB().getConexao().prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Cliente cliente = new ClienteDAO().buscarPorId(rs.getInt("id_cliente"));
                Funcionario funcionario = new FuncionarioDAO().buscarPorId(rs.getInt("id_funcionario"));
                OrdemServico.Status status = OrdemServico.Status.valueOf(rs.getString("status")); // Converte String para enum Status
                String descricao = rs.getString("descricao");
                return new OrdemServico(
                        rs.getInt("id_ordem"),
                        rs.getTimestamp("data_abertura").toLocalDateTime(),
                        rs.getTimestamp("data_conclusao") != null ? rs.getTimestamp("data_conclusao").toLocalDateTime() : null,
                        status,
                        cliente,
                        funcionario,
                        descricao,
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                );
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar ordem de serviço por ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<OrdemServico> listarTodos() {
        String sql = "SELECT * FROM ordem_servico WHERE ativo = 1";
        List<OrdemServico> ordens = new ArrayList<>();
        try (PreparedStatement stmt = new ConexaoDB().getConexao().prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Cliente cliente = new ClienteDAO().buscarPorId(rs.getInt("id_cliente"));
                Funcionario funcionario = new FuncionarioDAO().buscarPorId(rs.getInt("id_funcionario"));
                OrdemServico.Status status = OrdemServico.Status.valueOf(rs.getString("status")); // Converte String para enum Status
                String descricao = rs.getString("descricao");
                ordens.add(new OrdemServico(
                        rs.getInt("id_ordem"),
                        rs.getTimestamp("data_abertura").toLocalDateTime(),
                        rs.getTimestamp("data_conclusao") != null ? rs.getTimestamp("data_conclusao").toLocalDateTime() : null,
                        status,
                        cliente,
                        funcionario,
                        descricao, // Incluindo a descrição na listagem
                        rs.getDouble("valor_pago"), // Incluindo o valor pago na listagem
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar ordens de serviço: " + e.getMessage());
        }
        return ordens;
    }

    public List<OrdemServico> buscarPorCliente(int idCliente) {
        String sql = "SELECT * FROM ordem_servico WHERE id_cliente = ? AND ativo = 1";
        List<OrdemServico> ordens = new ArrayList<>();
        try (Connection connection = new ConexaoDB().getConexao();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    OrdemServico ordem = new OrdemServico();
                    ordem.setIdOrdem(rs.getInt("id_ordem"));
                    ordem.setDataAbertura(rs.getTimestamp("data_abertura").toLocalDateTime());
                    ordem.setDataConclusao(rs.getTimestamp("data_conclusao") != null ? rs.getTimestamp("data_conclusao").toLocalDateTime() : null);
                    ordem.setStatus(OrdemServico.Status.valueOf(rs.getString("status")));
                    ordem.setDescricao(rs.getString("descricao"));
                    ordem.setCliente(new ClienteDAO().buscarPorId(rs.getInt("id_cliente")));
                    ordem.setFuncionario(new FuncionarioDAO().buscarPorId(rs.getInt("id_funcionario")));
                    ordens.add(ordem);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar ordens de serviço por cliente: " + e.getMessage());
        }
        return ordens;
    }

    public List<OrdemServico> buscarPorFuncionario(int idFuncionario) {
        String sql = "SELECT * FROM ordem_servico WHERE id_funcionario = ? AND ativo = 1";
        List<OrdemServico> ordens = new ArrayList<>();
        try (Connection connection = new ConexaoDB().getConexao();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idFuncionario);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    OrdemServico ordem = new OrdemServico();
                    ordem.setIdOrdem(rs.getInt("id_ordem"));
                    ordem.setDataAbertura(rs.getTimestamp("data_abertura").toLocalDateTime());
                    ordem.setDataConclusao(rs.getTimestamp("data_conclusao") != null ? rs.getTimestamp("data_conclusao").toLocalDateTime() : null);
                    ordem.setStatus(OrdemServico.Status.valueOf(rs.getString("status")));
                    ordem.setDescricao(rs.getString("descricao"));
                    ordem.setCliente(new ClienteDAO().buscarPorId(rs.getInt("id_cliente")));
                    ordem.setFuncionario(new FuncionarioDAO().buscarPorId(rs.getInt("id_funcionario")));
                    ordens.add(ordem);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar ordens de serviço por funcionário: " + e.getMessage());
        }
        return ordens;
    }
}