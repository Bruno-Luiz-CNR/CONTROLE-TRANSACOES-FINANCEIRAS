import java.util.Scanner;

public class AppFinanceiro {

    private static RepositorioContas repo = new RepositorioContas();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean rodando = true;

        while (rodando) {
            mostrarMenu();
            int opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1 -> criarConta();
                case 2 -> depositar();
                case 3 -> sacar();
                case 4 -> transferir();
                case 5 -> mostrarExtrato();
                case 6 -> {
                    System.out.println("Encerrando...");
                    rodando = false;
                }
                default -> System.out.println("Opção inválida");
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n=== MENU FINANCEIRO ===");
        System.out.println("1 - Criar Conta");
        System.out.println("2 - Depositar");
        System.out.println("3 - Sacar");
        System.out.println("4 - Transferir");
        System.out.println("5 - Extrato");
        System.out.println("6 - Sair");
        System.out.print("Escolha: ");
    }

    private static void criarConta() {
        System.out.print("Digite número da conta: ");
        String numero = scanner.nextLine();
        System.out.print("Digite nome do titular: ");
        String titular = scanner.nextLine();
        System.out.print("Tipo de conta (1-Corrente, 2-Poupança): ");
        int tipo = Integer.parseInt(scanner.nextLine());

        Conta conta = switch (tipo) {
            case 1 -> new ContaCorrente(numero, titular);
            case 2 -> new ContaPoupanca(numero, titular);
            default -> null;
        };

        if (conta != null) {
            repo.adicionarConta(conta);
            System.out.println("Conta criada com sucesso!");
        } else {
            System.out.println("Tipo inválido!");
        }
    }

    private static Conta buscarConta() {
        System.out.print("Número da conta: ");
        String numero = scanner.nextLine();
        Conta conta = repo.buscarConta(numero);
        if (conta == null) {
            System.out.println("Conta não encontrada!");
        }
        return conta;
    }

    private static void depositar() {
        Conta conta = buscarConta();
        if (conta != null) {
            System.out.print("Valor para depósito: ");
            double valor = Double.parseDouble(scanner.nextLine());
            conta.depositar(valor);
        }
    }

    private static void sacar() {
        Conta conta = buscarConta();
        if (conta != null) {
            System.out.print("Valor para saque: ");
            double valor = Double.parseDouble(scanner.nextLine());
            conta.sacar(valor);
        }
    }

    private static void transferir() {
        System.out.println("Conta origem:");
        Conta origem = buscarConta();
        if (origem == null) return;

        System.out.println("Conta destino:");
        Conta destino = buscarConta();
        if (destino == null) return;

        System.out.print("Valor para transferência: ");
        double valor = Double.parseDouble(scanner.nextLine());

        if (origem.transferir(destino, valor)) {
            System.out.println("Transferência realizada com sucesso!");
        } else {
            System.out.println("Falha na transferência.");
        }
    }

    private static void mostrarExtrato() {
        Conta conta = buscarConta();
        if (conta != null) {
            conta.mostrarExtrato();
        }
    }
}
