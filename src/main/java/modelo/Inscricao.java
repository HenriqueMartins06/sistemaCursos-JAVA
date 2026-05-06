package modelo;

public class Inscricao {

    private int idUsuario;
    private int idCurso;
    private String dataInscricao;

    // Necessário para edição da inscrição (curso antigo)
    private int idCursoAntigo;

    //  EXIBIR NA TABELA
    private String nomeAluno;
    private String nomeCurso;

    // GETTERS E SETTERS

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

    public int getIdCursoAntigo() {
        return idCursoAntigo;
    }

    public void setIdCursoAntigo(int idCursoAntigo) {
        this.idCursoAntigo = idCursoAntigo;
    }

    //  GETTERS E SETTERS NOVOS

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
 