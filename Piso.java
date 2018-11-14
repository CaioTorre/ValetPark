import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

public abstract class Piso {
	protected ArrayList<VagaData> vagas = new ArrayList(); // 100
	
	public JComponent assembleVagas() {
		JPanel out = new JPanel();
		out.setLayout(new GridLayout(11, 11, 5, 7));
		out.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		out.add(new JLabel(""));
		JLabel cod;
		int i;
		for (i = 0; i < 10; i++) { 
			cod = new JLabel(String.format("%d", i + 1), SwingConstants.CENTER);
			cod.setAlignmentX(JComponent.CENTER_ALIGNMENT);
			cod.setSize(new Dimension(50, 15));
			out.add(cod);
		}
		i = 0;
		for (VagaData vaga : vagas) { 
			if (i % 10 == 0) {
				cod = new JLabel(String.format("%c", (char)('A' + i / 10)), SwingConstants.RIGHT);
				cod.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
				cod.setSize(new Dimension(50, 15));
				out.add(cod);
			}
			out.add(vaga.formatAsPanel()); 
			i++;
		}
		return out;
	}
	

	public int tentaInserir(VeiculoData v) {
		for (VagaData vaga : vagas) {
			if (!vaga.getOcupado() && vaga.getTipo() == v.getTipo()) {
				vaga.ocupa(v);
				return vaga.getID();
			}
		}
		return -1;
	}
	
	public int tentaInserir(VeiculoData v, int vindex) {
		VagaData vaga = vagas.get(vindex);
		System.err.printf("Getting vaga @%d - %d...",  vindex, vaga.getID());
		if (!vaga.getOcupado() && vaga.getTipo() == v.getTipo()) {
			vaga.ocupa(v);
			return vaga.getID();
		}
		return -1;
	}
	
	public int tentaRemover(VeiculoData v) throws DeltaTInvalidoEX, PlacaNNEncontradaEX {
		for (VagaData vaga : vagas) {
			if (vaga.getOcupado() && vaga.comparaPlaca(v)) {
				VeiculoData removido = vaga.getVeiculo(); //vaga.desocupa();
				int delta = v.getEpoch().deltaT(removido.getEpoch());
				//System.err.printf("Delta = %d - %d = %d\n", removido.getEpoch().asEpoch(), v.getEpoch().asEpoch(), delta);
				if (delta < 0) { 
					throw new DeltaTInvalidoEX();
				} else if (delta == 0) {
					throw new DeltaTInvalidoEX();
				} else {
					vaga.desocupa();
					return delta;
				}
			}
		}
		throw new PlacaNNEncontradaEX();
	}
}
