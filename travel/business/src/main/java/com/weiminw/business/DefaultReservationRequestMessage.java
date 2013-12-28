package com.weiminw.business;

import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import com.weiminw.travel.interfaces.IReservationRequestMessage;
import com.weiminw.travel.interfaces.IUser;

public class DefaultReservationRequestMessage implements IReservationRequestMessage{
	private final static Logger logger = Logger.getLogger(DefaultReservationRequestMessage.class);
	private final static class MessagePush implements Callable<String> {

		@Override
		public String call() throws Exception {
			logger.debug("--------------");
			return null;
		}
		
	}
	private final MessagePush callable ;
	private DefaultReservationRequestMessage(IUser from, IUser to) {
		callable = new MessagePush();
	}

	public static IReservationRequestMessage create(IUser from, IUser to) {
		return new DefaultReservationRequestMessage(from,to);
	}

	@Override
	public void send() {
		MessageSendWorker.execute(callable);
	}

}