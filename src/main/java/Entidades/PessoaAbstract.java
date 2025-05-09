package Entidades;

import java.time.LocalDateTime;

/**
 * Classe abstrata que representa uma pessoa com atributos básicos.
 * Deve ser estendida por classes que definem tipos específicos de pessoas.
 */
public abstract class PessoaAbstract {
    protected String nome;
    protected String telefone;
    protected String email;
    protected Endereco endereco;
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;

    /**
     * Construtor padrão que inicializa uma pessoa sem atributos definidos.
     */
    public PessoaAbstract() {}

    /**
     * Construtor que inicializa uma pessoa com os atributos fornecidos.
     *
     * @param nome Nome da pessoa.
     * @param telefone Telefone de contato da pessoa.
     * @param email Endereço de e-mail da pessoa.
     * @param endereco Endereço residencial da pessoa.
     * @param createdAt Data de criação do registro.
     * @param updatedAt Data de atualização do registro.
     */
    public PessoaAbstract(String nome, String telefone, String email, Endereco endereco, java.time.LocalDateTime createdAt, java.time.LocalDateTime updatedAt) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;}

    /**
     * Obtém o nome da pessoa.
     *
     * @return O nome da pessoa.
     */
    public String getNome() {
        return nome;}

    /**
     * Define o nome da pessoa.
     *
     * @param nome O novo nome da pessoa.
     */
    public void setNome(String nome) {
        this.nome = nome;}

    /**
     * Obtém o telefone de contato da pessoa.
     *
     * @return O telefone da pessoa.
     */
    public String getTelefone() {
        return telefone;}

    /**
     * Define o telefone de contato da pessoa.
     *
     * @param telefone O novo telefone da pessoa.
     */
    public void setTelefone(String telefone) {
        this.telefone = telefone;}

    /**
     * Obtém o endereço de e-mail da pessoa.
     *
     * @return O e-mail da pessoa.
     */
    public String getEmail() {
        return email;}

    /**
     * Define o endereço de e-mail da pessoa.
     *
     * @param email O novo e-mail da pessoa.
     */
    public void setEmail(String email) {
        this.email = email;}

    /**
     * Obtém o endereço residencial da pessoa.
     *
     * @return O endereço da pessoa.
     */
    public Endereco getEndereco() {
        return endereco;}

    /**
     * Define o endereço residencial da pessoa.
     *
     * @param endereco O novo endereço da pessoa.
     */
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;}

    /**
     * Obtém a data de criação do registro.
     *
     * @return A data de criação.
     */
    public java.time.LocalDateTime getCreatedAt() {
        return createdAt;}

    /**
     * Define a data de criação do registro.
     *
     * @param createdAt A nova data de criação.
     */
    public void setCreatedAt(java.time.LocalDateTime createdAt) {
        this.createdAt = createdAt;}

    /**
     * Obtém a data de atualização do registro.
     *
     * @return A data de atualização.
     */
    public java.time.LocalDateTime getUpdatedAt() {
        return updatedAt;}

    /**
     * Define a data de atualização do registro.
     *
     * @param updatedAt A nova data de atualização.
     */
    public void setUpdatedAt(java.time.LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;}

    /**
     * Retorna o tipo específico da pessoa.
     * Este método deve ser implementado pelas subclasses.
     *
     * @return O tipo da pessoa.
     */
    public abstract String getTipoPessoa();
}