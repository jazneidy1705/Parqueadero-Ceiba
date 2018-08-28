package com.ceiba.parqueadero.excepciones;

public class ParqueaderoException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String codigo;
	
	private String mensaje;

	public ParqueaderoException(String message) {

		super(message);

	}
	
	public ParqueaderoException (String codigo,String mensaje) {
		super(mensaje);
		this.codigo=codigo;
		this.mensaje=mensaje;
	}

	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * @param mensaje the mensaje to set
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	
	
}
