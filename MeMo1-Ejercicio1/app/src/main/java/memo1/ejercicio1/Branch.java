package memo1.ejercicio1;

public class Branch {
  private int code;
  private String name;
  private String address;
  private boolean open;

  public Branch(int code, String name, String address) {
    this.code = code;
    this.name = name;
    this.address = address;
    this.open = false;
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

  public void setOpen(boolean open) {
    this.open = open;
  }

  public boolean isOpen() {
    return open;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setAddress(String address) {
    this.address = address;
  }
}
