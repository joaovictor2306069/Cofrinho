import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Modelo do cofrinho: guarda e manipula as moedas.
 *
 * Trabalha apenas com o tipo abstrato Moeda (polimorfismo): não sabe nem
 * precisa saber se cada item é Real, Dólar ou Euro. Não imprime nada — quem
 * cuida da interface é o MenuConsole (separação de responsabilidades).
 */
public class Cofrinho {

    private final List<Moeda> moedas = new ArrayList<>();

    public void adicionar(Moeda moeda) {
        moedas.add(moeda);
    }

    /**
     * Remove a moeda na posição informada (índice baseado em 0).
     * @return true se removeu; false se o índice for inválido.
     */
    public boolean remover(int indice) {
        if (indice < 0 || indice >= moedas.size()) {
            return false;
        }
        moedas.remove(indice);
        return true;
    }

    public boolean estaVazio() {
        return moedas.isEmpty();
    }

    /** Lista somente leitura, para o menu percorrer e exibir. */
    public List<Moeda> getMoedas() {
        return Collections.unmodifiableList(moedas);
    }

    /** Soma o valor de todas as moedas já convertido para Real (polimorfismo). */
    public double calcularTotalConvertidoParaReal() {
        double total = 0.0;
        for (Moeda moeda : moedas) {
            total += moeda.converterParaReal();
        }
        return total;
    }
}
