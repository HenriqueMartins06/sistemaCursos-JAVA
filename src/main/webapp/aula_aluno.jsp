<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, modelo.Curso, modelo.Aula" %>

<%
    // Cache e Sessão padrão
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    if (session.getAttribute("id_usuario") == null) { response.sendRedirect("login.jsp"); return; }

    List<Curso> cursos = (List<Curso>) request.getAttribute("cursosAluno");
    List<Aula> aulas = (List<Aula>) request.getAttribute("aulasCurso");
%>

<jsp:include page="header.jsp" />

<div class="container page-shell">

    <div class="hero-top">
        <span class="hero-kicker"><i class="bi bi-play-circle-fill"></i> Área do Aluno</span>
        <h1 class="hero-title fw-bold">Meus Cursos e Aulas</h1>
        <p class="text-secondary">Selecione um curso para listar as lições disponíveis.</p>
    </div>

    <h4 class="fw-bold mb-4 mt-5"><i class="bi bi-journal-bookmark me-2"></i>Cursos Inscritos</h4>

    <% if (cursos == null || cursos.isEmpty()) { %>
        <div class="alert alert-info border-0 shadow-sm rounded-4 p-4 text-center">
            <i class="bi bi-info-circle fs-2 d-block mb-2"></i>
            Você ainda não se inscreveu em nenhum curso.
        </div>
    <% } else { %>
        <div class="row g-4">
            <% for (Curso c : cursos) { %>
                <div class="col-md-6 col-lg-4">
                    <div class="card-estudo" onclick="window.location='aulaAluno?curso=<%= c.getIdCurso() %>'">
                        <div class="icon-box"><i class="bi bi-journal-richtext"></i></div>
                        <h5 class="fw-bold"><%= c.getNome() %></h5>
                        <p class="small text-muted mb-0">Prof: <%= c.getNomeProfessor() %></p>
                    </div>
                </div>
            <% } %>
        </div>
    <% } %>

    <% if (aulas != null) { %>
        <hr class="my-5 opacity-25">
        <h4 class="fw-bold mb-4"><i class="bi bi-easel me-2"></i>Aulas do Curso</h4>

        <% if (aulas.isEmpty()) { %>
            <div class="alert alert-warning border-0 shadow-sm rounded-4 p-4 text-center">
                Este curso ainda não possui aulas cadastradas.
            </div>
        <% } else { %>
            <div class="row g-4">
                <% for (Aula a : aulas) { %>
                    <div class="col-md-6">
                        <div class="card-estudo border-start border-4 border-primary">
                            <div class="icon-box"><i class="bi bi-play-btn-fill"></i></div>
                            <h5 class="fw-bold"><%= a.getTitulo() %></h5>
                            <p class="text-secondary small"><%= a.getConteudo() %></p>
                        </div>
                    </div>
                <% } %>
            </div>
        <% } %>
    <% } %>

</div>

</body>
</html>