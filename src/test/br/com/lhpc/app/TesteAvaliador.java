package test.br.com.lhpc.app;

import main.java.br.com.lhpc.app.servico.Avaliador;
import main.java.br.com.lhpc.app.Lance;
import main.java.br.com.lhpc.app.Leilao;
import main.java.br.com.lhpc.app.Usuario;
import org.junit.Before;
import org.junit.Test;
import test.br.com.lhpc.app.builder.CriadorDeLeilao;

import java.util.List;

import static org.junit.Assert.assertEquals;


public class TesteAvaliador {

    private Avaliador leiloeiro;
    private Usuario joao;
    private Usuario jose;
    private Usuario maria;

    @Before
    public void inicializar() {
        this.leiloeiro = new Avaliador();
        this.joao = new Usuario("João");
        this.jose = new Usuario("José");
        this.maria = new Usuario("Maria");
    }

    @Test
    public void deveEntenderLancesEmOrdemCrescente() {

        Leilao leilao = new CriadorDeLeilao().para("Playstation 4 Novo")
                .lance(maria, 100.0)
                .lance(joao, 350.0)
                .lance(jose, 400.0)
                .constroi();

        // executando a acao
        leiloeiro.avalia(leilao);

        // comparando a saida com o esperado
        double maiorEsperado = 400;
        double menorEsperado = 100;

        assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.0001);
        assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.0001);
    }

    @Test
    public void deveEntenderLeilaoComApenasUmLance() {
        Leilao leilao = new CriadorDeLeilao()
                .para("Playstation 4 Novo")
                .lance(joao, 1000.00)
                .constroi();

        leiloeiro.avalia(leilao);

        assertEquals(1000.00, leiloeiro.getMaiorLance(), 0.0001);
        assertEquals(1000.00, leiloeiro.getMenorLance(), 0.0001);
    }

    @Test
    public void deveEncontrarOsTresMaioresLances() {
        Leilao leilao = new CriadorDeLeilao().para("Playstation 4 Novo")
                .lance(joao, 500.0)
                .lance(maria, 200.0)
                .lance(joao, 300.0)
                .lance(maria, 400.0)
                .constroi();

        leiloeiro.avalia(leilao);
        List<Lance> maioresLances = leiloeiro.getTresMaioresLances();

        assertEquals(3, maioresLances.size());
        assertEquals(500.00, maioresLances.get(0).getValor(), 0.0001);
        assertEquals(400.00, maioresLances.get(1).getValor(), 0.0001);
        assertEquals(300.00, maioresLances.get(2).getValor(), 0.0001);
    }

	@Test(expected=RuntimeException.class)
	public void naoDeveAvaliarLeiloesSemNenhumLanceDado() {
		Leilao leilao = new CriadorDeLeilao()
				.para("Playstation 4  Novo")
				.constroi();

		leiloeiro.avalia(leilao);
	}
}
