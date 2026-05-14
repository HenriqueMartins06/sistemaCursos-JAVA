package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.NotaDAO;
import dao.CursoDAO;
import dao.InscricaoDAO;
import modelo.Nota;
import modelo.Usuario;
import modelo.Curso;

@WebServlet("/notas")
public class NotaServlet extends HttpServlet {

    private NotaDAO notaDAO = new NotaDAO();
    private CursoDAO cursoDAO = new CursoDAO();
    private InscricaoDAO inscricaoDAO = new InscricaoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("id_usuario") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int idUsuario = (int) session.getAttribute("id_usuario");
        int tipoUsuario = (int) session.getAttribute("tipo_usuario");

        if (tipoUsuario == 2) {
            List<Nota> lista = notaDAO.listarPorAluno(idUsuario);
            request.setAttribute("listaNotas", lista);
            request.getRequestDispatcher("notas_aluno.jsp").forward(request, response);
            return;
        }

        String acao = request.getParameter("acao");

        if (acao == null) {
            carregarTelaProfessor(request, response, idUsuario, null);
            return;
        }

        if (acao.equals("editar")) {
            int idNota = Integer.parseInt(request.getParameter("id"));
            Nota nota = notaDAO.buscarPorId(idNota);

            if (nota == null) {
                session.setAttribute("mensagemErro", "Nota não encontrada.");
                response.sendRedirect("notas");
                return;
            }

            if (!cursoDAO.professorTemCurso(idUsuario, nota.getIdCurso())) {
                session.setAttribute("mensagemErro", "Você não pode editar nota de um curso de outro professor.");
                response.sendRedirect("notas");
                return;
            }

            carregarTelaProfessor(request, response, idUsuario, nota);
            return;
        }

        if (acao.equals("excluir")) {
            int idNota = Integer.parseInt(request.getParameter("id"));
            Nota nota = notaDAO.buscarPorId(idNota);

            if (nota == null) {
                session.setAttribute("mensagemErro", "Nota não encontrada.");
                response.sendRedirect("notas");
                return;
            }

            if (!cursoDAO.professorTemCurso(idUsuario, nota.getIdCurso())) {
                session.setAttribute("mensagemErro", "Você não pode excluir nota de um curso de outro professor.");
                response.sendRedirect("notas");
                return;
            }

            notaDAO.excluir(idNota);
            session.setAttribute("mensagemSucesso", "Nota excluída com sucesso.");
            response.sendRedirect("notas");
            return;
        }

        response.sendRedirect("notas");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("id_usuario") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int idProfessor = (int) session.getAttribute("id_usuario");
        int tipoUsuario = (int) session.getAttribute("tipo_usuario");

        if (tipoUsuario != 1) {
            session.setAttribute("mensagemErro", "Apenas professores podem lançar notas.");
            response.sendRedirect("notas");
            return;
        }

        String idNotaStr = request.getParameter("idNota");
        int idUsuario = Integer.parseInt(request.getParameter("id_usuario"));
        int idCurso = Integer.parseInt(request.getParameter("id_curso"));
        double valorNota = Double.parseDouble(request.getParameter("nota").replace(",", "."));

        if (valorNota < 0 || valorNota > 10) {
            session.setAttribute("mensagemErro", "A nota deve estar entre 0 e 10.");
            response.sendRedirect("notas");
            return;
        }

        if (!cursoDAO.professorTemCurso(idProfessor, idCurso)) {
            session.setAttribute("mensagemErro", "Você só pode lançar notas em cursos cadastrados por você.");
            response.sendRedirect("notas");
            return;
        }

        if (!inscricaoDAO.alunoEstaInscrito(idUsuario, idCurso)) {
            session.setAttribute("mensagemErro", "Este aluno não está inscrito no curso selecionado.");
            response.sendRedirect("notas");
            return;
        }

        int idNotaIgnorar = 0;

        if (idNotaStr != null && !idNotaStr.isEmpty()) {
            idNotaIgnorar = Integer.parseInt(idNotaStr);
        }

        if (notaDAO.existeNota(idUsuario, idCurso, idNotaIgnorar)) {
            session.setAttribute("mensagemErro", "Este aluno já possui nota cadastrada para este curso.");
            response.sendRedirect("notas");
            return;
        }

        Nota nota = new Nota();
        nota.setIdUsuario(idUsuario);
        nota.setIdCurso(idCurso);
        nota.setNota(valorNota);

        if (idNotaStr != null && !idNotaStr.isEmpty()) {
            nota.setIdNota(Integer.parseInt(idNotaStr));
            notaDAO.atualizar(nota);
            session.setAttribute("mensagemSucesso", "Nota atualizada com sucesso.");
        } else {
            notaDAO.inserir(nota);
            session.setAttribute("mensagemSucesso", "Nota cadastrada com sucesso.");
        }

        response.sendRedirect("notas");
    }

    private void carregarTelaProfessor(HttpServletRequest request, HttpServletResponse response, int idProfessor, Nota notaEditando)
            throws ServletException, IOException {

        List<Nota> listaNotas = notaDAO.listarPorProfessor(idProfessor);
        List<Usuario> listaAlunos = notaDAO.listarAlunos();
        List<Curso> listaCursos = notaDAO.listarCursosDoProfessor(idProfessor);

        request.setAttribute("nota", notaEditando);
        request.setAttribute("listaNotas", listaNotas);
        request.setAttribute("listaAlunos", listaAlunos);
        request.setAttribute("listaCursos", listaCursos);

        request.getRequestDispatcher("notas_professor.jsp").forward(request, response);
    }
}