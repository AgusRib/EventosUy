package front;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.Box;
import javax.swing.JTextField;
import javax.swing.JButton;

public class ModificarDatosUsuario extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTextField txtFieldNombreUsuario;
	private JTextField txtFieldNicknameUsuario;
	private JTextField txtFieldEmailUsuario;
	private JTextField txtFieldfNacUsuario;
	private JTextField txtFieldApellidoUsuario;

	public ModificarDatosUsuario() {
		setBounds(100, 100, 200, 200);
		getContentPane().setLayout(null);
		setClosable(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		
		Box horizontalBox = Box.createHorizontalBox();
		horizontalBox.setBounds(0, 0, 1, 1);
		getContentPane().add(horizontalBox);
		
		JLabel lblUsuario = new JLabel("Usuario: ");
		lblUsuario.setBounds(12, 12, 76, 17);
		getContentPane().add(lblUsuario);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(90, 7, 124, 26);
		getContentPane().add(comboBox);
		
		JLabel lblNombre = new JLabel("Nombre: ");
		lblNombre.setBounds(12, 77, 60, 17);
		getContentPane().add(lblNombre);
		
		JLabel lblNombre_1 = new JLabel("nickname:");
		lblNombre_1.setBounds(12, 101, 68, 17);
		getContentPane().add(lblNombre_1);
		
		JLabel lblNombre_2 = new JLabel("Email:");
		lblNombre_2.setBounds(12, 130, 60, 17);
		getContentPane().add(lblNombre_2);
		
		JLabel lblFechaDeNacimiento = new JLabel("Fecha de nacimiento:");
		lblFechaDeNacimiento.setBounds(12, 159, 133, 17);
		getContentPane().add(lblFechaDeNacimiento);
		
		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setBounds(12, 191, 60, 17);
		getContentPane().add(lblApellido);
		
		txtFieldNombreUsuario = new JTextField();
		txtFieldNombreUsuario.setBounds(78, 77, 114, 17);
		getContentPane().add(txtFieldNombreUsuario);
		txtFieldNombreUsuario.setColumns(10);
		
		txtFieldNicknameUsuario = new JTextField();
		txtFieldNicknameUsuario.setEditable(false);
		txtFieldNicknameUsuario.setColumns(10);
		txtFieldNicknameUsuario.setBounds(90, 101, 114, 17);
		getContentPane().add(txtFieldNicknameUsuario);
		
		txtFieldEmailUsuario = new JTextField();
		txtFieldEmailUsuario.setEditable(false);
		txtFieldEmailUsuario.setColumns(10);
		txtFieldEmailUsuario.setBounds(60, 130, 114, 17);
		getContentPane().add(txtFieldEmailUsuario);
		
		txtFieldfNacUsuario = new JTextField();
		txtFieldfNacUsuario.setColumns(10);
		txtFieldfNacUsuario.setBounds(152, 157, 114, 17);
		getContentPane().add(txtFieldfNacUsuario);
		
		txtFieldApellidoUsuario = new JTextField();
		txtFieldApellidoUsuario.setColumns(10);
		txtFieldApellidoUsuario.setBounds(78, 189, 114, 17);
		getContentPane().add(txtFieldApellidoUsuario);
		
		JButton btnConfirmarEdicionUsuario = new JButton("Confirmar");
		btnConfirmarEdicionUsuario.setBounds(109, 229, 105, 27);
		getContentPane().add(btnConfirmarEdicionUsuario);
		
		JButton btnCancelarEdicionUsuario = new JButton("Cancelar");
		btnCancelarEdicionUsuario.setBounds(226, 229, 105, 27);
		getContentPane().add(btnCancelarEdicionUsuario);
		
		

	}
}
