package it.polito.applied.asti.clan.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.WriteResult;

import it.polito.applied.asti.clan.pojo.User;


public class UserRepositoryImpl implements CustomUserRepository{
	
	@Autowired
	private MongoOperations mongoOp;

	/*A questo metodo non passiamo la vecchia password in quanto 
	 * non è possibile controllare la password con una query in Mongo
	 * (BCrypt mischia il sale con la pwd e salva una sola riga nel DB)
	 * Il controllo viene fatto a monte (nel Service) della chiamata a questo
	 * metodo*/
	@Override
	public int changePassword(String username, String newPassword) {

		Query q = new Query();
		q.addCriteria(Criteria.where("username").is(username));
		Update u = new Update();
		u.set("password", newPassword);
		WriteResult w = mongoOp.updateFirst(q, u, User.class);
		return w.getN();
	}

}
