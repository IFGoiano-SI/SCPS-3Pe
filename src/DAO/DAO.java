package DAO;

    import java.util.List;

    /**
     * ‘Interface’ para definir operações de acesso a dados (DAO).
     *
     * @param <T> Tipo da entidade que será manipulada.
     */
    public interface DAO<T> {

        /**
         * Adiciona uma nova entidade ao banco de dados.
         *
         * @param entidade Entidade que será adicionada.
         * @return true se a entidade for adicionada com sucesso, false caso contrário.
         */
        int inserir(T entidade);

        /**
         * Atualiza os dados de uma entidade existente no banco de dados.
         *
         * @param entidade Entidade com os dados atualizados.
         * @return true se a entidade for atualizada com sucesso, false caso contrário.
         */
        boolean atualizar(T entidade);

        /**
         * Remove uma entidade do banco de dados com base no identificador fornecido.
         *
         * @param id Identificador da entidade que será removida.
         * @return true se a entidade for removida com sucesso, false caso contrário.
         */
        boolean remover(int id);

        /**
         * Recupera uma entidade do banco de dados com base no identificador fornecido.
         *
         * @param id Identificador da entidade que será buscada.
         * @return A entidade correspondente ao identificador ou null se não for encontrada.
         */
        T buscarPorId(int id);

        /**
         * Retorna todas as entidades armazenadas no banco de dados.
         *
         * @return Lista contendo todas as entidades.
         */
        List<T> listarTodos();
    }