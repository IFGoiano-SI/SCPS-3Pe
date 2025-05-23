package Environment;

public class EnvironmentExample { //Mude Para Environment
    // Constantes para conexão com o banco de dados
    private static final String SERVIDOR = "jdbc:mysql://localhost:3306/bd_scps";
    private static final String USUARIO = "root";
    private static final String SENHA = "password";

    //get
    public static String getServidor() {
        return SERVIDOR;
    }
    public static String getUsuario() {
        return USUARIO;
    }
    public static String getSenha() {
        return SENHA;
    }
}
