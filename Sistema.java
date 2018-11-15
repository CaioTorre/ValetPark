import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Sistema implements ActionListener{
	private static Sistema self;
	public  static Sistema getInstance() { if (self == null) { self = new Sistema(); } return self; }

	private JFrame control;
	private JPanel screen;
	
	private Piso pt, p1;
	
	private ArrayList<JFrame> popups = new ArrayList<JFrame>();
	private EntradaIHC popupEntrada;
	private SaidaIHC popupSaida;
	
	private JFrame veiculoIO;
	
	private Sistema() {
		pt = PisoT.getInstance();
		p1 = Piso1.getInstance();
		
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
		if (vaga < 101) { //Terreo
			return pt.tentaInserir(new VeiculoData(placa, new Epoch(hh, mm, ss), tipo), vaga);
		} else { //Piso 1
			return p1.tentaInserir(new VeiculoData(placa, new Epoch(hh, mm, ss), tipo), vaga - 100);
		}
	}
	
	public int entraCarro(String placa, int hh, int mm, int ss, int tipo) throws HoraInvalidaEX {
		if (hh < 0 || mm < 0 || mm > 59 || ss < 0 || ss > 59 || hh + mm + ss < 1) { throw new HoraInvalidaEX(); }
		int res;
		res = pt.tentaInserir(new VeiculoData(placa, new Epoch(hh, mm, ss), tipo));
		if (res > -1) return res;
		return p1.tentaInserir(new VeiculoData(placa, new Epoch(hh, mm, ss), tipo));
	}
	
	public int saiCarro(String placa, Epoch e) throws HoraInvalidaEX, DeltaTInvalidoEX, PlacaNNEncontradaEX {
		boolean gotIt = false;
		int res = 0;
		VeiculoData paraRemover = new VeiculoData(placa, e, 0);
		if (!paraRemover.getEpoch().isValid()) throw new HoraInvalidaEX();
		try {
			res = pt.tentaRemover(paraRemover);
			gotIt = true;
		} catch (PlacaNNEncontradaEX ex) { //Talvez esteja em P1
			
		} catch (DeltaTInvalidoEX ex) {
			throw ex;
		}
		if (gotIt) return res;
		
		try {
			res = p1.tentaRemover(paraRemover);
		} catch (PlacaNNEncontradaEX ex) {
			throw ex;
		} catch (DeltaTInvalidoEX ex) {
			throw ex;
		}
		return res;
	}
	
	public void refreshMain() {
		MainIHC m = (MainIHC) screen;
		m.refreshView();
	}

	@Override
	public void actionPerformed(ActionEvent a) {
		String[] cmd = a.getActionCommand().split("\\r?\\n");
		int from;
		if (cmd[0].equals("inserirFromVaga")) {
			from = Integer.parseInt(cmd[1]);
			VagaData vaga = getFromUID(from);
			try {
				requestEntrada( new EntradaIHC(vaga.getID(), vaga.getAndar(), vaga.getTipo()) );
				showEntrada();
			} catch (EntradaAtivaEX ex) {
				System.err.println("Entrada should not be set!");
			} catch (EntradaInativaEX e) {
				System.err.println("Entrada was not set!");
			} catch (IOFrameBusyEX e) {
				System.err.println("IO JFrame is already in use!");
			}
		} else if (cmd[0].equals("inserirNew")) {
			try {
				requestEntrada( new EntradaIHC() );
				showEntrada();
			} catch (EntradaAtivaEX ex) {
				System.err.println("Entrada should not be set!");
			} catch (EntradaInativaEX e) {
				System.err.println("Entrada was not set!");
			} catch (IOFrameBusyEX e) {
				System.err.println("IO JFrame is already in use!");
			}
		} else if (cmd[0].equals("removerFromVaga")) {
			from = Integer.parseInt(cmd[1]);
			VagaData vaga = getFromUID(from);
		}
	}
	
	private VagaData getFromUID(int UID) {
		if (UID < 101) return pt.vagas.get(UID - 1);
		return p1.vagas.get(UID - 101);
	}
	
	private void clearVeiculoIO() { this.veiculoIO = null; }
	
	private void requestEntrada(EntradaIHC e) throws EntradaAtivaEX {
		if (this.popupEntrada != null) throw new EntradaAtivaEX();
		this.popupEntrada = e;
	}
	
	private void clearEntrada() { this.popupEntrada = null; }
	
	private void showEntrada() throws EntradaInativaEX, IOFrameBusyEX {
		if (this.popupEntrada == null) throw new EntradaInativaEX();
		if (this.veiculoIO != null) throw new IOFrameBusyEX();
		this.veiculoIO = new JFrame("Entrar novo veiculo");
		this.veiculoIO.setContentPane( this.popupEntrada );
		this.veiculoIO.pack();
		this.veiculoIO.setVisible(true);
	}
	
	private void requestSaida(SaidaIHC s) throws SaidaAtivaEX {
		if (this.popupSaida != null) throw new SaidaAtivaEX();
		this.popupSaida = s;
	}
	
	private void clearSaida() { this.popupSaida = null; }
	
	private void showSaida() throws SaidaInativaEX, IOFrameBusyEX {
		if (this.popupSaida == null) throw new SaidaInativaEX();
		if (this.veiculoIO != null) throw new IOFrameBusyEX();
		this.veiculoIO = new JFrame("Sair veiculo");
		this.veiculoIO.setContentPane( this.popupSaida );
		this.veiculoIO.pack();
		this.veiculoIO.setVisible(true);
	}
}
