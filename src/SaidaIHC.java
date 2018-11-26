import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
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

public class SaidaIHC extends JPanel implements ActionListener {
	private JFormattedTextField fieldPlaca, fieldHorario;
	private JButton bConfirma, bCancela;
	
	private JFrame current;
	private VagaInfoIHC tipo_pai;
	private boolean fixed;
	
	public SaidaIHC(JFrame f) {
		Font font = new Font("FE-Schrift", Font.PLAIN, 42);
		
		f.setPreferredSize(new Dimension(500, 500));
		current = f;
		JPanel big = new JPanel();
		big.setLayout(new BoxLayout(big, BoxLayout.Y_AXIS));
		big.setAlignmentX(CENTER_ALIGNMENT);
		JLabel placaLabel, horaLabel;//, vagaLabel, tipoLabel;
		
		placaLabel = new JLabel("Placa do carro:");
		placaLabel.setAlignmentX(CENTER_ALIGNMENT);
		MaskFormatter tempFormatter = makeFormatter("UUU-####");
		tempFormatter.setPlaceholderCharacter('_');
		fieldPlaca = new JFormattedTextField( tempFormatter );
		fieldPlaca.addActionListener(this);
		fieldPlaca.setHorizontalAlignment(JTextField.CENTER);
		fieldPlaca.setFont(font);

		horaLabel = new JLabel("Horario de saida:");
		horaLabel.setAlignmentX(CENTER_ALIGNMENT);
		tempFormatter = makeFormatter("##:##:##");
		tempFormatter.setPlaceholderCharacter('0');
		fieldHorario = new JFormattedTextField( tempFormatter );
		fieldHorario.addActionListener(this);
		fieldHorario.setHorizontalAlignment(JTextField.CENTER);
		fieldHorario.setFont(font);
		
		bConfirma = new JButton("Confirma");
		bConfirma.setActionCommand("confirma");
		bConfirma.setMnemonic(KeyEvent.VK_ACCEPT);
		bConfirma.addActionListener(this);

		bCancela = new JButton("Cancela");
		bCancela.setActionCommand("cancela");
		bCancela.setMnemonic(KeyEvent.VK_ESCAPE);
		bCancela.addActionListener(this);
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
		buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		buttonPane.add(Box.createHorizontalGlue());
		buttonPane.add(bCancela);
		buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
		buttonPane.add(bConfirma);
		
		int vspace = 30;

		big.add(Box.createRigidArea(new Dimension(0, vspace)));
		big.add(placaLabel);
		big.add(fieldPlaca);
		big.add(Box.createRigidArea(new Dimension(0, vspace)));
		big.add(horaLabel);
		big.add(fieldHorario);
		//big.add(Box.createRigidArea(new Dimension(0, vspace)));
		//big.add(tipoLabel);
		//big.add(selectTipo);
		//big.add(Box.createRigidArea(new Dimension(0, vspace)));
		//big.add(vagaLabel);
		//big.add(selectVaga);
		big.add(Box.createRigidArea(new Dimension(0, vspace)));
		big.add(buttonPane);
		
		add(big);
		fixed = false;
	}

	public SaidaIHC(JFrame f, String placa, VagaInfoIHC info) {
		this(f);
		
		fixed = true;
		this.tipo_pai = info;
		
		fieldPlaca.setValue(placa);
		fieldPlaca.setEditable(false);
		fieldPlaca.setEnabled(false);
	}
	
	private MaskFormatter makeFormatter(String s) {
		 MaskFormatter formatter = null;
	        try {
	            formatter = new MaskFormatter(s);
	        } catch (java.text.ParseException exc) {
	            System.err.println("Formatter is bad: " + exc.getMessage());
	            System.exit(-1);
	        }
	        return formatter;
	}
	
	private void enableAll() {
		if (fixed == false) { fieldPlaca.setEditable(true); }
		fieldHorario.setEditable(true);
	}
	
	private void disableAll() {
		fieldPlaca.setEditable(false);
		fieldHorario.setEditable(false);
	}
	
	private void close() {
		current.dispatchEvent(new WindowEvent(current, WindowEvent.WINDOW_CLOSING));
	}
	
	public void actionPerformed(ActionEvent e) {
		Sistema s = Sistema.getInstance();
		String cmd = e.getActionCommand();
		if (cmd.equals("cancela")) {
			current.dispatchEvent(new WindowEvent(current, WindowEvent.WINDOW_CLOSING));
		} else if (cmd.equals("confirma")) {
			disableAll();
			String placa  = fieldPlaca.getText();
			String tempo_string = (String)fieldHorario.getText();
			//System.out.printf("tempo = %s\n", tempo_string);
			//System.out.printf("parsed = %d:%d:%d\n", hh, mm, ss);
			//int result;
			try {
				Epoch ep = Epoch.parseFromString(tempo_string);
				s.saiCarro(placa, ep);
				//System.out.println("OK");
				JOptionPane.showMessageDialog(null, "Veiculo removido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE); 
				
				s.refreshMain();
				if (tipo_pai != null) tipo_pai.rebuildIHC();
				close();
			} catch (HoraInvalidaEX ex) {
				JOptionPane.showMessageDialog(null, "O horario inserido nao e valido", "Erro", JOptionPane.ERROR_MESSAGE); 
			} catch (DeltaTInvalidoEX ex) {
				JOptionPane.showMessageDialog(null, "O horario de saida nao pode ser anterior ao de entrada", "Erro", JOptionPane.ERROR_MESSAGE); 
			} catch (PlacaNNEncontradaEX ex) {
				JOptionPane.showMessageDialog(null, "Nao foi encontrado um veiculo com a placa " + placa, "Erro", JOptionPane.ERROR_MESSAGE); 
			} catch (BadEpochStringEX ex) {
				JOptionPane.showMessageDialog(null, "O horario inserido nao e valido", "Erro", JOptionPane.ERROR_MESSAGE); 
			}
			enableAll();
		}	
	}
}
