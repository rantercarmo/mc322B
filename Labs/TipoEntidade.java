public enum TipoEntidade {
    VAZIO('.'), ROBO('R'), OBSTACULO('O'), DESCONHECIDO('?');

    private final char representacao;

    TipoEntidade (char r){
        this.representacao = r;
    }

    public char getRepresentacao(){
        return representacao;
    }
}
