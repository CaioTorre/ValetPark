import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

public class Piso {
	protected static ArrayList<VagaData> vagas = new ArrayList(); // 100
	
	public JComponent assembleVagas() {
		JPanel out = new JPanel();
		out.setLayout(new GridLayout(10, 10, 5, 15));
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
}
