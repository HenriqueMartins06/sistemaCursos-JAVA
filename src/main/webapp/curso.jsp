<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, modelo.Curso" %>

<%
    // Lógica de cache e sessão (Padrão que você usa)
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    if (session.getAttribute("id_usuario") == null) { response.sendRedirect("login.jsp"); return; }

    Curso cursoEditando = (Curso) request.getAttribute("curso");
    List<Curso> lista = (List<Curso>) request.getAttribute("listaCursos");
%>

<jsp:include page="header.jsp" />

<div class="container page-shell">

    <div class="hero-top mb-4">
        <span class="hero-kicker">
            <i class="bi bi-journal-bookmark-fill me-1"></i> Gestão Acadêmica
        </span>
        <h1 class="hero-title fw-bold">Gerenciar Cursos</h1>
        <p class="text-secondary">Cadastre novos cursos e organize os conteúdos do sistema.</p>
    </div>

    <div class="form-card mb-4">
        <h3 class="fw-bold mb-3"><%= cursoEditando != null ? "Editar Curso" : "Novo Curso" %></h3>

        <form action="curso" method="post">
            <input type="hidden" name="idCurso" value="<%= cursoEditando != null ? cursoEditando.getIdCurso() : "" %>">

            <div class="mb-3">
                <label class="form-label">Nome do curso</label>
                <input type="text" class="form-control" name="nome" 
                       value="<%= cursoEditando != null ? cursoEditando.getNome() : "" %>" required>
            </div>

            <div class="mb-4">
                <label class="form-label">Descrição</label>
                <textarea class="form-control" name="descricao" rows="4" 
                          placeholder="Fale brevemente sobre o que trata este curso..." required><%= cursoEditando != null ? cursoEditando.getDescricao() : "" %></textarea>
            </div>

            <button type="submit" class="btn btn-primary btn-entrar w-100">Salvar Curso</button>
        </form>
    </div>

    <div class="table-wrap p-0 overflow-hidden">
        <div class="p-4 border-bottom bg-light d-flex justify-content-between align-items-center">
            <h4 class="fw-bold m-0">Lista de Cursos</h4>
            <span class="badge bg-primary rounded-pill"><%= (lista != null) ? lista.size() : 0 %> Cursos</span>
        </div>

        <div class="table-responsive">
            <table class="table table-hover align-middle mb-0">
                <thead>
                    <tr>
                        <th class="ps-4">ID</th>
                        <th>Curso</th>
                        <th>Descrição</th>
                        <th class="text-center pe-4">Ações</th>
                    </tr>
                </thead>
                <tbody>
                <% if (lista != null && !lista.isEmpty()) {
                    for (Curso c : lista) { %>
                    <tr>
                        <td class="ps-4 text-secondary">#<%= c.getIdCurso() %></td>
                        <td>
                            <span class="curso-pill">
                                <i class="bi bi-journal-bookmark-fill me-1"></i>
                                <%= c.getNome() %>
                            </span>
                        </td>
                        <td><div class="text-muted" style="max-width: 300px; font-size: 0.9rem;"><%= c.getDescricao() %></div></td>
                        <td class="text-center pe-4">
                            <a href="curso?acao=editar&id=<%= c.getIdCurso() %>" class="btn btn-sm btn-outline-primary fw-bold me-1">Editar</a>
                            <a href="curso?acao=excluir&id=<%= c.getIdCurso() %>" class="btn btn-sm btn-outline-danger fw-bold" 
                               onclick="return confirm('Excluir curso?')">Excluir</a>
                        </td>
                    </tr>
                <% } } else { %>
                    <tr><td colspan="4" class="text-center p-5 text-secondary">Nenhum curso cadastrado no momento.</td></tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>

</body>
</html>