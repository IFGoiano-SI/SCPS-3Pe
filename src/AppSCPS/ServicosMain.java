package AppSCPS;
/*
import Manager.ClienteManager;
import Manager.FuncionarioManager;
import Manager.OrdemServicoManager;
import Manager.UsuarioManager;
import Entidades.Usuario;
import java.util.Scanner;
*/
public class ServicosMain {
    /*
    private static Scanner scanner = new Scanner(System.in);
    private static ClienteManager clienteManager = new ClienteManager();
    private static FuncionarioManager funcionarioManager = new FuncionarioManager();
    private static OrdemServicoManager ordemServicoManager = new OrdemServicoManager();
    private static UsuarioManager usuarioManager = new UsuarioManager();
    
    private static boolean realizarLogin() {
        System.out.println("\n===== LOGIN =====");
        System.out.print("Nome de Usuário: ");
        String nomeUsuario = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        Usuario usuario = usuarioManager.buscarPorNomeESenha(nomeUsuario, senha);
        if (usuario != null) {
            System.out.println("\nLogin realizado com sucesso! Bem-vindo, " + usuario.getNomeUsuario() + "!");
            return true;
        } else {
            System.out.println("\nErro: Nome de usuário ou senha inválidos.");
            return false;
        }
    }

    public static void main(String[] args) {
        boolean continuar = false;

        try {
            while (!continuar) {
                continuar = realizarLogin();
            }

            while (continuar) {
                continuar = exibirMenuPrincipal();
            }
        } catch (Exception e) {
            System.out.println("Erro inesperado: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    private static boolean exibirMenuPrincipal() {
        try {
            System.out.println("\n===== MENU PRINCIPAL =====");
            System.out.println("1 - Cadastros");
            System.out.println("2 - Ordem de Serviços");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1:
                    menuCadastros();
                    return true;
                case 2:
                    menuOrdemServicos();
                    return true;
                case 0:
                    System.out.println("Sistema encerrado.");
                    return false;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
                    return true;
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro: Digite apenas números!");
            return true;
        } catch (Exception e) {
            System.out.println("Erro inesperado: " + e.getMessage());
            return true;
        }
    }

    private static void menuCadastros() {
        boolean voltar = false;

        try {
            while (!voltar) {
                System.out.println("\n===== MENU DE CADASTROS =====");
                System.out.println("1 - Funcionário");
                System.out.println("2 - Cliente");
                System.out.println("3 - Usuário");
                System.out.println("0 - Voltar");
                System.out.print("Escolha uma opção: ");

                int opcao = Integer.parseInt(scanner.nextLine());

                switch (opcao) {
                    case 1:
                        menuFuncionarios();
                        break;
                    case 2:
                        menuClientes();
                        break;
                    case 3:
                        menuUsuarios();
                        break;
                    case 0:
                        voltar = true;
                        break;
                    default:
                        System.out.println("Opção inválida! Tente novamente.");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro: Digite apenas números!");
        } catch (Exception e) {
            System.out.println("Erro inesperado: " + e.getMessage());
        }
    }

    private static void menuFuncionarios() {
        boolean voltar = false;

        try {
            while (!voltar) {
                System.out.println("\n===== GERENCIAMENTO DE FUNCIONÁRIOS =====");
                System.out.println("1 - Cadastrar Funcionário");
                System.out.println("2 - Atualizar cadastro de Funcionário");
                System.out.println("3 - Pesquisar Funcionário");
                System.out.println("4 - Listar cadastros de funcionários");
                System.out.println("5 - Excluir cadastro de Funcionário");
                System.out.println("0 - Voltar");
                System.out.print("Escolha uma opção: ");

                int opcao = Integer.parseInt(scanner.nextLine());

                switch (opcao) {
                    case 1:
                        funcionarioManager.cadastrarFuncionario(scanner);
                        break;
                    case 2:
                        funcionarioManager.atualizarFuncionario(scanner);
                        break;
                    case 3:
                        funcionarioManager.pesquisarFuncionario(scanner);
                        break;
                    case 4:
                        funcionarioManager.listarFuncionarios();
                        break;
                    case 5:
                        funcionarioManager.excluirFuncionario(scanner);
                        break;
                    case 0:
                        voltar = true;
                        break;
                    default:
                        System.out.println("Opção inválida! Tente novamente.");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro: Digite apenas números!");
        } catch (Exception e) {
            System.out.println("Erro inesperado: " + e.getMessage());
        }
    }

    private static void menuClientes() {
        boolean voltar = false;

        try {
            while (!voltar) {
                System.out.println("\n===== GERENCIAMENTO DE CLIENTES =====");
                System.out.println("1 - Cadastrar Cliente");
                System.out.println("2 - Atualizar cadastro de Cliente");
                System.out.println("3 - Pesquisar Cliente");
                System.out.println("4 - Listar cadastros de Clientes");
                System.out.println("5 - Excluir cadastro de Cliente");
                System.out.println("0 - Voltar");
                System.out.print("Escolha uma opção: ");

                int opcao = Integer.parseInt(scanner.nextLine());

                switch (opcao) {
                    case 1:
                        clienteManager.cadastrarCliente(scanner);
                        break;
                    case 2:
                        clienteManager.atualizarCliente(scanner);
                        break;
                    case 3:
                        clienteManager.pesquisarCliente(scanner);
                        break;
                    case 4:
                        clienteManager.listarClientes();
                        break;
                    case 5:
                        clienteManager.excluirCliente(scanner);
                        break;
                    case 0:
                        voltar = true;
                        break;
                    default:
                        System.out.println("Opção inválida! Tente novamente.");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro: Digite apenas números!");
        } catch (Exception e) {
            System.out.println("Erro inesperado: " + e.getMessage());
        }
    }

    private static void menuUsuarios() {
        boolean voltar = false;

        try {
            while (!voltar) {
                System.out.println("\n===== GERENCIAMENTO DE USUÁRIOS =====");
                System.out.println("1 - Cadastrar Usuário");
                System.out.println("2 - Atualizar cadastro de Usuário");
                System.out.println("3 - Pesquisar Usuário");
                System.out.println("4 - Listar cadastros de Usuários");
                System.out.println("5 - Excluir cadastro de Usuário");
                System.out.println("0 - Voltar");
                System.out.print("Escolha uma opção: ");

                int opcao = Integer.parseInt(scanner.nextLine());

                switch (opcao) {
                    case 1:
                        usuarioManager.cadastrarUsuario(scanner);
                        break;
                    case 2:
                        usuarioManager.atualizarUsuario(scanner);
                        break;
                    case 3:
                        usuarioManager.pesquisarUsuario(scanner);
                        break;
                    case 4:
                        usuarioManager.listarUsuarios();
                        break;
                    case 5:
                        usuarioManager.excluirUsuario(scanner);
                        break;
                    case 0:
                        voltar = true;
                        break;
                    default:
                        System.out.println("Opção inválida! Tente novamente.");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro: Digite apenas números!");
        } catch (Exception e) {
            System.out.println("Erro inesperado: " + e.getMessage());
        }
    }

    private static void menuOrdemServicos() {
        boolean voltar = false;

        try {
            while (!voltar) {
                System.out.println("\n===== GERENCIAMENTO DE ORDEM DE SERVIÇO =====");
                System.out.println("1 - Inserir Ordem de Serviço");
                System.out.println("2 - Atualizar Ordem de Serviço");
                System.out.println("3 - Pesquisar Ordem de Serviço");
                System.out.println("4 - Listar Ordens de Serviço");
                System.out.println("5 - Atualizar Status da Ordem de Serviço");
                System.out.println("6 - Excluir Ordem de Serviço");
                System.out.println("7 - Concluir Ordem de Serviço");
                System.out.println("0 - Voltar");
                System.out.print("Escolha uma opção: ");

                int opcao = Integer.parseInt(scanner.nextLine());

                switch (opcao) {
                    case 1:
                        ordemServicoManager.adicionarOrdemServico(scanner);
                        break;
                    case 2:
                       ordemServicoManager.atualizarOrdemServico(scanner);
                        break;
                    case 3:
                       ordemServicoManager.pesquisarOrdemServico(scanner);
                        break;
                    case 4:
                        ordemServicoManager.listarOrdensServico();
                        break;
                    case 5:
                        ordemServicoManager.atualizarStatusOrdemServico(scanner);
                        break;
                    case 6:
                        ordemServicoManager.excluirOrdemServico(scanner);
                        break;
                    case 7:
                        System.out.println("\n - Concluir Ordem de Serviço - \n");
                        System.out.print("ID da Ordem de Serviço: ");
                        int idOrdem = Integer.parseInt(scanner.nextLine());
                        System.out.print("Valor Pago: ");
                        double valorPago = Double.parseDouble(scanner.nextLine());
                        ordemServicoManager.fecharOrdemServico(idOrdem, valorPago);
                        break;
                    case 0:
                        voltar = true;
                        break;
                    default:
                        System.out.println("Opção inválida! Tente novamente.");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro: Digite apenas números!");
        } catch (Exception e) {
            System.out.println("Erro inesperado: " + e.getMessage());
        }
    }

    private static void menuAtualizarStatus() {
        try {
            System.out.println("\n===== ATUALIZAR STATUS DA ORDEM DE SERVIÇO =====");
            System.out.println("1 - Aberto -> Em Execução");
            System.out.println("2 - Em Execução -> Concluído");
            System.out.println("3 - Aberto -> Cancelar");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");

            int opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1:
                    // OrdemServicoDAO.atualizarStatusParaEmExecucao();
                    System.out.println("Função para atualizar status para 'Em Execução' seria chamada aqui.");
                    break;
                case 2:
                    // OrdemServicoDAO.atualizarStatusParaConcluido();
                    System.out.println("Função para atualizar status para 'Concluído' seria chamada aqui.");
                    break;
                case 3:
                    // OrdemServicoDAO.atualizarStatusParaCancelado();
                    System.out.println("Função para atualizar status para 'Cancelado' seria chamada aqui.");
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro: Digite apenas números!");
        } catch (Exception e) {
            System.out.println("Erro inesperado: " + e.getMessage());
        }
    }
        */
}