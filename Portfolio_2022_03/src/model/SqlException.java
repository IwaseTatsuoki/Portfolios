package model;

import inventoryEnum.ErroMesEnum;

public class SqlException extends Exception {

	private static final long serialVersionUID = 1L;

	private final String ERRORURL = "index.jsp";

	private final String ERRORMESS = ErroMesEnum.DBERRORMES.getMes();

	public SqlException(String mess) {
		super(mess);
	}

	public String getERRORURL() {
		return ERRORURL;
	}

	public String getERRORMESS() {
		return ERRORMESS;
	}



}
