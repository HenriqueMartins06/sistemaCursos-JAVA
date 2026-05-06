package dao;

import java.sql.*;
import java.util.*;
import conexao.Conexao;
import modelo.Aula;

public class AulaDAO {

    // Inserir nova aula
    public void inserir(Aula aula) {
        String sql = "INSERT INTO aulas (titulo, conteudo, id_curso) VALUES (?, ?, ?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, aula.getTitulo());
            stmt.setString(2, aula.getConteudo());
            stmt.setInt(3, aula.getIdCurso());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Listar TODAS as aulas (usado na parte do aluno!)
    public List<Aula> listar() {
        List<Aula> lista = new ArrayList<>();
        String sql = "SELECT a.*, c.nome AS nomeCurso FROM aulas a JOIN cursos c ON c.id_curso = a.id_curso";

        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Aula aula = new Aula();
                aula.setIdAula(rs.getInt("id_aula"));
                aula.setTitulo(rs.getString("titulo"));
                aula.setConteudo(rs.getString("conteudo"));
                aula.setIdCurso(rs.getInt("id_curso"));
                aula.setNomeCurso(rs.getString("nomeCurso"));
                lista.add(aula);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // Listar aulas SOMENTE do professor logado
    public List<Aula> listarSomenteDoProfessor(int idProfessor) {
        List<Aula> lista = new ArrayList<>();
        String sql =
            "SELECT a.*, c.nome AS nomeCurso FROM aulas a " +
            "JOIN cursos c ON c.id_curso = a.id_curso " +
            "WHERE c.id_professor = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idProfessor);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Aula aula = new Aula();
                aula.setIdAula(rs.getInt("id_aula"));
                aula.setTitulo(rs.getString("titulo"));
                aula.setConteudo(rs.getString("conteudo"));
                aula.setIdCurso(rs.getInt("id_curso"));
                aula.setNomeCurso(rs.getString("nomeCurso"));
                lista.add(aula);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // Atualizar aula
    public void atualizar(Aula aula) {
        String sql = "UPDATE aulas SET titulo=?, conteudo=?, id_curso=? WHERE id_aula=?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, aula.getTitulo());
            stmt.setString(2, aula.getConteudo());
            stmt.setInt(3, aula.getIdCurso());
            stmt.setInt(4, aula.getIdAula());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Excluir aula
    public void excluir(int idAula) {
        String sql = "DELETE FROM aulas WHERE id_aula=?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAula);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Buscar aula por ID
    public Aula buscarPorId(int id) {
        Aula aula = null;
        String sql = "SELECT a.*, c.nome AS nomeCurso FROM aulas a JOIN cursos c ON c.id_curso = a.id_curso WHERE a.id_aula = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                aula = new Aula();
                aula.setIdAula(rs.getInt("id_aula"));
                aula.setTitulo(rs.getString("titulo"));
                aula.setConteudo(rs.getString("conteudo"));
                aula.setIdCurso(rs.getInt("id_curso"));
                aula.setNomeCurso(rs.getString("nomeCurso"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return aula;
    }

    // Listar aulas de um curso (tela aluno)
    public List<Aula> listarPorCurso(int idCurso) {
        List<Aula> lista = new ArrayList<>();
        String sql = "SELECT a.*, c.nome AS nomeCurso FROM aulas a JOIN cursos c ON c.id_curso = a.id_curso WHERE a.id_curso = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCurso);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Aula aula = new Aula();
                aula.setIdAula(rs.getInt("id_aula"));
                aula.setTitulo(rs.getString("titulo"));
                aula.setConteudo(rs.getString("conteudo"));
                aula.setIdCurso(rs.getInt("id_curso"));
                aula.setNomeCurso(rs.getString("nomeCurso"));
                lista.add(aula);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
