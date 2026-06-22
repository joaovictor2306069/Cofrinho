/** Moeda em Real (BRL). Cotação 1.0, pois já está em Real. */
public class Real extends Moeda {

    public Real(double valor, int quantidade) {
        super(valor, quantidade);
    }

    @Override
    public String getNome() {
        return "Real";
    }

    @Override
    public String getSimbolo() {
        return "R$";
    }

    @Override
    public double getCotacaoParaReal() {
        return 1.0;
    }
}
