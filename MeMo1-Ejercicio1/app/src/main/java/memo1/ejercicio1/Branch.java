package memo1.ejercicio1;

public class Branch {
	private int code;
	private String name;
	private String address;

	public Branch(int code, String name, String address) {
		this.code = code;
		this.name = name;
		this.address = address;
	}

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}
}
