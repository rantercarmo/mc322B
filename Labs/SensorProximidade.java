import java.util.ArrayList;

public class SensorProximidade extends Sensor implements SensorInterface {
    public SensorProximidade (int raio){
        super(raio);
    }
    @Override
    public void monitorar() {
        return; // Método não utilizado, para que seja feita sobrecarga
    }
    public void monitorar (ArrayList<Obstaculo> obstaculos, int Xrobo, int Yrobo) {
        ArrayList<Obstaculo> obstaculosProx = new ArrayList<Obstaculo>();

        for (int i = 0; i < obstaculos.size(); i++) {
            if ((obstaculos.get(i).posX - Xrobo) * (obstaculos.get(i).posX - Xrobo) + (obstaculos.get(i).posY - Yrobo) * (obstaculos.get(i).posY - Yrobo) < raio * raio) {
                obstaculosProx.add(obstaculos.get(i));
            }
        }

        System.out.println("Todos os obstáculos próximos são: " + obstaculosProx);
    }
}
