package Entidades;

import java.time.LocalDateTime;

/**
 * Classe que representa uma ordem de serviço.
 */
public class OrdemServico {
    private int idOrdem;
    private LocalDateTime dataAbertura;
    private LocalDateTime dataConclusao;
    private Cliente cliente;
    private Funcionario funcionario;
    private String descricao; // Adicionado o campo descrição
    private double valorPago; // Novo campo para armazenar o valor pago

    public enum Status {
        ABERTO,
        EM_EXECUCAO,
        CONCLUIDA,
        CANCELADA
    }

    private Status status = Status.ABERTO; // Define o status padrão como 'ABERTO'

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Construtor padrão.
     */
    public OrdemServico() {}

    /**
     * Construtor que inicializa uma ordem de serviço com os atributos fornecidos.
     *
     * @param idOrdem Identificador único da ordem de serviço.
     * @param dataAbertura Data de abertura da ordem.
     * @param dataConclusao Data de conclusão da ordem.
     * @param status Status da ordem de serviço.
     * @param cliente Cliente associado à ordem.
     * @param funcionario Funcionário responsável pela ordem.
     * @param createdAt Data de criação do registro.
     * @param updatedAt Data de atualização do registro.
     */
    public OrdemServico(int idOrdem, LocalDateTime dataAbertura, LocalDateTime dataConclusao, Status status, Cliente cliente, Funcionario funcionario, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.idOrdem = idOrdem;
        this.dataAbertura = dataAbertura;
        this.dataConclusao = dataConclusao;
        this.status = status;
        this.cliente = cliente;
        this.funcionario = funcionario;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Construtor que inicializa uma ordem de serviço com os atributos fornecidos, incluindo a descrição.
     *
     * @param idOrdem Identificador único da ordem de serviço.
     * @param dataAbertura Data de abertura da ordem.
     * @param dataConclusao Data de conclusão da ordem.
     * @param status Status da ordem de serviço.
     * @param cliente Cliente associado à ordem.
     * @param funcionario Funcionário responsável pela ordem.
     * @param descricao Descrição da ordem de serviço.
     * @param createdAt Data de criação do registro.
     * @param updatedAt Data de atualização do registro.
     */
    public OrdemServico(int idOrdem, LocalDateTime dataAbertura, LocalDateTime dataConclusao, Status status, Cliente cliente, Funcionario funcionario, String descricao, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.idOrdem = idOrdem;
        this.dataAbertura = dataAbertura;
        this.dataConclusao = dataConclusao;
        this.status = status;
        this.cliente = cliente;
        this.funcionario = funcionario;
        this.descricao = descricao;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Construtor que inicializa uma ordem de serviço com os atributos fornecidos, incluindo a descrição e o valor pago.
     *
     * @param idOrdem Identificador único da ordem de serviço.
     * @param dataAbertura Data de abertura da ordem.
     * @param dataConclusao Data de conclusão da ordem.
     * @param cliente Cliente associado à ordem.
     * @param funcionario Funcionário responsável pela ordem.
     * @param descricao Descrição da ordem de serviço.
     * @param valorPago Valor pago pela ordem de serviço.
     */
    public OrdemServico(int idOrdem, LocalDateTime dataAbertura, LocalDateTime dataConclusao, Cliente cliente, Funcionario funcionario, String descricao, double valorPago) {
        this.idOrdem = idOrdem;
        this.dataAbertura = dataAbertura;
        this.dataConclusao = dataConclusao;
        this.cliente = cliente;
        this.funcionario = funcionario;
        this.descricao = descricao;
        this.valorPago = valorPago;
    }

    /**
     * Construtor que inicializa uma ordem de serviço com os atributos fornecidos, incluindo a descrição, o valor pago e as datas de criação e atualização.
     *
     * @param idOrdem Identificador único da ordem de serviço.
     * @param dataAbertura Data de abertura da ordem.
     * @param dataConclusao Data de conclusão da ordem.
     * @param status Status da ordem de serviço.
     * @param cliente Cliente associado à ordem.
     * @param funcionario Funcionário responsável pela ordem.
     * @param descricao Descrição da ordem de serviço.
     * @param valorPago Valor pago pela ordem de serviço.
     * @param createdAt Data de criação do registro.
     * @param updatedAt Data de atualização do registro.
     */
    public OrdemServico(int idOrdem, LocalDateTime dataAbertura, LocalDateTime dataConclusao, Status status, Cliente cliente, Funcionario funcionario, String descricao, double valorPago, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.idOrdem = idOrdem;
        this.dataAbertura = dataAbertura;
        this.dataConclusao = dataConclusao;
        this.status = status;
        this.cliente = cliente;
        this.funcionario = funcionario;
        this.descricao = descricao;
        this.valorPago = valorPago;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Obtém o identificador único da ordem de serviço.
     *
     * @return O identificador da ordem de serviço.
     */
    public int getIdOrdem() {
        return idOrdem;}

    /**
     * Define o identificador único da ordem de serviço.
     *
     * @param idOrdem O novo identificador da ordem de serviço.
     */
    public void setIdOrdem(int idOrdem) {
        this.idOrdem = idOrdem;}

    /**
     * Obtém a data de abertura da ordem de serviço.
     *
     * @return A data de abertura.
     */
    public LocalDateTime getDataAbertura() {
        return dataAbertura;}

    /**
     * Define a data de abertura da ordem de serviço.
     *
     * @param dataAbertura A nova data de abertura.
     */
    public void setDataAbertura(LocalDateTime dataAbertura) {
        this.dataAbertura = dataAbertura;}

    /**
     * Obtém a data de conclusão da ordem de serviço.
     *
     * @return A data de conclusão.
     */
    public LocalDateTime getDataConclusao() {
        return dataConclusao;}

    /**
     * Define a data de conclusão da ordem de serviço.
     *
     * @param dataConclusao A nova data de conclusão.
     */
    public void setDataConclusao(LocalDateTime dataConclusao) {
        this.dataConclusao = dataConclusao;}

    /**
     * Obtém o status da ordem de serviço.
     *
     * @return O status da ordem de serviço.
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Define o status da ordem de serviço.
     *
     * @param status O novo status da ordem de serviço.
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Obtém o cliente associado à ordem de serviço.
     *
     * @return O cliente associado.
     */
    public Cliente getCliente() {
        return cliente;}

    /**
     * Define o cliente associado à ordem de serviço.
     *
     * @param cliente O novo cliente associado.
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;}

    /**
     * Obtém o funcionário responsável pela ordem de serviço.
     *
     * @return O funcionário responsável.
     */
    public Funcionario getFuncionario() {
        return funcionario;}

    /**
     * Define o funcionário responsável pela ordem de serviço.
     *
     * @param funcionario O novo funcionário responsável.
     */
    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;}

    /**
     * Obtém a descrição da ordem de serviço.
     *
     * @return A descrição.
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Define a descrição da ordem de serviço.
     *
     * @param descricao A nova descrição.
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Obtém o valor pago pela ordem de serviço.
     *
     * @return O valor pago.
     */
    public double getValorPago() {
        return valorPago;
    }

    /**
     * Define o valor pago pela ordem de serviço.
     *
     * @param valorPago O novo valor pago.
     */
    public void setValorPago(double valorPago) {
        this.valorPago = valorPago;
    }

    /**
     * Obtém a data de criação do registro.
     *
     * @return A data de criação.
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;}

    /**
     * Define a data de criação do registro.
     *
     * @param createdAt A nova data de criação.
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;}

    /**
     * Obtém a data de atualização do registro.
     *
     * @return A data de atualização.
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;}

    /**
     * Define a data de atualização do registro.
     *
     * @param updatedAt A nova data de atualização.
     */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;}

    /**
     * Sobrescrita do método {@code toString} para retornar os dados da ordem de serviço.
     * A string retornada contém o ID, datas, status, cliente, funcionário, serviço associados e valor pago.
     *
     * @return String com os dados da ordem de serviço.
     */
    @Override
    public String toString() {
        return "\n Ordem de Serviço: " + idOrdem + '\n'
                + " Data de Abertura: " + dataAbertura + '\n'
                + " Data de Conclusão: " + (dataConclusao != null ? dataConclusao : "Não concluída") + '\n'
                + " Status: " + status + '\n'
                + " Cliente: " + (cliente != null ? cliente.getNome() : "Sem cliente associado") + '\n'
                + " Funcionário: " + (funcionario != null ? funcionario.getNome() : "Sem funcionário associado") + '\n'
                + " Descrição: " + (descricao != null ? descricao : "Sem descrição") + '\n'
                + " Valor Pago: " + valorPago + '\n'
                + "-----------------------------------------------------";
    }
}