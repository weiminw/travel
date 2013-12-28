package com.weiminw.business;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.common.base.Optional;
import com.google.common.collect.Maps;
import com.weiminw.travel.interfaces.IHotel;
import com.weiminw.travel.interfaces.IUser;
import com.weiminw.travel.interfaces.IUserManager;
import com.weiminw.travel.persistence.impls.MySqlPersistence;
import com.weiminw.travel.persistence.impls.pos.UserPO;

public final class UserManager implements IUserManager{
	final static MySqlPersistence<IUser> persistence = MySqlPersistence.create();
	public static IUserManager create() {
		// TODO Auto-generated method stub
		return new UserManager();
	}
	@Override
	public IUser getUserById(long id) {
		return Optional.fromNullable(persistence.getPersistenceObject(UserPO.class, id)).or(IUser.NULL);
	}
	
	@Override
	public List<IUser> getUserByHid(long id) {
		// TODO Auto-generated method stub
		Map.Entry<String,Long> queryParameters = Maps.immutableEntry("1", id);
		return persistence.getPersistenceObjects("UserPO.findAllByHid", queryParameters);
	}

	@Override
	public List<IUser> getSellerByLntLat(double lnt, double lat) {
		// TODO Auto-generated method stub
		double minLnt = lnt - 1;
		double maxLnt = lnt + 1;
		double minLat  = lat - 1;
		double maxLat = lat + 1;
		List<IUser> hotels = persistence.getPersistenceObjects("UserPO.findAllByLntLat",
				Maps.immutableEntry("1", minLnt),
				Maps.immutableEntry("2", maxLnt),
				Maps.immutableEntry("3", minLat),
				Maps.immutableEntry("4", maxLat)
		);
		return hotels;
	}
	
	

}