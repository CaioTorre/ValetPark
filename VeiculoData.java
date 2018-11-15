public class VeiculoData {
	private String placa;
	private int hh, mm, ss;
	private int tipo;
	
	public VeiculoData(String placa, int hh, int mm, int ss, int tipo) {
		this.placa = placa;
		this.hh = hh;
		this.mm = mm;
		this.ss = ss;
		this.tipo = tipo;
	}
	
	public int getTipo() { return this.tipo; }
	public int getHH() { return this.hh ; }
	public int getMM() { return this.mm ; }
	public int getSS() { return this.ss ; }
	public String getPlaca() { return this.placa ; }
}
