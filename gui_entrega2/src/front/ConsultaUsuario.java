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

public class ConsultaUsuario extends JInternalFrame {
	
	private IControllerUsuario controllerUsr;
	private JPanel panelDetallesUsr;
	private JList<String> listUsuarios;
	private JList<String> listAsociaciones;
	private JTextField txtNombre;
	private JTextField txtEmail;
	private JLabel lblAsociaciones;
	
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
		panelDetallesUsr.setBounds(151, 11, 273, 248);
		getContentPane().add(panelDetallesUsr);
		panelDetallesUsr.setLayout(null);
		panelDetallesUsr.setVisible(false);
		
		JLabel lblDetalles = new JLabel("Detalles del Usuario");
		lblDetalles.setBounds(10, 0, 176, 14);
		panelDetallesUsr.add(lblDetalles);
		lblDetalles.setVisible(true);
		
		JLabel lblNombre = new JLabel("Nombre: ");
		lblNombre.setBounds(10, 36, 176, 14);
		panelDetallesUsr.add(lblNombre);
		lblNombre.setVisible(true);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(87, 33, 176, 20);
		panelDetallesUsr.add(txtNombre);
		txtNombre.setColumns(10);
		txtNombre.setVisible(true);
		txtNombre.setEditable(false);
		
		JLabel lblEmail = new JLabel("Email: ");
		lblEmail.setBounds(10, 61, 176, 14);
		panelDetallesUsr.add(lblEmail);
		lblEmail.setVisible(true);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(87, 58, 176, 20);
		panelDetallesUsr.add(txtEmail);
		txtEmail.setColumns(10);
		txtEmail.setVisible(true);
		txtEmail.setEditable(false);
		
		lblAsociaciones = new JLabel("Ediciones asociadas:");
		lblAsociaciones.setBounds(10, 89, 132, 14);
		panelDetallesUsr.add(lblAsociaciones);
		
		listAsociaciones = new JList<String>();
		listAsociaciones.setModel(new AbstractListModel<String>() {
			String[] values = new String[] {"Edicion 1", "Hola", "Que tal"};
			public int getSize() {
				return values.length;
			}
			public String getElementAt(int index) {
				return values[index];
			}
		});
		listAsociaciones.setBorder(new LineBorder(new Color(0, 0, 0)));
		listAsociaciones.setBounds(152, 86, 111, 162);
		panelDetallesUsr.add(listAsociaciones);	
		
		listUsuarios = new JList<String>();
		listUsuarios.setBounds(10, 11, 131, 248);
		getContentPane().add(listUsuarios);
		listUsuarios.setBorder(new LineBorder(new Color(0, 0, 0)));
		listUsuarios.setVisible(true);
		listUsuarios.setEnabled(true);
		listUsuarios.addListSelectionListener(e -> {
			detallesUsuario();
		});
		listUsuarios.setListData(controllerUsr.listarUsuarios().toArray(new String[0]));
		listUsuarios.setSelectedIndex(0);
	}
	
	public void refrescar() {
		listUsuarios.setListData(controllerUsr.listarUsuarios().toArray(new String[0]));
	}

	private void detallesUsuario() {
		String selected = listUsuarios.getSelectedValue();
		if (selected == null) {
			panelDetallesUsr.setVisible(false);
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
}
