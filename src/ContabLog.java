
public class ContabLog {
	private String placa;
	private String tipo;
	private Epoch entrada, saida;
	private float preco;
	
	public ContabLog(String placa, String tipo, Epoch entrada, Epoch saida, float preco) {
		this.placa = placa;
		this.tipo = tipo;
		this.entrada = entrada;
		this.saida = saida;
		this.preco = preco;
	}
	
	public String	getPlaca()	{ return placa; }
	public String	getTipo()	{ return tipo; }
	public Epoch	getEntrada(){ return entrada; }
	public Epoch	getSaida()	{ return saida; }
	public Epoch	getDelta()	{ return saida.deltaE(entrada); }
	public float	getPreco()	{ return preco; }
	
	public Object[]	getRow() { 
		Object[] list = {placa, tipo, entrada, saida, preco}; 
		return list;
	}
}
