package main.java.br.com.lhpc.app.servico;

import main.java.br.com.lhpc.app.Lance;
import main.java.br.com.lhpc.app.Leilao;
import java.util.List;
import java.util.stream.Collectors;

public class Avaliador {

	private double maiorDeTodos = Double.NEGATIVE_INFINITY;
	private double menorDeTodos = Double.POSITIVE_INFINITY;
	private double media = 0;
	private List<Lance> maioresLances;

	public void avalia(Leilao leilao) {

		if(leilao.getLances() == null || leilao.getLances().isEmpty()) {
			throw new RuntimeException("Não possível avaliar Leilão sem lances");
		}

		double total = 0;
		for (Lance lance : leilao.getLances()) {
			if (lance.getValor() > maiorDeTodos)
				maiorDeTodos = lance.getValor();
			if (lance.getValor() < menorDeTodos)
				menorDeTodos = lance.getValor();
			total += lance.getValor();
		}
		if (total == 0) {
			media = 0;
			return;
		}
		media = total / leilao.getLances().size();
		maioresLances = leilao.getLances().stream()
				.sorted((l1, l2) -> Double.compare(l2.getValor(), l1.getValor()))
				.limit(3)
				.collect(Collectors.toList());
	}

	public double getMaiorLance() {
		return maiorDeTodos;
	}

	public double getMenorLance() {
		return menorDeTodos;
	}

	public double getMedia() {
		return media;
	}

	public List<Lance> getTresMaioresLances() {
		return maioresLances;
	}
}
