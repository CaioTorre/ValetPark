import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.util.Formatter;
import java.util.Locale;

public class Sistema {
	private static Sistema self;
	public  static Sistema getInstance() { if (self == null) { self = new Sistema(); } return self; }

	private JFrame control;
	private JPanel screen;
	
	private Piso pt, p1;
	private Contabilidade contab;
	
	public final static String ptArq = "pisoTerreo.csv";
	public final static String p1Arq = "piso1.csv";
	public final static String lgArq = "contabilidade.csv";
	public final static String vlArq = "valores.csv";
	
	private double[] valores = { 7.0, 8.5, 5.0 };
	
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
        
        BufferedReader reader = null;
        try {
        	reader = new BufferedReader( new FileReader(vlArq) );
			String line = reader.readLine();
			String[] split = line.split(",");
			valores[0] = Float.parseFloat(split[0]);
			valores[1] = Float.parseFloat(split[1]);
			valores[2] = Float.parseFloat(split[2]);
        } catch (FileNotFoundException ex) {
        } catch (IOException e) {
		} finally {
			try {
				if (reader != null) reader.close();
			} catch (IOException e) {
			}
		}
        
	}
	
	public Contabilidade getContabilidade() { return contab; }
	
	//public int entraCarro(String placa, int dia, int mes, int hh, int mm, int ss, int tipo, int vaga) throws VagaInvalidaEX, HoraInvalidaEX {
	public int entraCarro(String placa, String ep_str, int tipo, int vaga) throws VagaInvalidaEX, HoraInvalidaEX, BadEpochStringEX, InsertFailEX {
		if (vaga < 1 || vaga > 200) { throw new VagaInvalidaEX(); }
		//if (hh < 0 || mm < 0 || mm > 59 || ss < 0 || ss > 59 || hh + mm + ss < 1) { throw new HoraInvalidaEX(); }
		int res = -1;
		try {
			Epoch ep = Epoch.parseFromString(ep_str);
			//Epoch ep = new Epoch(dia, mes, hh, mm, ss);
			if (!ep.isValid()) { throw new HoraInvalidaEX(); }
			if (vaga < 100) { //Terreo
				res = pt.tentaInserir(new VeiculoData(placa, ep, tipo), vaga);
				pt.salvaPiso(ptArq);
			} else { //Piso 1
				res = p1.tentaInserir(new VeiculoData(placa, ep, tipo), vaga - 100);
				p1.salvaPiso(p1Arq);
			}
		} catch (FileNotFoundException ex) {
			System.err.println("File not found when saving one of the files");
			ex.printStackTrace(System.err);
		} catch (BadEpochStringEX ex) {
			System.err.println("Got bad epoch string");
			throw ex;
		} catch (InsertFailEX ex) {
			throw ex;
		}
		return res;
	}
	
	//public int entraCarro(String placa, int dia, int mes, int hh, int mm, int ss, int tipo) throws HoraInvalidaEX {
	public int entraCarro(String placa, String ep_str, int tipo) throws HoraInvalidaEX, BadEpochStringEX, InsertFailEX {
		//if (hh < 0 || mm < 0 || mm > 59 || ss < 0 || ss > 59 || hh + mm + ss < 1) { throw new HoraInvalidaEX(); }
		int res;
		//Epoch ep = new Epoch(dia, mes, hh, mm, ss);
		Epoch ep;
		try {
			ep = Epoch.parseFromString(ep_str);
			if (!ep.isValid()) { throw new HoraInvalidaEX(); }
		} catch (BadEpochStringEX ex) {
			System.err.println("Got bad epoch string");
			throw ex;
		}
		res = pt.tentaInserir(new VeiculoData(placa, ep, tipo));
		if (res > -1) {
			try {
				pt.salvaPiso(ptArq);
			} catch (FileNotFoundException e) {
				System.err.println("File not found when saving pt");
				e.printStackTrace(System.err);
			}
			return res;
		}
		res = p1.tentaInserir(new VeiculoData(placa, ep, tipo));
		if (res > -1) {
			try {
				p1.salvaPiso(p1Arq);
			} catch (FileNotFoundException e) {
				System.err.println("File not found when saving p1");
				e.printStackTrace(System.err);
			}
		} else {
			throw new InsertFailEX("Estacionamento cheio");
		}
		return res;
	}
	
	public void saiCarro(String placa, Epoch e) throws HoraInvalidaEX, DeltaTInvalidoEX, PlacaNNEncontradaEX {
		boolean gotIt = false;
		VeiculoData paraRemover = new VeiculoData(placa, e, 0);
		if (!e.isValid()) throw new HoraInvalidaEX();
		try {
			processaSaida(pt, paraRemover, e);
			gotIt = true;
			pt.salvaPiso(ptArq);
			contab.salvaLogs(lgArq);
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
			contab.salvaLogs(lgArq);
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
		VeiculoData anterior = p.tentaRemover(paraRemover);
		Epoch res = anterior.getEpoch();
		//System.err.printf("Entrou: %d; Saiu: %d\n", res.asEpoch(), e.asEpoch());
		float preco = calculaPreco(e.deltaT(res), anterior.getTipo());
		String placa = paraRemover.getPlaca();
		String tipo = anterior.getTipoString();
		contab.insertLog( new ContabLog(placa, tipo, res, e, preco) );
		new NotinhaIHC(res.toString(), e.toString(), placa, tipo, preco);
	}
	
	private float calculaPreco(int t, int tipoVeiculo) {
		//System.err.printf("Delta: %s, %d; Valor: %f\n", delta.toString(), delta.asEpoch(), valores[tipoVeiculo]);
		//int t = delta.asEpoch();
		if (t < 15 * 60) return (float)0.0; //15 minutos de graca
		return (float)((t / 3600) * valores[tipoVeiculo]);
		//if (t < 60 * 60) return (float)7.0; //7 reais a primeira hora
		//if (t < 12 * 60 * 60) return (float)(7.0 + 3.0 * t / 12.0);
		//return (float)70.0; //Pernoite
	}
	
	public void refreshMain() {
		MainIHC m = (MainIHC) screen;
		m.refreshView();
	}
	
	/*public void setAllStatesMainIHC(boolean state) {
		MainIHC m = (MainIHC) screen;
		m.setAllStates(state);
	}*/
	
	public void atualizaValores(double car, double cam, double mot) {
//		horaCarro = car;
//		horaCaminhonete = cam;
//		horaMoto = mot;
		valores[0] = car;
		valores[1] = cam;
		valores[2] = mot;
		
		try {
			PrintWriter p = new PrintWriter( new File(vlArq) );
			p.write(String.format(Locale.US, "%f,%f,%f", car, cam, mot));
			//p.write(new Formatter(Locale.US).format("%f,%f,%f", car, cam, mot));
			p.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public double[] getValores() { return valores; }
}
