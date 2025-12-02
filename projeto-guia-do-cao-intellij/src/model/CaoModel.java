package model;

import enums.Ambiente;
import enums.Porte;
import enums.Sexo;

import java.util.ArrayList;
import java.util.List;

public class CaoModel {

    private String nome;
    private int idade;
    private double peso;
    private String raca;
    private Porte porte;
    private Sexo sexo;
    private Ambiente ambiente;
    private TutorModel tutor;
    private List<VacinaModel> vacinas;

    public CaoModel(String nome, int idade, double peso, String raca,
                    Porte porte, Sexo sexo, Ambiente ambiente, TutorModel tutor) {
        this.nome = nome;
        this.idade = idade;
        this.peso = peso;
        this.raca = raca;
        this.porte = porte;
        this.sexo = sexo;
        this.ambiente = ambiente;
        this.tutor = tutor;
        this.vacinas = new ArrayList<>();
    }

    public String getNome() { return nome; }
    public int getIdade() { return idade; }
    public double getPeso() { return peso; }
    public String getRaca() { return raca; }
    public Porte getPorte() { return porte; }
    public Sexo getSexo() { return sexo; }
    public Ambiente getAmbiente() { return ambiente; }
    public TutorModel getTutor() { return tutor; }
    public List<VacinaModel> getVacinas() { return vacinas; }

    public void setPeso(double peso) { this.peso = peso; }

    public void adicionarVacina(VacinaModel vacina) {
        this.vacinas.add(vacina);
    }

    @Override
    public String toString() {
        return "CaoModel{" +
                "nome='" + nome + '\'' +
                ", idade=" + idade +
                ", peso=" + peso +
                ", raca='" + raca + '\'' +
                ", porte=" + porte +
                ", sexo=" + sexo +
                ", ambiente=" + ambiente +
                ", tutor=" + (tutor != null ? tutor.getNome() : "Nenhum") +
                '}';
    }
}