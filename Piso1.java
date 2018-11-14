import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Piso1 extends Piso  {
	private static Piso1 self;
	
	public static Piso1 getInstance() {
		if (self == null) self = new Piso1();
		return self;
	}
	
	private Piso1() {
		int i;
		vagas.clear();
		for (i = 0; i < 100; i++) { vagas.add(new VagaData(i, 0, 1)); }
	}
}
