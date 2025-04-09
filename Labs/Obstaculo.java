public enum Obstaculo {
    // Atributos individuais de cada tipo de obstáculo. Todos eles possuem forma circular, logo o tamanhoX representa seu diametro e o tamanhoZ representa sua altura
    TORRE (8, 13),
    BURACO (0, 4),
    PEDRA (2, 2),
    MORRINHO (9, 8);

    public final int tamanhoX;
    public final int tamanhoZ;
    public int posX; // posicao X no mapa que o objeto pode ser adicionado
    public int posY; // posicao Y no mapa que o objeto pode ser adicionado

    Obstaculo (int tamanhoX, int tamanhoZ) {
        this.tamanhoX = tamanhoX;
        this.tamanhoZ = tamanhoZ;
    }

    // Registro da posicao do obstaculo no mapa
    public void AddObst (int X, int Y) {
        posX = X;
        posY = Y;
    }

    // Avalia se o objeto pode bloquear a passagem do robo
    public boolean bloqueiaPassagem (int Xpretendido, int Ypretendido, int Zpretendido, String tipoRobo) {
        if (tipoRobo == "aereo") {
            if (((posX - Xpretendido) * (posX - Xpretendido) < tamanhoX * tamanhoX) && Zpretendido < tamanhoZ) {
                return true;
            } else {
                return false;
            }
        } else {
            if ((posX - Xpretendido) * (posX - Xpretendido) < tamanhoX * tamanhoX) {
                return true;
            } else {
                return false;
            }
        }
    }

    // area do objeto
    public String Area () {
        double A = 3.14 * tamanhoX * tamanhoX / 4;
        String Area = A + "m2";
        return Area;
    }

    // altura do objeto
    public int Altura () {
        return tamanhoZ;
    }
}