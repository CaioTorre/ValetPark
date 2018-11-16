import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Sistema {
	private static Sistema self;
	public  static Sistema getInstance() { if (self == null) { self = new Sistema(); } return self; }

	private JFrame control;
	private JPanel screen;
	
	private Piso pt, p1;
	private Contabilidade contab;
	
	public final static String ptArq = "pisoTerreo.csv";
	public final static String p1Arq = "piso1.csv";
	
	private Sistema() {
		pt = PisoT.getInstance();
		p1 = Piso1.getInstance();
		contab = Contabilidade.getInstance();
		
		control = new JFrame("Valet Parking");
		control.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		screen = new MainIHC();
		
        control.setContentPane(screen);
        control.pack();
        control.setVisible(true);
	}
	
	public int entraCarro(String placa, int hh, int mm, int ss, int tipo, int vaga) throws VagaInvalidaEX, HoraInvalidaEX {
		if (vaga < 1 || vaga > 200) { throw new VagaInvalidaEX(); }
		if (hh < 0 || mm < 0 || mm > 59 || ss < 0 || ss > 59 || hh + mm + ss < 1) { throw new HoraInvalidaEX(); }
		int res = -1;
		try {
			if (vaga < 101) { //Terreo
				res = pt.tentaInserir(new VeiculoData(placa, new Epoch(hh, mm, ss), tipo), vaga);
				pt.salvaPiso(ptArq);
			} else { //Piso 1
				res = p1.tentaInserir(new VeiculoData(placa, new Epoch(hh, mm, ss), tipo), vaga - 100);
				p1.salvaPiso(p1Arq);
			}
		} catch (FileNotFoundException ex) {
			System.err.println("File not found when saving one of the files");
			ex.printStackTrace(System.err);
		}
		return res;
	}
	
	public int entraCarro(String placa, int hh, int mm, int ss, int tipo) throws HoraInvalidaEX {
		if (hh < 0 || mm < 0 || mm > 59 || ss < 0 || ss > 59 || hh + mm + ss < 1) { throw new HoraInvalidaEX(); }
		int res;
		res = pt.tentaInserir(new VeiculoData(placa, new Epoch(hh, mm, ss), tipo));
		if (res > -1) {
			try {
				pt.salvaPiso(ptArq);
			} catch (FileNotFoundException e) {
				System.err.println("File not found when saving pt");
				e.printStackTrace(System.err);
			}
			return res;
		}
		res = p1.tentaInserir(new VeiculoData(placa, new Epoch(hh, mm, ss), tipo));
		if (res > -1) {
			try {
				p1.salvaPiso(p1Arq);
			} catch (FileNotFoundException e) {
				System.err.println("File not found when saving p1");
				e.printStackTrace(System.err);
			}
		}
		return res;
	}
	
	public void saiCarro(String placa, Epoch e) throws HoraInvalidaEX, DeltaTInvalidoEX, PlacaNNEncontradaEX {
		boolean gotIt = false;
		VeiculoData paraRemover = new VeiculoData(placa, e, 0);
		if (!paraRemover.getEpoch().isValid()) throw new HoraInvalidaEX();
		try {
			processaSaida(pt, paraRemover, e);
			gotIt = true;
			pt.salvaPiso(ptArq);
		} catch (PlacaNNEncontradaEX ex) { //Talvez esteja em P1
			
		} catch (DeltaTInvalidoEX ex) {
			throw ex;
		} catch (FileNotFoundException e1) {
			System.err.println("File not found when saving pt");
			e1.printStackTrace(System.err);
		}
		if (gotIt) return;
		
		try {
			processaSaida(p1, paraRemover, e);
			p1.salvaPiso(p1Arq);
		} catch (PlacaNNEncontradaEX ex) {
			throw ex;
		} catch (DeltaTInvalidoEX ex) {
			throw ex;
		} catch (FileNotFoundException e1) {
			System.err.println("File not found when saving p1");
			e1.printStackTrace(System.err);
		}
	}
	
	private void processaSaida(Piso p, VeiculoData paraRemover, Epoch e) throws PlacaNNEncontradaEX, DeltaTInvalidoEX {
		Epoch res = p.tentaRemover(paraRemover);
		float preco = calculaPreco(e.deltaE(res));
		String placa = paraRemover.getPlaca();
		String tipo = paraRemover.getTipoString();
		contab.insertLog( new ContabLog(placa, tipo, res, e, preco) );
		new NotinhaIHC(res, e, placa, tipo, preco);
	}
	
	private float calculaPreco(Epoch delta) {
		int t = delta.asEpoch();
		if (t < 15 * 60) return (float)0.0; //15 minutos de graca
		if (t < 60 * 60) return (float)7.0; //7 reais a primeira hora
		if (t < 12 * 60 * 60) return (float)(7.0 + 3.0 * t / 12.0);
		return (float)70.0; //Pernoite
	}
	
	public void refreshMain() {
		MainIHC m = (MainIHC) screen;
		m.refreshView();
	}
}
