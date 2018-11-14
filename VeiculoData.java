public class VeiculoData {
	private String placa;
	//private int hh, mm, ss;
	private Epoch epoch;
	private int tipo;
	
	public VeiculoData(String placa, Epoch e, int tipo) { //, int hh, int mm, int ss, 
		this.placa = placa;
		this.epoch = e;
		//this.hh = hh;
		//this.mm = mm;
		//this.ss = ss;
		this.tipo = tipo;
	}
	
	public String getTipoString() {
		if (tipo == 0) return "Carro";
		if (tipo == 1) return "Caminhonete";
		return "Moto";
	}
	public int getTipo() { return this.tipo; }
	public Epoch getEpoch() { return this.epoch; }
	//public int getHH() { return this.hh ; }
	//public int getMM() { return this.mm ; }
	//public int getSS() { return this.ss ; }
	public String getPlaca() { return this.placa ; }
}
