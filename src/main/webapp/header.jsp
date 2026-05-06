<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    // Lógica de sessão centralizada
    HttpSession sessaoHeader = request.getSession(false);
    Integer tipo = (sessaoHeader != null) ? (Integer) sessaoHeader.getAttribute("tipo_usuario") : null;
    String homeLink = "login.jsp";
    
    if (tipo != null) {
        homeLink = (tipo == 1) ? "home_professor.jsp" : "home_aluno.jsp";
    }
%>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
</head>

<nav class="navbar navbar-expand-lg navbar-dark navbar-sistema">
    <div class="container">
        <a class="navbar-brand d-flex align-items-center fw-bold" href="<%= homeLink %>">
            <span class="brand-badge"><i class="bi bi-mortarboard-fill"></i></span>
            SistemaCursos
        </a>

        <button class="navbar-toggler border-0 shadow-none" type="button" data-bs-toggle="collapse" data-bs-target="#menuGeral">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="menuGeral">
            <ul class="navbar-nav ms-auto align-items-lg-center">
                <% if (tipo != null && tipo == 1) { %>
                    <li class="nav-item"><a class="nav-link" href="home_professor.jsp">Home</a></li>
                    <li class="nav-item"><a class="nav-link" href="usuario">Usuários</a></li>
                    <li class="nav-item"><a class="nav-link" href="curso">Cursos</a></li>
                    <li class="nav-item"><a class="nav-link" href="aula">Aulas</a></li>
                <% } else if (tipo != null && tipo == 2) { %>
                    <li class="nav-item"><a class="nav-link" href="home_aluno.jsp">Home</a></li>
                    <li class="nav-item"><a class="nav-link" href="aulaAluno">Minhas Aulas</a></li>
                    <li class="nav-item"><a class="nav-link" href="inscricao">Inscrever-se</a></li>
                <% } %>
                
                <li class="nav-item"><a class="nav-link" href="comentarios">Comentários</a></li>

                <% if (tipo != null) { %>
                    <li class="nav-item">
                        <a class="nav-link nav-link-sair ms-lg-2" href="logout">Sair</a>
                    </li>
                <% } %>
            </ul>
        </div>
    </div>
</nav>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>