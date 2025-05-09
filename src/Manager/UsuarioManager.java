package Manager;

import DAO.UsuarioDAO;
import Entidades.Usuario;

import java.util.List;
import java.util.Scanner;

/**
 * Classe responsável por realizar operações de CRUD na tabela usuario do banco de dados.
 */
public class UsuarioManager {

    private UsuarioDAO usuarioDAO;

    /**
     * Construtor da classe UsuarioManager.
     */
    public UsuarioManager() {
        this.usuarioDAO = new UsuarioDAO();
    }

    /**
     * Método que cadastra um novo usuário no banco de dados.
     *
     * @param entrada Scanner para entrada de dados.
     */
    public void cadastrarUsuario(Scanner entrada) {
        try {
            System.out.println("\n - Cadastrar Usuário - \n");
            System.out.print("Nome de Usuário: ");
            String nomeUsuario = entrada.nextLine();
            System.out.print("Senha: ");
            String senha = entrada.nextLine();
            System.out.print("Tipo de Usuário (ADMIN, FUNCIONARIO, CLIENTE): ");
            String tipoUsuario = entrada.nextLine().toUpperCase();

            Usuario usuario = new Usuario();
            usuario.setNomeUsuario(nomeUsuario);
            usuario.setSenha(senha);
            usuario.setTipoUsuario(tipoUsuario);

            usuarioDAO.inserir(usuario);
            System.out.println("\n - Usuário cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("\n - Erro ao cadastrar usuário: " + e.getMessage());
        }
    }

    /**
     * Método que pesquisa um usuário no banco de dados.
     *
     * @param entrada Scanner para entrada de dados.
     */
    public void pesquisarUsuario(Scanner entrada) {
        try {
            System.out.println("\n - Pesquisar Usuário - \n");
            System.out.print("ID do Usuário: ");
            int id = entrada.nextInt();
            entrada.nextLine();

            Usuario usuario = usuarioDAO.buscarPorId(id);
            if (usuario != null) {
                System.out.println(usuario);
            } else {
                System.out.println("\n - Usuário não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("\n - Erro ao pesquisar usuário: " + e.getMessage());
        }
    }

    /**
     * Método que atualiza um usuário no banco de dados.
     *
     * @param entrada Scanner para entrada de dados.
     */
    public void atualizarUsuario(Scanner entrada) {
        try {
            System.out.println("\n - Atualizar Usuário - \n");
            System.out.print("ID do Usuário: ");
            int id = entrada.nextInt();
            entrada.nextLine();

            Usuario usuario = usuarioDAO.buscarPorId(id);
            if (usuario != null) {
                System.out.print("\nNovo Nome de Usuário: ");
                String nomeUsuario = entrada.nextLine();
                System.out.print("Nova Senha: ");
                String senha = entrada.nextLine();
                System.out.print("Novo Tipo de Usuário (ADMIN, FUNCIONARIO, CLIENTE): ");
                String tipoUsuario = entrada.nextLine().toUpperCase();

                usuario.setNomeUsuario(nomeUsuario);
                usuario.setSenha(senha);
                usuario.setTipoUsuario(tipoUsuario);

                usuarioDAO.atualizar(usuario);
                System.out.println("\n - Usuário atualizado com sucesso!");
            } else {
                System.out.println("\n - Usuário não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("\n - Erro ao atualizar usuário: " + e.getMessage());
        }
    }

    /**
     * Método que exclui um usuário do banco de dados.
     *
     * @param entrada Scanner para entrada de dados.
     */
    public void excluirUsuario(Scanner entrada) {
        try {
            System.out.println("\n - Excluir Usuário - \n");
            System.out.print("ID do Usuário: ");
            int id = entrada.nextInt();
            entrada.nextLine();

            Usuario usuario = usuarioDAO.buscarPorId(id);
            if (usuario != null) {
                usuarioDAO.remover(id);
                System.out.println("\n - Usuário excluído com sucesso!");
            } else {
                System.out.println("\n - Usuário não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("\n - Erro ao excluir usuário: " + e.getMessage());
        }
    }

    /**
     * Método que lista todos os usuários cadastrados no banco de dados.
     */
    public void listarUsuarios() {
        try {
            System.out.println("\n - Listar Usuários - \n");
            List<Usuario> usuarios = usuarioDAO.listarTodos();
            for (Usuario usuario : usuarios) {
                System.out.println(usuario);
            }
        } catch (Exception e) {
            System.out.println("\n - Erro ao listar usuários: " + e.getMessage());
        }
    }

    /**
     * Método que busca um usuário pelo nome e senha.
     *
     * @param nomeUsuario Nome do usuário.
     * @param senha Senha do usuário.
     * @return Usuário encontrado ou null se não encontrado.
     */
    public Usuario buscarPorNomeESenha(String nomeUsuario, String senha) {
        return usuarioDAO.buscarPorNomeESenha(nomeUsuario, senha);
    }
}