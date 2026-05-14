<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, modelo.Nota" %>

<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

    if (session.getAttribute("id_usuario") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    List<Nota> listaNotas = (List<Nota>) request.getAttribute("listaNotas");
%>

<jsp:include page="header.jsp" />

<div class="container page-shell">

    <div class="hero-top mb-4">
        <span class="hero-kicker">
            <i class="bi bi-award-fill me-1"></i> Desempenho
        </span>
        <h1 class="hero-title fw-bold">Minhas Notas</h1>
        <p class="text-secondary">Acompanhe suas notas por curso.</p>
    </div>

    <div class="table-wrap p-0 overflow-hidden">
        <div class="p-4 border-bottom bg-light d-flex justify-content-between align-items-center">
            <h4 class="fw-bold m-0">Notas Recebidas</h4>
            <span class="badge bg-primary rounded-pill"><%= listaNotas != null ? listaNotas.size() : 0 %> Notas</span>
        </div>

        <div class="table-responsive">
            <table class="table table-hover align-middle mb-0">
                <thead>
                    <tr>
                        <th class="ps-4">Curso</th>
                        <th>Nota</th>
                    </tr>
                </thead>

                <tbody>
                <% if (listaNotas != null && !listaNotas.isEmpty()) {
                    for (Nota n : listaNotas) { %>

                    <tr>
                        <td class="ps-4">
                            <span class="curso-pill">
                                <i class="bi bi-journal-bookmark-fill me-1"></i>
                                <%= n.getNomeCurso() %>
                            </span>
                        </td>

                        <td>
                            <span class="curso-pill" style="background:#ecfdf5; color:#15803d; border-color:#bbf7d0;">
                                <i class="bi bi-star-fill me-1"></i>
                                <%= n.getNotaFormatada() %>
                            </span>
                        </td>
                    </tr>

                <% } } else { %>

                    <tr>
                        <td colspan="2" class="text-center p-5 text-secondary">
                            Nenhuma nota disponível no momento.
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