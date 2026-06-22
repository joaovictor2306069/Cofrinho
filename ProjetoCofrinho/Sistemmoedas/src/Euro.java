/** Moeda em Euro (EUR). Cotação fixa de exemplo: 1 euro = 5.5 reais. */
public class Euro extends Moeda {

    public Euro(double valor, int quantidade) {
        super(valor, quantidade);
    }

    @Override
    public String getNome() {
        return "Euro";
    }

    @Override
    public String getSimbolo() {
        return "€";
    }

    @Override
    public double getCotacaoParaReal() {
        return 5.5;
    }
}
