package com.microservices.rest.webservice.restfulwebservices.user;

//import com.microservices.rest.webservice.restfulwebservices.exception.UserNotFoundException;
import com.microservices.rest.webservice.restfulwebservices.exception.UserSocialMediaNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserSocialMediaJpaResource {
    @Autowired
    private UserSocialMediaDaoService service;
   @Autowired
   private UserSocialMediaRepository userSocialMediaRepository;
   @Autowired
   PostRepository postRepository;

   //GET /users
    //retrieveAllUsers
   @GetMapping("/jpa/users")
    public List<UserSocialMedia> retrieveAllUsers(){

       return userSocialMediaRepository.findAll();
   }
    //GET /users/{id}
    @GetMapping("/jpa/users/{id}")
    public EntityModel<UserSocialMedia> retrieveUser(@PathVariable int id){
       Optional<UserSocialMedia> user =  userSocialMediaRepository.findById(id);

       if(!user.isPresent())
           throw new UserSocialMediaNotFoundException("id-"+id);

       //all we want is to specify the method retrieveAllUsers() even when testing this method
        EntityModel<UserSocialMedia> resource = EntityModel.of(user.get());//new EntityModel<User>(user.get());
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        resource.add(linkTo.withRel("all-users"));
        // HATEOAS
        return resource;
    }

    //CREATED
    //input -details of user
    //output -CREATED & return the created URI
    @PostMapping("jpa/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserSocialMedia user){
       UserSocialMedia savedUser = userSocialMediaRepository.save(user);
       URI location = ServletUriComponentsBuilder
              .fromCurrentRequest()
              .path("/{id}")
              .buildAndExpand(savedUser.getId())
              .toUri();
       return ResponseEntity.created(location).build();
   }

    @DeleteMapping("jpa/users/{id}")
    public void deleteUser(@PathVariable int id){
        userSocialMediaRepository.deleteById(id);
    }

    @GetMapping("/jpa/users/{id}/posts")
    public List<Post> retrieveAllUsers(@PathVariable Integer id){

        Optional<UserSocialMedia>userSocialMediaOptional=
                          userSocialMediaRepository.findById(id);

        if(!userSocialMediaOptional.isPresent()){
            throw new UserSocialMediaNotFoundException("id-"+id);
        }
        return userSocialMediaOptional.get().getPosts();
    }

    @PostMapping("jpa/users/{id}/posts")
    public ResponseEntity<Object> createPost(@PathVariable Integer id,@RequestBody Post post){
        Optional<UserSocialMedia>userSocialMediaOptional=
                userSocialMediaRepository.findById(id);

        if(!userSocialMediaOptional.isPresent()){
            throw new UserSocialMediaNotFoundException("id-"+id);
        }

      UserSocialMedia user = userSocialMediaOptional.get();
       post.setUserSocialMedia(user);
       postRepository.save(post);

       URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
