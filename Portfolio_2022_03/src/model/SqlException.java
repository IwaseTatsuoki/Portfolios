package model;

public class SqlException extends Exception {

	private static final long serialVersionUID = 1L;

	private final String ERRORURL = "index.jsp";

	private final String ERRORMESS = "DBエラー";

	public SqlException(String mess) {
		// TODO 自動生成されたコンストラクター・スタブ

		super(mess);
	}

	public String getERRORURL() {
		return ERRORURL;
	}

	public String getERRORMESS() {
		return ERRORMESS;
	}



}
