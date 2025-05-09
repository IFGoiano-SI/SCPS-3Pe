package Entidades;

import java.time.LocalDateTime;

/**
 * Classe que representa um usuário do sistema.
 */
public class Usuario {
    private int idUsuario;
    private String nomeUsuario;
    private String senha;
    private String tipoUsuario;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Construtor padrão.
     */
    public Usuario() {
    }

    /**
     * Construtor que inicializa um usuário com os atributos fornecidos.
     *
     * @param idUsuario   Identificador único do usuário.
     * @param nomeUsuario Nome de usuário.
     * @param senha       Senha do usuário.
     * @param tipoUsuario Tipo do usuário (ADMIN, FUNCIONARIO, CLIENTE).
     * @param createdAt   Data de criação do registro.
     * @param updatedAt   Data de atualização do registro.
     */
    public Usuario(int idUsuario, String nomeUsuario, String senha, String tipoUsuario, java.time.LocalDateTime createdAt, java.time.LocalDateTime updatedAt) {
        this.idUsuario = idUsuario;
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
        this.tipoUsuario = tipoUsuario;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Usuario(int idUsuario, String nomeUsuario, String tipoUsuario, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.idUsuario = idUsuario;
        this.nomeUsuario = nomeUsuario;
        this.tipoUsuario = tipoUsuario;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    /**
     * Obtém o identificador único do usuário.
     *
     * @return O identificador do usuário.
     */
    public int getIdUsuario() {
        return idUsuario;
    }

    /**
     * Define o identificador único do usuário.
     *
     * @param idUsuario O novo identificador do usuário.
     */
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * Obtém o nome de usuário.
     *
     * @return O nome de usuário.
     */
    public String getNomeUsuario() {
        return nomeUsuario;
    }

    /**
     * Define o nome de usuário.
     *
     * @param nomeUsuario O novo nome de usuário.
     */
    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    /**
     * Obtém a senha do usuário.
     *
     * @return A senha do usuário.
     */
    public String getSenha() {
        return senha;
    }

    /**
     * Define a senha do usuário.
     *
     * @param senha A nova senha do usuário.
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * Obtém o tipo do usuário.
     *
     * @return O tipo do usuário.
     */
    public String getTipoUsuario() {
        return tipoUsuario;
    }

    /**
     * Define o tipo do usuário.
     *
     * @param tipoUsuario O novo tipo do usuário.
     */
    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    /**
     * Obtém a data de criação do registro.
     *
     * @return A data de criação.
     */
    public java.time.LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Define a data de criação do registro.
     *
     * @param createdAt A nova data de criação.
     */
    public void setCreatedAt(java.time.LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Obtém a data de atualização do registro.
     *
     * @return A data de atualização.
     */
    public java.time.LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Define a data de atualização do registro.
     *
     * @param updatedAt A nova data de atualização.
     */
    public void setUpdatedAt(java.time.LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Sobrescrita do método {@code toString} para retornar os dados do usuário.
     * A string retornada contém o ID, nome de usuário e tipo de usuário.
     *
     * @return String com os dados do usuário.
     */
    @Override
    public String toString() {
        return "\n | Usuário: " + nomeUsuario + '\n'
                + " | ID: " + idUsuario + '\n'
                + " | Tipo: " + tipoUsuario + '\n'
                + "-----------------------------------------------------";
    }
}