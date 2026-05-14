package dao;

import java.sql.*;
import java.util.*;

import conexao.Conexao;
import modelo.Nota;
import modelo.Usuario;
import modelo.Curso;

public class NotaDAO {

    public void inserir(Nota nota) {
        String sql = "INSERT INTO notas (id_usuario, id_curso, nota) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, nota.getIdUsuario());
            stmt.setInt(2, nota.getIdCurso());
            stmt.setDouble(3, nota.getNota());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizar(Nota nota) {
        String sql = "UPDATE notas SET id_usuario=?, id_curso=?, nota=? WHERE id_nota=?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, nota.getIdUsuario());
            stmt.setInt(2, nota.getIdCurso());
            stmt.setDouble(3, nota.getNota());
            stmt.setInt(4, nota.getIdNota());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void excluir(int idNota) {
        String sql = "DELETE FROM notas WHERE id_nota=?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idNota);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Nota buscarPorId(int idNota) {
        String sql = "SELECT * FROM notas WHERE id_nota=?";
        Nota nota = null;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idNota);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                nota = new Nota();
                nota.setIdNota(rs.getInt("id_nota"));
                nota.setIdUsuario(rs.getInt("id_usuario"));
                nota.setIdCurso(rs.getInt("id_curso"));
                nota.setNota(rs.getDouble("nota"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nota;
    }
    
    public boolean existeNota(int idUsuario, int idCurso, int idNotaIgnorar) {
        String sql = "SELECT COUNT(*) AS total FROM notas WHERE id_usuario = ? AND id_curso = ? AND id_nota <> ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idCurso);
            stmt.setInt(3, idNotaIgnorar);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("total") > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Nota> listarPorProfessor(int idProfessor) {
        List<Nota> lista = new ArrayList<>();

        String sql =
            "SELECT n.id_nota, n.id_usuario, n.id_curso, n.nota, " +
            "u.nome AS nome_aluno, c.nome AS nome_curso " +
            "FROM notas n " +
            "INNER JOIN usuarios u ON u.id_usuario = n.id_usuario " +
            "INNER JOIN cursos c ON c.id_curso = n.id_curso " +
            "WHERE c.id_professor = ? " +
            "ORDER BY c.nome, u.nome";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idProfessor);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Nota nota = new Nota();
                nota.setIdNota(rs.getInt("id_nota"));
                nota.setIdUsuario(rs.getInt("id_usuario"));
                nota.setIdCurso(rs.getInt("id_curso"));
                nota.setNota(rs.getDouble("nota"));
                nota.setNomeAluno(rs.getString("nome_aluno"));
                nota.setNomeCurso(rs.getString("nome_curso"));
                lista.add(nota);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<Nota> listarPorAluno(int idAluno) {
        List<Nota> lista = new ArrayList<>();

        String sql =
            "SELECT n.id_nota, n.id_usuario, n.id_curso, n.nota, " +
            "u.nome AS nome_aluno, c.nome AS nome_curso " +
            "FROM notas n " +
            "INNER JOIN usuarios u ON u.id_usuario = n.id_usuario " +
            "INNER JOIN cursos c ON c.id_curso = n.id_curso " +
            "WHERE n.id_usuario = ? " +
            "ORDER BY c.nome";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAluno);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Nota nota = new Nota();
                nota.setIdNota(rs.getInt("id_nota"));
                nota.setIdUsuario(rs.getInt("id_usuario"));
                nota.setIdCurso(rs.getInt("id_curso"));
                nota.setNota(rs.getDouble("nota"));
                nota.setNomeAluno(rs.getString("nome_aluno"));
                nota.setNomeCurso(rs.getString("nome_curso"));
                lista.add(nota);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<Usuario> listarAlunos() {
        List<Usuario> lista = new ArrayList<>();

        String sql = "SELECT * FROM usuarios WHERE tipo_usuario = 2 ORDER BY nome";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setTipoUsuario(rs.getInt("tipo_usuario"));
                lista.add(usuario);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<Curso> listarCursosDoProfessor(int idProfessor) {
        List<Curso> lista = new ArrayList<>();

        String sql = "SELECT * FROM cursos WHERE id_professor = ? ORDER BY nome";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idProfessor);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Curso curso = new Curso();
                curso.setIdCurso(rs.getInt("id_curso"));
                curso.setNome(rs.getString("nome"));
                curso.setDescricao(rs.getString("descricao"));
                curso.setIdProfessor(rs.getInt("id_professor"));
                lista.add(curso);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}