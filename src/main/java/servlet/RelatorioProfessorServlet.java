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
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@WebServlet("/relatorioProfessor")
public class RelatorioProfessorServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("id_usuario") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Connection conn = null;

        try {
            Integer idProfessor = (Integer) session.getAttribute("id_usuario");
            String nomeProfessor = (String) session.getAttribute("nome");

            conn = Conexao.conectar();

            InputStream relatorioStream = getClass().getResourceAsStream(
                    "/relatorios/relatorio_professor.jrxml"
            );

            if (relatorioStream == null) {
                throw new ServletException("Relatório do professor não encontrado.");
            }

            JasperReport jasperReport = JasperCompileManager.compileReport(relatorioStream);

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("idProfessor", idProfessor);
            parametros.put("nomeProfessor", nomeProfessor);

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    parametros,
                    conn
            );

            byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=relatorio_professor.pdf");

            response.getOutputStream().write(pdf);

        } catch (Exception e) {
            throw new ServletException("Erro ao gerar relatório do professor.", e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}