package servlet;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import conexao.Conexao;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@WebServlet("/relatorioAluno")
public class RelatorioAlunoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("id_usuario") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            int idAluno = (Integer) session.getAttribute("id_usuario");

            Connection conn = Conexao.conectar();

            InputStream relatorio = getClass().getResourceAsStream(
                    "/relatorios/relatorio_aluno.jrxml"
            );

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("idAluno", idAluno);

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    relatorio,
                    parametros,
                    conn
            );

            byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=relatorio_aluno.pdf");
            response.getOutputStream().write(pdf);

            conn.close();

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}