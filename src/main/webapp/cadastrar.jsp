<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Cadastrar Usuário - SistemaCursos</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/auth.css">
</head>
<body>

<div class="cadastro-card">
    <div class="cadastro-topo">
        <div class="cadastro-topo-badge">
            <i class="bi bi-person-plus-fill"></i> SistemaCursos
        </div>
        <h2 class="fw-extrabold">Criar conta</h2>
        <p>Preencha seus dados para acessar o sistema.</p>
    </div>

    <div class="p-4 p-md-5"> <form action="cadastrar" method="post">
            <div class="mb-3">
                <label class="form-label fw-bold">Nome</label>
                <input type="text" class="form-control" name="nome" placeholder="Nome completo" required>
            </div>

            <div class="mb-3">
                <label class="form-label fw-bold">Email</label>
                <input type="email" class="form-control" name="email" placeholder="seu@email.com" required>
            </div>

            <div class="mb-3">
                <label class="form-label fw-bold">Senha</label>
                <input type="password" class="form-control" name="senha" placeholder="Crie uma senha" required>
            </div>

            <div class="mb-4">
                <label class="form-label fw-bold">Tipo de Usuário</label>
                <select class="form-select" name="tipo_usuario" required>
                    <option value="1">Professor</option>
                    <option value="2">Aluno</option>
                </select>
            </div>

            <button type="submit" class="btn btn-cadastrar">Cadastrar</button>
        </form>

        <div class="text-center mt-4">
            <span class="text-secondary">Já tem conta?</span>
            <a href="login.jsp" class="text-primary fw-bold text-decoration-none ms-1">Fazer login</a>
        </div>
    </div>
</div>

</body>
</html>