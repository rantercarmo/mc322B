import java.util.ArrayList;

public class SensorProximidade extends Sensor {
    public SensorProximidade (int raio){
        super(raio);
    }

    public ArrayList<Obstaculo> monitorar (ArrayList<Obstaculo> obstaculos, int Xrobo, int Yrobo) {
        ArrayList<Obstaculo> obstaculosProx = new ArrayList<Obstaculo>();

        for (int i = 0; i < obstaculos.size(); i++) {
            if ((obstaculos.get(i).posX - Xrobo) * (obstaculos.get(i).posX - Xrobo) + (obstaculos.get(i).posY - Yrobo) * (obstaculos.get(i).posY - Yrobo) < raio * raio) {
                obstaculosProx.add(obstaculos.get(i));
            }
        }
        return obstaculosProx;
    }
}