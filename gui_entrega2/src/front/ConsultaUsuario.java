package front;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import logica.DataUsuario;
import logica.DataUsuario.TipoUsuario;
import logica.IControllerUsuario;
import javax.swing.AbstractListModel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;


//TODO: Implementar mensaje de usuarios no existentes
//TODO: Implementar llamado a ventana de consulta de edicion al hacer doble click en una edicion
//TODO: Implementar llamado a ventana de consulta de registro al hacer doble click en un registro
public class ConsultaUsuario extends JInternalFrame {
	private static ConsultaUsuario instance = null;
	private IControllerUsuario controllerUsr;
	private JPanel panelDetallesUsr;
	private JList<String> listUsuarios;
	private JList<String> listAsociaciones;
	private JTextField txtNombre;
	private JTextField txtEmail;
	private JLabel lblAsociaciones;
	private JScrollPane scrollPane_1;
	
	@SuppressWarnings({ "serial", "unchecked" })
	public ConsultaUsuario(IControllerUsuario ICU) {
		controllerUsr = ICU;
	
		setTitle("Consulta de Usuario");
		setClosable(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setTitle("Consulta de Usuario");
		setClosable(true);
		
		
		panelDetallesUsr = new JPanel();
		panelDetallesUsr.setBounds(133, 11, 291, 248);
		getContentPane().add(panelDetallesUsr);
		panelDetallesUsr.setVisible(true);
		GridBagLayout gbl_panelDetallesUsr = new GridBagLayout();
		gbl_panelDetallesUsr.columnWidths = new int[]{92, 138, 0};
		gbl_panelDetallesUsr.rowHeights = new int[]{14, 0, 20, 162, 0, 0};
		gbl_panelDetallesUsr.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_panelDetallesUsr.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		panelDetallesUsr.setLayout(gbl_panelDetallesUsr);
		
		JLabel lblDetalles = new JLabel("Detalles del Usuario");
		GridBagConstraints gbc_lblDetalles = new GridBagConstraints();
		gbc_lblDetalles.anchor = GridBagConstraints.NORTH;
		gbc_lblDetalles.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblDetalles.insets = new Insets(0, 0, 5, 0);
		gbc_lblDetalles.gridwidth = 2;
		gbc_lblDetalles.gridx = 0;
		gbc_lblDetalles.gridy = 0;
		panelDetallesUsr.add(lblDetalles, gbc_lblDetalles);
		lblDetalles.setVisible(true);
		
		JLabel lblNombre = new JLabel("Nombre: ");
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 0;
		gbc_lblNombre.gridy = 1;
		panelDetallesUsr.add(lblNombre, gbc_lblNombre);
		lblNombre.setVisible(true);
		
		txtNombre = new JTextField();
		GridBagConstraints gbc_txtNombre = new GridBagConstraints();
		gbc_txtNombre.anchor = GridBagConstraints.NORTH;
		gbc_txtNombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNombre.insets = new Insets(0, 0, 5, 0);
		gbc_txtNombre.gridx = 1;
		gbc_txtNombre.gridy = 1;
		panelDetallesUsr.add(txtNombre, gbc_txtNombre);
		txtNombre.setColumns(10);
		txtNombre.setVisible(true);
		txtNombre.setEditable(false);
		
		txtEmail = new JTextField();
		GridBagConstraints gbc_txtEmail = new GridBagConstraints();
		gbc_txtEmail.anchor = GridBagConstraints.NORTH;
		gbc_txtEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtEmail.insets = new Insets(0, 0, 5, 0);
		gbc_txtEmail.gridx = 1;
		gbc_txtEmail.gridy = 2;
		panelDetallesUsr.add(txtEmail, gbc_txtEmail);
		txtEmail.setColumns(10);
		txtEmail.setVisible(true);
		txtEmail.setEditable(false);
		
		JLabel lblEmail = new JLabel("Email: ");
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmail.gridx = 0;
		gbc_lblEmail.gridy = 2;
		panelDetallesUsr.add(lblEmail, gbc_lblEmail);
		lblEmail.setVisible(true);
		
		lblAsociaciones = new JLabel("Ediciones asociadas:");
		GridBagConstraints gbc_lblAsociaciones = new GridBagConstraints();
		gbc_lblAsociaciones.anchor = GridBagConstraints.NORTH;
		gbc_lblAsociaciones.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblAsociaciones.insets = new Insets(0, 0, 5, 5);
		gbc_lblAsociaciones.gridx = 0;
		gbc_lblAsociaciones.gridy = 3;
		panelDetallesUsr.add(lblAsociaciones, gbc_lblAsociaciones);
		
		scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 1;
		gbc_scrollPane_1.gridy = 3;
		panelDetallesUsr.add(scrollPane_1, gbc_scrollPane_1);
		
		listAsociaciones = new JList<String>();
		scrollPane_1.setViewportView(listAsociaciones);
		
		listUsuarios = new JList<String>();
		JScrollPane scrollPane = new JScrollPane(listUsuarios);
		scrollPane.setBounds(10, 11, 113, 248);
		getContentPane().add(scrollPane);
		listUsuarios.setVisible(true);
		listUsuarios.setEnabled(true);
		listUsuarios.addListSelectionListener(e -> {
			detallesUsuario();
		});
		listUsuarios.setListData(controllerUsr.listarUsuarios().toArray(new String[0]));
		listUsuarios.setModel(new AbstractListModel() {
			String[] values = new String[] {};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		listUsuarios.setSelectedIndex(0);
	}
	
	public void refrescar() {
		listUsuarios.setListData(controllerUsr.listarUsuarios().toArray(new String[0]));
	}

	private void detallesUsuario() {
		String selected = listUsuarios.getSelectedValue();
		if (selected == null) {
			
			return;
		}
		panelDetallesUsr.setVisible(true);
		DataUsuario dataUser = controllerUsr.infoUsuario(selected);
		txtNombre.setText(dataUser.getNombre());
		txtEmail.setText(dataUser.getEmail());
		if (dataUser.getTipo() == TipoUsuario.ASISTENTE) {
			lblAsociaciones.setText("Registros a ediciones:");
			listAsociaciones.setListData(controllerUsr.listarRegistrosAEventos(selected).toArray(new String[0]));
		} else {
			lblAsociaciones.setText("Ediciones organizadas:");
			listAsociaciones.setListData(controllerUsr.listarEdicionesOrganizadas(selected).toArray(new String[0]));
		}
	}
<<<<<<< HEAD
}
=======
	
	public static ConsultaUsuario getInstance(IControllerUsuario ICU) {
		if (instance == null) {
			instance = new ConsultaUsuario(ICU);
		}
		return instance;
	}
	
	
	
	
	
}
>>>>>>> branch 'main' of https://gitlab.fing.edu.uy/tprog/tpgr57.git
