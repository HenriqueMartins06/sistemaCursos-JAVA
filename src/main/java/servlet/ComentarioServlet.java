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
        if (session == null) { response.sendRedirect("login.jsp"); return; }

        int idUsuario = (int) session.getAttribute("id_usuario");
        int tipoUsuario = (int) session.getAttribute("tipo_usuario");

        String acao = request.getParameter("acao");

        // LISTAR
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

            //  Carrega aula ao escolher curso
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

        // Edita / verifica a permisao 
        if (acao.equals("editar")) {
            int id = Integer.parseInt(request.getParameter("id"));
            Comentario c = comentarioDAO.buscarPorId(id);

            if (c == null) { response.sendRedirect("comentarios"); return; }

            // PROFESSOR pode tudo
            // ALUNO só pode editar o próprio comentário
            if (tipoUsuario == 2 && c.getIdUsuario() != idUsuario) {
                response.getWriter().println("ERRO: Você não pode editar comentários de outros usuários.");
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

        // Excluir/ ver permisao
        if (acao.equals("excluir")) {
            int id = Integer.parseInt(request.getParameter("id"));
            Comentario c = comentarioDAO.buscarPorId(id);

            if (c == null) { response.sendRedirect("comentarios"); return; }

            // Professor pode excluir qualquer
            // Aluno só pode excluir o seu
            if (tipoUsuario == 2 && c.getIdUsuario() != idUsuario) {
                response.getWriter().println("ERRO: Você não pode excluir comentários de outros usuários.");
                return;
            }

            comentarioDAO.excluir(id);
            response.sendRedirect("comentarios");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null) { response.sendRedirect("login.jsp"); return; }

        int idUsuario = (int) session.getAttribute("id_usuario");
        int tipoUsuario = (int) session.getAttribute("tipo_usuario");

        String idComentario = request.getParameter("idComentario");
        String texto = request.getParameter("texto");
        int idCurso = Integer.parseInt(request.getParameter("idCurso"));

        String idAulaStr = request.getParameter("idAula");
        Integer idAula = (idAulaStr == null || idAulaStr.isEmpty()) ? null : Integer.parseInt(idAulaStr);

        // Aluno tem q ta inscrito
        if (tipoUsuario == 2 && !inscricaoDAO.alunoEstaInscrito(idUsuario, idCurso)) {
            response.getWriter().println("ERRO: Você não está inscrito neste curso.");
            return;
        }

        // professor tem q ser o dono do curso
        if (tipoUsuario == 1 && !cursoDAO.professorTemCurso(idUsuario, idCurso)) {
            response.getWriter().println("ERRO: Você não pode comentar neste curso.");
            return;
        }

        Comentario c = new Comentario();
        c.setIdCurso(idCurso);
        c.setIdAula(idAula);
        c.setTexto(texto);

        // NOVO COMENTÁRIO
        if (idComentario == null || idComentario.isEmpty()) {
            c.setIdUsuario(idUsuario);
            comentarioDAO.inserir(c);
        }
        // edita e verifica a permisao 
        else {
            Comentario antigo = comentarioDAO.buscarPorId(Integer.parseInt(idComentario));

            if (antigo == null) {
                response.sendRedirect("comentarios");
                return;
            }

            // aluno so edita o proprio
            if (tipoUsuario == 2 && antigo.getIdUsuario() != idUsuario) {
                response.getWriter().println("ERRO: Você não pode editar comentários de outros usuários.");
                return;
            }

            c.setIdComentario(Integer.parseInt(idComentario));
            comentarioDAO.atualizar(c);
        }

        response.sendRedirect("comentarios");
    }
}
