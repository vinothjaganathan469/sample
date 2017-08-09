package com.back.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.back.dao.UsersDao;
import com.back.model.Error;
import com.back.model.Users;



@RestController("usercontroller")
public class UserController {
	
	@Autowired
	private UsersDao usersDao;
	
	@RequestMapping(value="/registration",method=RequestMethod.POST)
	/*
	 * public ResponseEntity<?>-It can send any type of data
	 * public ResponseEntity<Void>-Void data in HttpResponse
	 * public ResponseEntity<Error>-It will add Error Type of data in HttpResponse
	 *  public ResponseEntity<T>-T is Type of data 
	 */
	
	public ResponseEntity<?> registration(@RequestBody Users user){
	try {
		user.setEnabled(true);
		List<Users> users=usersDao.getAllUsers();
		//for(T vaar:collection)
		for(Users u:users) {
			if(u.getUsername().equals(user.getUsername())){
				Error error=new Error(2,"Username already exists");
				return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);
			}
	}
	
	System.out.println(user.getUsername()+""+user.getFirstname()+""+user.getEmail());
	usersDao.registration(user);
	return new ResponseEntity<Void>(HttpStatus.CREATED);
   }catch(Exception e){
	Error error=new Error(1,"cannot register user details");
	return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);
   }
	
}
	/**
	 * login method is for authentication
	 * If user is authenticated,return Users,OK
	 * else return 401
	 */
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody Users users,HttpSession session){
	System.out.println("Is Session new for"+ users.getUsername() + session.isNew());
	Users validUser=usersDao.login(users);
	if(validUser==null){
		Error error=new Error(3,"Invalid username and password ...please enter valid credentials");
		return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
	}
	else{
		validUser.setOnline(true);
		validUser=usersDao.updateUser(validUser);
		session.setAttribute("user", validUser);
		return new ResponseEntity<Users>(validUser,HttpStatus.OK);
	}
  }
	
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public ResponseEntity<?> logout(HttpSession session){
		Users users=(Users)session.getAttribute("user");		
		if(users==null){
			Error error=new Error(3,"Unauthorized User");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		System.out.println("Is Session new for"+ users.getUsername() + session.isNew());
		users.setOnline(false);
		usersDao.updateUser(users);
	    session.removeAttribute("user");
	    session.invalidate();
	    return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/getuserdetails",method=RequestMethod.GET)
	public ResponseEntity<?> getUserDetails(HttpSession session){
		Users users=(Users)session.getAttribute("user");		
		if(users==null){
			Error error=new Error(3,"Unauthorized User");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		users=usersDao.getUserByUsername(users.getUsername());
		return new ResponseEntity<Users>(users,HttpStatus.OK);
  }

	@RequestMapping(value="/updateprofile",method=RequestMethod.PUT)
	public ResponseEntity<?> updateUserProfile(@RequestBody Users user,HttpSession session){
		//only for Authentication
		Users users=(Users)session.getAttribute("user");		
		if(users==null){
			Error error=new Error(3,"Unauthorized User");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		usersDao.updateUser(user);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
