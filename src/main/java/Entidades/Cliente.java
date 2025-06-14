package Entidades;

import java.time.LocalDateTime;

/**
 * Classe que representa um cliente, estendendo os atributos básicos de PessoaAbstract.
 */
public class Cliente extends PessoaAbstract {
    private int idCliente;
    private int ativo = 1;

    /**
     * Construtor padrão.
     */
    public Cliente() {}

    /**
     * Construtor que inicializa um cliente com os atributos fornecidos.
     *
     * @param idCliente Identificador único do cliente.
     * @param nome Nome do cliente.
     * @param telefone Telefone do cliente.
     * @param email E-mail do cliente.
     * @param endereco Endereço do cliente.
     * @param createdAt Data de criação do registro.
     * @param updatedAt Data de atualização do registro.
     */
    public Cliente(int idCliente, String nome, String telefone, String email, Endereco endereco, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(nome, telefone, email, endereco, createdAt, updatedAt);
        this.idCliente = idCliente;}

    /**
     * Obtém o identificador único do cliente.
     *
     * @return O identificador do cliente.
     */
    public int getIdCliente() {
        return idCliente;}

    /**
     * Define o identificador único do cliente.
     *
     * @param idCliente O novo identificador do cliente.
     */
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;}

    /**
     * Obtém o tipo de pessoa.
     *
     * @return O tipo de pessoa (Cliente).
     */
    @Override
    public String getTipoPessoa() {
        return "Cliente";}

    /**
     * Obtém o status de ativo do funcionário.
     *
     * @return O status de ativo do funcionário.
     */
    public int getAtivo() {
        return ativo;
    }

    /**
     * Define o status de ativo do funcionário.
     *
     * @param ativo O novo status de ativo do funcionário.
     */
    public void setAtivo(int ativo) {
        this.ativo = ativo;
    }

    /**
     * Sobrescrita do método {@code toString} para retornar os dados do cliente.
     * A string retornada contém o nome, ID, telefone, e-mail e endereço do cliente.
     *
     * @return String com os dados do cliente.
     */
    @Override
    public String toString() {
        return "\n Cliente: " + nome + '\n'
                + " ID: " + idCliente + '\n'
                + " Telefone: " + telefone + '\n'
                + " Email: " + email + '\n'
                + " Endereço: " + (endereco != null ? endereco.toString() : "Sem endereço cadastrado") + '\n'
                + "-----------------------------------------------------";}
}