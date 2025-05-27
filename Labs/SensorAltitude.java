public class SensorAltitude extends Sensor implements SensorInterface {
    int alturaMax;

    public SensorAltitude(int raio, int alturaMax) {
        super(raio);
        this.alturaMax = alturaMax;
    }

    @Override
    public void monitorar() {
        return; // Método não utilizado, para que seja feita sobrecarga de método
    }
    public void monitorar (int roboZ) {
        if (roboZ > alturaMax) {
            System.out.println("Não é possível definir a altitude: a posição do robô excede a altitude máxima de operação do sensor.");
        } else {
            System.out.println("Sua altitude é: " + roboZ);
        }
    }
}