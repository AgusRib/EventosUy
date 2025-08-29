package front;

import java.awt.Container;
import java.awt.FlowLayout;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import logica.Edicion;
import logica.Evento;
import logica.IControllerEvento;
import logica.ManejadorEdicion;
import logica.ManejadorEvento;

public class AltaTipoRegistro extends JInternalFrame {

	private static final long serialVersionUID = 1L;

	private JComboBox<String> seleccionarEvento;
	private JComboBox<String> seleccionarEdicion;
	
	private JLabel lbl_seleccionarEvento;
	private JLabel lbl_seleccionarEdicion;
	private JLabel lbl_nuevoTipoRegistro;
	private JLabel lbl_nombre;
	private JLabel lbl_descripcion;
	private JLabel lbl_costo;
	private JLabel lbl_cupo;
	
	private JTextField tf_nombre;
	private JTextField tf_descripcion;
	private JTextField tf_costo;
	private JTextField tf_cupo;
	
	private JButton btn_aceptar;
	private JButton btn_cancelar;
	
	public AltaTipoRegistro(IControllerEvento ice) {
		
		setTitle("Alta de Tipo Registro");
		setClosable(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setResizable(false);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		Container ventana = getContentPane();
		
		// fila de seleccionar evento
		JPanel filaSeleccionarEvento = new JPanel();
		lbl_seleccionarEdicion = new JLabel("Seleccione un evento: ");
		seleccionarEvento = new JComboBox<String>();
		filaSeleccionarEvento.setLayout(new FlowLayout(FlowLayout.LEFT));
		for (Evento ev : ManejadorEvento.getInstance().obtenerEventos()) {
			seleccionarEvento.addItem(ev.getNombre());
		}
		
		filaSeleccionarEvento.add(lbl_seleccionarEvento);
		filaSeleccionarEvento.add(seleccionarEvento);
		ventana.add(filaSeleccionarEvento);
		
		// fila de seleccionar edicion
		JPanel filaListarEdiciones = new JPanel();
		lbl_seleccionarEdicion = new JLabel("Seleccionar Edicion: ");
		seleccionarEdicion = new JComboBox<String>();
		HashMap<String, Edicion> ediciones = ManejadorEdicion.getInstance().obtenerEdiciones();
		for (String nomEdicion : ediciones.keySet()) {
		    seleccionarEdicion.addItem(nomEdicion);
		}
		filaSeleccionarEvento.setLayout(new FlowLayout(FlowLayout.LEFT));
		filaSeleccionarEvento.add(lbl_seleccionarEdicion);
		filaSeleccionarEvento.add(seleccionarEdicion);
		
		if (seleccionarEvento.getSelectedItem() == null) {
			seleccionarEdicion.setEnabled(false);
		} else {
			seleccionarEdicion.setEnabled(true);
		}
		ventana.add(filaListarEdiciones);
		
		
		JPanel filaNuevoTipoRegistro = new JPanel();
		lbl_nuevoTipoRegistro = new JLabel("Nuevo tipo de registro");
		filaNuevoTipoRegistro.add(lbl_nuevoTipoRegistro);
		ventana.add(filaNuevoTipoRegistro);
		
		
		JPanel filaDescripcion = new JPanel();
		filaDescripcion.setLayout(new FlowLayout(FlowLayout.LEFT));
		lbl_descripcion = new JLabel("Descripcion: ");
		filaDescripcion.add(lbl_descripcion);
		filaDescripcion.add(tf_descripcion);
		ventana.add(filaDescripcion);
		
		
		JPanel filaCosto = new JPanel();
		filaCosto.setLayout(new FlowLayout(FlowLayout.LEFT));
		lbl_costo = new JLabel("Costo: ");
		filaCosto.add(lbl_costo);
		filaCosto.add(tf_costo);
		ventana.add(filaCosto);
		
		
		JPanel filaCupo = new JPanel();
		filaCupo.setLayout(new FlowLayout(FlowLayout.LEFT));
		lbl_cupo = new JLabel("Cupo: ");
		filaCupo.add(lbl_cupo);
		filaCupo.add(tf_cupo);
		ventana.add(filaCupo);
		
		// Si no hay evento seleccionado, desactivamos todos los textfields
		if(seleccionarEvento.getSelectedItem() == null || seleccionarEdicion.getSelectedItem() == null) {
			desactivarTextFields();
		} else { //no se si es necesario este else pero lo pongo por las dudas
			activarTextFields();
		}
		
		
		JPanel filaBotones = new JPanel();
		filaBotones.setLayout(new FlowLayout(FlowLayout.LEFT));
		btn_aceptar = new JButton("Aceptar");
		btn_cancelar = new JButton("Cancelar");
		filaBotones.add(btn_aceptar);
		filaBotones.add(btn_cancelar);
		ventana.add(filaBotones);
		
		// Logica botones
		btn_aceptar.addActionListener(e -> {
		    if (seleccionarEdicion.getSelectedItem() != null) {
		        try {
		            String edi = (String) seleccionarEdicion.getSelectedItem();
		            String nombre = tf_nombre.getText();
		            String descripcion = tf_descripcion.getText();
		            float costo = Float.parseFloat(tf_costo.getText());
		            int cupo = Integer.parseInt(tf_cupo.getText());
		            
		        
		            ice.altaTipoDeRegistro(edi, nombre, descripcion, costo, cupo);
		        } catch (NumberFormatException ex) {

		            System.out.println("Error: Formato de número inválido en costo o cupo");
		        }catch (Exception ex) {
		            System.out.println("Error: " + ex.getMessage());
		        }
		    }
		});

		btn_cancelar.addActionListener(e -> {
		    setVisible(false);
		});

		
	}

	private void activarTextFields() {
		tf_costo.setEnabled(true);
		tf_cupo.setEnabled(true);
		tf_descripcion.setEnabled(true);
		tf_nombre.setEnabled(true);
		
	}

	private void desactivarTextFields() {
		tf_costo.setEnabled(false);
		tf_cupo.setEnabled(false);
		tf_descripcion.setEnabled(false);
		tf_nombre.setEnabled(false);
		
	}

}
