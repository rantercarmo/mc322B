import java.util.Objects;

public class Obstaculo implements Entidade {
    private static int contadorIds = 0;
    private final int idInterno;
    private int x, y, z;
    private String nomeTipo; 
    private char representacaoVisual;

    public Obstaculo(String nomeTipo, int x, int y, int z, char representacaoVisual) {
        this.idInterno = contadorIds++;
        this.nomeTipo = nomeTipo;
        this.x = x;
        this.y = y;
        this.z = z;
        this.representacaoVisual = representacaoVisual;
    }

    @Override
    public int getX() { return x; }
    @Override
    public int getY() { return y; }
    @Override
    public int getZ() { return z; }

    @Override
    public TipoEntidade getTipo() {
        return TipoEntidade.OBSTACULO;
    }

    @Override
    public String getDescricao() {
        return nomeTipo + " (ID_Obs:" + idInterno + ") em (" + x + "," + y + "," + z + ")";
    }

    @Override
    public char getRepresentacao() {
        return representacaoVisual;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Obstaculo obstaculo = (Obstaculo) o;
        // Dois obstáculos são considerados iguais se tiverem o mesmo ID interno e tipo.
        // A posição não entra aqui, pois o ID deve ser único para a lista de entidades.
        return idInterno == obstaculo.idInterno && Objects.equals(nomeTipo, obstaculo.nomeTipo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idInterno, nomeTipo);
    }

    @Override
    public String toString() {
        return getDescricao();
    }
}