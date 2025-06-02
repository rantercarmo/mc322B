public class SensorAltitude extends Sensor {
    private int altitudeMaximaOperacionalSensor;

    public SensorAltitude(int raioDeteccaoParaObstaculosAereos, int altitudeMaximaOperacionalSensor) {
        super(raioDeteccaoParaObstaculosAereos); // Raio pode ser usado para detectar obstáculos acima/abaixo
        this.altitudeMaximaOperacionalSensor = altitudeMaximaOperacionalSensor;
    }

    @Override
    public String lerDados(Robo robo, Ambiente ambiente) throws RoboDesligadoException {
        if (robo.getEstado() == EstadoRobo.DESLIGADO) {
            throw new RoboDesligadoException("Sensor altitude (Robo " + robo.getId() + "): Robô desligado.");
        }

        int altitudeAtual = robo.getZ();
        StringBuilder sb = new StringBuilder();
        sb.append("Sensor de Altitude (Robo ").append(robo.getId()).append("):\n");

        if (altitudeAtual > altitudeMaximaOperacionalSensor) {
            sb.append("  Altitude atual (").append(altitudeAtual).append("m) excede capacidade do sensor (")
              .append(altitudeMaximaOperacionalSensor).append("m). Leitura pode ser imprecisa.\n");
        } else {
            sb.append("  Altitude atual: ").append(altitudeAtual).append("m.\n");
        }

        if (robo.getZ() <= 0) {
             sb.append("  Alerta: Robô no nível do solo ou abaixo.\n");
        }
        if (robo.getZ() >= ambiente.altura -1 && ambiente.altura > 1) { // Evitar para ambiente altura 1
            sb.append("  Alerta: Robô próximo ao teto do ambiente (").append(ambiente.altura -1).append("m).\n");
        }

        // Detecção de obstáculos acima/abaixo usando o 'raioDeteccao'
        for (int dz = 1; dz <= raioDeteccao; dz++) {
            int checkZcima = robo.getZ() + dz;
            int checkZbaixo = robo.getZ() - dz;

            if (ambiente.dentroDosLimites(robo.getX(), robo.getY(), checkZcima)) {
                if (ambiente.getEntidadeEm(robo.getX(), robo.getY(), checkZcima).isPresent() ||
                    ambiente.getTipoEntidadeNoMapa(robo.getX(), robo.getY(), checkZcima) != TipoEntidade.VAZIO) {
                    sb.append("  Obstáculo detectado acima em Z=").append(checkZcima).append("\n");
                    break; // Para ao primeiro obstáculo
                }
            }
             if (ambiente.dentroDosLimites(robo.getX(), robo.getY(), checkZbaixo)) {
                if (ambiente.getEntidadeEm(robo.getX(), robo.getY(), checkZbaixo).isPresent() ||
                    ambiente.getTipoEntidadeNoMapa(robo.getX(), robo.getY(), checkZbaixo) != TipoEntidade.VAZIO) {
                    sb.append("  Obstáculo detectado abaixo em Z=").append(checkZbaixo).append("\n");
                    break; // Para ao primeiro obstáculo
                }
            }
        }
        return sb.toString();
    }
}