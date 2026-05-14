package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.CursoDAO;
import dao.InscricaoDAO;
import dao.UsuarioDAO;
import modelo.Curso;
import modelo.Inscricao;
import modelo.Usuario;

@WebServlet("/inscricao")
public class InscricaoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private InscricaoDAO inscricaoDAO = new InscricaoDAO();
    private CursoDAO cursoDAO = new CursoDAO();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Integer idAlunoSessao = session != null ? (Integer) session.getAttribute("id_usuario") : null;

        if (idAlunoSessao == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Usuario aluno = usuarioDAO.buscarPorId(idAlunoSessao);
        request.setAttribute("aluno", aluno);

        request.setAttribute("listaCursos", cursoDAO.listar());

        String acao = request.getParameter("acao");

        if (acao == null) {
            List<Inscricao> lista = inscricaoDAO.listarPorAluno(idAlunoSessao);
            request.setAttribute("listaInscricao", lista);
            request.getRequestDispatcher("inscricao.jsp").forward(request, response);
            return;
        }

        if ("excluir".equals(acao)) {
            int idCurso = Integer.parseInt(request.getParameter("id_curso"));

            inscricaoDAO.excluir(idAlunoSessao, idCurso);
            request.getSession().setAttribute("mensagemSucesso", "Inscrição excluída com sucesso.");
            response.sendRedirect("inscricao");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Integer idUsuarioSessao = session != null ? (Integer) session.getAttribute("id_usuario") : null;

        if (idUsuarioSessao == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int idUsuario = idUsuarioSessao;
        int idCurso = Integer.parseInt(request.getParameter("id_curso"));

        if (inscricaoDAO.alunoEstaInscrito(idUsuario, idCurso)) {
            request.getSession().setAttribute("mensagemErro", "Você já está inscrito neste curso.");
            response.sendRedirect("inscricao");
            return;
        }

        String dataAtual = LocalDate.now().toString();

        Inscricao i = new Inscricao();
        i.setIdUsuario(idUsuario);
        i.setIdCurso(idCurso);
        i.setDataInscricao(dataAtual);

        inscricaoDAO.inserir(i);
        request.getSession().setAttribute("mensagemSucesso", "Inscrição realizada com sucesso.");

        response.sendRedirect("inscricao");
    }
}