package Manager;

import DAO.FuncionarioDAO;
import DAO.EnderecoDAO;
import DAO.UsuarioDAO;
import Entidades.Funcionario;
import Entidades.Endereco;
import Entidades.Usuario;

import java.util.Scanner;

/**
 * Classe responsável por realizar operações de CRUD na tabela funcionario do
 * banco de dados.
 */
public class FuncionarioManager {

    private FuncionarioDAO funcionarioDAO;
    private EnderecoDAO enderecoDAO;
    private UsuarioDAO usuarioDAO;

    /**
     * Construtor da classe FuncionarioManager.
     */
    public FuncionarioManager() {
        this.funcionarioDAO = new FuncionarioDAO();
        this.enderecoDAO = new EnderecoDAO();
        this.usuarioDAO = new UsuarioDAO();
    }

    /**
     * Método que cadastra um novo funcionário e seu endereço no banco de dados.
     *
     * @param entrada Scanner para entrada de dados.
     */
    public void cadastrarFuncionario(Scanner entrada) {
        try {
            System.out.println("\n - Cadastrar Funcionário - \n");
            System.out.print("Nome: ");
            String nome = entrada.nextLine();
            System.out.print("Cargo: ");
            String cargo = entrada.nextLine();
            System.out.print("Telefone: ");
            String telefone = entrada.nextLine();
            System.out.print("Email: ");
            String email = entrada.nextLine();

            System.out.println("\n - Cadastrar Endereço - \n");
            System.out.print("Rua: ");
            String rua = entrada.nextLine();
            System.out.print("Número: ");
            String numero = entrada.nextLine();
            System.out.print("Bairro: ");
            String bairro = entrada.nextLine();
            System.out.print("Cidade: ");
            String cidade = entrada.nextLine();
            System.out.print("Estado (UF): ");
            String estado = entrada.nextLine();
            System.out.print("CEP: ");
            String cep = entrada.nextLine();

            // Criar o endereço
            Endereco endereco = new Endereco();
            endereco.setRua(rua);
            endereco.setNumero(numero);
            endereco.setBairro(bairro);
            endereco.setCidade(cidade);
            endereco.setEstado(estado);
            endereco.setCep(cep);

            // Inserir o endereço no banco e obter o ID gerado
            int idEndereco = enderecoDAO.inserir(endereco);
            if (idEndereco > 0) {
                endereco.setId(idEndereco);
            } else {
                System.out.println("\n - Erro ao cadastrar endereço.");
                return;
            }

            System.out.println("\n - Cadastrar Usuário - \n");
            System.out.print("Nome de Usuário: ");
            String nomeUsuario = entrada.nextLine();
            System.out.print("Senha: ");
            String senha = entrada.nextLine();
            System.out.print("Tipo de Usuário (FUNCIONARIO): ");
            String tipoUsuario = "FUNCIONARIO";

            // Criar o usuário
            Usuario usuario = new Usuario();
            usuario.setNomeUsuario(nomeUsuario);
            usuario.setSenha(senha);
            usuario.setTipoUsuario(tipoUsuario);

            // Inserir o usuário no banco e obter o ID gerado
            int idUsuario = usuarioDAO.inserir(usuario);
            if (idUsuario > 0) {
                usuario.setIdUsuario(idUsuario);
            } else {
                System.out.println("\n - Erro ao cadastrar usuário.");
                return;
            }

            // Criar o funcionário
            Funcionario funcionario = new Funcionario();
            funcionario.setNome(nome);
            funcionario.setCargo(cargo);
            funcionario.setTelefone(telefone);
            funcionario.setEmail(email);
            funcionario.setEndereco(endereco);
            funcionario.setUsuario(usuario);

            // Inserir o funcionário no banco
            funcionarioDAO.inserir(funcionario);

            System.out.println("\n - Funcionário cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("\n - Erro ao cadastrar funcionário: " + e.getMessage());
        }
    }

    /**
     * Método que pesquisa um funcionário no banco de dados.
     *
     * @param entrada Scanner para entrada de dados.
     */
    public void pesquisarFuncionario(Scanner entrada) {
        try {
            System.out.println("\n - Pesquisar Funcionário - \n");
            System.out.print("ID do Funcionário: ");
            int id = entrada.nextInt();
            entrada.nextLine();

            Funcionario funcionario = funcionarioDAO.buscarPorId(id);
            if (funcionario != null) {
                System.out.println(funcionario);
            } else {
                System.out.println("\n - Funcionário não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("\n - Erro ao pesquisar funcionário: " + e.getMessage());
        }
    }

    /**
     * Método que atualiza um funcionário no banco de dados.
     *
     * @param entrada Scanner para entrada de dados.
     */
    public void atualizarFuncionario(Scanner entrada) {
        try {
            System.out.println("\n - Atualizar Funcionário - \n");
            System.out.print("ID do Funcionário: ");
            int id = entrada.nextInt();
            entrada.nextLine();

            Funcionario funcionario = funcionarioDAO.buscarPorId(id);
            if (funcionario != null) {
                System.out.print("Novo Nome: ");
                String nome = entrada.nextLine();
                System.out.print("Novo Cargo: ");
                String cargo = entrada.nextLine();
                System.out.print("Novo Telefone: ");
                String telefone = entrada.nextLine();
                System.out.print("Novo Email: ");
                String email = entrada.nextLine();

                funcionario.setNome(nome);
                funcionario.setCargo(cargo);
                funcionario.setTelefone(telefone);
                funcionario.setEmail(email);

                funcionarioDAO.atualizar(funcionario);
                System.out.println("\n - Funcionário atualizado com sucesso!");
            } else {
                System.out.println("\n - Funcionário não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("\n - Erro ao atualizar funcionário: " + e.getMessage());
        }
    }

    /**
     * Método que exclui um funcionário do banco de dados.
     *
     * @param entrada Scanner para entrada de dados.
     */
    public void excluirFuncionario(Scanner entrada) {
        try {
            System.out.println("\n - Excluir Funcionário - \n");
            System.out.print("ID do Funcionário: ");
            int id = entrada.nextInt();
            entrada.nextLine();

            Funcionario funcionario = funcionarioDAO.buscarPorId(id);
            if (funcionario != null) {
                // funcionarioDAO.remover(id);
                // System.out.println("\n - Funcionário excluído com sucesso!");
                funcionario.setAtivo(0);
                if (funcionarioDAO.atualizar(funcionario)) {
                    System.out.println("\n - Funcionário excluído com sucesso!");
                } else {
                    System.out.println("\n - Erro ao excluir funcionário.");
                }
            } else {
                System.out.println("\n - Funcionário não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("\n - Erro ao excluir funcionário: " + e.getMessage());
        }
    }

    /**
     * Método que lista todos os funcionários cadastrados no banco de dados.
     */
    public void listarFuncionarios() {
        try {
            System.out.println("\n - Listar Funcionários - \n");
            for (Funcionario funcionario : funcionarioDAO.listarTodos()) {
                System.out.println(funcionario);
            }
        } catch (Exception e) {
            System.out.println("\n - Erro ao listar funcionários: " + e.getMessage());
        }
    }
}