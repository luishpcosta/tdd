package main.java.br.com.lhpc.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Leilao {

    private String descricao;
    private List<Lance> lances;

    public Leilao(String descricao) {
        this.descricao = descricao;
        this.lances = new ArrayList<Lance>();
    }

    public void propoe(Lance lance) {
        if (lances.isEmpty() || podeDarLance(lance.getUsuario())) {
            lances.add(lance);
        }
    }

    private boolean podeDarLance(Usuario usuario) {
        return !ultimoUsuario().equals(usuario) && qtdDelancesDo(usuario) < 5;
    }

    private long qtdDelancesDo(Usuario usuario) {
        return lances.stream().filter(lance -> lance.getUsuario().equals(usuario)).count();
    }

    private Usuario ultimoUsuario() {
        return lances.get(lances.size() - 1).getUsuario();
    }

    public List<Lance> getLances() {
        return Collections.unmodifiableList(lances);
    }

    public void dobrarLanceDo(Usuario usuario) {
        if (usuario == null) {
            return;
        }

        Lance ultimoLance = lances.stream()
                .filter(lance -> lance.getUsuario().equals(usuario))
                .reduce((primeiro, segundo) -> segundo).orElse(null);

        if (ultimoLance != null) {
            propoe(new Lance(ultimoLance.getUsuario(), ultimoLance.getValor() * 2));
        }
    }
}
