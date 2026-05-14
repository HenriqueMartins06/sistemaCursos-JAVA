package servlet;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.*;
import modelo.*;

@WebServlet("/comentarios")
public class ComentarioServlet extends HttpServlet {

    private final ComentarioDAO comentarioDAO = new ComentarioDAO();
    private final CursoDAO cursoDAO = new CursoDAO();
    private final AulaDAO aulaDAO = new AulaDAO();
    private final InscricaoDAO inscricaoDAO = new InscricaoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        if (session == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int idUsuario = (int) session.getAttribute("id_usuario");
        int tipoUsuario = (int) session.getAttribute("tipo_usuario");

        String acao = request.getParameter("acao");

        if (acao == null) {

            List<Curso> cursos = (tipoUsuario == 1)
                    ? cursoDAO.listarPorProfessor(idUsuario)
                    : cursoDAO.listar();

            List<Integer> idsCursos = cursos.stream()
                    .map(Curso::getIdCurso)
                    .collect(Collectors.toList());

            List<Comentario> comentarios = (tipoUsuario == 1)
                    ? comentarioDAO.listarPorCursos(idsCursos)
                    : comentarioDAO.listar();

            String cursoIdStr = request.getParameter("cursoId");

            if (cursoIdStr != null && !cursoIdStr.isEmpty()) {
                int cursoId = Integer.parseInt(cursoIdStr);
                List<Aula> aulas = aulaDAO.listarPorCurso(cursoId);
                request.setAttribute("listaAulas", aulas);
            }

            request.setAttribute("listaCursos", cursos);
            request.setAttribute("listaComentarios", comentarios);
            request.getRequestDispatcher("comentarios.jsp").forward(request, response);
            return;
        }

        if (acao.equals("editar")) {
            int id = Integer.parseInt(request.getParameter("id"));
            Comentario c = comentarioDAO.buscarPorId(id);

            if (c == null) {
                session.setAttribute("mensagemErro", "Comentário não encontrado.");
                response.sendRedirect("comentarios");
                return;
            }

            if (tipoUsuario == 2 && c.getIdUsuario() != idUsuario) {
                session.setAttribute("mensagemErro", "Você não pode editar comentários de outros usuários.");
                response.sendRedirect("comentarios");
                return;
            }

            List<Curso> cursos = (tipoUsuario == 1)
                    ? cursoDAO.listarPorProfessor(idUsuario)
                    : cursoDAO.listar();

            List<Aula> aulas = aulaDAO.listarPorCurso(c.getIdCurso());

            request.setAttribute("comentario", c);
            request.setAttribute("listaCursos", cursos);
            request.setAttribute("listaAulas", aulas);

            request.getRequestDispatcher("comentarios.jsp").forward(request, response);
            return;
        }

        if (acao.equals("excluir")) {
            int id = Integer.parseInt(request.getParameter("id"));
            Comentario c = comentarioDAO.buscarPorId(id);

            if (c == null) {
                session.setAttribute("mensagemErro", "Comentário não encontrado.");
                response.sendRedirect("comentarios");
                return;
            }

            if (tipoUsuario == 2 && c.getIdUsuario() != idUsuario) {
                session.setAttribute("mensagemErro", "Você não pode excluir comentários de outros usuários.");
                response.sendRedirect("comentarios");
                return;
            }

            comentarioDAO.excluir(id);
            session.setAttribute("mensagemSucesso", "Comentário excluído com sucesso.");
            response.sendRedirect("comentarios");
            return;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        if (session == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int idUsuario = (int) session.getAttribute("id_usuario");
        int tipoUsuario = (int) session.getAttribute("tipo_usuario");

        String idComentario = request.getParameter("idComentario");
        String texto = request.getParameter("texto");
        int idCurso = Integer.parseInt(request.getParameter("idCurso"));

        String idAulaStr = request.getParameter("idAula");
        Integer idAula = (idAulaStr == null || idAulaStr.isEmpty()) ? null : Integer.parseInt(idAulaStr);

        if (tipoUsuario == 2 && !inscricaoDAO.alunoEstaInscrito(idUsuario, idCurso)) {
            session.setAttribute("mensagemErro", "Você precisa estar inscrito no curso para comentar.");
            response.sendRedirect("comentarios");
            return;
        }

        if (tipoUsuario == 1 && !cursoDAO.professorTemCurso(idUsuario, idCurso)) {
            session.setAttribute("mensagemErro", "Você não pode comentar em um curso de outro professor.");
            response.sendRedirect("comentarios");
            return;
        }

        Comentario c = new Comentario();
        c.setIdCurso(idCurso);
        c.setIdAula(idAula);
        c.setTexto(texto);

        if (idComentario == null || idComentario.isEmpty()) {
            c.setIdUsuario(idUsuario);
            comentarioDAO.inserir(c);
            session.setAttribute("mensagemSucesso", "Comentário realizado com sucesso.");
        } else {
            Comentario antigo = comentarioDAO.buscarPorId(Integer.parseInt(idComentario));

            if (antigo == null) {
                session.setAttribute("mensagemErro", "Comentário não encontrado.");
                response.sendRedirect("comentarios");
                return;
            }

            if (tipoUsuario == 2 && antigo.getIdUsuario() != idUsuario) {
                session.setAttribute("mensagemErro", "Você não pode editar comentários de outros usuários.");
                response.sendRedirect("comentarios");
                return;
            }

            c.setIdComentario(Integer.parseInt(idComentario));
            comentarioDAO.atualizar(c);
            session.setAttribute("mensagemSucesso", "Comentário atualizado com sucesso.");
        }

        response.sendRedirect("comentarios");
    }
}