package dao;

import conexao.Conexao;
import modelo.Comentario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComentarioDAO {

    private static final String CAMPOS_JOIN =
            "SELECT c.*, u.nome AS nomeUsuario, " +
            "IF(u.tipo_usuario = 1, 'Professor', 'Aluno') AS tipoUsuario, " +
            "cu.nome AS nomeCurso, a.titulo AS nomeAula " +
            "FROM comentarios c " +
            "JOIN usuarios u ON u.id_usuario = c.id_usuario " +
            "LEFT JOIN cursos cu ON cu.id_curso = c.id_curso " +
            "LEFT JOIN aulas a ON a.id_aula = c.id_aula ";

    private Comentario map(ResultSet rs) throws SQLException {
        Comentario c = new Comentario();

        c.setIdComentario(rs.getInt("id_comentario"));
        c.setIdUsuario(rs.getInt("id_usuario"));

        int idC = rs.getInt("id_curso");
        c.setIdCurso(rs.wasNull() ? null : idC);

        int idA = rs.getInt("id_aula");
        c.setIdAula(rs.wasNull() ? null : idA);

        c.setTexto(rs.getString("texto"));
        c.setDataComentario(rs.getString("data_comentario"));
        c.setNomeUsuario(rs.getString("nomeUsuario"));
        c.setTipoUsuario(rs.getString("tipoUsuario"));
        c.setNomeCurso(rs.getString("nomeCurso"));
        c.setNomeAula(rs.getString("nomeAula"));

        return c;
    }

    // LISTAR
    public List<Comentario> listar() {
        String sql = CAMPOS_JOIN + "ORDER BY c.id_comentario DESC";
        List<Comentario> lista = new ArrayList<>();

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) lista.add(map(rs));

        } catch (SQLException e) { e.printStackTrace(); }

        return lista;
    }

    // LISTAR POR CURSOS
    public List<Comentario> listarPorCursos(List<Integer> idsCursos) {

        if (idsCursos == null || idsCursos.isEmpty()) return new ArrayList<>();

        String placeholders = String.join(",", idsCursos.stream().map(x -> "?").toArray(String[]::new));

        String sql = CAMPOS_JOIN + "WHERE c.id_curso IN (" + placeholders + ") ORDER BY c.id_comentario DESC";

        List<Comentario> lista = new ArrayList<>();

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < idsCursos.size(); i++)
                stmt.setInt(i + 1, idsCursos.get(i));

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) lista.add(map(rs));

        } catch (SQLException e) { e.printStackTrace(); }

        return lista;
    }

    // BUSCAR POR ID
    public Comentario buscarPorId(int id) {
        String sql = CAMPOS_JOIN + "WHERE c.id_comentario = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) return map(rs);

        } catch (SQLException e) { e.printStackTrace(); }

        return null;
    }

    // INSERIR
    public void inserir(Comentario c) {
        String sql = "INSERT INTO comentarios (id_usuario, id_curso, id_aula, texto, data_comentario) " +
                     "VALUES (?, ?, ?, ?, NOW())";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, c.getIdUsuario());
            stmt.setObject(2, c.getIdCurso(), Types.INTEGER);
            stmt.setObject(3, c.getIdAula(), Types.INTEGER);
            stmt.setString(4, c.getTexto());

            stmt.executeUpdate();

        } catch (SQLException e) { e.printStackTrace(); }
    }

    // ATUALIZAR
    public void atualizar(Comentario c) {
        String sql = "UPDATE comentarios SET id_curso = ?, id_aula = ?, texto = ? WHERE id_comentario = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, c.getIdCurso(), Types.INTEGER);
            stmt.setObject(2, c.getIdAula(), Types.INTEGER);
            stmt.setString(3, c.getTexto());
            stmt.setInt(4, c.getIdComentario());

            stmt.executeUpdate();

        } catch (SQLException e) { e.printStackTrace(); }
    }

    // EXCLUIR
    public void excluir(int id) {
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM comentarios WHERE id_comentario = ?")) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) { e.printStackTrace(); }
    }
}
