package tp.pr2.testprofesor;

import tp.pr2.CodeCard;

public class MockCodeCard extends CodeCard {
	public static final String DEF_CODE = "1234";
	public static final String DEF_NAME = "MockCard";
	public static final String DEF_DESC = "MockCard description";
	public static final String WRONG_CODE = "anotherCode";

	public MockCodeCard(String id, String description, String code) {
		super(id, description, code);
	}
	
	public MockCodeCard() {
		this(DEF_NAME, DEF_DESC, DEF_CODE);
	}

}
