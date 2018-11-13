import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

public class EntradaIHC extends JPanel implements ActionListener {
	private JFormattedTextField fieldPlaca, fieldHorario;
	private JButton bConfirma, bCancela;
	private JRadioButton rbAuto, rbManual;
	private JRadioButton rbCarro, rbCaminhonete, rbMoto;
	private JFormattedTextField fieldVaga;
	
	private JFrame current;
	
	private int tipo_veiculo = 0;
	
	public EntradaIHC(JFrame f) {
		current = f;
		JPanel big = new JPanel();
		big.setLayout(new BoxLayout(big, BoxLayout.Y_AXIS));
		big.setAlignmentX(CENTER_ALIGNMENT);
		JLabel placaLabel, horaLabel, vagaLabel, tipoLabel;
		
		placaLabel = new JLabel("Placa do carro:");
		placaLabel.setAlignmentX(CENTER_ALIGNMENT);
		MaskFormatter tempFormatter = makeFormatter("UUU-####");
		tempFormatter.setPlaceholderCharacter('_');
		fieldPlaca = new JFormattedTextField( tempFormatter );
		fieldPlaca.addActionListener(this);
		fieldPlaca.setHorizontalAlignment(JTextField.CENTER);

		horaLabel = new JLabel("Horario de entrada:");
		horaLabel.setAlignmentX(CENTER_ALIGNMENT);
		tempFormatter = makeFormatter("##:##:##");
		tempFormatter.setPlaceholderCharacter('0');
		fieldHorario = new JFormattedTextField( tempFormatter );
		fieldHorario.addActionListener(this);
		fieldHorario.setHorizontalAlignment(JTextField.CENTER);
		
		JPanel selectTipo = new JPanel();
		selectTipo.setLayout(new BoxLayout(selectTipo, BoxLayout.X_AXIS));
		ButtonGroup tipoGroup = new ButtonGroup();
		
		tipoLabel = new JLabel("Tipo do veiculo:");

		rbCarro = new JRadioButton("Carro");
		rbCarro.setSelected(true);
		rbCarro.setActionCommand("tipo_carro");
		rbCarro.addActionListener(this);
		tipoGroup.add(rbCarro);
		selectTipo.add(rbCarro);
		
		rbCaminhonete = new JRadioButton("Caminhonete");
		rbCaminhonete.setSelected(false);
		rbCaminhonete.setActionCommand("tipo_caminhonete");
		rbCaminhonete.addActionListener(this);
		tipoGroup.add(rbCaminhonete);
		selectTipo.add(rbCaminhonete);
		
		rbMoto = new JRadioButton("Moto");
		rbMoto.setSelected(false);
		rbMoto.setActionCommand("tipo_moto");
		rbMoto.addActionListener(this);
		tipoGroup.add(rbMoto);
		selectTipo.add(rbMoto);
		
		JPanel selectVaga = new JPanel();
		selectVaga.setLayout(new BoxLayout(selectVaga, BoxLayout.PAGE_AXIS));
		
		vagaLabel = new JLabel("Vaga para estacionar:");
		ButtonGroup group = new ButtonGroup();
		
		rbAuto = new JRadioButton("Automatico");
		rbAuto.setToolTipText("O sistema escolhera a primeira vaga livre");
		rbAuto.setSelected(true);
		rbAuto.setActionCommand("vaga_auto");
		rbAuto.addActionListener(this);
		rbAuto.setMnemonic(KeyEvent.VK_A);
		group.add(rbAuto);
		selectVaga.add(rbAuto);
		
		rbManual = new JRadioButton("Manual");
		rbManual.setToolTipText("Selecione a vaga manualmente com o campo ao lado");
		rbManual.setSelected(false);
		rbManual.setActionCommand("vaga_manual");
		rbManual.addActionListener(this);
		rbManual.setMnemonic(KeyEvent.VK_M);
		group.add(rbManual);
		selectVaga.add(rbManual);
		
		tempFormatter = makeFormatter("###");
		tempFormatter.setPlaceholderCharacter('0');
		fieldVaga = new JFormattedTextField( tempFormatter );
		fieldVaga.addActionListener(this);
		fieldVaga.setHorizontalAlignment(JTextField.CENTER);
		selectVaga.add(fieldVaga);
		
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
		
		big.add(placaLabel);
		big.add(fieldPlaca);
		big.add(horaLabel);
		big.add(fieldHorario);
		big.add(tipoLabel);
		big.add(selectTipo);
		big.add(vagaLabel);
		big.add(selectVaga);
		big.add(buttonPane);
		
		add(big);
		System.out.println("Created new EntradaIHC");
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
	
	public void actionPerformed(ActionEvent e) {
		Sistema s = Sistema.getInstance();
		String cmd = e.getActionCommand();
		if (cmd.equals("cancela")) {
			current.dispatchEvent(new WindowEvent(current, WindowEvent.WINDOW_CLOSING));
		} else if (cmd.equals("confirma")) {
			String placa = fieldPlaca.getText();
			String tempo_string = (String)fieldHorario.getText();
			//System.out.printf("tempo = %s\n", tempo_string);
			int hh = Integer.parseInt("" + tempo_string.charAt(0) + tempo_string.charAt(1));
			int mm = Integer.parseInt("" + tempo_string.charAt(3) + tempo_string.charAt(4));
			int ss = Integer.parseInt("" + tempo_string.charAt(6) + tempo_string.charAt(7));
			//System.out.printf("parsed = %d:%d:%d\n", hh, mm, ss);
			int result;
			try {
				if (rbAuto.isSelected()) {
					result = s.entraCarro(placa, hh, mm, ss, tipo_veiculo);
					if (result == -1) { //Cheio
						System.out.println("Cheio");
					} else { //OK
						System.out.println("OK");
						s.refreshMain();
						current.dispatchEvent(new WindowEvent(current, WindowEvent.WINDOW_CLOSING));
					}
				} else {
					int vaga = Integer.parseInt((String)fieldVaga.getText());
					result = s.entraCarro(placa, hh, mm, ss, tipo_veiculo, vaga);
					if (result == -1) { //Vaga ocupada
						System.out.println("Ocupada");
					} else { //OK
						System.out.println("OK");
						s.refreshMain();
						current.dispatchEvent(new WindowEvent(current, WindowEvent.WINDOW_CLOSING));
					}
				}
			} catch (VagaInvalidaEX ex) {
				JOptionPane.showMessageDialog(null, "A vaga selecionada nao e compativel com o tipo de veiculo", "Erro", JOptionPane.ERROR_MESSAGE); 
			} catch (HoraInvalidaEX ex) {
				JOptionPane.showMessageDialog(null, "O horario inserido nao e valido", "Erro", JOptionPane.ERROR_MESSAGE); 
			}
		} else if (cmd.equals("tipo_carro"))		{ tipo_veiculo = 0; }
		  else if (cmd.equals("tipo_caminhonete"))	{ tipo_veiculo = 1; }
		  else if (cmd.equals("tipo_moto"))			{ tipo_veiculo = 2; }
	}
}
