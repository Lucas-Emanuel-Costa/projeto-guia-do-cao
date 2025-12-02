package model;

import java.util.ArrayList;
import java.util.List;

public class TutorModel {

    private String nome;
    private String telefone;
    private String email;
    private String endereco;
    private List<CaoModel> caes;

    public TutorModel(String nome, String telefone, String email, String endereco) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
        this.caes = new ArrayList<>();
    }

    public String getNome() { return nome; }
    public String getTelefone() { return telefone; }
    public String getEmail() { return email; }
    public String getEndereco() { return endereco; }
    public List<CaoModel> getCaes() { return caes; }

    public void adicionarCao(CaoModel cao) {
        this.caes.add(cao);
    }

    public void removerCao(CaoModel cao) {
        this.caes.remove(cao);
    }
}