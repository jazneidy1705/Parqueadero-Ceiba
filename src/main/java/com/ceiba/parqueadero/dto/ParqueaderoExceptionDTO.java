package com.ceiba.parqueadero.dto;

public class ParqueaderoExceptionDTO extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4546104143083247090L;

	private String codigo;

	private String mensaje;

	public ParqueaderoExceptionDTO(String codigo, String mensaje) {
		super();
		this.codigo = codigo;
		this.mensaje = mensaje;
	}

	public ParqueaderoExceptionDTO (String mensaje,Throwable causa) {
		super(mensaje, causa);
	}
	
	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *            the codigo to set
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
	 * @param mensaje
	 *            the mensaje to set
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

}
