import java.io.FileNotFoundException;

public class PisoT extends Piso {
	private static PisoT self;
	
	public static PisoT getInstance() {
		if (self == null) self = new PisoT();
		return self;
	}
	
	private PisoT() {
		try {
			carregaPiso(Sistema.ptArq, 0);
		} catch (FileNotFoundException ex) {
			System.err.println("Arquivo nao foi encontrado, criando piso terreo vazio");
			criaVazio();
		} catch (NumberFormatException ex) {
			System.err.println("Arquivo possui inteiros mal-formados, criando piso terreo vazio");
			ex.printStackTrace(System.err);
			criaVazio();
		} catch (BadEpochStringEX ex) {
			System.err.println("Arquivo possui epochs mal-formados, criando piso terreo vazio");
			ex.printStackTrace(System.err);
			criaVazio();
		}
	}
	
	private void criaVazio() {
		double temp;
		vagas.clear();
		for (int i = 0; i < 100; i++) { 
			temp = Math.max(Math.ceil((i+1.0)/20.0 - 3.0), 0.0);
			//System.out.printf("%d - %f (%d)\n", i, temp, (int)temp);
			vagas.add( new VagaData(i, (int)temp, 0) );
		}
	}
}
