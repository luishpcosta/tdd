package test.br.com.lhpc.app.builder;

import main.java.br.com.lhpc.app.Lance;
import main.java.br.com.lhpc.app.Leilao;
import main.java.br.com.lhpc.app.Usuario;

public class CriadorDeLeilao {

    private Leilao leilao;

    public CriadorDeLeilao para(String descricao) {
        this.leilao = new Leilao(descricao);
        return this;
    }

    public CriadorDeLeilao lance(Usuario usuario, double valor) {
        this.leilao.propoe(new Lance(usuario, valor));
        return this;
    }

    public CriadorDeLeilao dobrarLanceDo(Usuario usuario) {
        this.leilao.dobrarLanceDo(usuario);
        return this;
    }

    public Leilao constroi() {
        return this.leilao;
    }
}