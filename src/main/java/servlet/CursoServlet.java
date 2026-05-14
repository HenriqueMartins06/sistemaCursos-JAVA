package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.CursoDAO;
import modelo.Curso;

@WebServlet("/curso")
public class CursoServlet extends HttpServlet {

    private CursoDAO cursoDAO = new CursoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	request.setCharacterEncoding("UTF-8");
    	response.setCharacterEncoding("UTF-8");
    	
        HttpSession session = request.getSession();
        Integer tipoUsuario = (Integer) session.getAttribute("tipo_usuario");

        // Só professor acessa
        if (tipoUsuario == null || tipoUsuario != 1) {
            response.sendRedirect("home_aluno.jsp");
            return;
        }

        String acao = request.getParameter("acao");

        if (acao == null) {

            Integer idProfessor = (Integer) session.getAttribute("id_usuario");
            List<Curso> lista = cursoDAO.listarPorProfessor(idProfessor);

            request.setAttribute("listaCursos", lista);
            RequestDispatcher rd = request.getRequestDispatcher("curso.jsp");
            rd.forward(request, response);
        }
        else if (acao.equals("editar")) {
            int id = Integer.parseInt(request.getParameter("id"));
            Curso curso = cursoDAO.buscarPorId(id);
            request.setAttribute("curso", curso);
            request.getRequestDispatcher("curso.jsp").forward(request, response);
        }
        else if (acao.equals("excluir")) {
            int id = Integer.parseInt(request.getParameter("id"));
            cursoDAO.excluir(id);
            request.getSession().setAttribute("mensagemSucesso", "Curso excluído com sucesso.");
            response.sendRedirect("curso");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Integer idProfessor = (Integer) session.getAttribute("id_usuario");

        // VERIFICA SE ESTÁ LOGADO
        if (idProfessor == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String idCurso = request.getParameter("idCurso");
        String nome = request.getParameter("nome");
        String descricao = request.getParameter("descricao");

        Curso curso = new Curso();
        curso.setNome(nome);
        curso.setDescricao(descricao);
        curso.setIdProfessor(idProfessor);

        if (idCurso != null && !idCurso.isEmpty()) {
            curso.setIdCurso(Integer.parseInt(idCurso));
            cursoDAO.atualizar(curso);
            request.getSession().setAttribute("mensagemSucesso", "Curso atualizado com sucesso.");
        } else {
            cursoDAO.inserir(curso);
            request.getSession().setAttribute("mensagemSucesso", "Curso cadastrado com sucesso.");
        }

        response.sendRedirect("curso");
    }
}
