import java.awt.Dimension;
import java.awt.Font;
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
	private VagaInfoIHC tipo_pai;
	
	private int tipo_veiculo = 0;
	
	private int andar;
	
	public EntradaIHC(JFrame f) {
		Font font = new Font("FE-Schrift", Font.PLAIN, 42);
		
		f.setPreferredSize(new Dimension(500, 500));
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
		fieldPlaca.setFont(font);

		horaLabel = new JLabel("Horario de entrada:");
		horaLabel.setAlignmentX(CENTER_ALIGNMENT);
		tempFormatter = makeFormatter("##:##:##");
		tempFormatter.setPlaceholderCharacter('0');
		fieldHorario = new JFormattedTextField( tempFormatter );
		fieldHorario.addActionListener(this);
		fieldHorario.setHorizontalAlignment(JTextField.CENTER);
		fieldHorario.setFont(font);
		
		JPanel selectTipo = new JPanel();
		selectTipo.setLayout(new BoxLayout(selectTipo, BoxLayout.X_AXIS));
		ButtonGroup tipoGroup = new ButtonGroup();
		
		tipoLabel = new JLabel("Tipo do veiculo:");
		tipoLabel.setAlignmentX(CENTER_ALIGNMENT);

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
		selectVaga.setLayout(new BoxLayout(selectVaga, BoxLayout.X_AXIS));
		
		vagaLabel = new JLabel("Vaga para estacionar:");
		vagaLabel.setAlignmentX(CENTER_ALIGNMENT);
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
		fieldVaga.setEnabled(false);
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
		
		int vspace = 30;

		big.add(Box.createRigidArea(new Dimension(0, vspace)));
		big.add(placaLabel);
		big.add(fieldPlaca);
		big.add(Box.createRigidArea(new Dimension(0, vspace)));
		big.add(horaLabel);
		big.add(fieldHorario);
		big.add(Box.createRigidArea(new Dimension(0, vspace)));
		big.add(tipoLabel);
		big.add(selectTipo);
		big.add(Box.createRigidArea(new Dimension(0, vspace)));
		big.add(vagaLabel);
		big.add(selectVaga);
		big.add(Box.createRigidArea(new Dimension(0, vspace)));
		big.add(buttonPane);
		
		add(big);
	}

	/*public EntradaIHC(JFrame f, VeiculoData veiculo) {
		this(f);
		
		fieldPlaca.setValue(veiculo.getPlaca());
		fieldPlaca.setEditable(false);
		
		rbCarro.setSelected(false);
		rbCaminhonete.setSelected(false);
		rbMoto.setSelected(false);
		if (veiculo.getTipo() == 0) { rbCarro.setSelected(true); }
		if (veiculo.getTipo() == 1) { rbCaminhonete.setSelected(true); }
		if (veiculo.getTipo() == 2) { rbMoto.setSelected(true); }
		rbCarro.setEnabled(false);
		rbCaminhonete.setEnabled(false);
		rbMoto.setEnabled(false);
		
		revalidate();
		repaint();
	}*/
	
	public EntradaIHC(JFrame f, int vaga, int andar, int tipo, VagaInfoIHC info) {
		this(f);
		
		//Fixando codigo da vaga
		fieldVaga.setValue(String.format("%03d", vaga + 1));
		fieldVaga.setEditable(false);
		rbAuto.setSelected(false);
		rbManual.setSelected(true);
		rbAuto.setEnabled(false);
		rbManual.setEnabled(false);
		
		//Fixando tipo de veiculo
		rbCarro.setSelected(false);
		rbCaminhonete.setSelected(false);
		rbMoto.setSelected(false);
		if (tipo == 0) { rbCarro.setSelected(true); }
		if (tipo == 1) { rbCaminhonete.setSelected(true); }
		if (tipo == 2) { rbMoto.setSelected(true); }
		rbCarro.setEnabled(false);
		rbCaminhonete.setEnabled(false);
		rbMoto.setEnabled(false);
		tipo_veiculo = tipo;
		this.andar = andar;
		tipo_pai = info;
		revalidate();
		repaint();
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
		if (rbManual.isSelected()) fieldVaga.setEditable(true);
		fieldPlaca.setEditable(true);
		fieldHorario.setEditable(true);
	}
	
	private void disableAll() {
		fieldVaga.setEditable(false);
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
						JOptionPane.showMessageDialog(null, "Estacionamento lotado!", "Erro", JOptionPane.ERROR_MESSAGE); 
						
						enableAll();
					} else { //OK
						System.out.println("OK");
						JOptionPane.showMessageDialog(null, String.format("Veiculo inserido com sucesso!\nVaga: %s\nPiso %s", VagaData.convertCoord(result), VagaData.floorName(result / 100)), "Sucesso", JOptionPane.INFORMATION_MESSAGE); 
						
						s.refreshMain();
						if (tipo_pai != null) tipo_pai.rebuildIHC();
						close();
					}
				} else {
					int vaga = Integer.parseInt((String)fieldVaga.getText()) - 1;
					result = s.entraCarro(placa, hh, mm, ss, tipo_veiculo, vaga + this.andar * 100);
					if (result == -1) { //Vaga ocupada
						System.out.println("Ocupada");
						enableAll();
					} else { //OK
						System.out.println("OK");
						JOptionPane.showMessageDialog(null, String.format("Veiculo inserido com sucesso!\nVaga: %s\nPiso %s", VagaData.convertCoord(result), VagaData.floorName(this.andar)), "Sucesso", JOptionPane.INFORMATION_MESSAGE); 
						
						s.refreshMain();
						if (tipo_pai != null) tipo_pai.rebuildIHC();
						close();
					}
				}
			} catch (VagaInvalidaEX ex) {
				JOptionPane.showMessageDialog(null, "A vaga selecionada nao e compativel com o tipo de veiculo", "Erro", JOptionPane.ERROR_MESSAGE); 
			} catch (HoraInvalidaEX ex) {
				JOptionPane.showMessageDialog(null, "O horario inserido nao e valido", "Erro", JOptionPane.ERROR_MESSAGE); 
			}
			enableAll();
		}	else if (cmd.equals("tipo_carro"))			{ tipo_veiculo = 0; }
			else if (cmd.equals("tipo_caminhonete"))	{ tipo_veiculo = 1; }
			else if (cmd.equals("tipo_moto"))			{ tipo_veiculo = 2; }
			else if (cmd.equals("vaga_auto"))			{ fieldVaga.setEnabled(false); }
			else if (cmd.equals("vaga_manual"))			{ fieldVaga.setEnabled(true); }
	}
}
