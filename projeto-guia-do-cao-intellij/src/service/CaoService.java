package service;

import model.CaoModel;
import model.VacinaModel;

public class CaoService {

    public String gerarAnalise(CaoModel cao) {
        StringBuilder resposta = new StringBuilder();

        resposta.append("=== Relatório de Saúde do Cão ===\n");
        resposta.append("Nome: ").append(cao.getNome()).append("\n");
        resposta.append("Raça: ").append(cao.getRaca()).append("\n");
        resposta.append("Idade: ").append(cao.getIdade()).append(" ano(s)\n");
        resposta.append("Peso: ").append(cao.getPeso()).append(" kg\n");
        resposta.append("Porte: ").append(cao.getPorte()).append("\n");
        resposta.append("Sexo: ").append(cao.getSexo()).append("\n");
        resposta.append("Ambiente: ").append(cao.getAmbiente()).append("\n");
        resposta.append("Tutor: ").append(cao.getTutor().getNome()).append("\n");

        resposta.append("\nAlimentação recomendada:\n");
        resposta.append(gerarAlimentacao(cao));

        resposta.append("\nSaúde preventiva:\n");
        resposta.append(gerarSaudePreventiva(cao));

        resposta.append("\nCuidados com o ambiente:\n");
        resposta.append(gerarCuidadosAmbiente(cao));

        resposta.append("\nPeso e possíveis cuidados:\n");
        resposta.append(gerarAnalisePeso(cao));

        resposta.append("\nVacinas cadastradas:\n");
        if (cao.getVacinas().isEmpty()) {
            resposta.append("- Nenhuma vacina cadastrada no sistema.\n");
        } else {
            for (VacinaModel vacina : cao.getVacinas()) {
                resposta.append("- ").append(vacina.toString()).append("\n");
            }
        }

        return resposta.toString();
    }

    private String gerarAlimentacao(CaoModel cao) {
        return switch (cao.getPorte()) {
            case PEQUENO -> "- Ração específica para porte pequeno, 2 a 3 refeições por dia.\n";
            case MEDIO   -> "- Ração para porte médio, 2 refeições por dia.\n";
            case GRANDE  -> "- Ração de alta qualidade para porte grande, 2 refeições ao dia.\n";
        };
    }

    private String gerarSaudePreventiva(CaoModel cao) {
        int idade = cao.getIdade();

        if (idade < 1) {
            return "- Filhote: vacinação, vermifugação e acompanhamento mensal.\n";
        } else if (idade < 7) {
            return "- Adulto: consultas a cada 6 meses.\n";
        } else {
            return "- Idoso: consultas a cada 3 meses e exames de rotina.\n";
        }
    }

    private String gerarCuidadosAmbiente(CaoModel cao) {
        return switch (cao.getAmbiente()) {
            case INTERNO -> "- Manter enriquecimento ambiental, brinquedos e espaço seguro.\n";
            case EXTERNO -> "- Abrigo adequado ao clima, água fresca e supervisão frequente.\n";
            case MISTO   -> "- Rotina equilibrada entre conforto interno e estímulos externos.\n";
        };
    }

    private String gerarAnalisePeso(CaoModel cao) {
        double peso = cao.getPeso();

        if (peso < 4) {
            return "- Atenção: abaixo do peso. Avaliar dieta mais calórica.\n";
        } else if (peso > 25) {
            return "- Atenção: possível sobrepeso. Monitorar alimentação e exercícios.\n";
        } else {
            return "- Peso dentro da faixa adequada (análise simples).\n";
        }
    }
}