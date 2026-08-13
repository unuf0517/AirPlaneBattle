package model;


public class AirplanebattleDO {

  private long id;
  private String nickname;
  private String account;
  private String password;
  private String status;

  public AirplanebattleDO(long id, String nickname, String account, String password) {
    this.id = id;
    this.password = password;
    this.account = account;
    this.nickname = nickname;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }


  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

}
