<<<<<<< HEAD
public abstract class Robo implements Entidade {
=======
public class Robo implements RoboInterface {
>>>>>>> ce5e6d26f89143468f134c545aa8cc7b6bbc1793
    public final String nome;
    public int posicaoX = 0;
    public int posicaoY = 0;
    public int posicaoZ = 0;
    public String direcao;


    public Robo(String nome, int posicaoX, int posicaoY, int posicaoZ){
        this.nome = nome;
        this.posicaoX = posicaoX;
        this.posicaoY = posicaoY;
        this.posicaoZ = posicaoZ;
    }

    //getters e setters
     // Implementação dos métodos da interface Entidade
    @Override
    public abstract String getDescricao();

    @Override
    public abstract char getRepresentacao();

    @Override
    public abstract TipoEntidade getTipo();

    @Override
    public int getX() {
        return this.posicaoX;
    }

    @Override
    public int getY() {
        return this.posicaoY;
    }

    @Override
    public int getZ() {
        return this.posicaoZ;
    }


    @Override
    public void mover(int deltaX, int deltaY, int deltaZ){
        this.posicaoX += deltaX;
        this.posicaoY += deltaY;
        this.posicaoZ += deltaZ;
    }

    public void exibirPosicao(){
        System.out.println("A posição é\nx:" + getX() + "\ny:" + getY() + "\nz:" + getZ() );
    }
   
}