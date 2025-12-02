package view;

import controller.CaoController;
import enums.Ambiente;
import enums.Porte;
import enums.Sexo;
import model.CaoModel;
import model.TutorModel;
import model.VacinaModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SistemaClinicaConsole implements SistemaView {

    // Cores ANSI
    private static final String RESET  = "\u001B[0m";
    private static final String CYAN   = "\u001B[36m";
    private static final String GREEN  = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RED    = "\u001B[31m";
    private static final String PURPLE = "\u001B[35m";

    private final Scanner sc = new Scanner(System.in);
    private final List<CaoModel> caes = new ArrayList<>();
    private final CaoController controller = new CaoController();

    @Override
    public void executar() {
        int opcao;

        do {
            limparTela();
            exibirMenu();
            opcao = lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1 -> cadastrarCaoComTutor();
                case 2 -> listarCaes();
                case 3 -> gerarRelatorioDeUmCao();
                case 4 -> buscarCaoPorNome();
                case 5 -> atualizarPesoDeUmCao();
                case 6 -> excluirCao();
                case 7 -> gerarRelatorioDeTutor();
                case 8 -> cadastrarVacinaParaCao();
                case 0 -> System.out.println(GREEN + "Saindo do sistema... Até mais!" + RESET);
                default -> {
                    System.out.println(RED + "Opção inválida. Tente novamente." + RESET);
                    pausar();
                }
            }

        } while (opcao != 0);

        sc.close();
    }

    // ====== HELPERS VISUAIS ======

    private void limparTela() {
        System.out.print("\u001B[H\u001B[2J");
        System.out.flush();
    }

    private void printTitulo(String titulo) {
        String linha = "==============================================";
        System.out.println(CYAN + linha + RESET);
        System.out.println(CYAN + "  " + titulo + RESET);
        System.out.println(CYAN + linha + RESET);
    }

    private void pausar() {
        System.out.println();
        System.out.print(YELLOW + "Pressione Enter para voltar ao menu..." + RESET);
        sc.nextLine();
    }

    // ====== MENU ======

    private void exibirMenu() {
        printTitulo("SISTEMA CLÍNICA VETERINÁRIA");

        System.out.println(GREEN + "1" + RESET + " - Cadastrar tutor + cão");
        System.out.println(GREEN + "2" + RESET + " - Listar cães cadastrados");
        System.out.println(GREEN + "3" + RESET + " - Gerar relatório de um cão");
        System.out.println(GREEN + "4" + RESET + " - Buscar cão por nome");
        System.out.println(GREEN + "5" + RESET + " - Atualizar peso de um cão");
        System.out.println(GREEN + "6" + RESET + " - Excluir cão");
        System.out.println(GREEN + "7" + RESET + " - Gerar relatório de um tutor (via cão)");
        System.out.println(GREEN + "8" + RESET + " - Cadastrar vacina para um cão");
        System.out.println(GREEN + "0" + RESET + " - Sair");
        System.out.println();
    }

    // ====== OPÇÃO 1: CADASTRAR ======

    private void cadastrarCaoComTutor() {
        printTitulo("Cadastro do Tutor");
        System.out.print("Nome do tutor: ");
        String nomeTutor = sc.nextLine();

        String telefoneTutor = lerTelefone("Telefone do tutor (apenas números): ");

        System.out.print("E-mail do tutor: ");
        String emailTutor = sc.nextLine();

        System.out.print("Endereço do tutor: ");
        String enderecoTutor = sc.nextLine();

        TutorModel tutor = new TutorModel(
                nomeTutor,
                telefoneTutor,
                emailTutor,
                enderecoTutor
        );

        printTitulo("Cadastro do Cão");
        System.out.print("Nome do cão: ");
        String nomeCao = sc.nextLine();

        System.out.print("Raça do cão: ");
        String racaCao = sc.nextLine();

        int idadeCao = lerInteiro("Idade do cão (anos): ");
        double pesoCao = lerDouble("Peso do cão (kg): ");

        Porte porte = lerPorte();
        Sexo sexo = lerSexo();
        Ambiente ambiente = lerAmbiente();

        CaoModel cao = new CaoModel(
                nomeCao,
                idadeCao,
                pesoCao,
                racaCao,
                porte,
                sexo,
                ambiente,
                tutor
        );

        // vincula cão ao tutor (1 → N)
        tutor.adicionarCao(cao);

        caes.add(cao);
        System.out.println();
        System.out.println(GREEN + "Cão cadastrado com sucesso!" + RESET);
        System.out.println(cao);

        pausar();
    }

    // ====== OPÇÃO 2: LISTAR CÃES ======

    private void listarCaes() {
        printTitulo("Lista de Cães Cadastrados");

        if (caes.isEmpty()) {
            System.out.println(YELLOW + "Nenhum cão cadastrado ainda." + RESET);
            pausar();
            return;
        }

        for (int i = 0; i < caes.size(); i++) {
            CaoModel cao = caes.get(i);
            System.out.printf("%d - %s (Tutor: %s)%n",
                    i + 1,
                    cao.getNome(),
                    cao.getTutor().getNome());
        }

        pausar();
    }

    private void listarCaesSemPausa() {
        if (caes.isEmpty()) {
            System.out.println(YELLOW + "Nenhum cão cadastrado ainda." + RESET);
            return;
        }

        for (int i = 0; i < caes.size(); i++) {
            CaoModel cao = caes.get(i);
            System.out.printf("%d - %s (Tutor: %s)%n",
                    i + 1,
                    cao.getNome(),
                    cao.getTutor().getNome());
        }
    }

    // ====== OPÇÃO 3: RELATÓRIO DO CÃO ======

    private void gerarRelatorioDeUmCao() {
        if (caes.isEmpty()) {
            printTitulo("Relatório do Cão");
            System.out.println(YELLOW + "Não há cães cadastrados para gerar relatório." + RESET);
            pausar();
            return;
        }

        printTitulo("Selecionar Cão para Relatório");
        listarCaesSemPausa();
        int indice = lerInteiro("\nDigite o número do cão para gerar o relatório: ");

        if (indice < 1 || indice > caes.size()) {
            System.out.println(RED + "Índice inválido." + RESET);
            pausar();
            return;
        }

        CaoModel selecionado = caes.get(indice - 1);
        String relatorio = controller.processarCao(selecionado);

        printTitulo("RELATÓRIO GERADO");
        System.out.println(relatorio);

        pausar();
    }

    // ====== OPÇÃO 4: BUSCAR CÃO POR NOME ======

    private void buscarCaoPorNome() {
        if (caes.isEmpty()) {
            printTitulo("Buscar Cão por Nome");
            System.out.println(YELLOW + "Não há cães cadastrados para buscar." + RESET);
            pausar();
            return;
        }

        printTitulo("Buscar Cão por Nome");
        System.out.print("Digite o nome do cão para buscar: ");
        String nomeBusca = sc.nextLine();

        boolean encontrado = false;
        for (CaoModel cao : caes) {
            if (cao.getNome().equalsIgnoreCase(nomeBusca)) {
                System.out.println(GREEN + "Cão encontrado:" + RESET);
                System.out.println(cao);
                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println(RED + "Nenhum cão encontrado com esse nome." + RESET);
        }

        pausar();
    }

    // ====== OPÇÃO 5: ATUALIZAR PESO ======

    private void atualizarPesoDeUmCao() {
        if (caes.isEmpty()) {
            printTitulo("Atualizar Peso do Cão");
            System.out.println(YELLOW + "Não há cães cadastrados para atualizar." + RESET);
            pausar();
            return;
        }

        printTitulo("Atualizar Peso do Cão");
        listarCaesSemPausa();
        int indice = lerInteiro("\nDigite o número do cão para atualizar o peso: ");

        if (indice < 1 || indice > caes.size()) {
            System.out.println(RED + "Índice inválido." + RESET);
            pausar();
            return;
        }

        CaoModel selecionado = caes.get(indice - 1);
        System.out.println("Cão selecionado: " + PURPLE + selecionado.getNome() + RESET +
                " (peso atual: " + selecionado.getPeso() + " kg)");
        double novoPeso = lerDouble("Novo peso (kg): ");

        selecionado.setPeso(novoPeso);

        System.out.println(GREEN + "Peso atualizado com sucesso!" + RESET);
        System.out.println(selecionado);

        pausar();
    }

    // ====== OPÇÃO 6: EXCLUIR CÃO ======

    private void excluirCao() {
        if (caes.isEmpty()) {
            printTitulo("Excluir Cão");
            System.out.println(YELLOW + "Não há cães cadastrados para excluir." + RESET);
            pausar();
            return;
        }

        printTitulo("Excluir Cão");
        listarCaesSemPausa();
        int indice = lerInteiro("\nDigite o número do cão que deseja excluir: ");

        if (indice < 1 || indice > caes.size()) {
            System.out.println(RED + "Índice inválido." + RESET);
            pausar();
            return;
        }

        CaoModel removido = caes.remove(indice - 1);

        // também remove do tutor
        TutorModel tutor = removido.getTutor();
        if (tutor != null) {
            tutor.removerCao(removido);
        }

        System.out.println(GREEN + "Cão " + removido.getNome() + " removido com sucesso." + RESET);

        pausar();
    }

    // ====== OPÇÃO 7: RELATÓRIO DO TUTOR ======

    private void gerarRelatorioDeTutor() {
        if (caes.isEmpty()) {
            printTitulo("Relatório do Tutor");
            System.out.println(YELLOW + "Não há cães cadastrados (logo, nenhum tutor com cães)." + RESET);
            pausar();
            return;
        }

        printTitulo("Selecionar Cão / Tutor para Relatório");
        listarCaesSemPausa();
        int indice = lerInteiro("\nDigite o número do cão: ");

        if (indice < 1 || indice > caes.size()) {
            System.out.println(RED + "Índice inválido." + RESET);
            pausar();
            return;
        }

        CaoModel selecionado = caes.get(indice - 1);
        TutorModel tutor = selecionado.getTutor();

        String relatorioTutor = controller.processarTutor(tutor);
        printTitulo("RELATÓRIO DO TUTOR");
        System.out.println(relatorioTutor);

        pausar();
    }

    // ====== OPÇÃO 8: CADASTRAR VACINA ======

    private void cadastrarVacinaParaCao() {
        if (caes.isEmpty()) {
            printTitulo("Cadastro de Vacina");
            System.out.println(YELLOW + "Não há cães cadastrados para aplicar vacina." + RESET);
            pausar();
            return;
        }

        printTitulo("Cadastro de Vacina");
        listarCaesSemPausa();
        int indice = lerInteiro("\nDigite o número do cão para cadastrar a vacina: ");

        if (indice < 1 || indice > caes.size()) {
            System.out.println(RED + "Índice inválido." + RESET);
            pausar();
            return;
        }

        CaoModel selecionado = caes.get(indice - 1);

        System.out.print("Nome da vacina: ");
        String nomeVacina = sc.nextLine();

        String dataAplicacao = lerData("Data de aplicação (dd/MM/aaaa): ");

        VacinaModel vacina = new VacinaModel(nomeVacina, dataAplicacao);
        selecionado.adicionarVacina(vacina);

        System.out.println(GREEN + "Vacina cadastrada com sucesso para o cão "
                + selecionado.getNome() + "!" + RESET);

        pausar();
    }

    // ====== LEITURA DE ENUMS ======

    private Porte lerPorte() {
        System.out.println("\nSelecione o porte do cão:");
        System.out.println("1 - PEQUENO");
        System.out.println("2 - MEDIO");
        System.out.println("3 - GRANDE");

        int opc = lerInteiro("Opção: ");

        return switch (opc) {
            case 1 -> Porte.PEQUENO;
            case 2 -> Porte.MEDIO;
            case 3 -> Porte.GRANDE;
            default -> {
                System.out.println(YELLOW + "Opção inválida, usando MEDIO por padrão." + RESET);
                yield Porte.MEDIO;
            }
        };
    }

    private Sexo lerSexo() {
        System.out.println("\nSelecione o sexo do cão:");
        System.out.println("1 - MACHO");
        System.out.println("2 - FEMEA");

        int opc = lerInteiro("Opção: ");

        return switch (opc) {
            case 1 -> Sexo.MACHO;
            case 2 -> Sexo.FEMEA;
            default -> {
                System.out.println(YELLOW + "Opção inválida, usando MACHO por padrão." + RESET);
                yield Sexo.MACHO;
            }
        };
    }

    private Ambiente lerAmbiente() {
        System.out.println("\nSelecione o ambiente do cão:");
        System.out.println("1 - INTERNO, casa ou apartamento");
        System.out.println("2 - EXTERNO, sítio ou fazenda");
        System.out.println("3 - MISTO, quintal de casa");

        int opc = lerInteiro("Opção: ");

        return switch (opc) {
            case 1 -> Ambiente.INTERNO;
            case 2 -> Ambiente.EXTERNO;
            case 3 -> Ambiente.MISTO;
            default -> {
                System.out.println(YELLOW + "Opção inválida, usando MISTO por padrão." + RESET);
                yield Ambiente.MISTO;
            }
        };
    }

    // ====== MÉTODOS GENÉRICOS DE LEITURA ======

    private int lerInteiro(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                int valor = Integer.parseInt(sc.nextLine());
                if (valor < 0) {
                    System.out.println(RED + "Valor inválido. Não pode ser negativo." + RESET);
                    continue;
                }
                return valor;
            } catch (NumberFormatException e) {
                System.out.println(RED + "Valor inválido. Digite um número inteiro." + RESET);
            }
        }
    }

    private double lerDouble(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                double valor = Double.parseDouble(sc.nextLine().replace(",", "."));
                if (valor <= 0) {
                    System.out.println(RED + "Valor inválido. Deve ser maior que zero." + RESET);
                    continue;
                }
                return valor;
            } catch (NumberFormatException e) {
                System.out.println(RED + "Valor inválido. Digite um número (ex: 12.5)." + RESET);
            }
        }
    }

    private String lerTelefone(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String telefone = sc.nextLine().trim();

            // 8 a 15 dígitos numéricos
            if (telefone.matches("\\d{8,15}")) {
                return telefone;
            } else {
                System.out.println(RED + "Telefone inválido. Digite apenas números (entre 8 e 15 dígitos, sem espaços ou traços)." + RESET);
            }
        }
    }

    private String lerData(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String data = sc.nextLine().trim();

            // formato dd/MM/aaaa
            if (data.matches("\\d{2}/\\d{2}/\\d{4}")) {
                return data;
            } else {
                System.out.println(RED + "Data inválida. Use o formato dd/MM/aaaa, por exemplo: 10/11/2025." + RESET);
            }
        }
    }
}