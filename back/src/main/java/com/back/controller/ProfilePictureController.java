package com.back.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.back.dao.ProfilePictureDao;
import com.back.model.Error;
import com.back.model.ProfilePicture;
import com.back.model.Users;

@Controller
public class ProfilePictureController {
	@Autowired
private ProfilePictureDao profilePictureDao;
	@RequestMapping(value="/uploadprofilepic",method=RequestMethod.POST)
public ResponseEntity<?> uploadProfilePicture(@RequestParam CommonsMultipartFile image,HttpSession session){
	Users users=(Users)session.getAttribute("user");
	if(users==null)		{
		    Error error=new Error(3,"UnAuthorized user");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
	} 
	ProfilePicture profilePicture=new ProfilePicture();
	profilePicture.setUsername(users.getUsername());
	profilePicture.setImage(image.getBytes());
	profilePictureDao.saveProfilePicture(profilePicture);
	return new ResponseEntity<Users>(users,HttpStatus.OK);
}
	
	//http://localhost:8080/backend_project2/getimage/admin
	@RequestMapping(value="/getimage/{username}", method=RequestMethod.GET)
	public @ResponseBody byte[] getProfilePic(@PathVariable String username,HttpSession session){
		Users user=(Users)session.getAttribute("user");
		if(user==null)
			return null;
		else
		{
			ProfilePicture profilePic=profilePictureDao.getProfilePicture(username);
			if(profilePic==null)
				return null;
			else
				return profilePic.getImage();
		}
		
}
}
