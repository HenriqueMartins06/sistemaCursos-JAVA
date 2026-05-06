<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">

<jsp:include page="header.jsp" />

<%
    // Controle de cache igual ao do professor
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);

    // Proteção de sessão
    if (session.getAttribute("id_usuario") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String nomeAluno = (String) session.getAttribute("nome");
    if (nomeAluno == null) {
        nomeAluno = "Aluno";
    }
%>

<body>
    <div class="container pagina-wrapper">
        
        <div class="hero-box">
            <span class="hero-eyebrow">
                <i class="bi bi-person-badge me-1"></i>
                Painel do Aluno
            </span>
            <h1 class="hero-title">Olá, <%= nomeAluno %>!</h1>
            <p class="hero-subtitle">Acesse suas aulas, realize inscrições e acompanhe os comentários no sistema.</p>
        </div>
        
        <div class="row g-4">
            
            <div class="col-md-4">
                <a href="aulaAluno" class="card-atalho">
                    <i class="bi bi-play-circle-fill"></i>
                    <h5>Aulas</h5>
                    <p>Visualize as aulas disponíveis e acompanhe os conteúdos.</p>
                </a>
            </div>

            <div class="col-md-4">
                <a href="inscricao" class="card-atalho">
                    <i class="bi bi-card-checklist"></i>
                    <h5>Inscrição</h5>
                    <p>Inscreva-se nos cursos disponíveis e organize sua participação.</p>
                </a>
            </div>

            <div class="col-md-4">
                <a href="comentarios" class="card-atalho">
                    <i class="bi bi-chat-left-text-fill"></i>
                    <h5>Comentários</h5>
                    <p>Cadastre comentários e participe das interações no ambiente.</p>
                </a>
            </div>

        </div>
    </div>
</body>
</html>