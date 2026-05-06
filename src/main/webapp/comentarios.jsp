<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, modelo.Comentario, modelo.Curso, modelo.Aula" %>

<%
    // Cache e Sessão (Padronizado)
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    HttpSession sessao = request.getSession(false);
    if (sessao == null || sessao.getAttribute("id_usuario") == null) { response.sendRedirect("login.jsp"); return; }

    int idLogado = (int) sessao.getAttribute("id_usuario");
    int tipoUsuario = (int) sessao.getAttribute("tipo_usuario");

    List<Curso> cursos = (List<Curso>) request.getAttribute("listaCursos");
    List<Aula> aulas = (List<Aula>) request.getAttribute("listaAulas");
    List<Comentario> comentarios = (List<Comentario>) request.getAttribute("listaComentarios");
    Comentario coment = (Comentario) request.getAttribute("comentario");

    Integer cursoSelecionado = null;
    if (coment != null) { cursoSelecionado = coment.getIdCurso(); } 
    else if (request.getParameter("cursoId") != null && !request.getParameter("cursoId").isEmpty()) {
        cursoSelecionado = Integer.parseInt(request.getParameter("cursoId"));
    }
%>

<jsp:include page="header.jsp" />

<div class="container page-shell">

    <div class="hero-top mb-4">
        <div class="hero-kicker"><i class="bi bi-chat-left-quote-fill"></i> Interações</div>
        <h1 class="hero-title fw-bold">Comentários</h1>
        <p class="text-secondary">Deixe seu feedback sobre os cursos e aulas.</p>

        <div class="mini-info-grid">
            <div class="mini-info-card">
                <div class="mini-info-label">Total de Interações</div>
                <p class="mini-info-value"><%= (comentarios != null) ? comentarios.size() : 0 %></p>
            </div>
        </div>
    </div>

    <div class="form-card mb-4">
        <h3 class="fw-bold mb-3"><%= coment != null ? "Editar Comentário" : "Novo Comentário" %></h3>

        <form action="comentarios" method="post">
            <input type="hidden" name="idComentario" value="<%= coment != null ? coment.getIdComentario() : "" %>">

            <div class="row">
                <div class="col-md-6 mb-3">
                    <label class="form-label">Curso</label>
                    <select class="form-select" name="idCurso" required onchange="location.href='comentarios?cursoId=' + this.value;">
                        <option value="">-- Escolha um curso --</option>
                        <% if (cursos != null) { for (Curso c : cursos) { %>
                            <option value="<%= c.getIdCurso() %>" <%= (cursoSelecionado != null && cursoSelecionado == c.getIdCurso()) ? "selected" : "" %>><%= c.getNome() %></option>
                        <% } } %>
                    </select>
                </div>

                <div class="col-md-6 mb-3">
                    <label class="form-label">Aula (opcional)</label>
                    <select class="form-select" name="idAula">
                        <option value="">-- Comentário geral do curso --</option>
                        <% if (aulas != null) { for (Aula a : aulas) { %>
                            <option value="<%= a.getIdAula() %>" <%= (coment != null && coment.getIdAula() != null && coment.getIdAula() == a.getIdAula()) ? "selected" : "" %>><%= a.getTitulo() %></option>
                        <% } } %>
                    </select>
                </div>
            </div>

            <div class="mb-4">
                <label class="form-label">Seu Comentário</label>
                <textarea class="form-control" name="texto" rows="4" required><%= coment != null ? coment.getTexto() : "" %></textarea>
            </div>

            <button type="submit" class="btn btn-primary btn-entrar w-100">Publicar Comentário</button>
        </form>
    </div>

    <div class="table-wrap p-0 overflow-hidden">
        <div class="p-4 border-bottom bg-light">
            <h4 class="fw-bold m-0">Últimas Interações</h4>
        </div>

        <div class="table-responsive">
            <table class="table table-hover align-middle mb-0">
                <thead>
                    <tr>
                        <th class="ps-4">Autor</th>
                        <th>Tipo</th>
                        <th>Local</th>
                        <th>Mensagem</th>
                        <th class="text-center pe-4">Ações</th>
                    </tr>
                </thead>
                <tbody>
                <% if (comentarios != null && !comentarios.isEmpty()) {
                    for (Comentario c : comentarios) { %>
                    <tr>
                        <td class="ps-4"><strong><%= c.getNomeUsuario() %></strong></td>
                        <td>
                            <span class="tipo-pill <%= "Professor".equalsIgnoreCase(c.getTipoUsuario()) ? "tipo-professor" : "tipo-aluno" %>">
                                <%= c.getTipoUsuario() %>
                            </span>
                        </td>
                        <td>
                            <small class="d-block fw-bold text-primary"><%= c.getNomeCurso() %></small>
                            <small class="text-muted"><%= c.getNomeAula() != null ? c.getNomeAula() : "Geral" %></small>
                        </td>
                        <td><div class="comentario-texto"><%= c.getTexto() %></div></td>
                        <td class="text-center pe-4">
                            <% if (tipoUsuario == 1 || (tipoUsuario == 2 && c.getIdUsuario() == idLogado)) { %>
                                <a href="comentarios?acao=editar&id=<%= c.getIdComentario() %>" class="btn btn-sm btn-outline-primary fw-bold me-1">Editar</a>
                                <a href="comentarios?acao=excluir&id=<%= c.getIdComentario() %>" class="btn btn-sm btn-outline-danger fw-bold" onclick="return confirm('Excluir?');">Excluir</a>
                            <% } else { %>
                                <span class="text-muted small">Sem permissão</span>
                            <% } %>
                        </td>
                    </tr>
                <% } } else { %>
                    <tr><td colspan="5" class="text-center p-5 text-secondary">Nenhum comentário por aqui.</td></tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>