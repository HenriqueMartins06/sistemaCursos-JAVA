package servlet;

import dao.AulaDAO;
import dao.CursoDAO;
import dao.InscricaoDAO;
import modelo.Aula;
import modelo.Curso;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

@WebServlet("/aulaAluno")
public class AulaAlunoServlet extends HttpServlet {

    private AulaDAO aulaDAO = new AulaDAO();
    private InscricaoDAO inscricaoDAO = new InscricaoDAO();
    private CursoDAO cursoDAO = new CursoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessao = request.getSession(false);

        if (sessao == null || sessao.getAttribute("id_usuario") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int idAluno = (int) sessao.getAttribute("id_usuario");

        // Cursos em que o aluno está inscrito
        List<Integer> cursosInscrito = inscricaoDAO.listarCursosDoAluno(idAluno);

        // Carrega informações completas dos cursos
        List<Curso> cursos = new ArrayList<>();
        for (int idCurso : cursosInscrito) {
            Curso c = cursoDAO.buscarPorId(idCurso);
            if (c != null) cursos.add(c);
        }

        request.setAttribute("cursosAluno", cursos);

        // Se clicou em um curso, carregar aulas
        String idCursoParam = request.getParameter("curso");
        if (idCursoParam != null) {
            int idCurso = Integer.parseInt(idCursoParam);
            List<Aula> aulas = aulaDAO.listarPorCurso(idCurso);
            request.setAttribute("aulasCurso", aulas);
        }

        RequestDispatcher rd = request.getRequestDispatcher("aula_aluno.jsp");
        rd.forward(request, response);
    }
}
