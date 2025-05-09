package Entidades;

/**
 * Classe que representa um endereço.
 */
public class Endereco {
    private int id;
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;

    /**
     * Construtor padrão.
     */
    public Endereco() {}

    /**
     * Construtor que inicializa um endereço com os atributos fornecidos.
     *
     * @param id     Identificador único do endereço.
     * @param rua    Nome da rua.
     * @param numero Número do endereço.
     * @param bairro Nome do bairro.
     * @param cidade Nome da cidade.
     * @param estado Sigla do estado.
     * @param cep    Código postal.
     */
    public Endereco(int id, String rua, String numero, String bairro, String cidade, String estado, String cep) {
        this.id = id;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;}

    /**
     * Obtém o identificador único do endereço.
     *
     * @return O identificador do endereço.
     */
    public int getId() {
        return id;}

    /**
     * Define o identificador único do endereço.
     *
     * @param id O novo identificador do endereço.
     */
    public void setId(int id) {
        this.id = id;}

    /**
     * Obtém o nome da rua do endereço.
     *
     * @return O nome da rua.
     */
    public String getRua() {
        return rua;}

    /**
     * Define o nome da rua do endereço.
     *
     * @param rua O novo nome da rua.
     */
    public void setRua(String rua) {
        this.rua = rua;}

    /**
     * Obtém o número do endereço.
     *
     * @return O número do endereço.
     */
    public String getNumero() {
        return numero;}

    /**
     * Define o número do endereço.
     *
     * @param numero O novo número do endereço.
     */
    public void setNumero(String numero) {
        this.numero = numero;}

    /**
     * Obtém o nome do bairro do endereço.
     *
     * @return O nome do bairro.
     */
    public String getBairro() {
        return bairro;}

    /**
     * Define o nome do bairro do endereço.
     *
     * @param bairro O novo nome do bairro.
     */
    public void setBairro(String bairro) {
        this.bairro = bairro;}

    /**
     * Obtém o nome da cidade do endereço.
     *
     * @return O nome da cidade.
     */
    public String getCidade() {
        return cidade;}

    /**
     * Define o nome da cidade do endereço.
     *
     * @param cidade O novo nome da cidade.
     */
    public void setCidade(String cidade) {
        this.cidade = cidade;}

    /**
     * Obtém a sigla do estado do endereço.
     *
     * @return A sigla do estado.
     */
    public String getEstado() {
        return estado;}

    /**
     * Define a sigla do estado do endereço.
     *
     * @param estado A nova sigla do estado.
     */
    public void setEstado(String estado) {
        this.estado = estado;}

    /**
     * Obtém o código postal (CEP) do endereço.
     *
     * @return O código postal (CEP).
     */
    public String getCep() {
        return cep;}

    /**
     * Define o código postal (CEP) do endereço.
     *
     * @param cep O novo código postal (CEP).
     */
    public void setCep(String cep) {
        this.cep = cep;}

    /**
     * Sobrescrita do método {@code toString} para retornar os dados do endereço.
     * A string retornada contém a rua, número, bairro, cidade, estado e CEP.
     *
     * @return String com os dados do endereço.
     */
    @Override
    public String toString() {
        return "\n | Rua: " + rua + '\n'
                + " | Número: " + numero + '\n'
                + " | Bairro: " + bairro + '\n'
                + " | Cidade: " + cidade + '\n'
                + " | Estado: " + estado + '\n'
                + " | CEP: " + cep + '\n'
                + "-----------------------------------------------------";}
}