import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class NotinhaIHC extends JFrame {
	private JLabel text;
	private JPanel self;
	
	public NotinhaIHC(Epoch entrada, Epoch saida, String placa, String tipoVeiculo, float preco) {
		String contents = "<html><div style='text-align: center;'><br><br><br>Veiculo - " + tipoVeiculo + "<br>Placa " + placa + "<br><br>Horario de entrada: " + entrada.toString() + "<br>Horario de saida: " + saida.toString() + "<br><br>Total: " + String.format("R$%.2f", preco) + "<br><br><br></div></html>";
		text = new JLabel(contents);
		text.setAlignmentX(CENTER_ALIGNMENT);
		
		self = new JPanel();
		self.setBackground(Color.getHSBColor((float)(52.0/360.0), (float)0.13, (float)1.0));
		self.setOpaque(true);
		self.setAlignmentX(CENTER_ALIGNMENT);
		self.add(text);
		
		this.setContentPane(self);
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Nota fiscal");
	}
}
