package modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Inscricao {

    private int idUsuario;
    private int idCurso;
    private String dataInscricao;

    private int idCursoAntigo;

    private String nomeAluno;
    private String nomeCurso;

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public String getDataInscricao() {
        return dataInscricao;
    }

    public void setDataInscricao(String dataInscricao) {
        this.dataInscricao = dataInscricao;
    }

    public String getDataInscricaoFormatada() {
        if (dataInscricao == null || dataInscricao.isEmpty()) {
            return "";
        }

        try {
            LocalDate data = LocalDate.parse(dataInscricao);
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return data.format(formato);
        } catch (Exception e) {
            return dataInscricao;
        }
    }

    public int getIdCursoAntigo() {
        return idCursoAntigo;
    }

    public void setIdCursoAntigo(int idCursoAntigo) {
        this.idCursoAntigo = idCursoAntigo;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }
}