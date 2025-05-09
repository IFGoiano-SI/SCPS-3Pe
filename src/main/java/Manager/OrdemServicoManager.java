package Manager;

import DAO.OrdemServicoDAO;
import Entidades.OrdemServico;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

/**
 * Classe responsável por gerenciar ordens de serviço.
 */
public class OrdemServicoManager {

    private OrdemServicoDAO ordemServicoDAO;

    public OrdemServicoManager() {
        this.ordemServicoDAO = new OrdemServicoDAO();
    }

    // Removido o uso de usuário no cadastro de ordem de serviço
    public void adicionarOrdemServico(Scanner entrada) {
        try {
            System.out.println("\n - Adicionar Ordem de Serviço - \n");
            System.out.print("ID do Cliente: ");
            int idCliente = entrada.nextInt();
            entrada.nextLine();

            System.out.print("ID do Funcionário Responsável: ");
            int idFuncionario = entrada.nextInt();
            entrada.nextLine();

            System.out.print("Descrição da Ordem de Serviço: ");
            String descricao = entrada.nextLine();

            OrdemServico ordemServico = new OrdemServico();
            ordemServico.setDataAbertura(java.time.LocalDateTime.now());
            ordemServico.setStatus(OrdemServico.Status.ABERTO); // Define o status inicial como ABERTO usando o enum
            ordemServico.setCliente(new DAO.ClienteDAO().buscarPorId(idCliente));
            ordemServico.setFuncionario(new DAO.FuncionarioDAO().buscarPorId(idFuncionario));
            ordemServico.setDescricao(descricao); // Adicionado o campo descrição no cadastro

            if (ordemServicoDAO.inserir(ordemServico) > 0) {
                System.out.println("\n - Ordem de Serviço adicionada com sucesso!");
            } else {
                System.out.println("\n - Falha ao adicionar Ordem de Serviço.");
            }
        } catch (Exception e) {
            System.out.println("\n - Erro ao adicionar Ordem de Serviço: " + e.getMessage());
        }
    }

    public void excluirOrdemServico(Scanner entrada) {
        try {
            System.out.println("\n - Excluir Ordem de Serviço - \n");
            System.out.print("ID da Ordem de Serviço: ");
            int idOrdem = entrada.nextInt();
            entrada.nextLine();

            OrdemServico ordemServico = ordemServicoDAO.buscarPorId(idOrdem);
            if (ordemServico != null) {
                if (ordemServicoDAO.remover(idOrdem)) {
                    System.out.println("\n - Ordem de Serviço excluída com sucesso!");
                } else {
                    System.out.println("\n - Falha ao excluir Ordem de Serviço.");
                }
            } else {
                System.out.println("\n - Ordem de Serviço não encontrada.");
            }
        } catch (Exception e) {
            System.out.println("\n - Erro ao excluir Ordem de Serviço: " + e.getMessage());
        }
    }

    public void listarOrdensServico() {
        try {
            System.out.println("\n - Listar Ordens de Serviço - \n");
            List<OrdemServico> ordens = ordemServicoDAO.listarTodos();
            for (OrdemServico ordem : ordens) {
                System.out.println(ordem);
                System.out.println("-----------------------------------------------------");
            }
        } catch (Exception e) {
            System.out.println("\n - Erro ao listar Ordens de Serviço: " + e.getMessage());
        }
    }

    public void atualizarStatusOrdemServico(Scanner entrada) {
        try {
            System.out.println("\n - Atualizar Status da Ordem de Serviço - \n");
            System.out.print("ID da Ordem de Serviço: ");
            int idOrdem = entrada.nextInt();
            entrada.nextLine();

            OrdemServico ordemServico = ordemServicoDAO.buscarPorId(idOrdem);
            if (ordemServico != null) {
                System.out.print("Novo Status (ABERTO, EM_EXECUCAO, CONCLUIDA, CANCELADA): ");
                String novoStatus = entrada.nextLine();

                try {
                    OrdemServico.Status statusEnum = OrdemServico.Status.valueOf(novoStatus.toUpperCase());
                    ordemServico.setStatus(statusEnum);

                    if (statusEnum == OrdemServico.Status.CONCLUIDA) {
                        ordemServico.setDataConclusao(java.time.LocalDateTime.now());
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("\n - Status inválido. Use: ABERTO, EM_EXECUCAO, CONCLUIDA, CANCELADA.");
                    return;
                }

                if (ordemServicoDAO.atualizar(ordemServico)) {
                    System.out.println("\n - Status da Ordem de Serviço atualizado com sucesso!");
                } else {
                    System.out.println("\n - Falha ao atualizar Status da Ordem de Serviço.");
                }
            } else {
                System.out.println("\n - Ordem de Serviço não encontrada.");
            }
        } catch (Exception e) {
            System.out.println("\n - Erro ao atualizar Status da Ordem de Serviço: " + e.getMessage());
        }
    }

    public void atualizarOrdemServico(Scanner entrada) {
        try {
            System.out.println("\n - Atualizar Ordem de Serviço - \n");
            System.out.print("ID da Ordem de Serviço: ");
            int idOrdem = entrada.nextInt();
            entrada.nextLine();

            OrdemServico ordemServico = ordemServicoDAO.buscarPorId(idOrdem);
            if (ordemServico == null) {
                System.out.println("\n - Ordem de Serviço não encontrada.");
                return;
            }

            System.out.print("Nova descrição da Ordem de Serviço: ");
            String novaDescricao = entrada.nextLine();
            ordemServico.setDescricao(novaDescricao);

            if (ordemServicoDAO.atualizar(ordemServico)) {
                System.out.println("\n - Ordem de Serviço atualizada com sucesso!");
            } else {
                System.out.println("\n - Falha ao atualizar Ordem de Serviço.");
            }
        } catch (Exception e) {
            System.out.println("\n - Erro ao atualizar Ordem de Serviço: " + e.getMessage());
        }
    }

    public void pesquisarOrdemServico(Scanner entrada) {
        try {
            System.out.println("\n - Pesquisar Ordem de Serviço - \n");
            System.out.println("1 - Pesquisar por ID da Ordem de Serviço");
            System.out.println("2 - Pesquisar por ID do Cliente");
            System.out.println("3 - Pesquisar por ID do Funcionário");
            System.out.print("Escolha uma opção: ");

            int opcao = entrada.nextInt();
            entrada.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("Digite o ID da Ordem de Serviço: ");
                    int idOrdem = entrada.nextInt();
                    entrada.nextLine();
                    OrdemServico ordemPorId = ordemServicoDAO.buscarPorId(idOrdem);
                    if (ordemPorId != null) {
                        System.out.println(ordemPorId);
                    } else {
                        System.out.println("\n - Ordem de Serviço não encontrada.");
                    }
                    break;
                case 2:
                    System.out.print("Digite o ID do Cliente: ");
                    int idCliente = entrada.nextInt();
                    entrada.nextLine();
                    List<OrdemServico> ordensPorCliente = ordemServicoDAO.buscarPorCliente(idCliente);
                    if (!ordensPorCliente.isEmpty()) {
                        for (OrdemServico ordem : ordensPorCliente) {
                            System.out.println(ordem);
                            System.out.println("-----------------------------------------------------");
                        }
                    } else {
                        System.out.println("\n - Nenhuma Ordem de Serviço encontrada para o Cliente informado.");
                    }
                    break;
                case 3:
                    System.out.print("Digite o ID do Funcionário: ");
                    int idFuncionario = entrada.nextInt();
                    entrada.nextLine();
                    List<OrdemServico> ordensPorFuncionario = ordemServicoDAO.buscarPorFuncionario(idFuncionario);
                    if (!ordensPorFuncionario.isEmpty()) {
                        for (OrdemServico ordem : ordensPorFuncionario) {
                            System.out.println(ordem);
                            System.out.println("-----------------------------------------------------");
                        }
                    } else {
                        System.out.println("\n - Nenhuma Ordem de Serviço encontrada para o Funcionário informado.");
                    }
                    break;
                default:
                    System.out.println("\n - Opção inválida. Tente novamente.");
            }
        } catch (Exception e) {
            System.out.println("\n - Erro ao pesquisar Ordem de Serviço: " + e.getMessage());
        }
    }

    public void fecharOrdemServico(int idOrdem, double valorPago) {
        OrdemServico ordemServico = ordemServicoDAO.buscarPorId(idOrdem);
        if (ordemServico != null && ordemServico.getStatus() != OrdemServico.Status.CONCLUIDA) {
            ordemServico.setStatus(OrdemServico.Status.CONCLUIDA);
            ordemServico.setDataConclusao(LocalDateTime.now());
            ordemServico.setValorPago(valorPago);
            ordemServicoDAO.atualizar(ordemServico);
            System.out.println("Ordem de serviço fechada com sucesso e pagamento registrado.");
        } else {
            System.out.println("Ordem de serviço não encontrada ou já concluída.");
        }
    }
}