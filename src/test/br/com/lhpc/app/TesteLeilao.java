package test.br.com.lhpc.app;

import main.java.br.com.lhpc.app.Leilao;
import main.java.br.com.lhpc.app.Usuario;
import org.junit.Before;
import org.junit.Test;
import test.br.com.lhpc.app.builder.CriadorDeLeilao;

import static org.junit.Assert.assertEquals;

public class TesteLeilao {

    private Usuario steveJobs;
    private Usuario billGates;
    private Usuario steveWozniak;

    @Before
    public void inicializar() {
        this.steveJobs = new Usuario("Steve Jobs");
        this.billGates = new Usuario("Bill Gates");
        this.steveWozniak = new Usuario("Steve Wozniak");
    }


    @Test
    public void deveReceberUmLance() {
        Leilao leilao = new CriadorDeLeilao()
                .para("Macbook Pro 15")
                .lance(steveJobs, 2000)
                .constroi();

        assertEquals(1, leilao.getLances().size());
        assertEquals(2000.0, leilao.getLances().get(0).getValor(), 0.00001);
    }

    @Test
    public void deveReceberVariosLances() {
        Leilao leilao = new CriadorDeLeilao()
                .para("Macbook Pro 15")
                .lance(steveJobs, 2000)
                .lance(steveWozniak, 3000)
                .constroi();

        assertEquals(2, leilao.getLances().size());
        assertEquals(2000.0, leilao.getLances().get(0).getValor(), 0.00001);
        assertEquals(3000.0, leilao.getLances().get(1).getValor(), 0.00001);
    }

    @Test
    public void naoDeveAceitarDoisLancesSeguidosDoMesmoUsuario() {
        Leilao leilao = new CriadorDeLeilao().para("Macbook Pro 12")
                .lance(steveJobs, 2000.0)
                .lance(steveJobs, 3000.0)
                .constroi();

        assertEquals(1, leilao.getLances().size());
        assertEquals(2000.0, leilao.getLances().get(0).getValor(), 0.00001);
    }

    @Test
    public void naoDeveAceitarMaisDoQue5LancesDeUmMesmoUsuario() {
        Leilao leilao = new CriadorDeLeilao().para("Macbook Pro 21")
                .lance(steveJobs, 2000)
                .lance(billGates, 3000)
                .lance(steveJobs, 4000)
                .lance(billGates, 5000)
                .lance(steveJobs, 6000)
                .lance(billGates, 7000)
                .lance(steveJobs, 8000)
                .lance(billGates, 9000)
                .lance(steveJobs, 10000)
                .lance(billGates, 11000)
                // lance deve ser ignorado
                .lance(steveJobs, 12000)
                .constroi();

        assertEquals(10, leilao.getLances().size());
        assertEquals(11000.0, leilao.getLances().get(leilao.getLances().size() - 1).getValor(), 0.00001);
    }

    @Test
    public void deveDobrarOUltimoLanceDado() {
        Leilao leilao = new CriadorDeLeilao()
                .para("Macbook Pro 16")
                .lance(steveJobs, 2000)
                .lance(billGates, 3000)
                .dobrarLanceDo(steveJobs)
                .constroi();

        assertEquals(4000, leilao.getLances().get(2).getValor(), 0.00001);
    }
}
