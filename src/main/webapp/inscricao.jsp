<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, modelo.Inscricao, modelo.Curso, modelo.Usuario" %>

<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

    if (session.getAttribute("id_usuario") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    Usuario aluno = (Usuario) request.getAttribute("aluno");
    List<Curso> listaCursos = (List<Curso>) request.getAttribute("listaCursos");
    List<Inscricao> lista = (List<Inscricao>) request.getAttribute("listaInscricao");
%>

<jsp:include page="header.jsp" />

<div class="container page-shell">

    <div class="hero-top">
        <div class="hero-kicker"><i class="bi bi-list-check"></i> Participação</div>
        <h1 class="hero-title fw-bold">Inscrição em cursos</h1>
        <p class="text-secondary">Escolha um curso disponível para realizar sua inscrição.</p>

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
        <h3 class="fw-bold mb-1">Nova Inscrição</h3>
        <p class="text-secondary mb-4">Selecione o curso desejado. A data será registrada automaticamente pelo sistema.</p>

        <form action="inscricao" method="post">

            <div class="mb-4">
                <label class="form-label">Curso</label>
                <select name="id_curso" class="form-select" required>
                    <option value="">Selecione um curso</option>

                    <% if (listaCursos != null) {
                        for (Curso c : listaCursos) { %>
                            <option value="<%= c.getIdCurso() %>"><%= c.getNome() %></option>
                    <% } } %>
                </select>
            </div>

            <button type="submit" class="btn btn-primary btn-entrar w-100">Realizar Inscrição</button>
        </form>
    </div>

    <div class="table-wrap overflow-hidden p-0">
        <div class="p-4">
            <h4 class="fw-bold m-0">Histórico</h4>
        </div>

        <div class="table-responsive">
            <table class="table table-hover mb-0">
                <thead>
                    <tr>
                        <th>Curso</th>
                        <th>Data da inscrição</th>
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
                            <a href="inscricao?acao=excluir&id_curso=<%= i.getIdCurso() %>"
                               class="btn btn-sm btn-danger fw-bold px-3"
                               onclick="return confirm('Excluir inscrição?');">
                                Excluir
                            </a>
                        </td>
                    </tr>

                <% } } else { %>

                    <tr>
                        <td colspan="3" class="text-center p-4 text-secondary">
                            Nenhuma inscrição.
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