package org.rikbsd.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class UserPassword implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID")
	private Long userId;

	@Column(name = "PASSWORD")
	private String password;

	public UserPassword()
	{
	}

	public UserPassword(UserPassword userPassword)
	{
		this.userId = userPassword.userId;
		this.password = userPassword.password;
	}

	public Long getUserid()
	{
		return userId;
	}

	public void setUserid(Long userid)
	{
		this.userId = userid;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}
}