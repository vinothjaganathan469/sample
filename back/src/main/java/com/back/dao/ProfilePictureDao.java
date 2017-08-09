package com.back.dao;

import com.back.model.ProfilePicture;
public interface ProfilePictureDao {

    void saveProfilePicture(ProfilePicture profilePicture);
	ProfilePicture getProfilePicture(String username);
}
