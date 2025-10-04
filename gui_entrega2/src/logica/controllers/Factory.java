package logica.controllers;

public class Factory {
	
	private static Factory instance = null;
	
	public static Factory getInstance() {
		if (instance == null) {
			instance = new Factory();
		}
		return instance;
	}
	
	private Factory() {};
	
	public IControllerUsuario getControllerUsuario() {
		return new ControllerUsuario();
	}

	public IControllerEvento getControllerEvento() {
		return new ControllerEvento();
	}

}
