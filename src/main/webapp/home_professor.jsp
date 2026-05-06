<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-BR">

<jsp:include page="header.jsp" />

<%
    // controlar o cache da sessão
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);

    if (session.getAttribute("id_usuario") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String nomeProfessor = (String) session.getAttribute("nome");
    if (nomeProfessor == null) {
        nomeProfessor = "Professor";
    }
%>

<body>
    <div class="container pagina-wrapper">

        <div class="hero-box">
            <span class="hero-eyebrow">
                <i class="bi bi-person-workspace me-1"></i>
                Painel do Professor
            </span>
            <h1 class="hero-title">Olá, <%= nomeProfessor %></h1>
            <p class="hero-subtitle">
                Gerencie usuários, cursos, aulas e comentários em um ambiente mais organizado e moderno.
            </p>
        </div>

        <div class="row g-4">
            <div class="col-md-6 col-lg-3">
                <a href="usuario" class="card-atalho">
                    <i class="bi bi-people-fill"></i>
                    <h5>Usuários</h5>
                    <p>Visualize e acompanhe os usuários cadastrados no sistema.</p>
                </a>
            </div>

            <div class="col-md-6 col-lg-3">
                <a href="curso" class="card-atalho">
                    <i class="bi bi-journal-bookmark-fill"></i>
                    <h5>Criar Curso</h5>
                    <p>Cadastre novos cursos e organize os conteúdos disponíveis.</p>
                </a>
            </div>

            <div class="col-md-6 col-lg-3">
                <a href="aula" class="card-atalho">
                    <i class="bi bi-easel-fill"></i>
                    <h5>Criar Aula</h5>
                    <p>Adicione aulas aos cursos e monte a estrutura de ensino.</p>
                </a>
            </div>

            <div class="col-md-6 col-lg-3">
                <a href="comentarios" class="card-atalho">
                    <i class="bi bi-chat-dots-fill"></i>
                    <h5>Comentários</h5>
                    <p>Gerencie interações e acompanhe observações cadastradas.</p>
                </a>
            </div>
        </div>
    </div>
</body>
</html>