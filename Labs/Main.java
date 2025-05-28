public class Main {

    public static void main(String[] args){
        Ambiente a1 = new Ambiente(10, 10,100);
        MenuInterativo NovoMenu = new MenuInterativo();

        a1.imprimirMapa();

        while (true) {
            NovoMenu.Iniciar();
            NovoMenu.In = NovoMenu.UserIn.nextLine();

            if (NovoMenu.In.equals("/s")) {
                NovoMenu.Sensores(a1.robos);
            } else if (NovoMenu.In.equals("/st")) {
                NovoMenu.Status(a1.robos, a1.obstaculos);
            } else if (NovoMenu.In.equals("/m")) {
                NovoMenu.Movimento(a1.robos);
            } else if (NovoMenu.In.equals("/pr")) {
                NovoMenu.Imprimir(a1);
            } else if (NovoMenu.In.equals("/add")){
                NovoMenu.AddRobo(a1);
            } else if (NovoMenu.In.equals("/obs")) {
                NovoMenu.addObstaculo(a1);
            } else if (NovoMenu.In.equals("/e")) {
                break;
            }
        }
    }
}
