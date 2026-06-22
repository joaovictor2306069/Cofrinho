/** Ponto de entrada. Apenas monta as dependências e inicia o menu. */
public class Main {
    public static void main(String[] args) {
        Cofrinho cofrinho = new Cofrinho();
        MenuConsole menu = new MenuConsole(cofrinho);
        menu.iniciar();
    }
}
