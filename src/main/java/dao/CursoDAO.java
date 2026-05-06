package dao;

import java.sql.*;
import java.util.*;
import conexao.Conexao;
import modelo.Curso;
import dao.UsuarioDAO;
import modelo.Usuario;


public class CursoDAO {

    // Inserir novo curso
    public void inserir(Curso curso) {
        String sql = "INSERT INTO cursos (nome, descricao, id_professor) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, curso.getNome());
            stmt.setString(2, curso.getDescricao());
            stmt.setInt(3, curso.getIdProfessor());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Listar todos os cursos
    public List<Curso> listar() {
        List<Curso> lista = new ArrayList<>();
        String sql = "SELECT * FROM cursos ORDER BY id_curso DESC";

        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

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

 
    // LISTAR CURSOS DE UM PROFESSOR
   
    public List<Curso> listarPorProfessor(int idProfessor) {
        List<Curso> lista = new ArrayList<>();
        String sql = "SELECT * FROM cursos WHERE id_professor = ? ORDER BY id_curso DESC";

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

  
    // VERIFICAR SE CURSO E  DO PROFESS
 
    public boolean professorTemCurso(int idProfessor, int idCurso) {
        String sql = "SELECT COUNT(*) AS total FROM cursos WHERE id_professor = ? AND id_curso = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idProfessor);
            stmt.setInt(2, idCurso);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total") > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // caso der erro, evita liberar acesso
    }

    // Buscar curso por ID
    public Curso buscarPorId(int id) {
        Curso curso = null;
        String sql = "SELECT * FROM cursos WHERE id_curso = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                curso = new Curso();
                curso.setIdCurso(rs.getInt("id_curso"));
                curso.setNome(rs.getString("nome"));
                curso.setDescricao(rs.getString("descricao"));
                curso.setIdProfessor(rs.getInt("id_professor"));

                // 🔥 pegar nome do professor
                UsuarioDAO udao = new UsuarioDAO();
                Usuario prof = udao.buscarPorId(curso.getIdProfessor());
                if (prof != null) {
                    curso.setNomeProfessor(prof.getNome());
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return curso;
    }


    // Atualizar curso
    public void atualizar(Curso curso) {
        String sql = "UPDATE cursos SET nome=?, descricao=?, id_professor=? WHERE id_curso=?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, curso.getNome());
            stmt.setString(2, curso.getDescricao());
            stmt.setInt(3, curso.getIdProfessor());
            stmt.setInt(4, curso.getIdCurso());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Excluir curso
    public void excluir(int idCurso) {
        String sql = "DELETE FROM cursos WHERE id_curso=?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCurso);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
