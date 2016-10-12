package org.rikbsd.service;

import java.util.*;

import org.rikbsd.model.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

	//get user from the database, via Hibernate
	@Autowired
	private UserDao userDao;

	@Transactional(readOnly=true)
	@Override
	public UserDetails loadUserByUsername(final String username)
			throws UsernameNotFoundException
	{

		org.rikbsd.model.User user = userDao.findByUserName(username);

		if (user == null)
			throw new UsernameNotFoundException("User name not found!"); // Unknown user

		if (user.getPassword() == null)
			throw new UsernameNotFoundException("Check user or password name!"); // Unknown password

		return buildUserForAuthentication(user, Collections.singletonList(new SimpleGrantedAuthority("USER")));
	}

	private User buildUserForAuthentication(org.rikbsd.model.User user,
											List<? extends GrantedAuthority> authorities)
	{
		if (user == null)
			return null;

		return new User(user.getUserName(), user.getPassword(), true, true, true, true, authorities);
	}
}