package com.in28minutes.rest.webservices.restfulwebservices.resource;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.in28minutes.rest.webservices.restfulwebservices.exception.PostNotFoundException;
import com.in28minutes.rest.webservices.restfulwebservices.exception.UserNotFoundException;
import com.in28minutes.rest.webservices.restfulwebservices.repository.PostRepository;
import com.in28minutes.rest.webservices.restfulwebservices.repository.UserRepository;
import com.in28minutes.rest.webservices.restfulwebservices.user.Post;
import com.in28minutes.rest.webservices.restfulwebservices.user.User;

import jakarta.validation.Valid;

@RestController
public class UserJpaResource {

	private UserRepository userRepository;

	private PostRepository postRepository;

	public UserJpaResource(UserRepository userRepository, PostRepository postRepository) {
		super();
		this.userRepository = userRepository;
		this.postRepository = postRepository;
	}

	@GetMapping("/jpa/users")
	public List<User> retrieveAllUsers() {
		return userRepository.findAll();
	}

	// http://localhost:8080/users

	// EntityModel
	// WebMvcLinkBuilder

	@GetMapping("/jpa/users/{id}")
	public EntityModel<User> retrieveUsers(@PathVariable Integer id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isEmpty()) {
			throw new UserNotFoundException("id:" + id);
		}

		EntityModel<User> entityModel = EntityModel.of(user.get());

		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		entityModel.add(link.withRel("all-users"));
		return entityModel;
	}

	@PostMapping("/jpa/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User savedUser = userRepository.save(user);
		// /users/4 => /users/{id} user.getID
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}

	@DeleteMapping("/jpa/users/{id}")
	public void deleteUser(@PathVariable int id) {
		userRepository.deleteById(id);
	}

	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> retrievePostsForUser(@PathVariable int id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isEmpty()) {
			throw new UserNotFoundException("id:" + id);
		}

		return user.get().getPosts();

	}

	@PostMapping("/jpa/users/{id}/posts")
	public ResponseEntity<Object> createPostForUser(@PathVariable int id, @Valid @RequestBody Post post) {
		Optional<User> user = userRepository.findById(id);

		if (user.isEmpty())
			throw new UserNotFoundException("id:" + id);
		post.setUser(user.get());

		Post savedPost = postRepository.save(post);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedPost.getId())
				.toUri();
		return ResponseEntity.created(location).build();

	}
	
	@GetMapping("jpa/users/{id1}/posts/{id2}")
	public Post gePostById(@PathVariable int id1,@PathVariable int id2) {
		Optional<User> user = userRepository.findById(id1);

		if (user.isEmpty())
			throw new UserNotFoundException("id:" + id1);
		
		Post post=user.get().getPosts().stream().filter(i-> i.getId().equals(id2)).findFirst().get();
//		if(post==null) {
//			throw new PostNotFoundException("id:"+id2);
//		}
	
		return post;
	}

}
