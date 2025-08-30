package front;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logica.CargaDatos;
import logica.Factory;
import logica.IControllerEvento;
import logica.IControllerUsuario;
import java.awt.BorderLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JDesktopPane;

public class Main {

	private static final long serialVersionUID = 1L;
	private JFrame frmMain;
	private JDesktopPane desktopPane;  // Añadir como variable de clase
	
	private AltaUsuario frmAltaUsuario;
	private ConsultaUsuario frmConsultaUsuario;
	private ModificarDatosUsuario frmModificarDatosUsuario;
	private ConsultaEdicionDeEvento frmConsultaEdicion;
	private RegistroEdicion frmRegistroEdicion;
	private AltaEvento frmAltaEvento;
	private ConsultaDeEvento frmConsultaDeEvento;
	private ConsultaDeTipoDeRegistro frmConsultaTipoDeRegistro;
	private IControllerUsuario ICU;
	private IControllerEvento ICE;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.frmMain.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	public Main() {
		frmMain = new JFrame();
		frmMain.setTitle("Main");

		ICU = Factory.getInstance().getControllerUsuario();
		ICE = Factory.getInstance().getControllerEvento();
		
		frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMain.setBounds(100, 100, 720, 480);
		
		// Crear el JDesktopPane primero
		desktopPane = new JDesktopPane();
		frmMain.setContentPane(desktopPane);
		
	
	
		JMenuBar menuBar = new JMenuBar();
		frmMain.setJMenuBar(menuBar);
		
		JMenu mnSistema = new JMenu("Sistema");
		menuBar.add(mnSistema);
		
		JMenuItem mntmSalir = new JMenuItem("Salir");
		mnSistema.add(mntmSalir);
		
		JMenuItem mntmCargarDatos = new JMenuItem("Cargar Datos");
		mnSistema.add(mntmCargarDatos);
		mntmCargarDatos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					CargaDatos.cargarDatos();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		
		JMenu mnFuncionalidades = new JMenu("Funcionalidades");
		menuBar.add(mnFuncionalidades);
		
		JMenu mnUsuario = new JMenu("Usuario");
		mnFuncionalidades.add(mnUsuario);
		
		JMenuItem mntmAltaUsuario = new JMenuItem("Alta Usuario");
		mnUsuario.add(mntmAltaUsuario);
		frmAltaUsuario = AltaUsuario.getInstance(ICU);
        desktopPane.add(frmAltaUsuario);
        frmAltaUsuario.setVisible(false);
        mntmAltaUsuario.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frmAltaUsuario.setVisible(true);
                frmAltaUsuario.toFront();
            }
        });
		
		JMenuItem mntmConsultaUsuario = new JMenuItem("Consulta Usuario");
		mnUsuario.add(mntmConsultaUsuario);
		frmConsultaUsuario = ConsultaUsuario.getInstance(ICU);
        desktopPane.add(frmConsultaUsuario);
        frmConsultaUsuario.setVisible(false);
        mntmConsultaUsuario.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frmConsultaUsuario.refrescar();
                frmConsultaUsuario.setVisible(true);
                frmConsultaUsuario.toFront();
            }
        });
		
		JMenuItem mntmModificarDatos = new JMenuItem("Modificar Datos");
		mnUsuario.add(mntmModificarDatos);
		frmModificarDatosUsuario = ModificarDatosUsuario.getInstance();
        desktopPane.add(frmModificarDatosUsuario);
        frmModificarDatosUsuario.setVisible(false);
        mntmModificarDatos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frmModificarDatosUsuario.setVisible(true);
                frmModificarDatosUsuario.toFront();
            }
        });
		
		JMenu mnEvento = new JMenu("Evento");
		mnFuncionalidades.add(mnEvento);
		
		JMenuItem mntmConsultaEvento = new JMenuItem("Consulta");
		mnEvento.add(mntmConsultaEvento);
		frmConsultaDeEvento = ConsultaDeEvento.getInstance(ICE);
        desktopPane.add(frmConsultaDeEvento);
        frmConsultaDeEvento.setVisible(false);
        mntmConsultaEvento.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frmConsultaDeEvento.setVisible(true);
                frmConsultaDeEvento.refrescar();
                frmConsultaDeEvento.toFront();
            }
        });
        
        JMenuItem mntmAltaEvento = new JMenuItem("Alta");
        mnEvento.add(mntmAltaEvento);
        frmAltaEvento = AltaEvento.getInstance(ICE);
        desktopPane.add(frmAltaEvento);
        frmAltaEvento.setVisible(false);
        mntmAltaEvento.addActionListener(new ActionListener() {
        				public void actionPerformed(ActionEvent e) { 
							frmAltaEvento.setVisible(true);
							frmAltaEvento.toFront();
						}
        });
        JMenu mnEdicion = new JMenu("Edicion");
        mnFuncionalidades.add(mnEdicion);
        
        JMenuItem mntmRegistro = new JMenuItem("Registro");
        mnEdicion.add(mntmRegistro);
        frmRegistroEdicion = RegistroEdicion.getInstance();
        desktopPane.add(frmRegistroEdicion);
        frmRegistroEdicion.setVisible(false);
        mntmRegistro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frmRegistroEdicion.setVisible(true);
                frmRegistroEdicion.toFront();
            }
        });
        
        JMenuItem mntmConsultaEdicion = new JMenuItem("Consulta");
        mnEdicion.add(mntmConsultaEdicion);
        frmConsultaEdicion = ConsultaEdicionDeEvento.getInstance(ICE);
        frmConsultaEdicion.setOnOpenTipoRegistro((evento, edicion, dto) -> {
            DetalleTipoRegistroFrame f = new DetalleTipoRegistroFrame(evento, edicion, dto);
            desktopPane.add(f);
            f.setVisible(true);
            f.toFront();
        });
        frmConsultaEdicion.setOnOpenPatrocinio((evento, edicion, nivel, lista) -> {
            DetallePatrociniosFrame f = new DetallePatrociniosFrame(evento, edicion, nivel, lista);
            desktopPane.add(f);
            f.setVisible(true);
            f.toFront();
        });
        desktopPane.add(frmConsultaEdicion);
        frmConsultaEdicion.setVisible(false);
        mntmConsultaEdicion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frmConsultaEdicion.setVisible(true);
                frmConsultaEdicion.toFront();
            }
        });
		
		JMenu mnPatrocinio = new JMenu("Patrocinio");
		mnFuncionalidades.add(mnPatrocinio);
		
		JMenuItem mntmConsultaPatrocinio = new JMenuItem("Consulta");
		mnPatrocinio.add(mntmConsultaPatrocinio);
		
		JMenu mnTipoRegistro = new JMenu("Tipo Registro");
		mnFuncionalidades.add(mnTipoRegistro);
		
		JMenuItem mntmConsultaTipoReg = new JMenuItem("Consulta");
		mnTipoRegistro.add(mntmConsultaTipoReg);
		frmConsultaTipoDeRegistro = ConsultaDeTipoDeRegistro.getInstance();
		desktopPane.add(frmConsultaTipoDeRegistro);
		frmConsultaTipoDeRegistro.setVisible(false);
		mntmConsultaTipoReg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmConsultaTipoDeRegistro.setVisible(true);
				frmConsultaTipoDeRegistro.toFront();
			}
		});
		
	
}}