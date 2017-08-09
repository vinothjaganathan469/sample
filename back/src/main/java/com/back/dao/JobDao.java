package com.back.dao;

import java.util.List;

import com.back.model.Job;

public interface JobDao {
	void saveJob(Job job);
	List<Job> getAllJobs();
	Job getJobById(int id);
}
