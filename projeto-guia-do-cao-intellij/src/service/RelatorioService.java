package service;

import model.CaoModel;
import model.TutorModel;

public class RelatorioService {

    private final CaoService caoService = new CaoService();

    public String gerarRelatorioCao(CaoModel cao) {
        return caoService.gerarAnalise(cao);
    }

    public String gerarRelatorioTutor(TutorModel tutor) {
        StringBuilder sb = new StringBuilder();

        sb.append("=== Relatório do Tutor ===\n");
        sb.append("Nome: ").append(tutor.getNome()).append("\n");
        sb.append("Telefone: ").append(tutor.getTelefone()).append("\n");
        sb.append("Email: ").append(tutor.getEmail()).append("\n");
        sb.append("Endereço: ").append(tutor.getEndereco()).append("\n\n");

        sb.append("Cães cadastrados:\n");
        if (tutor.getCaes().isEmpty()) {
            sb.append("- Nenhum cão cadastrado para este tutor.\n");
        } else {
            tutor.getCaes().forEach(c ->
                    sb.append("- ")
                      .append(c.getNome())
                      .append(" (")
                      .append(c.getRaca())
                      .append(")\n")
            );
        }

        return sb.toString();
    }
}