package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UsuarioDAO;
import modelo.Usuario;

@WebServlet("/usuario")
public class UsuarioServlet extends HttpServlet {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
        String acao = request.getParameter("acao");

        // LISTAR
        if (acao == null) {
            List<Usuario> lista = usuarioDAO.listar();
            request.setAttribute("listaUsuarios", lista);
            RequestDispatcher rd = request.getRequestDispatcher("usuario.jsp");
            rd.forward(request, response);
        }

        // EDITAR
        else if (acao.equals("editar")) {
            int id = Integer.parseInt(request.getParameter("id"));
            Usuario usuario = usuarioDAO.buscarPorId(id);

            request.setAttribute("usuario", usuario);
            RequestDispatcher rd = request.getRequestDispatcher("usuario.jsp");
            rd.forward(request, response);
        }

        // Excluir
        else if (acao.equals("excluir")) {
            int id = Integer.parseInt(request.getParameter("id"));
            usuarioDAO.excluir(id);
            request.getSession().setAttribute("mensagemSucesso", "Usuário excluído com sucesso.");
            response.sendRedirect("usuario");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idUsuario = request.getParameter("idUsuario");
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        int tipo = Integer.parseInt(request.getParameter("tipo_usuario"));

        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        usuario.setTipoUsuario(tipo);
        
        // atualiazr
        if (idUsuario != null && !idUsuario.isEmpty()) {
            usuario.setIdUsuario(Integer.parseInt(idUsuario));
            usuarioDAO.atualizar(usuario);
            request.getSession().setAttribute("mensagemSucesso", "Usuário atualizado com sucesso.");
            
         // Inserir
        } else {
            usuarioDAO.inserir(usuario);
            request.getSession().setAttribute("mensagemSucesso", "Usuário cadastrado com sucesso.");
        }

        response.sendRedirect("usuario");
    }
}
