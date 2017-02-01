public class Users {
  private Long id;
  private String username;
  private String password;
  private String email;
  private String text;
  private Long playlists;
  private Long privileges;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Long getPlaylists() {
    return playlists;
  }

  public void setPlaylists(Long playlists) {
    this.playlists = playlists;
  }

  public Long getPrivileges() {
    return privileges;
  }

  public void setPrivileges(Long privileges) {
    this.privileges = privileges;
  }
}
