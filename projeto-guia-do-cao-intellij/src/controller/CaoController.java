package controller;

import model.CaoModel;
import model.TutorModel;
import service.CaoService;
import service.RelatorioService;

public class CaoController {

    private final CaoService caoService = new CaoService();
    private final RelatorioService relatorioService = new RelatorioService();

    public String processarCao(CaoModel cao) {
        return caoService.gerarAnalise(cao);
    }

    public String processarTutor(TutorModel tutor) {
        return relatorioService.gerarRelatorioTutor(tutor);
    }
}