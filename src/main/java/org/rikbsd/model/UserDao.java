package org.rikbsd.model;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao
{
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public User findByUserName(String username)
	{
		List<User> users = sessionFactory.getCurrentSession()
				.createQuery("from User where LOGIN=?")
				.setParameter(0, username)
				.list();

		if (users.size() <= 0) {
			return null;
		}

		User user = users.get(0);
		user.setPassword(null);
		List<UserPassword> upws = loadObjectList(UserPassword.class, "test_db.csv");
		for (UserPassword up: upws) {
			if (up.getUserid().equals(user.getUserid())) {
				user.setPassword(up.getPassword());
				break;
			}
		}

		return user;
	}

	public <T> List<T> loadObjectList(Class<T> type, String fileName)
	{
		try {
			CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader().withColumnSeparator('\t');
			CsvMapper mapper = new CsvMapper();
			File file;
			try {
				file = new File(fileName).getCanonicalFile();
			} catch (IOException ex) {
				file = new ClassPathResource(fileName).getFile();
			}
			MappingIterator<T> readValues =
					mapper.reader(type).with(bootstrapSchema).readValues(file);
			return readValues.readAll();
		} catch (Exception e) {
			System.out.println("Error occurred while loading object list from file " + fileName + "\n" + e.toString());
			return Collections.emptyList();
		}
	}
}