package front;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultCellEditor;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.ListSelectionModel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import net.miginfocom.swing.MigLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.table.TableModel;

@SuppressWarnings("serial")
public class ConsultaEdicionDeEvento extends JInternalFrame {

    private static final String PLACEHOLDER_REG_TIPO = "— Seleccione tipo —";
    private static final String PLACEHOLDER_PAT_TIPO = "— Seleccione nivel —";
    
    private JComboBox<String> cbxListadoDeEdiciones;
    private JComboBox<String> cbxListadoDeEventos;

    // JCombos para desplegar los detalles correspondntes en las tablas
    private JComboBox<String> editorTiposRegCombo;  // col 7
    private JComboBox<String> editorTiposPatCombo;  // col 8

    // Datos que s cargn en memoria
    private final Map<String, List<String>> edicionesPorEvento = new LinkedHashMap<>();
    private final Map<String, Object[]> detalleEdicionPorNombre = new HashMap<>();
    private final Map<String, List<Object[]>> registrosPorEdicion = new HashMap<>();
    private final Map<String, List<Object[]>> patrociniosPorEdicion = new HashMap<>();
    
    //Tablas
    private JTable tblDetallesDeEdicion;
    private JTable tblDetalleDeRegistro;
    private JTable tblDetalleDePatrocinio;
    
    // ScrollPanes para ocultar o mostrar
    private JScrollPane spReg;
    private JScrollPane spPat;

    public ConsultaEdicionDeEvento() {
        setTitle("Consulta edición de evento");
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setBounds(30, 30, 627, 365);
        
        getContentPane().setLayout(new BorderLayout());
        
        //Contenedor scrolleable
        JPanel content = new JPanel();
        content.setBorder(new EmptyBorder(8, 10, 10, 10));
        content.setLayout(new FormLayout(
                new ColumnSpec[] {
                        FormSpecs.RELATED_GAP_COLSPEC,
                        ColumnSpec.decode("fill:default:grow"),
                        FormSpecs.RELATED_GAP_COLSPEC
                },
                new RowSpec[] {
                        FormSpecs.RELATED_GAP_ROWSPEC,
                        FormSpecs.DEFAULT_ROWSPEC,
                        FormSpecs.RELATED_GAP_ROWSPEC,
                        FormSpecs.DEFAULT_ROWSPEC,
                        FormSpecs.RELATED_GAP_ROWSPEC,
                        FormSpecs.DEFAULT_ROWSPEC,
                        FormSpecs.RELATED_GAP_ROWSPEC,
                        FormSpecs.DEFAULT_ROWSPEC,
                        FormSpecs.RELATED_GAP_ROWSPEC,
                        FormSpecs.DEFAULT_ROWSPEC,
                        FormSpecs.RELATED_GAP_ROWSPEC,
                        RowSpec.decode("fill:default:grow"),
                        FormSpecs.RELATED_GAP_ROWSPEC,
                        FormSpecs.DEFAULT_ROWSPEC,
                        FormSpecs.RELATED_GAP_ROWSPEC,
                        RowSpec.decode("fill:default:grow"),
                        FormSpecs.RELATED_GAP_ROWSPEC,
                        FormSpecs.DEFAULT_ROWSPEC,
                        FormSpecs.RELATED_GAP_ROWSPEC,
                        RowSpec.decode("fill:default:grow")
                }
        ));
        getContentPane().add(content, BorderLayout.CENTER);
        
        
        //Evenots lbl y cbx
        JLabel lblEventos = new JLabel("Listado de eventos");
        content.add(lblEventos, "2, 2");
        
        cbxListadoDeEventos = new JComboBox<String>();
        cbxListadoDeEventos.setPrototypeDisplayValue("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        content.add(cbxListadoDeEventos, "2, 4, fill, default");
        
        
        // label de ediciones
        JLabel lblEdiciones = new JLabel("Listado de Ediciones");
        content.add(lblEdiciones, "2, 6");
        
        cbxListadoDeEdiciones = new JComboBox<>();
        cbxListadoDeEventos.setPrototypeDisplayValue("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        content.add(cbxListadoDeEdiciones, "2, 8");
        
        
        //tabla de Ediciones
        JLabel lblDetallesEd = new JLabel("Detalles de edición");
        content.add(lblDetallesEd, "2, 10");

        tblDetallesDeEdicion = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Nombre", "Sigla", "Fecha inicio", "Fecha fin", "Ciudad", "País",
                        "Organizador", "Tipo de registros", "Patrocinios"}
        ) {
            @Override public boolean isCellEditable(int r, int c) { return c == 7 || c == 8; }
        });
        configurarTablaBasica(tblDetallesDeEdicion);
        JScrollPane spDetEd = new JScrollPane(tblDetallesDeEdicion);
        spDetEd.setPreferredSize(new Dimension(860, 170));
        content.add(spDetEd, "2, 12, fill, fill");

        // JCombo en TRegistros en JTable de ediciones
        editorTiposRegCombo = new JComboBox<>();
        editorTiposRegCombo.setToolTipText("Elegí un tipo de registro");
        editorTiposRegCombo.addActionListener(e -> {
            if (tblDetallesDeEdicion.isEditing()) tblDetallesDeEdicion.getCellEditor().stopCellEditing();
            String tipo = (String) editorTiposRegCombo.getSelectedItem();
            String edicion = (String) cbxListadoDeEdiciones.getSelectedItem();
            if (tipo == null || PLACEHOLDER_REG_TIPO.equals(tipo)) {
                limpiarRegistros();
                spReg.setVisible(false);
            } else {
                actualizarRegistrosParaTipo(edicion, tipo);
                spReg.setVisible(true);
            }
            content.revalidate();
            content.repaint();
        });
        TableColumn colTipoReg = tblDetallesDeEdicion.getColumnModel().getColumn(7);
        colTipoReg.setCellEditor(new DefaultCellEditor(editorTiposRegCombo));

        // JCombo en patrocinios en JTable de ediciones
        editorTiposPatCombo = new JComboBox<>();
        editorTiposPatCombo.setToolTipText("Elegí un nivel de patrocinio");
        editorTiposPatCombo.addActionListener(e -> {
            if (tblDetallesDeEdicion.isEditing()) tblDetallesDeEdicion.getCellEditor().stopCellEditing();
            String nivel = (String) editorTiposPatCombo.getSelectedItem();
            String edicion = (String) cbxListadoDeEdiciones.getSelectedItem();
            if (nivel == null || PLACEHOLDER_PAT_TIPO.equals(nivel)) {
                limpiarPatrocinios();
                spPat.setVisible(false);
            } else {
                actualizarPatrociniosParaNivel(edicion, nivel);
                spPat.setVisible(true);
            }
            content.revalidate();
            content.repaint();
        });
        TableColumn colPat = tblDetallesDeEdicion.getColumnModel().getColumn(8);
        colPat.setCellEditor(new DefaultCellEditor(editorTiposPatCombo));


        // Registros
        JLabel lblReg = new JLabel("Ver detalles del registro");
        content.add(lblReg, "2, 14");

        tblDetalleDeRegistro = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Nombre", "Descripción", "Costo", "Cupo"}
        ) { @Override public boolean isCellEditable(int r, int c) { return false; }});
        configurarTablaBasica(tblDetalleDeRegistro);
        spReg = new JScrollPane(tblDetalleDeRegistro);
        spReg.setVisible(false);
        content.add(spReg, "2, 16, fill, fill");

        // Partocinios
        JLabel lblPat = new JLabel("Ver detalle del patrocinio");
        content.add(lblPat, "2, 18");

        tblDetalleDePatrocinio = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Fecha", "Monto", "Código", "Nivel Patrocinio"}
        ) { @Override public boolean isCellEditable(int r, int c) { return false; }});
        configurarTablaBasica(tblDetalleDePatrocinio);
        spPat = new JScrollPane(tblDetalleDePatrocinio);
        spPat.setVisible(false);
        content.add(spPat, "2, 20, fill, fill");
        
        
        
        // Datos cargados y el listener para caudno se cambie de evento
        cargarDatosDemo();
        alCambiarEvento();

        if (cbxListadoDeEventos.getItemCount() > 0) cbxListadoDeEventos.setSelectedIndex(0);
    }

    
    private void configurarTablaBasica(JTable t) {
        t.setFillsViewportHeight(true);
        t.setRowHeight(22);
        t.setAutoCreateRowSorter(true);
        t.getTableHeader().setReorderingAllowed(false);
        t.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        t.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    /* ---------- listeners ---------- */
    private void alCambiarEvento() {
        cbxListadoDeEventos.addActionListener(e -> {
            String evento = (String) cbxListadoDeEventos.getSelectedItem();
            actualizarEdicionesPara(evento);
        });
        cbxListadoDeEdiciones.addActionListener(e -> {
            String edicion = (String) cbxListadoDeEdiciones.getSelectedItem();
            actualizarTablasParaEdicion(edicion);
        });
    }

    private void actualizarEdicionesPara(String evento) {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        List<String> eds = edicionesPorEvento.getOrDefault(evento, Collections.emptyList());
        for (String ed : eds) model.addElement(ed);
        cbxListadoDeEdiciones.setModel(model);
        cbxListadoDeEdiciones.setEnabled(!eds.isEmpty());

        // limpiar dependientes
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

        // fila única de detalles
        DefaultTableModel detModel = (DefaultTableModel) tblDetallesDeEdicion.getModel();
        detModel.setRowCount(0);
        Object[] fila = detalleEdicionPorNombre.get(edicion);
        if (fila != null) detModel.addRow(fila);

        // placeholders en col 7 y 8
        if (detModel.getRowCount() > 0) {
            detModel.setValueAt(PLACEHOLDER_REG_TIPO, 0, 7);
            detModel.setValueAt(PLACEHOLDER_PAT_TIPO, 0, 8);
        }

        // combos embebidos
        prepararEditorTipoRegistros(edicion);
        prepararEditorTipoPatrocinios(edicion);

        // limpiar/ocultar dependientes
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

    private void actualizarRegistrosParaTipo(String edicion, String tipo) {
        DefaultTableModel regModel = (DefaultTableModel) tblDetalleDeRegistro.getModel();
        regModel.setRowCount(0);
        for (Object[] r : registrosPorEdicion.getOrDefault(edicion, Collections.emptyList())) {
            String nombre = (r != null && r.length >= 1 && r[0] != null) ? String.valueOf(r[0]) : "";
            if (nombre.equalsIgnoreCase(tipo)) regModel.addRow(r);
        }
    }

    private void actualizarPatrociniosParaNivel(String edicion, String nivel) {
        DefaultTableModel patModel = (DefaultTableModel) tblDetalleDePatrocinio.getModel();
        patModel.setRowCount(0);
        for (Object[] p : patrociniosPorEdicion.getOrDefault(edicion, Collections.emptyList())) {
            String n = (p != null && p.length >= 4 && p[3] != null) ? String.valueOf(p[3]) : "";
            if (n.equalsIgnoreCase(nivel)) patModel.addRow(p);
        }
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
    

    /**
     * Para las capturas nomás, datos en memoria cargador por elchatg (gracias chatgpt por existir)
     */
    private void cargarDatosDemo() {
        // Eventos y ediciones
        edicionesPorEvento.put("Jornadas de Informática",
                Arrays.asList("JI 2025 - Montevideo", "JI 2024 - Salto", "JI 2023 - Online"));
        edicionesPorEvento.put("ExpoTech",
                Arrays.asList("Primavera 2025", "Otoño 2024"));
        edicionesPorEvento.put("DataConf",
                Collections.singletonList("DC 2025 - Buenos Aires"));

        DefaultComboBoxModel<String> evModel = new DefaultComboBoxModel<>();
        for (String ev : edicionesPorEvento.keySet()) evModel.addElement(ev);
        cbxListadoDeEventos.setModel(evModel);

        // Detalle por edición
        detalleEdicionPorNombre.put("JI 2025 - Montevideo", new Object[]{
                "Jornadas de Informática", "JI25", "2025-09-10", "2025-09-12",
                "Montevideo", "Uruguay", "FING", "General/Estudiante", "Oro, Plata, Bronce"
        });
        detalleEdicionPorNombre.put("JI 2024 - Salto", new Object[]{
                "Jornadas de Informática", "JI24", "2024-09-11", "2024-09-13",
                "Salto", "Uruguay", "FING", "General/Estudiante", "Oro, Plata"
        });
        detalleEdicionPorNombre.put("JI 2023 - Online", new Object[]{
                "Jornadas de Informática", "JI23", "2023-09-01", "2023-09-03",
                "Online", "Uruguay", "FING", "General", "Plata"
        });
        detalleEdicionPorNombre.put("Primavera 2025", new Object[]{
                "ExpoTech", "XT25P", "2025-11-05", "2025-11-07",
                "Punta del Este", "Uruguay", "Cámara TI", "General/Pro", "Oro, Plata, Bronce"
        });
        detalleEdicionPorNombre.put("Otoño 2024", new Object[]{
                "ExpoTech", "XT24O", "2024-04-18", "2024-04-20",
                "Montevideo", "Uruguay", "Cámara TI", "General", "Oro"
        });
        detalleEdicionPorNombre.put("DC 2025 - Buenos Aires", new Object[]{
                "DataConf", "DC25", "2025-08-20", "2025-08-22",
                "Buenos Aires", "Argentina", "Data Org", "General/Estudiante", "Platino, Oro"
        });

        // Registros por edición
        registrosPorEdicion.put("JI 2025 - Montevideo", Arrays.<Object[]>asList(
                new Object[]{"General", "Acceso completo", 1200, 300},
                new Object[]{"Estudiante", "Acceso completo (50% off)", 600, 500}
        ));
        registrosPorEdicion.put("JI 2024 - Salto", Arrays.<Object[]>asList(
                new Object[]{"General", "Acceso completo", 1000, 250},
                new Object[]{"Estudiante", "Acceso completo (50% off)", 500, 400}
        ));
        registrosPorEdicion.put("JI 2023 - Online", Arrays.<Object[]>asList(
                new Object[]{"General", "Streaming + material", 300, 2000}
        ));
        registrosPorEdicion.put("Primavera 2025", Arrays.<Object[]>asList(
                new Object[]{"General", "Expo + Charlas", 1500, 350},
                new Object[]{"Pro", "General + Talleres", 2500, 120}
        ));
        registrosPorEdicion.put("Otoño 2024", Arrays.<Object[]>asList(
                new Object[]{"General", "Expo + Charlas", 1100, 300}
        ));
        registrosPorEdicion.put("DC 2025 - Buenos Aires", Arrays.<Object[]>asList(
                new Object[]{"General", "Charlas + Networking", 2000, 400},
                new Object[]{"Estudiante", "Charlas + Networking (40% off)", 1200, 300}
        ));

        // Patrocinios por edición
        patrociniosPorEdicion.put("JI 2025 - Montevideo", Arrays.<Object[]>asList(
                new Object[]{"2025-07-01", 5000, "SP-001", "Oro"},
                new Object[]{"2025-07-15", 2500, "SP-002", "Plata"}
        ));
        patrociniosPorEdicion.put("Primavera 2025", Arrays.<Object[]>asList(
                new Object[]{"2025-09-10", 8000, "SP-101", "Oro"},
                new Object[]{"2025-10-01", 3000, "SP-102", "Bronce"}
        ));
        patrociniosPorEdicion.put("DC 2025 - Buenos Aires", Arrays.<Object[]>asList(
                new Object[]{"2025-06-20", 12000, "SP-201", "Platino"},
                new Object[]{"2025-07-05", 6000, "SP-202", "Oro"}
        ));
    }
}