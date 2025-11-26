package app;

import model.Tarefa;
import repository.TarefaRepository;
import service.TarefaService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final TarefaRepository repo = new TarefaRepository();
    private static final TarefaService service = new TarefaService(repo);
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            mostrarMenu();
            String opcao = lerLinha("Escolha uma opção: ");

            switch (opcao) {
                case "1": listarTarefas(); break;
                case "2": cadastrarTarefa(); break;
                case "3": buscarTarefaPorId(); break;
                case "4": atualizarTarefa(); break;
                case "5": deletarTarefa(); break;
                case "0":
                    System.out.println("Encerrando o programa...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n--- GERENCIADOR DE TAREFAS ---");
        System.out.println("1 - Listar tarefas");
        System.out.println("2 - Cadastrar tarefa");
        System.out.println("3 - Buscar por ID");
        System.out.println("4 - Atualizar tarefa");
        System.out.println("5 - Deletar tarefa");
        System.out.println("0 - Sair");
    }

    private static String lerLinha(String texto) {
        System.out.print(texto);
        return scanner.nextLine().trim();
    }

    private static int lerInt(String texto) {
        try {
            return Integer.parseInt(lerLinha(texto));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static LocalDate lerDataOpcional(String texto) {
        String linha = lerLinha(texto);
        if (linha.isBlank()) return null;
        try {
            return LocalDate.parse(linha);
        } catch (DateTimeParseException e) {
            System.out.println("Data inválida.");
            return null;
        }
    }

    private static void listarTarefas() {
        List<Tarefa> tarefas = service.listar();
        if (tarefas.isEmpty()) {
            System.out.println("Nenhuma tarefa cadastrada.");
            return;
        }
        tarefas.forEach(System.out::println);
    }

    private static void cadastrarTarefa() {
        String titulo = lerLinha("Título: ");
        if (titulo.isBlank()) {
            System.out.println("Título não pode ser vazio.");
            return;
        }

        String descricao = lerLinha("Descrição: ");

        LocalDate data = null;
        while (data == null) {
            String s = lerLinha("Data de conclusão (AAAA-MM-DD): ");
            try {
                data = LocalDate.parse(s);
            } catch (DateTimeParseException e) {
                System.out.println("Data inválida.");
            }
        }

        Tarefa criada = service.cadastrar(titulo, descricao, data);
        System.out.println("Tarefa criada: " + criada);
    }

    private static void buscarTarefaPorId() {
        int id = lerInt("ID: ");
        if (id < 0) {
            System.out.println("ID inválido.");
            return;
        }

        service.buscar(id).ifPresentOrElse(
                System.out::println,
                () -> System.out.println("Tarefa não encontrada.")
        );
    }

    private static void atualizarTarefa() {
        int id = lerInt("ID da tarefa a atualizar: ");
        if (id < 0) {
            System.out.println("ID inválido.");
            return;
        }

        String novoTitulo = lerLinha("Novo título (enter = manter): ");
        String novaDescricao = lerLinha("Nova descrição (enter = manter): ");
        LocalDate novaData = lerDataOpcional("Nova data (AAAA-MM-DD) (enter = manter): ");

        boolean atualizado = service.atualizar(
                id,
                novoTitulo.isBlank() ? null : novoTitulo,
                novaDescricao.isBlank() ? null : novaDescricao,
                novaData
        );

        System.out.println(atualizado ? "Atualizado com sucesso." : "Tarefa não encontrada.");
    }

    private static void deletarTarefa() {
        int id = lerInt("ID da tarefa a deletar: ");
        if (id < 0) {
            System.out.println("ID inválido.");
            return;
        }

        boolean removido = service.remover(id);
        System.out.println(removido ? "Tarefa removida." : "Tarefa não encontrada.");
    }
}
