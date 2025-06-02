import java.util.Optional;

public class SensorProximidade extends Sensor {
    public SensorProximidade(int raioDeteccao) {
        super(raioDeteccao);
    }

    @Override
    public String lerDados(Robo robo, Ambiente ambiente) throws RoboDesligadoException {
        if (robo.getEstado() == EstadoRobo.DESLIGADO) {
            throw new RoboDesligadoException("Sensor proximidade (Robo " + robo.getId() + "): Robô desligado.");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Sensor Proximidade (Robo ").append(robo.getId()).append(" em (")
          .append(robo.getX()).append(",").append(robo.getY()).append(",").append(robo.getZ())
          .append("), Raio: ").append(raioDeteccao).append("):\n");
        boolean detectouAlgo = false;

        // Verifica um cubo ao redor do robô
        for (int dz = -raioDeteccao; dz <= raioDeteccao; dz++) {
            for (int dy = -raioDeteccao; dy <= raioDeteccao; dy++) {
                for (int dx = -raioDeteccao; dx <= raioDeteccao; dx++) {
                    if (dx == 0 && dy == 0 && dz == 0) continue; // Posição do próprio robô

                    int checkX = robo.getX() + dx;
                    int checkY = robo.getY() + dy;
                    int checkZ = robo.getZ() + dz;

                    // Verifica distância para simular um sensor mais esférico (opcional)
                    // double distancia = Math.sqrt(dx*dx + dy*dy + dz*dz);
                    // if (distancia > raioDeteccao) continue;

                    if (ambiente.dentroDosLimites(checkX, checkY, checkZ)) {
                        Optional<Entidade> entOpt = ambiente.getEntidadeEm(checkX, checkY, checkZ);
                        if (entOpt.isPresent()) {
                            Entidade entidadeDetectada = entOpt.get();
                            // Não detectar a si mesmo se por acaso a lógica permitir
                            if (!entidadeDetectada.equals(robo)) {
                                 sb.append("  - ").append(entidadeDetectada.getDescricao()).append(" detectado em (")
                                  .append(checkX).append(",").append(checkY).append(",").append(checkZ).append(")\n");
                                detectouAlgo = true;
                            }
                        } else if (ambiente.getTipoEntidadeNoMapa(checkX, checkY, checkZ) != TipoEntidade.VAZIO) {
                             // Se há algo no mapa de ocupação mas não é uma "Entidade" gerenciada (ex: terreno)
                        }
                    }
                }
            }
        }

        if (!detectouAlgo) {
            sb.append("  Nenhuma entidade ou obstáculo significativo detectado no raio.\n");
        }
        return sb.toString();
    }
}