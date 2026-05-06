package conexao;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {
	private static final String URL = "jdbc:mysql://localhost:3306/sistemacursos?useUnicode=true&characterEncoding=UTF-8&connectionCollation=utf8mb4_unicode_ci&serverTimezone=America/Sao_Paulo";
	private static final String USUARIO = "root";
	private static final String SENHA = "root";

    public static Connection conectar() {
        Connection conexao = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
            System.out.println(" Conectado com sucesso ao banco!");
        } catch (Exception e) {
            System.out.println(" Erro na conexão: " + e.getMessage());
        }
        return conexao;
    }
}
