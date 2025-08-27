package front;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
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

	// Java
	private static final String PLACEHOLDER_PAT_TIPO = "— Seleccione nivel —";
    private static final String PLACEHOLDER_REG_TIPO = "— Seleccione tipo —";
    private JComboBox<String> cbxListadoDeEventos;
    private JComboBox<String> cbxListadoDeEdiciones;

    private JTable tblDetallesDeEdicion;
    private JTable tblDetalleDePatrocinio;
    private JTable tblDetalleDeRegistro;
    private JLabel lblNombreEvento;
    private JLabel lblSiglaEvento;
    

    private JTextArea textAreaDescripcion;
    private JTextArea textAreaCategorias;
    
    // JCombos para embebidos en tabla
    private JComboBox<String> editorTiposRegCombo;  // col 7
    private JComboBox<String> editorTiposPatCombo;  // col 8
    private JScrollPane spPat;
    private JScrollPane spReg;
    
    // Datos
    private final Map<String, String[]> detalleEventoPorNombre = new LinkedHashMap<>();
    private final Map<String, List<String>> categoriasPorEvento = new LinkedHashMap<>();
    private final Map<String, List<String>> edicionesPorEvento = new LinkedHashMap<>();
    private final Map<String, Object[]> detalleEdicionPorNombre = new LinkedHashMap<>();
    private final Map<String, List<Object[]>> registrosPorEdicion = new HashMap<>();
    private final Map<String, List<Object[]>> patrociniosPorEdicion = new HashMap<>();
    public ConsultaDeEvento() {
        setTitle("Consulta de evento");
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setBounds(10, 10, 700, 410); 

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

        // Nombre y Sigla como labels
        lblNombreEvento = new JLabel("Nombre: ");
        
        content.add(lblNombreEvento, gbc(0, y++, 2, 1, 1, 0, GridBagConstraints.HORIZONTAL));

        lblSiglaEvento = new JLabel("Sigla: ");
       
        content.add(lblSiglaEvento, gbc(0, y++, 2, 1, 1, 0, GridBagConstraints.HORIZONTAL));

        // Panel horizontal para descripción y categorías
        textAreaDescripcion = new JTextArea(6, 40);
        textAreaDescripcion.setLineWrap(true);
        textAreaDescripcion.setWrapStyleWord(true);
        textAreaDescripcion.setEditable(false);
        JScrollPane spDesc = new JScrollPane(textAreaDescripcion);
        spDesc.setPreferredSize(new Dimension(400, 120));

        textAreaCategorias = new JTextArea(6, 20);
        textAreaCategorias.setLineWrap(true);
        textAreaCategorias.setWrapStyleWord(true);
        textAreaCategorias.setEditable(false);
        JScrollPane spCats = new JScrollPane(textAreaCategorias);
        spCats.setPreferredSize(new Dimension(200, 120));
     
        JLabel lbldes = new JLabel("Descripcion");
        JLabel lblcat = new JLabel("Categorias");
        content.add(lbldes, gbc(0, y++, 1, 1, 1, 0, GridBagConstraints.HORIZONTAL));
        content.add(lblcat, gbc(1, y-1, 1, 1, 1, 0, GridBagConstraints.HORIZONTAL));
        content.add(spDesc, gbc(0, y, 1, 1, 1, 1, GridBagConstraints.BOTH));
        content.add(spCats, gbc(1, y++, 1, 1, 0.5, 1, GridBagConstraints.BOTH));

        // Combo de ediciones
        JLabel lblEdiciones = new JLabel("Listado de Ediciones");
        content.add(lblEdiciones, gbc(0, y++, 2, 1, 1, 0, GridBagConstraints.HORIZONTAL));

        cbxListadoDeEdiciones = new JComboBox<>();
        cbxListadoDeEdiciones.setPrototypeDisplayValue("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        content.add(cbxListadoDeEdiciones, gbc(0, y++, 2, 1, 1, 0, GridBagConstraints.HORIZONTAL));

     // Dentro de tu constructor, después de crear la tabla tblDetallesDeEdicion
        tblDetallesDeEdicion = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Nombre", "Sigla", "Fecha inicio", "Fecha fin", "Ciudad", "País", "Organizador", "Tipo de registros", "Patrocinios"}
        ) {
            @Override public boolean isCellEditable(int r, int c) { return c == 7 || c == 8; }
        });
        tblDetallesDeEdicion.setRowHeight(22);
        JScrollPane spDetEd = new JScrollPane(tblDetallesDeEdicion);
        spDetEd.setPreferredSize(new Dimension(600, 180));
        content.add(spDetEd, gbc(0, y++, 2, 1, 1, 1, GridBagConstraints.BOTH));
       
        // Registros
        JLabel lblReg = new JLabel("Ver detalles del registro");
        content.add(lblReg, gbc(0, y++, 1, 1, 1, 0, GridBagConstraints.HORIZONTAL));
        
        // Crear JScrollPane para registros y patrocinios
         tblDetalleDeRegistro = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Nombre", "Descripción", "Costo", "Cupo"}
        ) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        });
        spReg = new JScrollPane(tblDetalleDeRegistro);
        spReg.setVisible(false);
        content.add(spReg, gbc(0, y++, 2, 1, 1, 1, GridBagConstraints.BOTH));
        
        // Patrocinios
        JLabel lblPat = new JLabel("Ver detalle del patrocinio");
        content.add(lblPat, gbc(0, y++, 1, 1, 1, 0, GridBagConstraints.HORIZONTAL));

         tblDetalleDePatrocinio = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Fecha", "Monto", "Código", "Nivel Patrocinio"}
        ) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        });
        spPat = new JScrollPane(tblDetalleDePatrocinio);
        spPat.setVisible(false);
        content.add(spPat, gbc(0, y++, 2, 1, 1, 1, GridBagConstraints.BOTH));

        // Crear combos para columnas
    
        editorTiposRegCombo = new JComboBox<>();
         editorTiposPatCombo = new JComboBox<>();

        // Acción para "Tipo de registros"
        editorTiposRegCombo.addActionListener(e -> {
            if (tblDetallesDeEdicion.isEditing()) tblDetallesDeEdicion.getCellEditor().stopCellEditing();
            String tipo = (String) editorTiposRegCombo.getSelectedItem();
            String edicion = (String) cbxListadoDeEdiciones.getSelectedItem();
            if (tipo == null || tipo.equals("— Seleccione tipo —")) {
                ((DefaultTableModel) tblDetalleDeRegistro.getModel()).setRowCount(0);
                spReg.setVisible(false);
            } else {
                actualizarRegistrosParaTipo(edicion, tipo, tblDetalleDeRegistro);
                spReg.setVisible(true);
            }
            content.revalidate();
            content.repaint();
        });

        // Acción para "Patrocinios"
        editorTiposPatCombo.addActionListener(e -> {
            if (tblDetallesDeEdicion.isEditing()) tblDetallesDeEdicion.getCellEditor().stopCellEditing();
            String nivel = (String) editorTiposPatCombo.getSelectedItem();
            String edicion = (String) cbxListadoDeEdiciones.getSelectedItem();
            if (nivel == null || nivel.equals("— Seleccione nivel —")) {
                ((DefaultTableModel) tblDetalleDePatrocinio.getModel()).setRowCount(0);
                spPat.setVisible(false);
            } else {
                actualizarPatrociniosParaNivel(edicion, nivel, tblDetalleDePatrocinio);
                spPat.setVisible(true);
            }
            content.revalidate();
            content.repaint();
        });

        // Asignar combos como CellEditor en la tabla
        tblDetallesDeEdicion.getColumnModel().getColumn(7).setCellEditor(new DefaultCellEditor(editorTiposRegCombo));
        tblDetallesDeEdicion.getColumnModel().getColumn(8).setCellEditor(new DefaultCellEditor(editorTiposPatCombo));


        // Cargar datos demo
        cargarDatosDemo();

        // Listeners
        cbxListadoDeEventos.addActionListener(e -> actualizarEventoSeleccionado());
        cbxListadoDeEdiciones.addActionListener(e -> {
        	String edicion = (String) cbxListadoDeEdiciones.getSelectedItem();
            actualizarTablasParaEdicion(edicion);
        });

        if (cbxListadoDeEventos.getItemCount() > 0) cbxListadoDeEventos.setSelectedIndex(0);
    }

    private void actualizarEventoSeleccionado() {
        String evento = (String) cbxListadoDeEventos.getSelectedItem();
        if (evento != null) {
            // Labels de Nombre y Sigla
            String[] fila = detalleEventoPorNombre.get(evento);
            if (fila != null) {
                lblNombreEvento.setText("Nombre: " + fila[0]);
                lblSiglaEvento.setText("Sigla: " + fila[1]);
                textAreaDescripcion.setText(fila[2] != null ? fila[2] : "");
            }

            // Categorías
            List<String> cats = categoriasPorEvento.get(evento);
            textAreaCategorias.setText(cats != null ? String.join("\n", cats) : "");

            // Combo de ediciones
            cbxListadoDeEdiciones.removeAllItems();
            List<String> eds = edicionesPorEvento.get(evento);
            if (eds != null) {
                for (String ed : eds) cbxListadoDeEdiciones.addItem(ed);
                if (!eds.isEmpty()) cbxListadoDeEdiciones.setSelectedIndex(0);
            }
        }
    }

    private void actualizarEdicionesPara(String evento) {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        List<String> eds = edicionesPorEvento.getOrDefault(evento, Collections.emptyList());
        for (String ed : eds) model.addElement(ed);
        cbxListadoDeEdiciones.setModel(model);
        cbxListadoDeEdiciones.setEnabled(!eds.isEmpty());

        limpiarTablas();
        prepararEditorTipoRegistros(null);
        prepararEditorTipoPatrocinios(null);
        spReg.setVisible(false);
        spPat.setVisible(false);

        if (!eds.isEmpty()) cbxListadoDeEdiciones.setSelectedIndex(0);
    }

    private void actualizarTablasParaEdicion(String edicion) {
        if (edicion == null) {
            limpiarTablas();
            prepararEditorTipoRegistros(null);
            prepararEditorTipoPatrocinios(null);
            spReg.setVisible(false);
            spPat.setVisible(false);
            return;
        }

        DefaultTableModel detModel = (DefaultTableModel) tblDetallesDeEdicion.getModel();
        detModel.setRowCount(0);
        Object[] fila = detalleEdicionPorNombre.get(edicion);
        if (fila != null) detModel.addRow(fila);

        if (detModel.getRowCount() > 0) {
            detModel.setValueAt(PLACEHOLDER_REG_TIPO, 0, 7);
            detModel.setValueAt(PLACEHOLDER_PAT_TIPO, 0, 8);
        }

        prepararEditorTipoRegistros(edicion);
        prepararEditorTipoPatrocinios(edicion);

        limpiarRegistros();  spReg.setVisible(false);
        limpiarPatrocinios(); spPat.setVisible(false);
    }

    private void prepararEditorTipoRegistros(String edicion) {
        DefaultComboBoxModel<String> tipoModel = new DefaultComboBoxModel<>();
        tipoModel.addElement(PLACEHOLDER_REG_TIPO);
        if (edicion != null) {
            Set<String> tipos = new LinkedHashSet<>();
            for (Object[] r : registrosPorEdicion.getOrDefault(edicion, Collections.emptyList())) {
                if (r != null && r.length >= 1 && r[0] != null) tipos.add(String.valueOf(r[0]));
            }
            for (String t : tipos) tipoModel.addElement(t);
        }
        editorTiposRegCombo.setModel(tipoModel);
    }

    private void prepararEditorTipoPatrocinios(String edicion) {
        DefaultComboBoxModel<String> patModel = new DefaultComboBoxModel<>();
        patModel.addElement(PLACEHOLDER_PAT_TIPO);
        if (edicion != null) {
            Set<String> niveles = new LinkedHashSet<>();
            for (Object[] p : patrociniosPorEdicion.getOrDefault(edicion, Collections.emptyList())) {
                if (p != null && p.length >= 4 && p[3] != null) niveles.add(String.valueOf(p[3]));
            }
            for (String n : niveles) patModel.addElement(n);
        }
        editorTiposPatCombo.setModel(patModel);
    }
    private void actualizarRegistrosParaTipo(String edicion, String tipo, JTable tblDetalleDeRegistro) {
        DefaultTableModel model = (DefaultTableModel) tblDetalleDeRegistro.getModel();
        model.setRowCount(0);
        List<Object[]> registros = registrosPorEdicion.getOrDefault(edicion, List.of());
        for (Object[] r : registros) {
            if (r[0].equals(tipo)) model.addRow(r);
        }
    }

    private void actualizarPatrociniosParaNivel(String edicion, String nivel, JTable tblDetalleDePatrocinio) {
        DefaultTableModel model = (DefaultTableModel) tblDetalleDePatrocinio.getModel();
        model.setRowCount(0);
        List<Object[]> patrocinios = patrociniosPorEdicion.getOrDefault(edicion, List.of());
        for (Object[] p : patrocinios) {
            if (p[3].equals(nivel)) model.addRow(p);
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
    private void limpiarRegistros() {
        ((DefaultTableModel) tblDetalleDeRegistro.getModel()).setRowCount(0);
    }

    private void limpiarPatrocinios() {
        ((DefaultTableModel) tblDetalleDePatrocinio.getModel()).setRowCount(0);
    }

    private void limpiarTablas() {
        ((DefaultTableModel) tblDetallesDeEdicion.getModel()).setRowCount(0);
        limpiarRegistros();
        limpiarPatrocinios();
    }
    private void cargarDatosDemo() {
        // --- Eventos ---
        detalleEventoPorNombre.put("Evento 1", new String[]{"Congreso de Tecnología", "CT2025", "Evento anual sobre tecnología."});
        detalleEventoPorNombre.put("Evento 2", new String[]{"Feria de Ciencia", "FC2025", "Feria para jóvenes científicos."});

        // --- Categorías ---
        categoriasPorEvento.put("Evento 1", Arrays.asList("Tecnología", "Innovación", "Networking"));
        categoriasPorEvento.put("Evento 2", Arrays.asList("Ciencia", "Educación", "Exposición"));

        // --- Ediciones ---
        edicionesPorEvento.put("Evento 1", Arrays.asList("Edición 2024", "Edición 2025"));
        edicionesPorEvento.put("Evento 2", Arrays.asList("Edición 2023", "Edición 2024"));

        // --- Detalles de ediciones ---
        detalleEdicionPorNombre.put("Edición 2024", new Object[]{
            "Congreso de Tecnología 2024", "CT2024", "01/06/2024", "03/06/2024", "Montevideo", "Uruguay", "OrgTech", "— Seleccione tipo —", "— Seleccione nivel —"
        });
        detalleEdicionPorNombre.put("Edición 2025", new Object[]{
            "Congreso de Tecnología 2025", "CT2025", "05/06/2025", "07/06/2025", "Montevideo", "Uruguay", "OrgTech", "— Seleccione tipo —", "— Seleccione nivel —"
        });
        detalleEdicionPorNombre.put("Edición 2023", new Object[]{
            "Feria de Ciencia 2023", "FC2023", "10/09/2023", "12/09/2023", "Paysandú", "Uruguay", "OrgCiencia", "— Seleccione tipo —", "— Seleccione nivel —"
        });
        detalleEdicionPorNombre.put("Edición 2024", new Object[]{
            "Feria de Ciencia 2024", "FC2024", "15/09/2024", "17/09/2024", "Paysandú", "Uruguay", "OrgCiencia", "— Seleccione tipo —", "— Seleccione nivel —"
        });

        // --- Registros por edicion ---
        registrosPorEdicion.put("Edición 2024", Arrays.asList(
            new Object[]{"General", "Acceso completo al evento", 50, 100},
            new Object[]{"VIP", "Acceso completo + charlas exclusivas", 150, 20}
        ));
        registrosPorEdicion.put("Edición 2025", Arrays.asList(
            new Object[]{"General", "Acceso completo al evento", 60, 120},
            new Object[]{"VIP", "Acceso completo + charlas exclusivas", 160, 25}
        ));
        registrosPorEdicion.put("Edición 2023", Arrays.asList(
            new Object[]{"Estudiante", "Acceso parcial", 20, 50},
            new Object[]{"General", "Acceso completo", 40, 80} // añadido para tener al menos 2
        ));
        registrosPorEdicion.put("Edición 2024", Arrays.asList(
            new Object[]{"General", "Acceso completo", 30, 80},
            new Object[]{"VIP", "Acceso completo + talleres", 120, 15} // añadido para tener al menos 2
        ));

        // --- Patrocinios por edicion ---
        patrociniosPorEdicion.put("Edición 2024", Arrays.asList(
            new Object[]{"01/05/2024", 5000, "PAT001", "Oro"},
            new Object[]{"02/05/2024", 2000, "PAT002", "Plata"}
        ));
        patrociniosPorEdicion.put("Edición 2025", Arrays.asList(
            new Object[]{"01/05/2025", 6000, "PAT003", "Oro"},
            new Object[]{"02/05/2025", 2500, "PAT004", "Plata"}
        ));
        patrociniosPorEdicion.put("Edición 2023", Arrays.asList(
            new Object[]{"01/08/2023", 1000, "PAT005", "Bronce"},
            new Object[]{"02/08/2023", 1200, "PAT006", "Plata"} // añadido
        ));
        patrociniosPorEdicion.put("Edición 2024", Arrays.asList(
            new Object[]{"01/08/2024", 1500, "PAT006", "Bronce"},
            new Object[]{"02/08/2024", 1800, "PAT007", "Plata"} // añadido
        ));

        // --- Cargar combo de eventos ---
        cbxListadoDeEventos.removeAllItems();
        for (String ev : detalleEventoPorNombre.keySet()) cbxListadoDeEventos.addItem(ev);
    }


} 
