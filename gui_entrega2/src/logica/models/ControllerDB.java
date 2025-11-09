package logica.models;

import logica.controllers.IControllerDB;
import logica.manejadores.ManejadorEdicion;
import logica.manejadores.ManejadorInstitucion;
import logica.manejadores.ManejadorUsuario;

public class ControllerDB implements IControllerDB {
	ManejadorUsuario mUser;
	ManejadorInstitucion mInsti;
	ManejadorEdicion mEdicion;
	

	ControllerDB() {
		mUser = ManejadorUsuario.getInstance();
		mInsti = ManejadorInstitucion.getInstance();
		mEdicion = ManejadorEdicion.getInstance();
	}
	
	@Override
	public void inicializarDB() {
		mInsti.inicializarInstituciones();
		mUser.inicializarUsuarios();
		mEdicion.inicializarEdicionesArchivadas();
	}
}
