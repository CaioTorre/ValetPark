import java.io.FileNotFoundException;

public class Piso1 extends Piso  {
	private static Piso1 self;
	
	public static Piso1 getInstance() {
		if (self == null) self = new Piso1();
		return self;
	}
	
	private Piso1() {
		try {
			carregaPiso(Sistema.p1Arq, 100);
		} catch (FileNotFoundException ex) {
			System.err.println("Arquivo nao foi encontrado, criando piso 1 vazio");
			criaVazio();
		} catch (NumberFormatException ex) {
			System.err.println("Arquivo possui inteiros mal-formados, criando piso 1 vazio");
			ex.printStackTrace(System.err);
			criaVazio();
		} catch (BadEpochStringEX ex) {
			System.err.println("Arquivo possui epochs mal-formados, criando piso 1 vazio");
			ex.printStackTrace(System.err);
			criaVazio();
		}
	}
	
	private void criaVazio() {
		vagas.clear();
		for (int i = 0; i < 100; i++) { vagas.add(new VagaData(100 + i, 0, 1)); }
	}
}
