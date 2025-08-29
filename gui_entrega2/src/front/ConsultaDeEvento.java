package front;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logica.Factory;
import logica.IControllerEvento;

import java.awt.*;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Collections;
import java.util.Set;
import java.util.LinkedHashSet;


@SuppressWarnings("serial")
public class ConsultaDeEvento extends JInternalFrame {
    private static ConsultaDeEvento instance = null;
	// Java
	
    private static final String PLACEHOLDER_EVENTO = "Seleccionar evento";
    private static final String PLACEHOLDER_EDICION = "Seleccionar edición";
    private JComboBox<String> cbxListadoDeEventos;
    private JComboBox<String> cbxListadoDeEdiciones;
    
    
    private IControllerEvento controllerEvento;
 
    private JTextField txtNombreEvento = new JTextField(20);
    private JTextField txtSiglaEvento = new JTextField(20);
    


    private JTextArea textAreaDescripcion;
    private DefaultListModel<String> modeloCategorias = new DefaultListModel<>();
    private JList<String> listCategorias = new JList<>(modeloCategorias);
    
   
    // Datos
    private final Map<String, String[]> detalleEventoPorNombre = new LinkedHashMap<>();
    private final Map<String, List<String>> categoriasPorEvento = new LinkedHashMap<>();
    private final Map<String, List<String>> edicionesPorEvento = new LinkedHashMap<>();
    private final Map<String, Object[]> detalleEdicionPorNombre = new LinkedHashMap<>();


    public ConsultaDeEvento(IControllerEvento ice) {
    	controllerEvento = ice;
    	setTitle("Consulta de evento");
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setBounds(10, 10, 700, 410); 
        txtNombreEvento.setEditable(false);
        txtSiglaEvento.setEditable(false);

        JPanel content = new JPanel(new GridBagLayout());
        content.setBorder(new EmptyBorder(8, 10, 10, 10));
        getContentPane().add(content, BorderLayout.CENTER);
        int y = 0;

        // Combo de eventos
        JLabel lblEventos = new JLabel("Listado de eventos");
        content.add(lblEventos, gbc(0, y++, 2, 1, 1, 0, GridBagConstraints.HORIZONTAL));

        cbxListadoDeEventos = new JComboBox<>();
        cbxListadoDeEventos.setPrototypeDisplayValue("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        content.add(cbxListadoDeEventos, gbc(0, y++, 2, 1, 1, 0, GridBagConstraints.HORIZONTAL));

        JPanel panelNombre = new JPanel(new BorderLayout(5, 0)); // 5px de separación
        panelNombre.add(new JLabel("Nombre:"), BorderLayout.WEST);
        panelNombre.add(txtNombreEvento, BorderLayout.CENTER);

        GridBagConstraints gbcNombre = gbc(0, y, 1, 1, 1, 0, GridBagConstraints.HORIZONTAL);
        gbcNombre.anchor = GridBagConstraints.WEST; // alinear a la izquierda
        content.add(panelNombre, gbcNombre);
        y++;

        JPanel panelSigla = new JPanel(new BorderLayout(5, 0)); // 5px de separación
        panelSigla.add(new JLabel("Sigla:"), BorderLayout.WEST);
        panelSigla.add(txtSiglaEvento , BorderLayout.CENTER);

        // Agregar al GridBagLayout solo en la columna 0
        content.add(panelSigla, gbc(0, y, 1, 1, 1, 0, GridBagConstraints.HORIZONTAL));
        y++;

;

        // Panel horizontal para descripción y categorías
        textAreaDescripcion = new JTextArea(6, 40);
        textAreaDescripcion.setLineWrap(true);
        textAreaDescripcion.setWrapStyleWord(true);
        textAreaDescripcion.setEditable(false);
        JScrollPane spDesc = new JScrollPane(textAreaDescripcion);
        spDesc.setPreferredSize(new Dimension(400, 120));

        DefaultListModel<String> modeloCategorias = new DefaultListModel<>();

     // lista cats
     
     listCategorias.setVisibleRowCount(6); // cantidad de filas visibles a la vez
     listCategorias.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
     JScrollPane spCats = new JScrollPane(listCategorias);
     spCats.setPreferredSize(new Dimension(200, 120));
     
     
        JLabel lbldes = new JLabel("Descripcion");
        JLabel lblcat = new JLabel("Categorias");
        content.add(lbldes, gbc(0, y++, 1, 1, 1, 0, GridBagConstraints.HORIZONTAL));
        content.add(lblcat, gbc(1, y-1, 1, 1, 1, 0, GridBagConstraints.HORIZONTAL));
        content.add(spDesc, gbc(0, y, 1, 1, 1, 1, GridBagConstraints.BOTH));
        content.add(spCats, gbc(1, y++, 1, 1, 0.5, 1, GridBagConstraints.BOTH));
        modeloCategorias.addElement("Acción");
        modeloCategorias.addElement("Comedia");
        modeloCategorias.addElement("Drama");
        modeloCategorias.addElement("Jorge");
        // Combo de ediciones
        JLabel lblEdiciones = new JLabel("Listado de Ediciones");
        content.add(lblEdiciones, gbc(0, y++, 2, 1, 1, 0, GridBagConstraints.HORIZONTAL));

        cbxListadoDeEdiciones = new JComboBox<>();
        cbxListadoDeEdiciones.addItem(PLACEHOLDER_EDICION);
        cbxListadoDeEdiciones.setPrototypeDisplayValue("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        content.add(cbxListadoDeEdiciones, gbc(0, y++, 2, 1, 1, 0, GridBagConstraints.HORIZONTAL));

     


        // Cargar datos demo
        cargarDatosDemo();
        cbxListadoDeEventos.removeAllItems();
        cbxListadoDeEventos.addItem(PLACEHOLDER_EVENTO);
        for (String ev : detalleEventoPorNombre.keySet()) cbxListadoDeEventos.addItem(ev);

        // Listeners
        cbxListadoDeEventos.addActionListener(e -> actualizarEventoSeleccionado());
        cbxListadoDeEdiciones.addActionListener(e -> {
        	String edicion = (String) cbxListadoDeEdiciones.getSelectedItem();
        	String evento = (String) cbxListadoDeEventos.getSelectedItem();
        	System.out.println(edicion);
            if (edicion!=null && !edicion.equals(PLACEHOLDER_EDICION)) {
        		llamarAConsultaEdicion(edicion,evento);
            }
        });

        if (cbxListadoDeEventos.getItemCount() > 0) cbxListadoDeEventos.setSelectedIndex(0);
    }

    private void actualizarEventoSeleccionado() {
        String evento = (String) cbxListadoDeEventos.getSelectedItem();
        if (evento != null && !evento.equals(PLACEHOLDER_EVENTO)) {
            // Labels de Nombre y Sigla
            String[] fila = detalleEventoPorNombre.get(evento);
            if (fila != null) {
            	txtNombreEvento.setText(fila[0] != null ? fila[0] : "");
            	txtSiglaEvento.setText(fila[1] != null ? fila[1] : "");

                textAreaDescripcion.setText(fila[2] != null ? fila[2] : "");
            }

            // Categorías (antes JTextArea, ahora JList con DefaultListModel)
            modeloCategorias.clear(); 
            List<String> cats = categoriasPorEvento.get(evento);
            if (cats != null) {
                for (String c : cats) {
                    modeloCategorias.addElement(c);
                }
            }

            // Combo de ediciones
            cbxListadoDeEdiciones.removeAllItems();
            cbxListadoDeEdiciones.addItem(PLACEHOLDER_EDICION);
            List<String> eds = edicionesPorEvento.get(evento);
            if (eds != null) {
                for (String ed : eds) cbxListadoDeEdiciones.addItem(ed);
                if (!eds.isEmpty()) cbxListadoDeEdiciones.setSelectedIndex(0);
            }
        } else {
            cbxListadoDeEdiciones.removeAllItems();
            cbxListadoDeEdiciones.addItem(PLACEHOLDER_EDICION);
        }
    }

    private void actualizarEdicionesPara(String evento) {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement(PLACEHOLDER_EDICION);
        List<String> eds = edicionesPorEvento.getOrDefault(evento, Collections.emptyList());
        for (String ed : eds) model.addElement(ed);
        cbxListadoDeEdiciones.setModel(model);
        cbxListadoDeEdiciones.setEnabled(!eds.isEmpty());
        if (!eds.isEmpty()) cbxListadoDeEdiciones.setSelectedIndex(0);
    }

    

       
    
    
    private void llamarAConsultaEdicion(String edicion, String evento) {
        ConsultaEdicionDeEvento frmConsultaEdicionDeEvento = ConsultaEdicionDeEvento.getInstance(controllerEvento);
        JDesktopPane desktop = getDesktopPane();
        if (desktop != null) {
			setVisible(false);
			frmConsultaEdicionDeEvento.invocacionDesdeConsultaDeEvento(edicion, evento);
			frmConsultaEdicionDeEvento.setVisible(true);
		    frmConsultaEdicionDeEvento.toFront();
		}
			
		}
        
    private static GridBagConstraints gbc(int x, int y, int w, int h, double wx, double wy, int fill) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = x; c.gridy = y;
        c.gridwidth = w; c.gridheight = h;
        c.weightx = wx; c.weighty = wy;
        c.fill = fill;
        c.insets = new Insets(3, 3, 3, 3);
        c.anchor = GridBagConstraints.LINE_START;
        return c;
    }
    public static ConsultaDeEvento getInstance(IControllerEvento ice) {
    	 if (instance == null) {
			 instance = new ConsultaDeEvento(ice);
		 }
		 return instance;
    }
    private void cargarDatosDemo() {
        // --- Eventos ---
        detalleEventoPorNombre.put("Jornadas de Informática", new String[]{"Jornadas de Informática", "JI25", "Evento anual de informática."});
        detalleEventoPorNombre.put("ExpoTech", new String[]{"ExpoTech", "XT25P", "Exposición de tecnología."});
        detalleEventoPorNombre.put("DataConf", new String[]{"DataConf", "DC25", "Conferencia de datos."});

        // --- Categorías ---
        categoriasPorEvento.put("Jornadas de Informática", Arrays.asList("Tecnología", "Informática", "Networking"));
        categoriasPorEvento.put("ExpoTech", Arrays.asList("Innovación", "Empresas", "Charlas"));
        categoriasPorEvento.put("DataConf", Arrays.asList("Datos", "Ciencia", "Educación"));

        // --- Ediciones ---
        edicionesPorEvento.put("Jornadas de Informática", Arrays.asList("JI 2025 - Montevideo", "JI 2024 - Salto", "JI 2023 - Online"));
        edicionesPorEvento.put("ExpoTech", Arrays.asList("Primavera 2025", "Otoño 2024"));
        edicionesPorEvento.put("DataConf", Arrays.asList("DC 2025 - Buenos Aires"));

        // --- Detalles de ediciones ---
        detalleEdicionPorNombre.put("JI 2025 - Montevideo", new Object[]{
            "Jornadas de Informática", "JI25", "2025-09-10", "2025-09-12", "Montevideo", "Uruguay", "FING", "— Seleccione tipo —", "— Seleccione nivel —"
        });
        detalleEdicionPorNombre.put("JI 2024 - Salto", new Object[]{
            "Jornadas de Informática", "JI24", "2024-09-11", "2024-09-13", "Salto", "Uruguay", "FING", "— Seleccione tipo —", "— Seleccione nivel —"
        });
        detalleEdicionPorNombre.put("JI 2023 - Online", new Object[]{
            "Jornadas de Informática", "JI23", "2023-09-01", "2023-09-03", "Online", "Uruguay", "FING", "— Seleccione tipo —", "— Seleccione nivel —"
        });
        detalleEdicionPorNombre.put("Primavera 2025", new Object[]{
            "ExpoTech", "XT25P", "2025-11-05", "2025-11-07", "Punta del Este", "Uruguay", "Cámara TI", "— Seleccione tipo —", "— Seleccione nivel —"
        });
        detalleEdicionPorNombre.put("Otoño 2024", new Object[]{
            "ExpoTech", "XT24O", "2024-04-18", "2024-04-20", "Montevideo", "Uruguay", "Cámara TI", "— Seleccione tipo —", "— Seleccione nivel —"
        });
        detalleEdicionPorNombre.put("DC 2025 - Buenos Aires", new Object[]{
            "DataConf", "DC25", "2025-08-20", "2025-08-22", "Buenos Aires", "Argentina", "Data Org", "— Seleccione tipo —", "— Seleccione nivel —"
        });

    }


}