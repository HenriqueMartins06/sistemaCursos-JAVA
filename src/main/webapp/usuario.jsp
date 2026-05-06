<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, modelo.Usuario" %>

<%
    // Lógica de cache e sessão padrão
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    if (session.getAttribute("id_usuario") == null) { response.sendRedirect("login.jsp"); return; }

    Usuario usuarioEdit = (Usuario) request.getAttribute("usuario");
    List<Usuario> lista = (List<Usuario>) request.getAttribute("listaUsuarios");
%>

<jsp:include page="header.jsp" />

<div class="container page-shell">

    <div class="hero-top mb-4">
        <span class="hero-kicker">
            <i class="bi bi-people-fill me-1"></i> Administração
        </span>
        <h1 class="hero-title fw-bold">Gerenciar Usuários</h1>
        <p class="text-secondary">Cadastre, edite e acompanhe os perfis do sistema.</p>
    </div>

    <div class="form-card mb-4">
        <h3 class="fw-bold mb-3"><%= usuarioEdit != null ? "Editar Usuário" : "Novo Usuário" %></h3>

        <form action="usuario" method="post">
            <input type="hidden" name="idUsuario" value="<%= usuarioEdit != null ? usuarioEdit.getIdUsuario() : "" %>">

            <div class="row">
                <div class="col-md-6 mb-3">
                    <label class="form-label">Nome</label>
                    <input type="text" name="nome" class="form-control" value="<%= usuarioEdit != null ? usuarioEdit.getNome() : "" %>" required>
                </div>
                <div class="col-md-6 mb-3">
                    <label class="form-label">Email</label>
                    <input type="email" name="email" class="form-control" value="<%= usuarioEdit != null ? usuarioEdit.getEmail() : "" %>" required>
                </div>
            </div>

            <div class="row">
                <div class="col-md-6 mb-3">
                    <label class="form-label">Senha</label>
                    <input type="password" name="senha" class="form-control" value="<%= usuarioEdit != null ? usuarioEdit.getSenha() : "" %>" required>
                </div>
                <div class="col-md-6 mb-4">
                    <label class="form-label">Tipo de Usuário</label>
                    <select name="tipo_usuario" class="form-select" required>
                        <option value="1" <%= (usuarioEdit != null && usuarioEdit.getTipoUsuario() == 1) ? "selected" : "" %>>Professor</option>
                        <option value="2" <%= (usuarioEdit != null && usuarioEdit.getTipoUsuario() == 2) ? "selected" : "" %>>Aluno</option>
                    </select>
                </div>
            </div>

            <button type="submit" class="btn btn-primary btn-entrar w-100">Salvar Usuário</button>
        </form>
    </div>

    <div class="table-wrap p-0 overflow-hidden">
        <div class="p-4 border-bottom bg-light">
            <h4 class="fw-bold m-0">Lista de Usuários</h4>
        </div>

        <div class="table-responsive">
            <table class="table table-hover align-middle mb-0">
                <thead>
                    <tr>
                        <th class="ps-4">ID</th>
                        <th>Nome</th>
                        <th>Email</th>
                        <th>Tipo</th>
                        <th class="text-center pe-4">Ações</th>
                    </tr>
                </thead>
                <tbody>
                <% if (lista != null && !lista.isEmpty()) {
                    for (Usuario u : lista) { %>
                    <tr>
                        <td class="ps-4 text-secondary">#<%= u.getIdUsuario() %></td>
                        <td><strong><%= u.getNome() %></strong></td>
                        <td><%= u.getEmail() %></td>
                        <td>
                            <% if (u.getTipoUsuario() == 1) { %>
                                <span class="curso-pill" style="background:#ede9fe; color:#7c3aed; border-color:#ddd6fe;">Professor</span>
                            <% } else { %>
                                <span class="curso-pill">Aluno</span>
                            <% } %>
                        </td>
                        <td class="text-center pe-4">
                            <a href="usuario?acao=editar&id=<%= u.getIdUsuario() %>" class="btn btn-sm btn-outline-primary fw-bold me-1">Editar</a>
                            <a href="usuario?acao=excluir&id=<%= u.getIdUsuario() %>" class="btn btn-sm btn-outline-danger fw-bold" onclick="return confirm('Excluir usuário?')">Excluir</a>
                        </td>
                    </tr>
                <% } } else { %>
                    <tr><td colspan="5" class="text-center p-5 text-secondary">Nenhum usuário encontrado.</td></tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>

</body>
</html>