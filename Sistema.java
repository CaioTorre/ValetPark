import javax.swing.JFrame;
import javax.swing.JPanel;

public class Sistema {
	private static Sistema self;
	public  static Sistema getInstance() { if (self == null) { self = new Sistema(); } return self; }

	private JFrame control;
	private JPanel screen;
	
	private Piso pt, p1;
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
			return pt.tentaInserir(new VeiculoData(placa, hh, mm, ss, tipo), vaga - 1);
		} else { //Piso 1
			return p1.tentaInserir(new VeiculoData(placa, hh, mm, ss, tipo), vaga - 101);
		}
	}
	
	public int entraCarro(String placa, int hh, int mm, int ss, int tipo) throws HoraInvalidaEX {
		if (hh < 0 || mm < 0 || mm > 59 || ss < 0 || ss > 59 || hh + mm + ss < 1) { throw new HoraInvalidaEX(); }
		int res;
		res = pt.tentaInserir(new VeiculoData(placa, hh, mm, ss, tipo));
		if (res > -1) return res;
		return p1.tentaInserir(new VeiculoData(placa, hh, mm, ss, tipo));
	}
}
