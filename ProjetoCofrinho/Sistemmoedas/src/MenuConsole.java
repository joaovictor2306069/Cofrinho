import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * Camada de interface no console. Exibe menus, lê e valida a entrada do
 * usuário e coordena as chamadas ao Cofrinho. Não contém regra de negócio
 * pesada: cálculos e armazenamento ficam no Cofrinho e nas Moedas.
 */
public class MenuConsole {

    private final Cofrinho cofrinho;
    private final Scanner scanner;

    public MenuConsole(Cofrinho cofrinho) {
        this.cofrinho = cofrinho;
        this.scanner = new Scanner(System.in);
    }

    /** Mantém o programa rodando até o usuário escolher sair. */
    public void iniciar() {
        int opcao;
        do {
            exibirMenuPrincipal();
            opcao = lerInteiro("Escolha uma opção: ");
            switch (opcao) {
                case 1 -> listarMoedas();
                case 2 -> guardarMoeda();
                case 3 -> removerMoeda();
                case 4 -> calcularTotal();
                case 0 -> System.out.println("\nEncerrando o Cofrinho. Até a próxima!");
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    private void exibirMenuPrincipal() {
        System.out.println("\n==== COFRINHO ====");
        System.out.println("[1] - Listar moedas");
        System.out.println("[2] - Guardar moeda");
        System.out.println("[3] - Remover moeda");
        System.out.println("[4] - Calcular total convertido para Real");
        System.out.println("[0] - Sair");
    }

    private void listarMoedas() {
        if (cofrinho.estaVazio()) {
            System.out.println("\nO cofrinho está vazio.");
            return;
        }
        System.out.println("\n==== MOEDAS NO COFRINHO ====");
        List<Moeda> moedas = cofrinho.getMoedas();
        for (int i = 0; i < moedas.size(); i++) {
            // o índice mostrado começa em 1, mais natural para o usuário
            System.out.printf("[%d] %s%n", i + 1, moedas.get(i));
        }
        System.out.printf(Locale.US, "%nTotal convertido para Real: R$ %.2f%n",
                cofrinho.calcularTotalConvertidoParaReal());
    }

    private void guardarMoeda() {
        System.out.println("\n==== SELECIONE A MOEDA ====");
        System.out.println("[1] - Real");
        System.out.println("[2] - Dólar");
        System.out.println("[3] - Euro");
        System.out.println("[0] - Voltar");

        int tipo = lerInteiro("Escolha a moeda: ");
        if (tipo == 0) {
            return;
        }
        if (tipo < 1 || tipo > 3) {
            System.out.println("Moeda inválida. Operação cancelada.");
            return;
        }

        double valor = lerValorPositivo("Informe o valor da moeda (ex.: 0.25): ");
        int quantidade = lerInteiroPositivo("Informe a quantidade: ");

        // Cria o objeto correto via herança; o Cofrinho só conhece Moeda.
        Moeda moeda = switch (tipo) {
            case 1 -> new Real(valor, quantidade);
            case 2 -> new Dolar(valor, quantidade);
            default -> new Euro(valor, quantidade);
        };

        cofrinho.adicionar(moeda);
        System.out.println("Moeda guardada: " + moeda);
    }

    private void removerMoeda() {
        if (cofrinho.estaVazio()) {
            System.out.println("\nO cofrinho está vazio. Não há o que remover.");
            return;
        }
        listarMoedas();
        int indice = lerInteiro("\nInforme o índice da moeda a remover (0 para cancelar): ");
        if (indice == 0) {
            return;
        }
        // o usuário vê índices a partir de 1, então convertemos para base 0
        if (cofrinho.remover(indice - 1)) {
            System.out.println("Moeda removida com sucesso.");
        } else {
            System.out.println("Índice inválido. Nenhuma moeda foi removida.");
        }
    }

    private void calcularTotal() {
        if (cofrinho.estaVazio()) {
            System.out.println("\nO cofrinho está vazio. Total: R$ 0.00");
            return;
        }
        System.out.printf(Locale.US, "%nTotal convertido para Real: R$ %.2f%n",
                cofrinho.calcularTotalConvertidoParaReal());
    }

    // ----- Leitura e validação de entrada -----

    /** Lê um inteiro qualquer; repete enquanto a entrada não for um número. */
    private int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String linha = scanner.nextLine().trim();
            try {
                return Integer.parseInt(linha);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número inteiro.");
            }
        }
    }

    private int lerInteiroPositivo(String mensagem) {
        while (true) {
            int valor = lerInteiro(mensagem);
            if (valor > 0) {
                return valor;
            }
            System.out.println("O valor deve ser maior que zero.");
        }
    }

    /** Lê um valor decimal positivo; aceita vírgula ou ponto como separador. */
    private double lerValorPositivo(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String linha = scanner.nextLine().trim().replace(",", ".");
            try {
                double valor = Double.parseDouble(linha);
                if (valor > 0) {
                    return valor;
                }
                System.out.println("O valor deve ser maior que zero.");
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número (ex.: 0.25).");
            }
        }
    }
}
