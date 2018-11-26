import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.*;

public abstract class Piso {
	protected ArrayList<VagaData> vagas = new ArrayList<VagaData>(); // 100
	
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
	
	//public Epoch tentaRemover(VeiculoData v) throws DeltaTInvalidoEX, PlacaNNEncontradaEX {
	public VeiculoData tentaRemover(VeiculoData v) throws DeltaTInvalidoEX, PlacaNNEncontradaEX {
		for (VagaData vaga : vagas) {
			if (vaga.getOcupado() && vaga.comparaPlaca(v)) {
				VeiculoData removido = vaga.getVeiculo(); //vaga.desocupa();
				Epoch delta = v.getEpoch().deltaE(removido.getEpoch());
				Epoch entrada = vaga.getVeiculo().getEpoch();
				//System.err.printf("Delta = %d - %d = %d\n", removido.getEpoch().asEpoch(), v.getEpoch().asEpoch(), delta);
				if (delta.asEpoch() < 0) { 
					throw new DeltaTInvalidoEX();
				} else if (delta.asEpoch() == 0) {
					throw new DeltaTInvalidoEX();
				} else {
					return vaga.desocupa();
					//return entrada;
				}
			}
		}
		throw new PlacaNNEncontradaEX();
	}
	
	public void salvaPiso(String arquivo) throws FileNotFoundException {
		PrintWriter p = new PrintWriter( new File(arquivo) );
		StringBuilder b = new StringBuilder();
		for (VagaData vaga : vagas) {
			app(b, vaga.getID());
			app(b, vaga.getTipo());
			app(b, vaga.getAndar());
			
			VeiculoData v = vaga.getVeiculo();
			if (v != null) {
				app(b, v.getPlaca());
				//app(b, v.getTipoString());
				app(b, v.getEpoch().toString());
			}
			
			b.append("\n");
		}
		p.write(b.toString());
		p.close();
		System.out.println("Saved file " + arquivo);
	}
	
	public void carregaPiso(String arquivo) throws FileNotFoundException, NumberFormatException, BadEpochStringEX {
		BufferedReader reader = new BufferedReader( new FileReader(arquivo) );
		String line;
		String split[];
		vagas.clear();
		try {
			while ( (line = reader.readLine()) != null ) {
				split = line.split(",");
				String idString = split[0];
				String tipoString = split[1];
				String andarString = split[2];
				int id = Integer.parseInt(idString);
				int tipo = Integer.parseInt(tipoString);
				int andar = Integer.parseInt(andarString);
				
				if (split.length == 3) { //Vaga vazia
					vagas.add(new VagaData(id, tipo, andar));
				} else if (split.length == 5) { //Vaga ocupada
					String placa = split[3];
					String epochString = split[4];
					Epoch entrada = Epoch.parseFromString(epochString);
					VeiculoData veiculo = new VeiculoData(placa, entrada, tipo);
					vagas.add(new VagaData(id, tipo, andar, veiculo));
				}
			}
		} catch (IOException e) { }
	}
	
	private void app(StringBuilder b, String s) {
		b.append(s);
		b.append(",");
	}
	private void app(StringBuilder b, int i) {
		b.append(i);
		b.append(",");
	}
}
