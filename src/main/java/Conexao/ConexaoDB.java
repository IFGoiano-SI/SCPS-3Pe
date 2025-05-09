package Conexao;


import Environment.Environment; // Importa a classe Environment para obter as credenciais do banco de dados

/**
*   javac -cp lib/mysql-connector-java-8.0.30.jar src/Conexao/ConexaoDB.java
*   java -cp lib/mysql-connector-java-8.0.30.jar;src Conexao.ConexaoDB
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import Entidades.Cliente;

/**
 * Classe responsável por gerir a conexão com o banco de dados.
 */
public class ConexaoDB {

    /**
     * Estabelece uma nova conexão com o banco de dados.
     *
     * @return Objeto Connection se a conexão for bem-sucedida, ou null em caso de
     *         erro.
     */
    public static Connection getConexao() {
        try {
            return DriverManager.getConnection(Environment.getServidor(), Environment.getUsuario(),Environment.getSenha());
        } catch (SQLException e) {
            System.err.println("Erro ao estabelecer conexão: " + e.getMessage());
            return null;
        }
    }

    // Testei a conexão com o banco de dados
    // public static void main(String[] args) throws SQLException {

    //     try {
    //         Class.forName("com.mysql.cj.jdbc.Driver");
    //         System.out.println("Driver JDBC do MySQL carregado com sucesso!");
    //     } catch (ClassNotFoundException e) {
    //         System.err.println("Erro ao carregar o driver JDBC do MySQL: " + e.getMessage());
    //     }
        
    //     Connection connection = ConexaoDB().conexaoDB.getConexao();
    //     if (connection != null) {
    //         System.out.println("Conexão estabelecida com sucesso!");
    //         try {
    //             // Executa a consulta
    //             var rs = connection.createStatement().executeQuery("SELECT * FROM cliente");
                
    //             // Processa os resultados da consulta
    //             while (rs.next()) {
    //                 // Supondo que Cliente tenha id e nome como atributos
    //                 int id = rs.getInt("id_cliente");
    //                 String nome = rs.getString("nome");
    //                 System.out.println("ID: " + id + ", Nome: " + nome);
                    
    //                 // Você pode criar objetos Cliente aqui se necessário
    //                 // Cliente cliente = new Cliente(id, nome);
    //             }
    //             System.out.println("Consulta realizada com sucesso!");
    //         } catch (SQLException e) {
    //             System.err.println("Erro ao executar a consulta: " + e.getMessage());
    //         } finally {
    //             // Fecha a conexão
    //             conexaoDB.closeConexao(connection);
    //         }
    //     } else {
    //         System.out.println("Falha ao testar estabelecimento da conexão.");
    //     }
    // }

    /**
     * Encerra a conexão com o banco de dados.
     *
     * @param connection Conexão que será encerrada.
     */
    public void closeConexao(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }
}