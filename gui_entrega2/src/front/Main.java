package front;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import logica.Factory;
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
	private ConsultaDeEvento frmConsultaDeEvento;
	private IControllerUsuario ICU;
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
		
		JMenu mnFuncionalidades = new JMenu("Funcionalidades");
		menuBar.add(mnFuncionalidades);
		
		JMenu mnUsuario = new JMenu("Usuario");
		mnFuncionalidades.add(mnUsuario);
		
		JMenuItem mntmAltaUsuario = new JMenuItem("Alta Usuario");
		mnUsuario.add(mntmAltaUsuario);
		frmAltaUsuario = new AltaUsuario(ICU);
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
		frmConsultaUsuario = new ConsultaUsuario(ICU);
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
		frmModificarDatosUsuario = new ModificarDatosUsuario();
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
		frmConsultaDeEvento = new ConsultaDeEvento();
		desktopPane.add(frmConsultaDeEvento);
		frmConsultaDeEvento.setVisible(false);
		mntmConsultaEvento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmConsultaDeEvento.setVisible(true);
				frmConsultaDeEvento.toFront();
			}
		});
		JMenu mnEdicion = new JMenu("Edicion");
		mnFuncionalidades.add(mnEdicion);
		
		JMenuItem mntmRegistro = new JMenuItem("Registro");
		mnEdicion.add(mntmRegistro);
		frmRegistroEdicion = new RegistroEdicion();
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
		frmConsultaEdicion = new ConsultaEdicionDeEvento();
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
	
}}