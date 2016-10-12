package org.rikbsd.model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "users")
public class User implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
	private Long userId;

	@Column(name = "LOGIN")
	private String userName;

	@Column(name = "NAME")
	private String name;

	@Column(name = "password")
	private String password;

	public User()
	{
	}

	public User(User user)
	{
		this.userId = user.userId;
		this.userName = user.userName;
		this.name = user.name;
		this.password = user.password;
	}

	public Long getUserid() {
		return userId;
	}

	public void setUserid(Long userid) {
		this.userId = userid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}