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
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

public class EntradaIHC extends JPanel implements ActionListener {
	private JFormattedTextField fieldPlaca, fieldHorario;
	private JButton bConfirma, bCancela;
	private JRadioButton rbAuto, rbManual;
	private JFormattedTextField fieldVaga;
	
	private JFrame current;
	
	public EntradaIHC(JFrame f) {
		current = f;
		JPanel big = new JPanel();
		big.setLayout(new BoxLayout(big, BoxLayout.Y_AXIS));
		big.setAlignmentX(CENTER_ALIGNMENT);
		JLabel placaLabel, horaLabel, vagaLabel;
		
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
		String cmd = e.getActionCommand();
		if (cmd.equals("cancela")) {
			current.dispatchEvent(new WindowEvent(current, WindowEvent.WINDOW_CLOSING));
		}
	}
}
