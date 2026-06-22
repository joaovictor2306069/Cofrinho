/** Moeda em Dólar (USD). Cotação fixa de exemplo: 1 dólar = 5.0 reais. */
public class Dolar extends Moeda {

    public Dolar(double valor, int quantidade) {
        super(valor, quantidade);
    }

    @Override
    public String getNome() {
        return "Dólar";
    }

    @Override
    public String getSimbolo() {
        return "$";
    }

    @Override
    public double getCotacaoParaReal() {
        return 5.0;
    }
}
