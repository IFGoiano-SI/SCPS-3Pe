package Manager;

import DAO.ClienteDAO;
import DAO.EnderecoDAO;
import Entidades.Cliente;
import Entidades.Endereco;

import java.util.List;
import java.util.Scanner;

/**
 * Classe responsável por realizar operações de CRUD na tabela cliente do banco
 * de dados.
 */
public class ClienteManager {

    private ClienteDAO clienteDAO;
    private EnderecoDAO enderecoDAO;

    /**
     * Construtor da classe ClienteManager.
     */
    public ClienteManager() {
        this.clienteDAO = new ClienteDAO();
        this.enderecoDAO = new EnderecoDAO();
    }

    /**
     * Método que cadastra um novo cliente e seu endereço no banco de dados.
     *
     * @param entrada Scanner para entrada de dados.
     */
    public void cadastrarCliente(Scanner entrada) {
        try {
            System.out.println("\n - Cadastrar Cliente - \n");
            System.out.print("Nome: ");
            String nome = entrada.nextLine();
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

            Endereco endereco = new Endereco();
            endereco.setRua(rua);
            endereco.setNumero(numero);
            endereco.setBairro(bairro);
            endereco.setCidade(cidade);
            endereco.setEstado(estado);
            endereco.setCep(cep);

            int idEndereco = enderecoDAO.inserir(endereco);
            if (idEndereco > 0) {
                endereco.setId(idEndereco);
            } else {
                System.out.println("\n - Erro ao cadastrar endereço.");
                return;
            }

            Cliente cliente = new Cliente();
            cliente.setNome(nome);
            cliente.setTelefone(telefone);
            cliente.setEmail(email);
            cliente.setEndereco(endereco);

            clienteDAO.inserir(cliente);

            System.out.println("\n - Cliente cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("\n - Erro ao cadastrar cliente: " + e.getMessage());
        }
    }

    /**
     * Método que pesquisa um cliente no banco de dados.
     *
     * @param entrada Scanner para entrada de dados.
     */
    public void pesquisarCliente(Scanner entrada) {
        try {
            System.out.println("\n - Pesquisar Cliente - \n");
            System.out.print("ID do Cliente: ");
            int id = entrada.nextInt();
            entrada.nextLine();

            Cliente cliente = clienteDAO.buscarPorId(id);
            if (cliente != null) {
                System.out.println(cliente);
            } else {
                System.out.println("\n - Cliente não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("\n - Erro ao pesquisar cliente: " + e.getMessage());
        }
    }

    /**
     * Método que atualiza um cliente no banco de dados.
     *
     * @param entrada Scanner para entrada de dados.
     */
    public void atualizarCliente(Scanner entrada) {
        try {
            System.out.println("\n - Atualizar Cliente - \n");
            System.out.print("ID do Cliente: ");
            int id = entrada.nextInt();
            entrada.nextLine();

            Cliente cliente = clienteDAO.buscarPorId(id);
            if (cliente != null) {
                System.out.print("Novo Nome: ");
                String nome = entrada.nextLine();
                System.out.print("Novo Telefone: ");
                String telefone = entrada.nextLine();
                System.out.print("Novo Email: ");
                String email = entrada.nextLine();

                System.out.println("\n - Atualizar Endereço - \n");
                System.out.print("Nova Rua: ");
                String rua = entrada.nextLine();
                System.out.print("Novo Número: ");
                String numero = entrada.nextLine();
                System.out.print("Novo Bairro: ");
                String bairro = entrada.nextLine();
                System.out.print("Nova Cidade: ");
                String cidade = entrada.nextLine();
                System.out.print("Novo Estado (UF): ");
                String estado = entrada.nextLine();
                System.out.print("Novo CEP: ");
                String cep = entrada.nextLine();

                Endereco endereco = cliente.getEndereco();
                endereco.setRua(rua);
                endereco.setNumero(numero);
                endereco.setBairro(bairro);
                endereco.setCidade(cidade);
                endereco.setEstado(estado);
                endereco.setCep(cep);

                enderecoDAO.atualizar(endereco);

                cliente.setNome(nome);
                cliente.setTelefone(telefone);
                cliente.setEmail(email);

                clienteDAO.atualizar(cliente);

                System.out.println("\n - Cliente atualizado com sucesso!");
            } else {
                System.out.println("\n - Cliente não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("\n - Erro ao atualizar cliente: " + e.getMessage());
        }
    }

    /**
     * Método que exclui um cliente do banco de dados.
     *
     * @param entrada Scanner para entrada de dados.
     */
    public void excluirCliente(Scanner entrada) {
        try {
            System.out.println("\n - Excluir Cliente - \n");
            System.out.print("ID do Cliente: ");
            int id = entrada.nextInt();
            entrada.nextLine();

            Cliente cliente = clienteDAO.buscarPorId(id);
            if (cliente != null) {
                clienteDAO.remover(id);
                System.out.println("\n - Cliente excluído com sucesso!");
            } else {
                System.out.println("\n - Cliente não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("\n - Erro ao excluir cliente: " + e.getMessage());
        }
    }

    /**
     * Método que lista todos os clientes cadastrados no banco de dados.
     */
    public void listarClientes() {
        try {
            System.out.println("\n - Listar Clientes - \n");
            List<Cliente> clientes = clienteDAO.listarTodos();
            for (Cliente cliente : clientes) {
                System.out.println(cliente);
            }
        } catch (Exception e) {
            System.out.println("\n - Erro ao listar clientes: " + e.getMessage());
        }
    }
}