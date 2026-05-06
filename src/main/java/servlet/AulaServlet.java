package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.AulaDAO;
import dao.CursoDAO;
import modelo.Aula;
import modelo.Curso;

@WebServlet("/aula")
public class AulaServlet extends HttpServlet {

    private AulaDAO aulaDAO = new AulaDAO();
    private CursoDAO cursoDAO = new CursoDAO(); // para listar cursos no select

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	request.setCharacterEncoding("UTF-8");
    	response.setCharacterEncoding("UTF-8");
    	
        HttpSession session = request.getSession();
        Integer idProfessor = (Integer) session.getAttribute("id_usuario");

        String acao = request.getParameter("acao");

        // lista
        if (acao == null) {

            // AQUI: só aulas do professor
            List<Aula> lista = aulaDAO.listarSomenteDoProfessor(idProfessor);

            // AQUI: só cursos do professor
            List<Curso> cursos = cursoDAO.listarPorProfessor(idProfessor);

            request.setAttribute("listaAulas", lista);
            request.setAttribute("listaCursos", cursos);

            RequestDispatcher rd = request.getRequestDispatcher("aula_professor.jsp");
            rd.forward(request, response);
        }

        // EDITAR
        else if (acao.equals("editar")) {
            int id = Integer.parseInt(request.getParameter("id"));
            Aula aula = aulaDAO.buscarPorId(id);

            request.setAttribute("aula", aula);

            // Lista cursos do professor
            request.setAttribute("listaCursos", cursoDAO.listarPorProfessor(idProfessor));

            RequestDispatcher rd = request.getRequestDispatcher("aula_professor.jsp");
            rd.forward(request, response);
        }

        // excluirr
        else if (acao.equals("excluir")) {
            int id = Integer.parseInt(request.getParameter("id"));
            aulaDAO.excluir(id);
            response.sendRedirect("aula");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idAula = request.getParameter("idAula");
        String titulo = request.getParameter("titulo");
        String conteudo = request.getParameter("conteudo");
        int idCurso = Integer.parseInt(request.getParameter("id_curso"));

        Aula aula = new Aula();
        aula.setTitulo(titulo);
        aula.setConteudo(conteudo);
        aula.setIdCurso(idCurso);

        // atualiza
        if (idAula != null && !idAula.isEmpty()) {
            aula.setIdAula(Integer.parseInt(idAula));
            aulaDAO.atualizar(aula);
        }
        // Inseri
        else {
            aulaDAO.inserir(aula);
        }

        response.sendRedirect("aula");
    }
}
