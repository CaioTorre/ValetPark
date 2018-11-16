import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

public class ValoresIHC extends JPanel implements ActionListener {
	private JFormattedTextField fieldCarro, fieldCaminhonete, fieldMoto;
	private JLabel labelCarro, labelCaminhonete, labelMoto;
	private JLabel titulo;
	
	private JButton bCancela, bConfirma;
	
	private JFrame controle;
	
	public ValoresIHC(JFrame pai, double car, double cam, double mot) {
		controle = pai;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		titulo = new JLabel("Configuracao dos valores em R$/hora");
		titulo.setAlignmentX(CENTER_ALIGNMENT);
		
		labelCarro = new JLabel("Carro:");
		labelCaminhonete = new JLabel("Caminhonete:");
		labelMoto = new JLabel("Moto:");
		
		String formatValor = "R$##.##";
		//String stringFormat = "R$%02d%.02f";
		MaskFormatter format;
		//format.setPlaceholderCharacter('0');
		
		int fieldWidth = 100;
		
		format = makeFormatter(formatValor);
		format.setPlaceholder(formPreco(car));
		//format.setPlaceholderCharacter('0');
		fieldCarro = new JFormattedTextField(format);
		//fieldCarro.setText( formPreco(car) );
		fieldCarro.setMaximumSize(new Dimension(fieldWidth, 20));

		format = makeFormatter(formatValor);
		format.setPlaceholder(formPreco(cam));
		//format.setPlaceholderCharacter('0');
		fieldCaminhonete = new JFormattedTextField(format);
		//fieldCaminhonete.setText( formPreco(cam) );
		fieldCaminhonete.setMaximumSize(new Dimension(fieldWidth, 20));

		format = makeFormatter(formatValor);
		format.setPlaceholder(formPreco(mot));
		//format.setPlaceholderCharacter('0');
		fieldMoto = new JFormattedTextField(format);
		//fieldMoto.setText( formPreco(mot) );
		fieldMoto.setMaximumSize(new Dimension(fieldWidth, 20));
		
		JPanel container = new JPanel();
		container.setLayout(new GridLayout(0, 1, 0, 20));
		
		JPanel boxCarro = new JPanel();
		addCombo(boxCarro, labelCarro, fieldCarro);
//		boxCarro.setLayout(new BoxLayout(boxCarro, BoxLayout.X_AXIS));
//		boxCarro.add(Box.createRigidArea(new Dimension(50, 0)));
//		boxCarro.add(labelCarro);
//		boxCarro.add(Box.createHorizontalGlue());
//		boxCarro.add(fieldCarro);
//		boxCarro.add(Box.createRigidArea(new Dimension(50, 0)));
		
		JPanel boxCaminhonete = new JPanel();
		addCombo(boxCaminhonete, labelCaminhonete, fieldCaminhonete);
//		boxCaminhonete.setLayout(new BoxLayout(boxCaminhonete, BoxLayout.X_AXIS));
//		boxCaminhonete.add(Box.createRigidArea(new Dimension(50, 0)));
//		boxCaminhonete.add(labelCaminhonete);
//		boxCaminhonete.add(Box.createHorizontalGlue());
//		boxCaminhonete.add(fieldCaminhonete);
//		boxCaminhonete.add(Box.createRigidArea(new Dimension(50, 0)));
		
		JPanel boxMoto = new JPanel();
		addCombo(boxMoto, labelMoto, fieldMoto);
//		boxMoto.setLayout(new BoxLayout(boxMoto, BoxLayout.X_AXIS));
//		boxMoto.add(Box.createRigidArea(new Dimension(50, 0)));
//		boxMoto.add(labelMoto);
//		boxMoto.add(Box.createHorizontalGlue());
//		boxMoto.add(fieldMoto);
//		boxMoto.add(Box.createRigidArea(new Dimension(50, 0)));
		
		container.add(boxCarro);
		container.add(boxCaminhonete);
		container.add(boxMoto);

		JPanel painelBotoes = new JPanel();
		painelBotoes.setLayout(new BoxLayout(painelBotoes, BoxLayout.X_AXIS));
		
		bCancela = new JButton("Cancelar");
		bCancela.addActionListener(this);
		bCancela.setActionCommand("cancela");
		
		bConfirma = new JButton("Confirma");
		bConfirma.addActionListener(this);
		bConfirma.setActionCommand("confirma");
		
		painelBotoes.add(Box.createRigidArea(new Dimension(30, 0)));
		painelBotoes.add(bCancela);
		painelBotoes.add(Box.createHorizontalGlue());
		painelBotoes.add(bConfirma);
		painelBotoes.add(Box.createRigidArea(new Dimension(30, 0)));
		
		add(Box.createRigidArea(new Dimension(0, 20)));
		add(titulo);
		add(Box.createRigidArea(new Dimension(0, 20)));
		add(container);
		add(Box.createRigidArea(new Dimension(0, 20)));
		add(painelBotoes);
		add(Box.createRigidArea(new Dimension(0, 20)));
	}
	
	private MaskFormatter makeFormatter(String s) {
		 MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(s);
        } catch (java.text.ParseException exc) {
            System.err.println("Formatter is bad: " + exc.getMessage());
            System.exit(-1);
        }
        formatter.setPlaceholderCharacter('0');
        return formatter;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String cmd = event.getActionCommand();
		if (cmd.equals("cancela")) {
			close();
		} else if (cmd.equals("confirma")) {
			disableAll();
			String carStr = fieldCarro.getText();
			String camStr = fieldCaminhonete.getText();
			String motStr = fieldMoto.getText();
			double car = Double.parseDouble(carStr.substring(2));
			double cam = Double.parseDouble(camStr.substring(2));
			double mot = Double.parseDouble(motStr.substring(2));
			Sistema.getInstance().atualizaValores(car, cam, mot);
			System.err.printf("car = %f, cam = %f, mot = %f", car, cam, mot);
			JOptionPane.showMessageDialog(null, "Valores atualizados com sucesso", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
			close();
		}
	}
	
	private void close() {
		controle.dispatchEvent(new WindowEvent(controle, WindowEvent.WINDOW_CLOSING));
	}
	
//	private void enableAll() {
//		fieldCarro.setEditable(true);
//		fieldCaminhonete.setEditable(true);
//		fieldMoto.setEditable(true);
//	}
	
	private void disableAll() {
		fieldCarro.setEditable(false);
		fieldCaminhonete.setEditable(false);
		fieldMoto.setEditable(false);
	}
	
	private String formPreco(double preco) {
		String parteInt = String.format("R$%02d", (int)preco);
		String parteDec = String.format("%.02f", preco - (int)preco).substring(2);
		return parteInt + "." + parteDec;
	}
	
	private void addCombo(JPanel box, JLabel l, JTextField f) {
		box.setLayout(new BoxLayout(box, BoxLayout.X_AXIS));
		box.add(Box.createRigidArea(new Dimension(50, 0)));
		box.add(l);
		box.add(Box.createHorizontalGlue());
		box.add(Box.createRigidArea(new Dimension(40, 0)));
		box.add(f);
		box.add(Box.createRigidArea(new Dimension(50, 0)));
	}
}
