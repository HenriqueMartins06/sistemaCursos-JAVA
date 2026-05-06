package servlet;

import dao.UsuarioDAO;
import modelo.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String email = req.getParameter("email");
        String senha = req.getParameter("senha");

        UsuarioDAO dao = new UsuarioDAO();
        List<Usuario> lista = dao.listar();

        for (Usuario u : lista) {
            if (u.getEmail().equals(email) && u.getSenha().equals(senha)) {

                HttpSession sessao = req.getSession();
                sessao.setAttribute("id_usuario", u.getIdUsuario());
                sessao.setAttribute("nome", u.getNome());
                sessao.setAttribute("tipo_usuario", u.getTipoUsuario());

                if (u.getTipoUsuario() == 1) {
                    resp.sendRedirect("home_professor.jsp");
                } else {
                    resp.sendRedirect("home_aluno.jsp");
                }

                return;
            }
        }

        resp.getWriter().println("Login inválido");
    }
}