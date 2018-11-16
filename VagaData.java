import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class VagaData implements ActionListener{
	private boolean ocupado = false;
	//private String placa;
	//private int hh, mm, ss;
	private VeiculoData veiculo = null;
	private int id;
	private int tipo;
	private int andar;
	
	public VagaData(int id, int tipo, int andar) {
		//System.out.printf("Creating %d as %d\n", id, tipo);
		//this.ocupado = false;
		//this.placa = "";
		//this.hh = -1;
		//this.mm = -1;
		//this.ss = -1;
		//this.veiculo = null;
		this.id = id;
		this.tipo = tipo;
		this.andar = andar;
	}
	
	public VagaData(int id, int tipo, int andar, VeiculoData v) {
		this(id, tipo, andar);
		this.veiculo = v;
		this.ocupado = true;
	}
	
	public int getTipo() { return this.tipo; }
	public int getID()   { return this.id; }
	public boolean getOcupado() { return this.ocupado; }
	public VeiculoData getVeiculo() { return this.veiculo; }
	public int getAndar() { return this.andar; }
	
	public void ocupa(VeiculoData v) {
		if (this.ocupado) { System.err.println("Veiculo inserido em vaga ocupada"); return; }
		this.ocupado = true;
		this.veiculo = v;
	}
	
	public VeiculoData desocupa() {
		if (!this.ocupado) { System.err.println("Veiculo removido de vaga desocupada"); return null; }
		this.ocupado = false;
		VeiculoData ret = this.veiculo;
		this.veiculo = null;
		return ret;
	}
	
	public boolean comparaPlaca(VeiculoData v) {
		return v.getPlaca().equals(this.veiculo.getPlaca());
	}
	
	public JButton formatAsPanel() {
		Font f = new Font("Arial", Font.PLAIN, 10);
		String content;
		String tipo_vaga;
		if (this.tipo == 0) {
			tipo_vaga = "Carro";
		} else if (this.tipo == 1) {
			tipo_vaga = "Caminhonete";
		} else {
			tipo_vaga = "Moto";
		}
		
		if (this.ocupado) {
			content = "<html><div style='text-align: center;'>" + tipo_vaga + "<br>" + this.veiculo.getPlaca() + "<br>" + this.veiculo.getEpoch().toString() + "</div></html>";
		} else {
			content = "<html><div style='text-align: center;'>" + tipo_vaga + "<br>Vaga livre</div></html>";
		}
		
		JButton out = new JButton(content);
		
		if (this.ocupado) { out.setBackground(Color.RED); } else { out.setBackground(Color.GREEN); }
		out.setOpaque(true);
		out.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		out.setPreferredSize(new Dimension(90, 50));
		
		out.addActionListener(this);
		
		out.setFont(f);
		return out;
	}

	@Override
	public void actionPerformed(ActionEvent a) {
		//String cmd = a.getActionCommand();
		//if (cmd.equals(String.format("load_%d", id))) { }
		JFrame popup = new JFrame(String.format("Vaga %d", id + 1));
		popup.setContentPane( new VagaInfoIHC(popup, this));
		popup.pack();
		popup.setVisible(true);
	}
}
