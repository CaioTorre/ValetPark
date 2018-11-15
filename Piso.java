import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

public abstract class Piso {
	protected ArrayList<VagaData> vagas = new ArrayList(); // 100
	
	public JComponent assembleVagas() {
		JPanel out = new JPanel();
		out.setLayout(new GridLayout(10, 10, 5, 7));
		out.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		for (VagaData vaga : vagas) { out.add(vaga.formatAsPanel()); }
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
		if (!vaga.getOcupado() && vaga.getTipo() == v.getTipo()) {
			vaga.ocupa(v);
			return vaga.getID();
		}
		return -1;
	}
}
