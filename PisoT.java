import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;

public class PisoT extends Piso {
	private static PisoT self;
	
	public static PisoT getInstance() {
		if (self == null) self = new PisoT();
		return self;
	}
	
	private PisoT() {
		int i;
		double temp;
		vagas.clear();
		for (i = 0; i < 100; i++) { 
			temp = Math.max(Math.ceil((i+1.0)/20.0 - 3.0), 0.0);
			//System.out.printf("%d - %f (%d)\n", i, temp, (int)temp);
			vagas.add( new VagaData(i, (int)temp, 0) );
		}
	}
}
