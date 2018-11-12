import java.awt.Color;

import javax.swing.JLabel;

public class VagaData {
	private boolean ocupado;
	//private String placa;
	//private int hh, mm, ss;
	private VeiculoData veiculo;
	private int id;
	private int tipo;
	
	public VagaData(int id, int tipo) {
		this.ocupado = false;
		//this.placa = "";
		//this.hh = -1;
		//this.mm = -1;
		//this.ss = -1;
		this.veiculo = null;
		this.id = id;
		this.tipo = tipo;
	}
	
	public int getTipo() { return this.tipo; }
	public int getID()   { return this.id; }
	public boolean getOcupado() { return this.ocupado; }
	
	public void ocupa(VeiculoData v) {
		if (this.ocupado) { System.err.println("Veiculo inserido em vaga ocupada"); return; }
		this.ocupado = true;
		this.veiculo = v;
	}
	
	public void desocupa() {
		if (this.ocupado) { System.err.println("Veiculo removido de vaga desocupada"); return; }
		this.ocupado = false;
		this.veiculo = null;
	}
	
	public JLabel formatAsPanel() {
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
			content = "<html><div style='text-align: center;'>" + tipo_vaga + "<br>" + this.veiculo.getPlaca() + "<br>" + String.format("%2d:%2d:%2d", this.veiculo.getHH(), this.veiculo.getMM(), this.veiculo.getSS()) + "</div></html>";
		} else {
			content = "<html><div style='text-align: center;'>" + tipo_vaga + "<br>Vaga livre</div></html>";
		}
		
		JLabel out = new JLabel(content);
		
		if (this.ocupado) { out.setBackground(Color.RED); } else { out.setBackground(Color.GREEN); }
		out.setOpaque(false);
		return out;
	}
}
