<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, modelo.Inscricao, modelo.Curso, modelo.Usuario" %>

<%
    // Lógica de cache e sessão 
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    if (session.getAttribute("id_usuario") == null) { response.sendRedirect("login.jsp"); return; }

    Inscricao ins = (Inscricao) request.getAttribute("inscricao");
    Usuario aluno = (Usuario) request.getAttribute("aluno");
    List<Curso> listaCursos = (List<Curso>) request.getAttribute("listaCursos");
    List<Inscricao> lista = (List<Inscricao>) request.getAttribute("listaInscricao");
%>

<jsp:include page="header.jsp" />

<div class="container page-shell">

    <div class="hero-top">
        <div class="hero-kicker"><i class="bi bi-list-check"></i> Participação</div>
        <h1 class="hero-title fw-bold">Inscrição em cursos</h1>
        <p class="text-secondary">Associe o aluno aos cursos disponíveis.</p>

        <div class="mini-info-grid">
            <div class="mini-info-card">
                <div class="mini-info-label">Aluno</div>
                <p class="mini-info-value"><%= aluno != null ? aluno.getNome() : "Não encontrado" %></p>
            </div>
            <div class="mini-info-card">
                <div class="mini-info-label">Inscrições Ativas</div>
                <p class="mini-info-value"><%= lista != null ? lista.size() : 0 %></p>
            </div>
        </div>
    </div>

    <div class="form-card">
        <h3 class="fw-bold mb-1"><%= ins != null ? "Editar Inscrição" : "Nova Inscrição" %></h3>
        <p class="text-secondary mb-4">Selecione o curso e a data de início.</p>

        <form action="inscricao" method="post">
            <input type="hidden" name="editando" value="<%= ins != null ? "1" : "" %>">
            <input type="hidden" name="id_usuario" value="<%= aluno != null ? aluno.getIdUsuario() : "" %>">

            <div class="mb-3">
                <label class="form-label">Curso</label>
                <select name="id_curso" class="form-select" required>
                    <option value="">Selecione um curso</option>
                    <% if (listaCursos != null) {
                        for (Curso c : listaCursos) {
                            boolean sel = (ins != null && ins.getIdCurso() == c.getIdCurso()); %>
                            <option value="<%= c.getIdCurso() %>" <%= sel ? "selected" : "" %>><%= c.getNome() %></option>
                    <% } } %>
                </select>
            </div>

            <div class="mb-4">
                <label class="form-label">Data da inscrição</label>
                <input type="date" name="data_inscricao" class="form-control" 
                       value="<%= ins != null ? ins.getDataInscricao() : "" %>" required>
            </div>

            <button type="submit" class="btn btn-primary btn-entrar w-100">Salvar Inscrição</button>
        </form>
    </div>

    <div class="table-wrap overflow-hidden p-0"> <div class="p-4">
            <h4 class="fw-bold m-0">Histórico</h4>
        </div>
        <div class="table-responsive">
            <table class="table table-hover mb-0">
                <thead>
                    <tr>
                        <th>Curso</th>
                        <th>Data</th>
                        <th class="text-center">Ações</th>
                    </tr>
                </thead>
                <tbody>
                <% if (lista != null && !lista.isEmpty()) {
                    for (Inscricao i : lista) { %>
                    <tr>
                        <td><span class="curso-pill"><%= i.getNomeCurso() %></span></td>
                        <td><%= i.getDataInscricao() %></td>
                        <td class="text-center">
                            <a href="inscricao?acao=editar&id_curso=<%= i.getIdCurso() %>" class="btn btn-sm btn-warning fw-bold px-3">Editar</a>
                            <a href="inscricao?acao=excluir&id_curso=<%= i.getIdCurso() %>" class="btn btn-sm btn-danger fw-bold px-3" onclick="return confirm('Excluir?');">Excluir</a>
                        </td>
                    </tr>
                <% } } else { %>
                    <tr><td colspan="3" class="text-center p-4 text-secondary">Nenhuma inscrição.</td></tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>