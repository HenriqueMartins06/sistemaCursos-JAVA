<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, modelo.Nota, modelo.Usuario, modelo.Curso" %>

<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

    if (session.getAttribute("id_usuario") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    Nota notaEditando = (Nota) request.getAttribute("nota");
    List<Nota> listaNotas = (List<Nota>) request.getAttribute("listaNotas");
    List<Usuario> listaAlunos = (List<Usuario>) request.getAttribute("listaAlunos");
    List<Curso> listaCursos = (List<Curso>) request.getAttribute("listaCursos");
%>

<jsp:include page="header.jsp" />

<div class="container page-shell">

    <div class="hero-top mb-4">
        <span class="hero-kicker">
            <i class="bi bi-award-fill me-1"></i> Avaliação
        </span>
        <h1 class="hero-title fw-bold">Gerenciar Notas</h1>
        <p class="text-secondary">Atribua, edite e acompanhe as notas dos alunos nos cursos.</p>
    </div>

    <div class="form-card mb-4">
        <h3 class="fw-bold mb-3"><%= notaEditando != null ? "Editar Nota" : "Nova Nota" %></h3>

        <form action="notas" method="post">
            <input type="hidden" name="idNota" value="<%= notaEditando != null ? notaEditando.getIdNota() : "" %>">

            <div class="row">
                <div class="col-md-4 mb-3">
                    <label class="form-label">Aluno</label>
                    <select name="id_usuario" class="form-select" required>
                        <option value="">Selecione um aluno</option>

                        <% if (listaAlunos != null) {
                            for (Usuario u : listaAlunos) {
                                boolean selecionado = notaEditando != null && notaEditando.getIdUsuario() == u.getIdUsuario();
                        %>
                            <option value="<%= u.getIdUsuario() %>" <%= selecionado ? "selected" : "" %>>
                                <%= u.getNome() %>
                            </option>
                        <% } } %>
                    </select>
                </div>

                <div class="col-md-4 mb-3">
                    <label class="form-label">Curso</label>
                    <select name="id_curso" class="form-select" required>
                        <option value="">Selecione um curso</option>

                        <% if (listaCursos != null) {
                            for (Curso c : listaCursos) {
                                boolean selecionado = notaEditando != null && notaEditando.getIdCurso() == c.getIdCurso();
                        %>
                            <option value="<%= c.getIdCurso() %>" <%= selecionado ? "selected" : "" %>>
                                <%= c.getNome() %>
                            </option>
                        <% } } %>
                    </select>
                </div>

                <div class="col-md-4 mb-4">
                    <label class="form-label">Nota</label>
                    <input type="number" name="nota" class="form-control" min="0" max="10" step="0.01"
                           value="<%= notaEditando != null ? notaEditando.getNota() : "" %>" required>
                </div>
            </div>

            <button type="submit" class="btn btn-primary btn-entrar w-100">
                <%= notaEditando != null ? "Atualizar Nota" : "Salvar Nota" %>
            </button>
        </form>
    </div>

    <div class="table-wrap p-0 overflow-hidden">
        <div class="p-4 border-bottom bg-light d-flex justify-content-between align-items-center">
            <h4 class="fw-bold m-0">Notas Lançadas</h4>
            <span class="badge bg-primary rounded-pill"><%= listaNotas != null ? listaNotas.size() : 0 %> Notas</span>
        </div>

        <div class="table-responsive">
            <table class="table table-hover align-middle mb-0">
                <thead>
                    <tr>
                        <th class="ps-4">ID</th>
                        <th>Aluno</th>
                        <th>Curso</th>
                        <th>Nota</th>
                        <th class="text-center pe-4">Ações</th>
                    </tr>
                </thead>

                <tbody>
                <% if (listaNotas != null && !listaNotas.isEmpty()) {
                    for (Nota n : listaNotas) { %>

                    <tr>
                        <td class="ps-4 text-secondary">#<%= n.getIdNota() %></td>

                        <td>
                            <strong><%= n.getNomeAluno() %></strong>
                        </td>

                        <td>
                            <span class="curso-pill">
                                <i class="bi bi-journal-bookmark-fill me-1"></i>
                                <%= n.getNomeCurso() %>
                            </span>
                        </td>

                        <td>
                            <span class="curso-pill" style="background:#ecfdf5; color:#15803d; border-color:#bbf7d0;">
                                <i class="bi bi-star-fill me-1"></i>
                                <%= n.getNotaFormatada() %>
                            </span>
                        </td>

                        <td class="text-center pe-4">
                            <a href="notas?acao=editar&id=<%= n.getIdNota() %>"
                               class="btn btn-sm btn-outline-primary fw-bold me-1">
                                Editar
                            </a>

                            <a href="notas?acao=excluir&id=<%= n.getIdNota() %>"
                               class="btn btn-sm btn-outline-danger fw-bold"
                               onclick="return confirm('Excluir nota?')">
                                Excluir
                            </a>
                        </td>
                    </tr>

                <% } } else { %>

                    <tr>
                        <td colspan="5" class="text-center p-5 text-secondary">
                            Nenhuma nota cadastrada no momento.
                        </td>
                    </tr>

                <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>

</body>
</html>