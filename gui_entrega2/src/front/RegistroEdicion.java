package front;

<<<<<<< HEAD
import javax.swing.JInternalFrame;

public class RegistroEdicion extends JInternalFrame {
=======
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
>>>>>>> branch 'main' of https://gitlab.fing.edu.uy/tprog/tpgr57.git

public class RegistroEdicion extends JInternalFrame {
	
	private JLabel lblEvento;
	private JComboBox<String> comboBoxEvento;
	
	private JLabel lblEdicion;
	private JComboBox<String> comboBoxEdicion;
	
	private JLabel lblAsistente;
	private JComboBox<String> comboBoxAsistente;
	
	private JLabel lblTipoReg;
	private JComboBox<String> comboBoxTipoReg;
	
	private JButton btnAceptar;
	private JButton btnCancelar;

	
	public RegistroEdicion() {
		setTitle("Registro a Edición de Evento");
		setClosable(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		lblEvento = new JLabel("Evento:");
		lblEvento.setBounds(45, 33, 100, 14);
		getContentPane().add(lblEvento);
		
		comboBoxEvento = new JComboBox<String>();
		comboBoxEvento.setBounds(185, 30, 200, 20);
		getContentPane().add(comboBoxEvento);
		
		lblEdicion = new JLabel("Edición:");
		lblEdicion.setBounds(45, 72, 100, 14);
		getContentPane().add(lblEdicion);
		
		comboBoxEdicion = new JComboBox<String>();
		comboBoxEdicion.setBounds(185, 69, 200, 20);
		getContentPane().add(comboBoxEdicion);
		
		lblAsistente = new JLabel("Asistente:");
		lblAsistente.setBounds(45, 112, 100, 14);
		getContentPane().add(lblAsistente);
		
		comboBoxAsistente = new JComboBox<String>();
		comboBoxAsistente.setBounds(185, 109, 200, 20);
		getContentPane().add(comboBoxAsistente);
		
		lblTipoReg = new JLabel("Tipo de Registro:");
		lblTipoReg.setBounds(45, 152, 100, 14);
		getContentPane().add(lblTipoReg);
		
		comboBoxTipoReg = new JComboBox<String>();
		comboBoxTipoReg.setBounds(185, 149, 200, 20);
		getContentPane().add(comboBoxTipoReg);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(77, 200, 90, 25);
		getContentPane().add(btnAceptar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(260, 200, 90, 25);
		getContentPane().add(btnCancelar);
		btnCancelar.addActionListener(e -> {
			limpiarFormulario();
			setVisible(false);
		});
	}

	private void limpiarFormulario() {
		comboBoxEvento.setSelectedIndex(-1);
		comboBoxEdicion.setSelectedIndex(-1);
		comboBoxAsistente.setSelectedIndex(-1);
		comboBoxTipoReg.setSelectedIndex(-1);
	}
	
}
