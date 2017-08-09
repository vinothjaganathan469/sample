package com.back.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.back.model.Friend;
import com.back.model.Users;
import com.back.dao.FriendDao;

@Repository
public class FriendDaoImpl implements FriendDao{
	@Autowired
	private SessionFactory sessionFactory;
	 public List<Users> listOfSuggestedUsers(String username){
		 Session session=sessionFactory.openSession();
		 SQLQuery sqlQuery= session.createSQLQuery("select * from BF_users where username in (select username from BF_users where username!=? minus (select fromid from BF_friend where toId=? union select toId from BF_friend where fromId=? ))");
		 sqlQuery.setString(0,username);
		 sqlQuery.setString(1,username);
		 sqlQuery.setString(2,username);
		 sqlQuery.addEntity(Users.class);
		    List<Users> suggestedUsersList=sqlQuery.list();
		    session.close();
		    return suggestedUsersList;
		 }
      public void friendRequest(String fromUsername,String toUsername) {
    	  Session session=sessionFactory.openSession();
    	  //fromUsername-
    	  //toUsername-
    	  Friend friend=new Friend();
    	  friend.setFromId(fromUsername);
    	  friend.setToId(toUsername);
    	  friend.setStatus('P');
    	  session.save(friend);
    	  session.flush();
    	  session.close();
    	}
      public List<Friend> listOfPendingRequests(String loggedInUsername) {
    	  Session session=sessionFactory.openSession();
    	  Query query=session.createQuery("from Friend where toId=? and status=?");
    	  query.setString(0, loggedInUsername);
    	  query.setCharacter(1,'P');
    	  List<Friend> pendingRequests=query.list();
    	  session.close();
    	  return pendingRequests;
      }
      public void updatePendingRequest(String fromId,String toId,char status) {
    	  Session session=sessionFactory.openSession();
    	  Query query=session.createQuery("from Friend where fromId=? and toId=?");
    	  query.setString(0, fromId);
    	  query.setString(1, toId);
    	  Friend friend=(Friend)query.uniqueResult();
    	  friend.setStatus(status);//status can be either 'A' or 'D'
    	  session.update(friend);
    	  session.flush();
    	  session.close();
      } 
      public List<Friend> listOfFriends(String username) {
    	  Session session=sessionFactory.openSession();
    	  Query query=session.createQuery("from Friend where (fromId=? or toId=?) and status=?");
    	  query.setString(0, username);
    	  query.setString(1, username);
    	  query.setCharacter(2, 'A');
    	  List<Friend> friends=query.list();
    	  session.close();
    	  return friends;
      } 
    	  
}
