package controller.events;

public class KeyValue {
	/*Ez az objektum ad�dik �t, amikor billenty�le�t�s t�rt�nik.Itt k�t lehet�s�g lehet,
	 vagy valami bet�t �t, vagy valami whitespace karaktert(illetve escape).
	 Ha karaktert akkor a keyType null,ha entert akkor a value null.Teh�t a k�t adattag
	 k�z�l az egyik �rt�ke mindig null.*/
	
	private Character value;
	private KeyValueConstant keyType;
	
	public KeyValue() {
	}
	
	public KeyValue(Character value) {
		super();
		this.value = value;
	}
	
	public KeyValue(KeyValueConstant value) {
		this.keyType = value;
	}
	
	
	
	public KeyValueConstant getKeyType() {
		return keyType;
	}

	public void setKeyType(KeyValueConstant keyType) {
		this.keyType = keyType;
	}

	public Character getValue() {
		return value;
	}
	public void setValue(Character c) {
		this.value = c;
	}
}
