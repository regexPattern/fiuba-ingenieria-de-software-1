package memo1.ejercicio1;

import java.util.Objects;

public class Branch {
  private long code;
  private String name;
  private String address;
  private boolean open;

  public Branch(long code, String name, String address) {
    this.code = code;
    this.name = name;
    this.address = address;
    this.open = true;
  }

  public long getCode() {
    return code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public boolean getOpen() {
    return open;
  }

  public void setOpen(boolean open) {
    this.open = open;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    } else if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    Branch otherBranch = (Branch) obj;

    return Objects.equals(getCode(), otherBranch.getCode());
  }
}
