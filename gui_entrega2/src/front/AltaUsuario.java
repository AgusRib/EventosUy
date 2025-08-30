package front;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import logica.IControllerUsuario;
import logica.Institucion;
import logica.ManejadorInstitucion;


//TODO: Validar que el nickname y email no esten vacíos (puede hacerse desde el controller)
public class AltaUsuario extends JInternalFrame {
	private static AltaUsuario instance = null;
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
	private JTextField txtFechaNac;
	private JLabel lbltxtFechaNac;
	private JLabel lblFormatoFecha;
	private JLabel txtInstitucion;
	private JComboBox<String> cmBxInstitucion;
	
	//Componentes para el caso en el que sea organizador
	private JTextField txtDescripcion;
	private JLabel lblDescripcion;
	private JTextField txtWeb;
	private JLabel lblWeb;
	
	private JButton btnAceptar;
	private JButton btnCancelar;
	
	private IControllerUsuario controllerUsr;

	public AltaUsuario(IControllerUsuario icu) {
		
		controllerUsr = icu;		

		
		setTitle("Alta de Usuario");
		setClosable(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		txtNickname = new JTextField();
		txtNickname.setBounds(150, 8, 200, 20);
		getContentPane().add(txtNickname);
		
		lblNickname = new JLabel("Nickname:");
		lblNickname.setBounds(10, 11, 100, 14);
		getContentPane().add(lblNickname);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(150, 39, 200, 20);
		getContentPane().add(txtNombre);
		
		lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(10, 42, 100, 14);
		getContentPane().add(lblNombre);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(150, 70, 200, 20);
		getContentPane().add(txtEmail);
		
		lblEmail = new JLabel("Email:");
		lblEmail.setBounds(10, 73, 100, 14);
		getContentPane().add(lblEmail);

		btnAsistente = new JRadioButton("Asistente");
		btnAsistente.setBounds(150, 97, 100, 23);
		btnAsistente.setSelected(true);
		btnAsistente.addActionListener(e -> {
			verFormAsistente(btnAsistente.isSelected());
			verFormOrganizador(false);
		});
		getContentPane().add(btnAsistente);
		
		btnOrganizador = new JRadioButton("Organizador");
		btnOrganizador.setBounds(250, 97, 100, 23);
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
		
		txtApellido = new JTextField();
		txtApellido.setBounds(150, 127, 200, 20);
		getContentPane().add(txtApellido);
		
		lbltxtFechaNac = new JLabel("Fecha de Nacimiento:");
		lbltxtFechaNac.setBounds(10, 161, 150, 14);
		getContentPane().add(lbltxtFechaNac);
		
		txtFechaNac = new JTextField();
		txtFechaNac.setBounds(150, 158, 200, 20);
		getContentPane().add(txtFechaNac);
		
		// Etiqueta de ayuda para el formato de fecha
        lblFormatoFecha = new JLabel("Formato: yyyy-MM-dd");
        lblFormatoFecha.setBounds(150, 180, 200, 14);
        getContentPane().add(lblFormatoFecha);
		
		
		txtInstitucion = new JLabel("Institucion:");
		txtInstitucion.setBounds(10, 208, 100, 14);
		getContentPane().add(txtInstitucion);
		
		cmBxInstitucion = new JComboBox<String>();
		cmBxInstitucion.addItem("-- Seleccione una institucion ---");
		for (String inst : ManejadorInstitucion.getInstance().obtenerInstituciones()) {
			cmBxInstitucion.addItem(inst);
		}
		
		cmBxInstitucion.setBounds(150, 205, 200, 20);
		getContentPane().add(cmBxInstitucion);
		
		
		lblDescripcion = new JLabel("Descripcion:");
		lblDescripcion.setBounds(10, 130, 100, 14);
		getContentPane().add(lblDescripcion);
		
		txtDescripcion = new JTextField();
		txtDescripcion.setBounds(150, 127, 200, 51);
		getContentPane().add(txtDescripcion);
		
		lblWeb = new JLabel("Web:");
		lblWeb.setBounds(10, 208, 100, 14);
		getContentPane().add(lblWeb);
		
		txtWeb = new JTextField();
		txtWeb.setBounds(150, 205, 200, 20);
		getContentPane().add(txtWeb);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(150, 236, 89, 23);
		getContentPane().add(btnAceptar);
		btnAceptar.addActionListener(a -> {
			try {
				if (btnOrganizador.isSelected()) {
					controllerUsr.ingresarOrganizador(txtNickname.getText(), txtNombre.getText(), 
							txtEmail.getText(), txtDescripcion.getText(), txtWeb.getText());
				} else {
					controllerUsr.ingresarAsistente(txtNickname.getText(), txtNombre.getText(), 
							txtEmail.getText(), txtApellido.getText(), LocalDate.parse(txtFechaNac.getText())); 
					if (cmBxInstitucion.getSelectedIndex() > 0) {
						controllerUsr.agregarAsistente(txtNickname.getText(), (String) cmBxInstitucion.getSelectedItem());
					}
				}
                JOptionPane.showMessageDialog(this, "El Usuario se ha registrado con éxito", "Alta de Usuario",
                        JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
			} catch (Exception e) {
				if (e instanceof DateTimeParseException) {
					JOptionPane.showMessageDialog(this, "El formato de la fecha es incorrecto", "Alta de Usuario", JOptionPane.ERROR_MESSAGE);
				} else
				JOptionPane.showMessageDialog(this, e.getMessage(), "Alta de Usuario", JOptionPane.ERROR_MESSAGE);
			}
		});
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(261, 236, 89, 23);
		getContentPane().add(btnCancelar);
		btnCancelar.addActionListener(e -> {
			this.setVisible(false);
			limpiarFormulario();
		});
		
		verFormAsistente(true);
		verFormOrganizador(false);
		
	}
	
	private void verFormAsistente(boolean b) {
		if (b) {
			lblApellido.setVisible(true);
			txtApellido.setVisible(true);
			lbltxtFechaNac.setVisible(true);
			txtFechaNac.setVisible(true);
			cmBxInstitucion.setVisible(true);
			txtInstitucion.setVisible(true);
			lblFormatoFecha.setVisible(true);
		} else {
			lblApellido.setVisible(false);
			txtApellido.setVisible(false);
			lbltxtFechaNac.setVisible(false);
			txtFechaNac.setVisible(false);
			cmBxInstitucion.setVisible(false);
			txtInstitucion.setVisible(false);
			lblFormatoFecha.setVisible(false);
		}
	}
	
	private void verFormOrganizador(boolean b) {
		if (b) {
			lblDescripcion.setVisible(true);
			txtDescripcion.setVisible(true);
			lblWeb.setVisible(true);
			txtWeb.setVisible(true);
		} else {
			lblDescripcion.setVisible(false);
			txtDescripcion.setVisible(false);
			lblWeb.setVisible(false);
			txtWeb.setVisible(false);
		}
	}
	
	private void limpiarFormulario() {
		txtNickname.setText("");
		txtNombre.setText("");
		txtEmail.setText("");
		txtApellido.setText("");
		txtFechaNac.setText("");
		txtDescripcion.setText("");
		cmBxInstitucion.setSelectedIndex(0);
		txtWeb.setText("");
		btnAsistente.setSelected(true);
		verFormAsistente(true);
		verFormOrganizador(false);
	}
	

	public void refrescar() {
		cmBxInstitucion.removeAllItems();
		cmBxInstitucion.addItem("-- Seleccione una institucion ---");
		for (String inst : ManejadorInstitucion.getInstance().obtenerInstituciones()) {
			cmBxInstitucion.addItem(inst);
		}
		
	}
	public static AltaUsuario getInstance(IControllerUsuario icu) {
		if (instance == null) {
			instance = new AltaUsuario(icu);
		}
		return instance;
}}