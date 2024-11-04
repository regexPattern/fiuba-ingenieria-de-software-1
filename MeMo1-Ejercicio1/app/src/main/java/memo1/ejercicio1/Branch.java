package memo1.ejercicio1;

public class Branch {
  private Long code;
  private String name;
  private String address;
  private Boolean open;

  public Branch(Long code, String name, String address) {
    this.code = code;
    this.name = name;
    this.address = address;
    this.open = false;
  }

  public Long getCode() {
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

  public Boolean isOpen() {
    return open;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setAddress(String address) {
    this.address = address;
  }
}
