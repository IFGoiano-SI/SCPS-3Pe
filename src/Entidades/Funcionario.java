package Entidades;

 import java.time.LocalDateTime;

 /**
  * Classe que representa um funcionário, estendendo os atributos básicos de PessoaAbstract.
  */
 public class Funcionario extends PessoaAbstract {
     private int idFuncionario;
     private String cargo;
     private Usuario usuario;

     /**
      * Construtor padrão.
      */
     public Funcionario() {}

     /**
      * Construtor que inicializa um funcionário com os atributos fornecidos.
      *
      * @param idFuncionario Identificador único do funcionário.
      * @param nome Nome do funcionário.
      * @param telefone Telefone do funcionário.
      * @param email E-mail do funcionário.
      * @param endereco Endereço do funcionário.
      * @param cargo Cargo do funcionário.
      * @param usuario Objeto do usuário associado ao funcionário.
      * @param createdAt Data de criação do registro.
      * @param updatedAt Data de atualização do registro.
      */
     public Funcionario(int idFuncionario, String nome, String telefone, String email, Endereco endereco, String cargo, Usuario usuario, LocalDateTime createdAt, LocalDateTime updatedAt) {
         super(nome, telefone, email, endereco, createdAt, updatedAt);
         this.idFuncionario = idFuncionario;
         this.cargo = cargo;
         this.usuario = usuario;}

     /**
      * Obtém o identificador único do funcionário.
      *
      * @return O identificador do funcionário.
      */
     public int getIdFuncionario() {
         return idFuncionario;}

     /**
      * Define o identificador único do funcionário.
      *
      * @param idFuncionario O novo identificador do funcionário.
      */
     public void setIdFuncionario(int idFuncionario) {
         this.idFuncionario = idFuncionario;}

     /**
      * Obtém o cargo do funcionário.
      *
      * @return O cargo do funcionário.
      */
     public String getCargo() {
         return cargo;}

     /**
      * Define o cargo do funcionário.
      *
      * @param cargo O novo cargo do funcionário.
      */
     public void setCargo(String cargo) {
         this.cargo = cargo;}

     /**
      * Obtém o identificador do usuário associado ao funcionário.
      *
      * @return O identificador do usuário.
      */
     public Usuario getUsuario() {
         return usuario;}

     /**
      * Define o identificador do usuário associado ao funcionário.
      *
      * @param usuario O novo identificador do usuário.
      */
     public void setUsuario(Usuario usuario) {
         this.usuario = usuario;}

     /**
      * Obtém o tipo de pessoa.
      *
      * @return O tipo de pessoa.
      */
     @Override
     public String getTipoPessoa() {
         return "Funcionario";}

     /**
      * Sobrescrita do método {@code toString} para retornar os dados do funcionário.
      * A string retornada contém o nome, ID, cargo, telefone, e-mail e usuário associado.
      *
      * @return String com os dados do funcionário.
      */
     @Override
     public String toString() {
         return "\n Funcionário: " + nome + '\n'
                 + " ID: " + idFuncionario + '\n'
                 + " Cargo: " + cargo + '\n'
                 + " Telefone: " + telefone + '\n'
                 + " Email: " + email + '\n'
                 + " Usuário: " + (usuario != null ? usuario.getNomeUsuario() : "Sem usuário associado") + '\n'
                 + "-----------------------------------------------------";}
 }