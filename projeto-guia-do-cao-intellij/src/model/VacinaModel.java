package model;

public class VacinaModel {

    private String nome;
    private String dataAplicacao;

    public VacinaModel(String nome, String dataAplicacao) {
        this.nome = nome;
        this.dataAplicacao = dataAplicacao;
    }

    public String getNome() { return nome; }
    public String getDataAplicacao() { return dataAplicacao; }

    @Override
    public String toString() {
        return nome + " (Data: " + dataAplicacao + ")";
    }
}