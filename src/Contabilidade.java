import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Contabilidade {
	private static Contabilidade self;
	private Contabilidade() {
		try {
			carregaLogs(Sistema.lgArq);
		} catch (NumberFormatException e) {
			System.err.println("Arquivo de contabilidade possui inteiros mal-formados");
			e.printStackTrace(System.err);
		} catch (FileNotFoundException e) {
			System.err.println("Arquivo de contabilidade nao foi encontrado");
		} catch (BadEpochStringEX e) {
			System.err.println("Arquivo de contabilidade possui epochs mal-formados");
			e.printStackTrace(System.err);
		}
	}
	
	public static Contabilidade getInstance() {
		if (self == null) self = new Contabilidade();
		return self;
	}
	
	private ArrayList<ContabLog> log = new ArrayList<ContabLog>();
	public void insertLog(ContabLog l) { log.add(l); }
	public ContabLog getLogIndexed(int index) { return log.get(index); }
	//public ArrayList<ContabLog> getLogs() { return log; }
	public Object[][] getRows() {
		Object[][] precursor = new Object[log.size()][];
		int i = 0;
		for (ContabLog l : log) {
			precursor[i++] = l.getRow();
		}
		return precursor;
	}
	
	public void salvaLogs(String arquivo) throws FileNotFoundException {
		PrintWriter p = new PrintWriter( new File(arquivo) );
		StringBuilder b = new StringBuilder();
		for (ContabLog l : log) {
			app(b, l.getPlaca());
			app(b, l.getTipo());
			app(b, l.getEntrada().toString());
			app(b, l.getSaida().toString());
			app(b, l.getPreco());
			b.append("\n");
		}
		p.write(b.toString());
		p.close();
		System.out.println("Saved file " + arquivo);
	}
	
	public void carregaLogs(String arquivo) throws FileNotFoundException, NumberFormatException, BadEpochStringEX {
		BufferedReader reader = new BufferedReader( new FileReader(arquivo) );
		String line;
		String split[];
		log.clear();
		try {
			while ( (line = reader.readLine()) != null ) {
				split = line.split(",");
				String placa = split[0];
				String tipo = split[1];
				String entradaString = split[2];
				Epoch entrada = Epoch.parseFromString(entradaString);
				String saidaString = split[3];
				Epoch saida = Epoch.parseFromString(saidaString);
				String precoString = split[4];
				float preco = Float.parseFloat(precoString);
				
				insertLog( new ContabLog(placa, tipo, entrada, saida, preco) );
			}
		} catch (IOException e) { }
	}
	
	private void app(StringBuilder b, String s) {
		b.append(s);
		b.append(",");
	}
	private void app(StringBuilder b, float f) {
		b.append(f);
		b.append(",");
	}
}
