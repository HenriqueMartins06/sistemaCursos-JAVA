<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, modelo.Aula, modelo.Curso" %>

<%
    // Lógica de cache e sessão
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    HttpSession sessao = request.getSession(false);
    if (sessao == null || sessao.getAttribute("id_usuario") == null) { 
        response.sendRedirect("login.jsp"); 
        return; 
    }

    List<Curso> cursos = (List<Curso>) request.getAttribute("listaCursos");
    Aula aulaEdit = (Aula) request.getAttribute("aula");
    List<Aula> lista = (List<Aula>) request.getAttribute("listaAulas");
%>

<jsp:include page="header.jsp" />

<div class="container page-shell">

    <div class="hero-top mb-4">
        <span class="hero-kicker">
            <i class="bi bi-easel-fill me-1"></i> Conteúdo do Curso
        </span>
        <h1 class="hero-title fw-bold">Gerenciar Aulas</h1>
        <p class="text-secondary">Cadastre aulas e vincule-as aos cursos ativos.</p>
    </div>

    <div class="form-card mb-4">
        <h3 class="fw-bold mb-3"><%= aulaEdit != null ? "Editar Aula" : "Nova Aula" %></h3>

        <form action="aula" method="post">
            <input type="hidden" name="idAula" value="<%= aulaEdit != null ? aulaEdit.getIdAula() : "" %>">

            <div class="mb-3">
                <label class="form-label">Título da aula</label>
                <input type="text" class="form-control" name="titulo" 
                       placeholder="Ex: Introdução ao Java"
                       value="<%= aulaEdit != null ? aulaEdit.getTitulo() : "" %>" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Conteúdo / Descrição</label>
                <textarea class="form-control" name="conteudo" rows="4" 
                          placeholder="Descreva o que será ensinado nesta aula..." required><%= aulaEdit != null ? aulaEdit.getConteudo() : "" %></textarea>
            </div>

            <div class="mb-4">
                <label class="form-label">Curso vinculado</label>
                <select class="form-select" name="id_curso" required>
                    <option value="">Selecione o curso...</option>
                    <% if (cursos != null) {
                        for (Curso c : cursos) {
                            boolean sel = (aulaEdit != null && aulaEdit.getIdCurso() == c.getIdCurso()); %>
                            <option value="<%= c.getIdCurso() %>" <%= sel ? "selected" : "" %>><%= c.getNome() %></option>
                    <% } } %>
                </select>
            </div>

            <button type="submit" class="btn btn-primary btn-entrar w-100">Salvar Aula</button>
        </form>
    </div>

    <div class="table-wrap p-0 overflow-hidden">
        <div class="p-4 border-bottom bg-light d-flex justify-content-between align-items-center">
            <h4 class="fw-bold m-0">Lista de Aulas</h4>
            <span class="badge bg-primary rounded-pill"><%= (lista != null) ? lista.size() : 0 %> Aulas</span>
        </div>

        <div class="table-responsive">
            <table class="table table-hover align-middle mb-0">
                <thead>
                    <tr>
                        <th class="ps-4">ID</th>
                        <th>Título</th>
                        <th>Curso</th>
                        <th>Conteúdo</th>
                        <th class="text-center pe-4">Ações</th>
                    </tr>
                </thead>
                <tbody>
                <% if (lista != null && !lista.isEmpty()) {
                    for (Aula a : lista) { %>
                    <tr>
                        <td class="ps-4 text-secondary">#<%= a.getIdAula() %></td>
                        <td><strong><%= a.getTitulo() %></strong></td>
                        <td>
                            <span class="curso-pill">
                                <i class="bi bi-bookmark-fill me-1"></i>
                                <%= a.getNomeCurso() %>
                            </span>
                        </td>
                        <td><div class="text-muted" style="max-width: 250px; font-size: 0.85rem; line-height: 1.4;"><%= a.getConteudo() %></div></td>
                        <td class="text-center pe-4">
                            <a href="aula?acao=editar&id=<%= a.getIdAula() %>" class="btn btn-sm btn-outline-primary fw-bold me-1">Editar</a>
                            <a href="aula?acao=excluir&id=<%= a.getIdAula() %>" class="btn btn-sm btn-outline-danger fw-bold" 
                               onclick="return confirm('Excluir esta aula?')">Excluir</a>
                        </td>
                    </tr>
                <% } } else { %>
                    <tr><td colspan="5" class="text-center p-5 text-secondary">Nenhuma aula cadastrada.</td></tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>

</body>
</html>