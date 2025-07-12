import java.util.*;

enum TipoTransacao {
    DEPOSITO, SAQUE, TRANSFERENCIA, INVESTIMENTO
}

record Transacao(TipoTransacao tipo, double valor, Date data, String descricao) {}

abstract class Conta {
    protected String numero;
    protected String titular;
    protected double saldo;
    protected List<Transacao> historico = new ArrayList<>();

    public Conta(String numero, String titular) {
        this.numero = numero;
        this.titular = titular;
        this.saldo = 0;
    }

    public void depositar(double valor) {
        if (valor <= 0) {
            System.out.println("Valor inválido para depósito.");
            return;
        }
        saldo += valor;
        historico.add(new Transacao(TipoTransacao.DEPOSITO, valor, new Date(), "Depósito"));
        System.out.println("Depósito realizado: " + valor);
    }

    public boolean sacar(double valor) {
        if (valor <= 0 || valor > saldo) {
            System.out.println("Saldo insuficiente ou valor inválido.");
            return false;
        }
        saldo -= valor;
        historico.add(new Transacao(TipoTransacao.SAQUE, valor, new Date(), "Saque"));
        System.out.println("Saque realizado: " + valor);
        return true;
    }

    public boolean transferir(Conta destino, double valor) {
        if (sacar(valor)) {
            destino.depositar(valor);
            historico.add(new Transacao(TipoTransacao.TRANSFERENCIA, valor, new Date(),
                    "Transferência para conta " + destino.numero));
            return true;
        }
        return false;
    }

    public void mostrarExtrato() {
        System.out.println("Extrato da conta " + numero + " - Titular: " + titular);
        for (Transacao t : historico) {
            System.out.printf("%s | %.2f | %s | %s\n", t.data(), t.valor(), t.tipo(), t.descricao());
        }
        System.out.println("Saldo atual: " + saldo);
    }

    public double getSaldo() {
        return saldo;
    }

    public String getNumero() {
        return numero;
    }
}

class ContaCorrente extends Conta {
    public ContaCorrente(String numero, String titular) {
        super(numero, titular);
    }
    // Pode ter métodos específicos, como limite especial, tarifas, etc.
}

class ContaPoupanca extends Conta {
    public ContaPoupanca(String numero, String titular) {
        super(numero, titular);
    }
    // Pode ter métodos específicos, como rendimento mensal, etc.
}

class Investimento {
    private String nome;
    private double valorInvestido;
    private Date dataInvestimento;

    public Investimento(String nome, double valorInvestido) {
        this.nome = nome;
        this.valorInvestido = valorInvestido;
        this.dataInvestimento = new Date();
    }

    public String getNome() {
        return nome;
    }

    public double getValorInvestido() {
        return valorInvestido;
    }

    public Date getDataInvestimento() {
        return dataInvestimento;
    }
}

class RepositorioContas {
    private Map<String, Conta> contas = new HashMap<>();

    public void adicionarConta(Conta conta) {
        contas.put(conta.getNumero(), conta);
    }

    public Conta buscarConta(String numero) {
        return contas.get(numero);
    }

    public Collection<Conta> listarContas() {
        return contas.values();
    }
}
