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
            <p class="hero-subtitle">Acesse suas aulas, realize inscrições, acompanhe suas notas e participe dos comentários no sistema.</p>
        </div>
        
        <div class="row g-4">
            
            <div class="col-md-6 col-lg-3">
                <a href="aulaAluno" class="card-atalho">
                    <span class="card-atalho-icon">
                        <i class="bi bi-play-circle-fill"></i>
                    </span>
                    <h5>Aulas</h5>
                    <p>Visualize as aulas disponíveis e acompanhe os conteúdos.</p>
                    <span class="card-atalho-action">Acessar <i class="bi bi-arrow-right"></i></span>
                </a>
            </div>

            <div class="col-md-6 col-lg-3">
                <a href="inscricao" class="card-atalho">
                    <span class="card-atalho-icon">
                        <i class="bi bi-card-checklist"></i>
                    </span>
                    <h5>Inscrição</h5>
                    <p>Inscreva-se nos cursos disponíveis e organize sua participação.</p>
                    <span class="card-atalho-action">Acessar <i class="bi bi-arrow-right"></i></span>
                </a>
            </div>

            <div class="col-md-6 col-lg-3">
                <a href="notas" class="card-atalho">
                    <span class="card-atalho-icon">
                        <i class="bi bi-award-fill"></i>
                    </span>
                    <h5>Minhas Notas</h5>
                    <p>Acompanhe seu desempenho e visualize suas notas por curso.</p>
                    <span class="card-atalho-action">Acessar <i class="bi bi-arrow-right"></i></span>
                </a>
            </div>

            <div class="col-md-6 col-lg-3">
                <a href="comentarios" class="card-atalho">
                    <span class="card-atalho-icon">
                        <i class="bi bi-chat-left-text-fill"></i>
                    </span>
                    <h5>Comentários</h5>
                    <p>Cadastre comentários e participe das interações no ambiente.</p>
                    <span class="card-atalho-action">Acessar <i class="bi bi-arrow-right"></i></span>
                </a>
            </div>

        </div>
    </div>
</body>
</html>