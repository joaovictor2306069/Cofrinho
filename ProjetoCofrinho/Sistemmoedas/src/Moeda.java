import java.util.Locale;

/**
 * Representa uma moeda genérica guardada no cofrinho.
 *
 * É abstrata porque "moeda" sozinha não existe na prática: o que existe é
 * Real, Dólar ou Euro. Cada subclasse define seu nome, símbolo e cotação.
 */
public abstract class Moeda {

    private final double valor;   // denominação da moeda (ex.: 0.25)
    private int quantidade;       // quantas moedas dessa denominação foram guardadas

    public Moeda(double valor, int quantidade) {
        this.valor = valor;
        this.quantidade = quantidade;
    }

    // ----- Dados próprios de cada moeda (polimorfismo) -----
    public abstract String getNome();
    public abstract String getSimbolo();
    public abstract double getCotacaoParaReal();

    // ----- Encapsulamento -----
    public double getValor() {
        return valor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    // ----- Regras de negócio comuns a todas as moedas -----

    /** Valor total nesta moeda (denominação x quantidade). */
    public double calcularTotal() {
        return valor * quantidade;
    }

    /** Valor total já convertido para Real, usando a cotação da moeda. */
    public double converterParaReal() {
        return calcularTotal() * getCotacaoParaReal();
    }

    /**
     * Ex.: "Dólar - $ 0.25 x 4 = $ 1.00 | Em Real: R$ 5.00"
     * Locale.US garante o ponto como separador decimal, igual ao enunciado.
     */
    @Override
    public String toString() {
        return String.format(Locale.US,
                "%s - %s %.2f x %d = %s %.2f | Em Real: R$ %.2f",
                getNome(), getSimbolo(), valor, quantidade,
                getSimbolo(), calcularTotal(), converterParaReal());
    }
}
