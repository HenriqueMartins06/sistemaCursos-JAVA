<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - SistemaCursos</title>
    
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/auth.css">
</head>
<body>

<div class="auth-shell">
    <div class="row g-0 align-items-stretch">
        <div class="col-md-6 d-none d-md-block"> 
            <div class="auth-left h-100">
                <div class="auth-badge">
                    <i class="bi bi-mortarboard-fill"></i> 
                    SistemaCursos
                </div>
                <h1>Gerencie cursos, aulas e inscrições em um só lugar</h1>
                <p>Entre no sistema para acessar seu painel, organizar conteúdos e acompanhar o ambiente acadêmico.</p>
            </div>
        </div>

        <div class="col-md-6">
            <div class="auth-right h-100 d-flex flex-column justify-content-center">
                <h2 class="auth-title">Login</h2>
                <p class="text-secondary mb-4">Digite seu email e senha para acessar.</p>

                <form action="login" method="post">
                    <div class="mb-3">
                        <label class="form-label fw-bold">Email</label>
                        <div class="input-group">
                            <span class="input-group-text"><i class="bi bi-envelope-fill"></i></span>
                            <input type="email" name="email" class="form-control" placeholder="seu@email.com" required>
                        </div>
                    </div>
                    
                    <div class="mb-4">
                        <label class="form-label fw-bold">Senha</label>
                        <div class="input-group">
                            <span class="input-group-text"><i class="bi bi-lock-fill"></i></span>
                            <input type="password" name="senha" class="form-control" placeholder="Sua senha" required>
                        </div>
                    </div>
                    
                    <button type="submit" class="btn btn-entrar w-100">Entrar no sistema</button>
                </form>

                <div class="text-center mt-4">
                    <span class="text-secondary">Não tem conta?</span>
                    <a href="cadastrar.jsp" class="text-primary fw-bold text-decoration-none ms-1">Cadastre-se</a>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>