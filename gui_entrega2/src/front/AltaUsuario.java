package front;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class AltaUsuario extends JInternalFrame {
	
	private JTextField txtNickname;
	private JTextField txtNombre;
	private JTextField txtEmail;
	
	private JLabel lblNickname;
	private JLabel lblNombre;
	private JLabel lblEmail;
	
	private JRadioButton btnOrganizador;
	private JRadioButton btnAsistente;
	private ButtonGroup buttonGroup;
	
	
	// Componentes para el caso en el que sea asistente
	private JTextField txtApellido;
	private JLabel lblApellido;
	private JTextField fechaNacimiento;
	private JLabel lblFechaNacimiento;
	private JLabel textInstitucion;
	private JComboBox<String> cmBxInstitucion;
	
	//Componentes para el caso en el que sea organizador
	private JTextField txtDescripcion;
	private JLabel lblDescripcion;
	private JTextField textWeb;
	private JLabel lblWeb;

	public AltaUsuario() {
		setTitle("Alta de Usuario");
		setClosable(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		txtNickname = new JTextField();
		txtNickname.setBounds(120, 8, 200, 20);
		getContentPane().add(txtNickname);
		
		lblNickname = new JLabel("Nickname:");
		lblNickname.setBounds(10, 11, 100, 14);
		getContentPane().add(lblNickname);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(120, 39, 200, 20);
		getContentPane().add(txtNombre);
		
		lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(10, 42, 100, 14);
		getContentPane().add(lblNombre);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(120, 70, 200, 20);
		getContentPane().add(txtEmail);
		
		lblEmail = new JLabel("Email:");
		lblEmail.setBounds(10, 73, 100, 14);
		getContentPane().add(lblEmail);

		btnAsistente = new JRadioButton("Asistente");
		btnAsistente.setBounds(120, 101, 100, 23);
		btnAsistente.setSelected(true);
		btnAsistente.addActionListener(e -> {
			verFormAsistente(btnAsistente.isSelected());
			verFormOrganizador(false);
		});
		getContentPane().add(btnAsistente);
		
		btnOrganizador = new JRadioButton("Organizador");
		btnOrganizador.setBounds(220, 101, 100, 23);
		btnOrganizador.setSelected(false);
		btnOrganizador.addActionListener(e -> {
			verFormOrganizador(btnOrganizador.isSelected());
			verFormAsistente(false);
		});
		getContentPane().add(btnOrganizador);
		
		
		buttonGroup = new ButtonGroup();
		buttonGroup.add(btnOrganizador);
		buttonGroup.add(btnAsistente);
		
		lblApellido = new JLabel("Apellido:");
		lblApellido.setBounds(10, 130, 100, 14);
		getContentPane().add(lblApellido);
		lblApellido.setVisible(false);
		
		txtApellido = new JTextField();
		txtApellido.setBounds(120, 127, 200, 20);
		getContentPane().add(txtApellido);
		
		lblFechaNacimiento = new JLabel("Fecha de Nacimiento:");
		lblFechaNacimiento.setBounds(10, 161, 150, 14);
		getContentPane().add(lblFechaNacimiento);
		lblFechaNacimiento.setVisible(false);
		
		fechaNacimiento = new JTextField();
		fechaNacimiento.setBounds(130, 158, 200, 20);
		getContentPane().add(fechaNacimiento);
		
		
		textInstitucion = new JLabel("Institucion:");
		textInstitucion.setBounds(10, 192, 100, 14);
		getContentPane().add(textInstitucion);
		textInstitucion.setVisible(false);
		
		cmBxInstitucion = new JComboBox<String>();
		cmBxInstitucion.setBounds(120, 189, 200, 20);
		cmBxInstitucion.setVisible(false);
		getContentPane().add(cmBxInstitucion);
		
		
		lblDescripcion = new JLabel("Descripcion:");
		lblDescripcion.setBounds(10, 130, 100, 14);
		getContentPane().add(lblDescripcion);
		lblDescripcion.setVisible(true);
		
		txtDescripcion = new JTextField();
		txtDescripcion.setBounds(120, 127, 200, 51);
		getContentPane().add(txtDescripcion);
		txtDescripcion.setVisible(true);
		
		lblWeb = new JLabel("Web:");
		lblWeb.setBounds(10, 192, 100, 14);
		getContentPane().add(lblWeb);
		lblWeb.setVisible(true);
		
		textWeb = new JTextField();
		textWeb.setBounds(120, 189, 200, 20);
		getContentPane().add(textWeb);
		textWeb.setVisible(true);
		
	}
	
	private void verFormAsistente(boolean b) {
		if (b) {
			lblApellido.setVisible(true);
			txtApellido.setVisible(true);
			lblFechaNacimiento.setVisible(true);
			fechaNacimiento.setVisible(true);
			cmBxInstitucion.setVisible(true);
			textInstitucion.setVisible(true);
		} else {
			lblApellido.setVisible(false);
			txtApellido.setVisible(false);
			lblFechaNacimiento.setVisible(false);
			fechaNacimiento.setVisible(false);
			cmBxInstitucion.setVisible(false);
			textInstitucion.setVisible(false);
		}
	}
	
	private void verFormOrganizador(boolean b) {
		if (b) {
			lblDescripcion.setVisible(true);
			txtDescripcion.setVisible(true);
			lblWeb.setVisible(true);
			textWeb.setVisible(true);
		} else {
			lblDescripcion.setVisible(false);
			txtDescripcion.setVisible(false);
			lblWeb.setVisible(false);
			textWeb.setVisible(false);
		}
	}
	
}
