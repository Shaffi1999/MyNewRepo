package com.in28minutes.rest.webservices.restfulwebservices.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.in28minutes.rest.webservices.restfulwebservices.user.User;

@Component
public class UserDaoService {
	private static List<User> users = new ArrayList<>();
	
	private static Integer userCount=0;

//	static {
//		users.add(new User(++userCount, "Adam", LocalDate.now().minusYears(30)));
//		users.add(new User(++userCount, "Eve", LocalDate.now().minusYears(25)));
//		users.add(new User(++userCount, "Jim", LocalDate.now().minusYears(20)));
//	}

//	public List<User> findAll() {
//		return users;
//	}

	public User findOne(Integer id) {
		return users.stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null);
	}
	
	public User save(User user) {
		user.setId(++userCount);
		users.add(user);
		return user;
		
	}
	
	public void deleteById(int id) {
		users.removeIf(user->user.getId().equals(id));
	}
}
