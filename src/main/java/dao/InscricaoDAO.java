package dao;

import java.sql.*;
import java.util.*;
import conexao.Conexao;
import modelo.Inscricao;

public class InscricaoDAO {

    // Inserir nova inscrição
    public void inserir(Inscricao i) {
        String sql = "INSERT INTO inscricoes (id_usuario, id_curso, data_inscricao) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, i.getIdUsuario());
            stmt.setInt(2, i.getIdCurso());
            stmt.setString(3, i.getDataInscricao());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 🔥 LISTAR INSCRIÇÕES DE UM ALUNO (AGORA MOSTRA NOME DO CURSO!)
    public List<Inscricao> listarPorAluno(int idAluno) {
        List<Inscricao> lista = new ArrayList<>();

        String sql =
            "SELECT i.id_usuario, i.id_curso, i.data_inscricao, " +
            "       u.nome AS nome_aluno, c.nome AS nome_curso " +
            "FROM inscricoes i " +
            "INNER JOIN usuarios u ON u.id_usuario = i.id_usuario " +
            "INNER JOIN cursos c ON c.id_curso = i.id_curso " +
            "WHERE i.id_usuario = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAluno);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Inscricao i = new Inscricao();
                i.setIdUsuario(rs.getInt("id_usuario"));
                i.setIdCurso(rs.getInt("id_curso"));
                i.setDataInscricao(rs.getString("data_inscricao"));
                i.setNomeAluno(rs.getString("nome_aluno"));   // novo
                i.setNomeCurso(rs.getString("nome_curso"));   // novo
                lista.add(i);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // Buscar inscrição específica
    public Inscricao buscar(int idUsuario, int idCurso) {
        String sql = "SELECT * FROM inscricoes WHERE id_usuario = ? AND id_curso = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idCurso);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Inscricao i = new Inscricao();
                i.setIdUsuario(rs.getInt("id_usuario"));
                i.setIdCurso(rs.getInt("id_curso"));
                i.setDataInscricao(rs.getString("data_inscricao"));
                return i;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Atualizar inscrição
    public void atualizar(Inscricao i) {
        String sql = "UPDATE inscricoes SET data_inscricao=? WHERE id_usuario=? AND id_curso=?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, i.getDataInscricao());
            stmt.setInt(2, i.getIdUsuario());
            stmt.setInt(3, i.getIdCurso());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Excluir inscrição
    public void excluir(int idUsuario, int idCurso) {
        String sql = "DELETE FROM inscricoes WHERE id_usuario=? AND id_curso=?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idCurso);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Verificar se aluno já está inscrito
    public boolean alunoEstaInscrito(int idUsuario, int idCurso) {
        String sql = "SELECT 1 FROM inscricoes WHERE id_usuario = ? AND id_curso = ? LIMIT 1";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idCurso);
            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Cursos nos quais o aluno está inscrito
    public List<Integer> listarCursosDoAluno(int idUsuario) {
        List<Integer> cursos = new ArrayList<>();
        String sql = "SELECT id_curso FROM inscricoes WHERE id_usuario = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                cursos.add(rs.getInt("id_curso"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cursos;
    }
}
