package front;

import java.awt.Font;
import java.util.Set;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import logica.IControllerUsuario;

public class ModificarDatosUsuario extends JInternalFrame {
    private static ModificarDatosUsuario instance = null;
	private static final long serialVersionUID = 1L;
	private JTextField txtFieldNombreUsuario;
	private JTextField txtFieldNicknameUsuario;
	private JTextField txtFieldEmailUsuario;
	private JTextField txtFieldfNacUsuario;
	private JTextField txtFieldApellidoUsuario;
	JComboBox<String> comboBox;

	public ModificarDatosUsuario(IControllerUsuario ICU) {
		setTitle("Modificar datos de Usuario");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		setClosable(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		
		Box horizontalBox = Box.createHorizontalBox();
		horizontalBox.setBounds(0, 0, 1, 1);
		getContentPane().add(horizontalBox);
		
		JLabel lblUsuario = new JLabel("Seleccionar Usuario:");
		lblUsuario.setFont(new Font("Dialog", Font.BOLD, 15));
		lblUsuario.setBounds(58, 16, 156, 17);
		getContentPane().add(lblUsuario);
		
		comboBox = new JComboBox<String>();
		Set<String> usuarios = ICU.listarUsuarios();
		for (String nomUsuario : usuarios) {
			comboBox.addItem(nomUsuario);
		}
		comboBox.setBounds(214, 12, 124, 26);
		getContentPane().add(comboBox);
		
		
		JLabel lblNombre = new JLabel("Nombre: ");
		lblNombre.setBounds(107, 69, 60, 17);
		getContentPane().add(lblNombre);
		
		JLabel lblNombre_1 = new JLabel("nickname:");
		lblNombre_1.setBounds(107, 93, 68, 17);
		getContentPane().add(lblNombre_1);
		
		JLabel lblNombre_2 = new JLabel("Email:");
		lblNombre_2.setBounds(107, 122, 60, 17);
		getContentPane().add(lblNombre_2);
		
		JLabel lblFechaDeNacimiento = new JLabel("Fecha de nacimiento:");
		lblFechaDeNacimiento.setBounds(107, 155, 133, 17);
		getContentPane().add(lblFechaDeNacimiento);
		
		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setBounds(107, 173, 60, 17);
		getContentPane().add(lblApellido);
		
		txtFieldNombreUsuario = new JTextField();
		txtFieldNombreUsuario.setBounds(247, 69, 114, 17);
		getContentPane().add(txtFieldNombreUsuario);
		txtFieldNombreUsuario.setColumns(10);
		
		txtFieldNicknameUsuario = new JTextField();
		txtFieldNicknameUsuario.setEditable(false);
		txtFieldNicknameUsuario.setColumns(10);
		txtFieldNicknameUsuario.setBounds(247, 93, 114, 17);
		getContentPane().add(txtFieldNicknameUsuario);
		
		txtFieldEmailUsuario = new JTextField();
		txtFieldEmailUsuario.setEditable(false);
		txtFieldEmailUsuario.setColumns(10);
		txtFieldEmailUsuario.setBounds(247, 122, 114, 17);
		getContentPane().add(txtFieldEmailUsuario);
		
		txtFieldfNacUsuario = new JTextField();
		txtFieldfNacUsuario.setColumns(10);
		txtFieldfNacUsuario.setBounds(247, 153, 114, 17);
		getContentPane().add(txtFieldfNacUsuario);
		
		txtFieldApellidoUsuario = new JTextField();
		txtFieldApellidoUsuario.setColumns(10);
		txtFieldApellidoUsuario.setBounds(247, 173, 114, 17);
		getContentPane().add(txtFieldApellidoUsuario);
		
		JButton btnConfirmarEdicionUsuario = new JButton("Confirmar");
		btnConfirmarEdicionUsuario.setBounds(109, 229, 105, 27);
		getContentPane().add(btnConfirmarEdicionUsuario);
		
		JButton btnCancelarEdicionUsuario = new JButton("Cancelar");
		btnCancelarEdicionUsuario.setBounds(226, 229, 105, 27);
		getContentPane().add(btnCancelarEdicionUsuario);
		
		btnConfirmarEdicionUsuario.addActionListener(e -> {
			
		});
		
		btnCancelarEdicionUsuario.addActionListener(e -> {
		    setVisible(false);
		    limpiarFormulario();
		});
		
		

	}
	private void limpiarFormulario() {
		comboBox.setSelectedIndex(-1);
		txtFieldNombreUsuario.setText("");
		txtFieldNicknameUsuario.setText("");
		txtFieldfNacUsuario.setText("");
		txtFieldEmailUsuario.setText("");
		txtFieldApellidoUsuario.setText("");
	}
	
	public static ModificarDatosUsuario getInstance(IControllerUsuario ICU) {
		if (instance == null) {
			instance = new ModificarDatosUsuario(ICU);
		}
		return instance;
	}
}
