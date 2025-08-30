package front;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import logica.CargaDatos;
import logica.Factory;
import logica.IControllerEvento;
import logica.IControllerUsuario;

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
	private AltaTipoRegistro frmAltaTipoRegistro;
	private ConsultaDeTipoDeRegistro frmConsultaTipoDeRegistro;
	private IControllerUsuario ICU;
	private IControllerEvento ICE;
	
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
		
	
		// Barra superior
		JMenuBar menuBar = new JMenuBar();
		frmMain.setJMenuBar(menuBar);
		
		JMenu mnSistema = new JMenu("Sistema");
		menuBar.add(mnSistema);
		
		JMenuItem mntmSalir = new JMenuItem("Salir");
		mnSistema.add(mntmSalir);
		mntmSalir.addActionListener(e-> {
			frmMain.dispose();
			System.exit(0);
		});
		
		JMenuItem mntmCargarDatos = new JMenuItem("Cargar Datos");
		mnSistema.add(mntmCargarDatos);
		mntmCargarDatos.addActionListener(e -> {
		    try {
		        CargaDatos.cargarDatos();
		        // refrescar pantallas que dependen de los datos:
		        if (frmConsultaEdicion != null) frmConsultaEdicion.refrescar();
		        if (frmConsultaDeEvento != null) frmConsultaDeEvento.refrescar(); // si tenés método similar
		        // idem otras vistas
		    } catch (Exception ex) {
		        ex.printStackTrace();
		    }
		});

		
		JMenu mnFuncionalidades = new JMenu("Funcionalidades");
		menuBar.add(mnFuncionalidades);
		
		
		// Menu Usuario
		JMenu mnUsuario = new JMenu("Usuario");
		mnFuncionalidades.add(mnUsuario);
		
		//Submenus Usuario
			JMenuItem mntmAltaUsuario = new JMenuItem("Alta Usuario");
			mnUsuario.add(mntmAltaUsuario);
			frmAltaUsuario = AltaUsuario.getInstance(ICU);
			frmMain.getContentPane().add(frmAltaUsuario);
			frmAltaUsuario.setVisible(false);
			mntmAltaUsuario.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frmAltaUsuario.setVisible(true);
					frmAltaUsuario.toFront();
				}
			});
			
			
			JMenuItem mntmModificarDatos = new JMenuItem("Modificar Datos");
			mnUsuario.add(mntmModificarDatos);
			frmModificarDatosUsuario = ModificarDatosUsuario.getInstance(ICU);
			frmMain.getContentPane().add(frmModificarDatosUsuario);
			frmModificarDatosUsuario.setVisible(false);
			mntmModificarDatos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frmModificarDatosUsuario.setVisible(true);
					frmModificarDatosUsuario.toFront();
				}
			});
			
			JMenuItem mtnmConsultaUsuario = new JMenuItem("Consulta");
			mnUsuario.add(mtnmConsultaUsuario);
			frmConsultaUsuario = ConsultaUsuario.getInstance(ICU);
			frmMain.getContentPane().add(frmConsultaUsuario);
			frmConsultaUsuario.setVisible(false);
			mtnmConsultaUsuario.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frmConsultaUsuario.setVisible(true);
					frmConsultaUsuario.toFront();
				}
			});

		
        //Menu Evento
		JMenu mnEvento = new JMenu("Evento");
		mnFuncionalidades.add(mnEvento);
		
		// Submenus Evento
			JMenuItem mntmAltaEvento = new JMenuItem("Alta");
			mnEvento.add(mntmAltaEvento);
			frmAltaEvento= AltaEvento.getInstance(ICE);
			frmMain.getContentPane().add(frmAltaEvento);
			frmAltaEvento.setVisible(false);
			mntmAltaEvento.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frmAltaEvento.setVisible(true);
					frmAltaEvento.toFront();
				}
			});
		
			JMenuItem mntmConsultaEvento = new JMenuItem("Consulta");
			mnEvento.add(mntmConsultaEvento);
			frmConsultaDeEvento = ConsultaDeEvento.getInstance(ICE);
			frmMain.getContentPane().add(frmConsultaDeEvento);
			frmConsultaDeEvento.setVisible(false);
			mntmConsultaEvento.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frmConsultaDeEvento.setVisible(true);
					frmConsultaDeEvento.toFront();
				}
			});
		
		// Edicion
		JMenu mnEdicion = new JMenu("Edicion");
		mnFuncionalidades.add(mnEdicion);
		
		//Submenus Edicion
			JMenuItem mntmRegistro = new JMenuItem("Registro");
			mnEdicion.add(mntmRegistro);
			frmRegistroEdicion = new RegistroEdicion();
			frmMain.getContentPane().add(frmRegistroEdicion);
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
			frmMain.getContentPane().add(frmConsultaEdicion);
			frmConsultaEdicion.setVisible(false);
			mntmConsultaEdicion.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent e) {
			        frmConsultaEdicion.refrescar();    // ← importante
			        frmConsultaEdicion.setVisible(true);
			        frmConsultaEdicion.toFront();
			    }
			});

		
		// Patrocinio
		JMenu mnPatrocinio = new JMenu("Patrocinio");
		mnFuncionalidades.add(mnPatrocinio);
		
		//Submenus patrocinio
			JMenuItem mntmConsultaPatrocinio = new JMenuItem("Consulta");
			mnPatrocinio.add(mntmConsultaPatrocinio);
		
		
		// Tipo Registro
		JMenu mnTipoRegistro = new JMenu("Tipo Registro");
		mnFuncionalidades.add(mnTipoRegistro);
			//Sub menus TipoRegistro
			JMenuItem mntmAltaTipoRegistro = new JMenuItem("Alta");
			mnTipoRegistro.add(mntmAltaTipoRegistro);
			frmAltaTipoRegistro = new AltaTipoRegistro(ICE);
			frmMain.getContentPane().add(frmAltaTipoRegistro);
			frmAltaTipoRegistro.setVisible(false);
			mntmAltaTipoRegistro.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frmAltaTipoRegistro.setVisible(true);
					frmAltaTipoRegistro.toFront();
				}
			});
			
			JMenuItem mntmConsultaTipoReg = new JMenuItem("Consulta");
			mnTipoRegistro.add(mntmConsultaTipoReg);
			frmConsultaTipoDeRegistro = ConsultaDeTipoDeRegistro.getInstance(ICE);
			desktopPane.add(frmConsultaTipoDeRegistro);
			frmConsultaTipoDeRegistro.setVisible(false);
			mntmConsultaTipoReg.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frmConsultaTipoDeRegistro.setVisible(true);
					frmConsultaTipoDeRegistro.toFront();
				}
			});
		
		frmMain.getContentPane().setLayout(null);
	}
}

