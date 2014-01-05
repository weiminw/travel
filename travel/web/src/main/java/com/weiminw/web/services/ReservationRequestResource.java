package com.weiminw.web.services;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

import javax.servlet.ServletContext;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;
import com.weiminw.business.aos.Seller;
import com.weiminw.business.managers.HotelManager;
import com.weiminw.business.managers.ReservationMessageFactory;
import com.weiminw.business.managers.UserManager;
import com.weiminw.business.workers.MessageSendWorker;
import com.weiminw.travel.interfaces.IHotel;
import com.weiminw.travel.interfaces.IHotelManager;
import com.weiminw.travel.interfaces.IReservationRequestMessage;
import com.weiminw.travel.interfaces.IUser;
import com.weiminw.travel.interfaces.IUserManager;

@Path("/reservation/request")
public class ReservationRequestResource {
	@Context
	UriInfo uriInfo;
	IHotelManager hotelManager = HotelManager.create();
	
	IUserManager userManager = UserManager.create();
	private final static Logger logger = LogManager.getLogger(ReservationRequestResource.class);
	@Path("/")
	@POST
	public Response createReservationRequest(
			@FormParam("lowPrice") double lowPrice,/*最低价格*/
			@FormParam("highPrice") double highPrice,/*最高价格*/
			@FormParam("checkIn") Date checkIn,/*入住时间*/
			@FormParam("checkOut") Date checkOut,/*离店时间*/
			@FormParam("lnt") double lnt,/*用户指定经度*/
			@FormParam("lat") double lat /*用户指定维度 */) {
		String uuid = UUID.randomUUID().toString();
//		List<IHotel> hotels = this.hotelManager.getHotelsByLntLat(lnt, lat);
//		logger.debug(hotels);
//		for(IHotel hotel:hotels){
//			List<IUser> tos = userManager.getUserByHid(hotel.getId());
//			for(IUser user:tos){
//				IReservationRequestMessage message = ReservationMessageFactory.createRequestMessage(user, user);
//				message.send();
//			}
//		}
		List<IHotel> hotels = this.hotelManager.getHotelsByLntLat(lnt, lat, 0);
				
		for(IHotel hotel:hotels){
			List<IUser> users = this.userManager.getSellerByHid(hotel.getId());
			for(IUser user:users){
				IReservationRequestMessage message = ReservationMessageFactory.createRequestMessage(user, user);
				message.send();
			}
		}
		return Response.created(uriInfo.getRequestUri().resolve(UUID.randomUUID().toString())).build();
		
	}
}
