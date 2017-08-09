package com.back.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
 
//import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="V_blogpost")
public class BlogPost {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private String title;
	@Lob
	private String description;
	@ManyToOne 
	@JoinColumn(name="username")
	private Users createdBy;
	private Date postedOn;
	private boolean approved;
	@OneToMany(mappedBy="blogPost",fetch=FetchType.EAGER)
	
	List<BlogComment> blogComments;
	
	
public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Users getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Users createdBy) {
		this.createdBy = createdBy;
	}
	public Date getPostedOn() {
		return postedOn;
	}
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}
	public boolean isApproved() {
		return approved;
	}
	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public List<BlogComment> getBlogComments() {
		return blogComments;
	}
	public void setBlogComments(List<BlogComment> blogComments) {
		this.blogComments = blogComments;
	}

}

