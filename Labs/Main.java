public class Main {

    public static void main(String[] args){
        Ambiente a1 = new Ambiente(10, 10,100);
        
        a1.adicionarObstaculo("buraco", 45, 28);
        a1.adicionarObstaculo("torre", 10, 10);
        a1.adicionarObstaculo("pedra", 450, 120);
        a1.adicionarObstaculo("morrinho", 34, 567);
        a1.adicionarObstaculo("buraco", 68, 150);

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
            } else if (NovoMenu.In.equals("/e")) {
                break;
            }
        }
    }
}
