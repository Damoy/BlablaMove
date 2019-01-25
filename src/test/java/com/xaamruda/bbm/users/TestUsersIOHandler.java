package com.xaamruda.bbm.users;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.junit.MockitoJUnitRunner;

import com.xaamruda.bbm.commons.logging.BBMLogger;
import com.xaamruda.bbm.users.dbaccess.service.IUserService;
import com.xaamruda.bbm.users.identification.IUserIdentificator;
import com.xaamruda.bbm.users.info.IUserDataManager;
import com.xaamruda.bbm.users.model.User;

@RunWith(MockitoJUnitRunner.class)
public class TestUsersIOHandler {

	private UsersIOHandler usersIOHandler;
	
	@Mock
	private IUserService service;
	
	@Mock
	private IUserDataManager dataManager;
	
	@Mock
	private IUserIdentificator identificator;
	
	@Before
	@Test
	public void setup() {
		MockitoAnnotations.initMocks(this);
		usersIOHandler = new UsersIOHandler();
		
		Whitebox.setInternalState(usersIOHandler, "service", service);
		Whitebox.setInternalState(usersIOHandler, "identificator", identificator);
		Whitebox.setInternalState(usersIOHandler, "dataManager", dataManager);

		assertNotNull(service);
		assertNotNull(identificator);
		assertNotNull(dataManager);
		
		assertEquals(Whitebox.getInternalState(usersIOHandler, "service"), service);
		assertEquals(Whitebox.getInternalState(usersIOHandler, "identificator"), identificator);
		assertEquals(Whitebox.getInternalState(usersIOHandler, "dataManager"), dataManager);
	}
	
	@Test
	public void testIdentifyUserByMailPlusPassword_success() {
		String mail = "mail@mail";
		String pass = "pass";
		
		List<User> users = new ArrayList<>();
		User user = new User();
		user.setMail(mail);
		user.setPassword(pass);
		user.setIdentified(false);
		users.add(user);
		
		Mockito.when(identificator.identify(mail, pass)).thenReturn(true);
		Mockito.when(service.getUserByMail(mail)).thenReturn(users);
		
		boolean result = usersIOHandler.identifyUserByMailPlusPassword(mail, pass);
		assertEquals(result, user.isIdentified());
		assertTrue(user.isIdentified());
	}
	
	@Test
	public void testIdentifyUserByMailPlusPassword_fail() {
		String mail = "mail@mail";
		String pass = "pass";
		
		Mockito.when(identificator.identify(mail, pass)).thenReturn(false);
		
		List<User> users = new ArrayList<>();
		User user = new User();
		user.setMail(mail);
		user.setPassword(pass);
		user.setIdentified(false);
		users.add(user);
		
		boolean result = usersIOHandler.identifyUserByMailPlusPassword(mail, pass);
		assertEquals(result, user.isIdentified());
		assertFalse(user.isIdentified());
	}
	
	@Test
	public void testIdentify_success() {
		String mail = "mail@mail";
		String pass = "pass";
		
		List<User> users = new ArrayList<>();
		User user = new User();
		user.setMail(mail);
		user.setPassword(pass);
		user.setIdentified(true);
		users.add(user);
		
		Mockito.when(service.getUserByMail(mail)).thenReturn(users);

		boolean identified = usersIOHandler.isIdentified(mail);  
		assertEquals(user.isIdentified(), identified);
		assertTrue(identified);
	}
	
	@Test
	public void testIdentify_fail() {
		String mail = "mail@mail";
		String pass = "pass";
		
		List<User> users = new ArrayList<>();
		User user = new User();
		user.setMail(mail);
		user.setPassword(pass);
		user.setIdentified(false);
		users.add(user);
		
		Mockito.when(service.getUserByMail(mail)).thenReturn(users);

		boolean identified = usersIOHandler.isIdentified(mail);  
		assertEquals(user.isIdentified(), identified);
		assertFalse(identified);	
	}
	
	@Test
	public void testIdentify_empty() {
		String mail = "mail@mail";
		Mockito.when(service.getUserByMail(mail)).thenReturn(null);
		boolean identified = usersIOHandler.isIdentified(mail);  
		assertFalse(identified);	
	}
	
	@Test
	public void testRetrieveUsers() {
		String mail = "mail@mail";
		String pass = "pass";
		List<User> users = new ArrayList<>();
		User user = new User();
		user.setMail(mail);
		user.setPassword(pass);
		user.setIdentified(false);
		users.add(user);
		
		Mockito.when(service.getAllUsers()).thenReturn(users);
		assertEquals(users, usersIOHandler.retrieveUsers());
	}
	
	public User retrieveUser(String mail) {
		BBMLogger.infoln("Processing...");
		User user = service.getUserByMail(mail).get(0);
		return user;
	}
	
	@Test
	public void testRetrieveUser() {
		String mail = "mail@mail";
		String pass = "pass";
		List<User> users = new ArrayList<>();
		User user = new User();
		user.setMail(mail);
		user.setPassword(pass);
		user.setIdentified(false);
		users.add(user);
		
		Mockito.when(service.getUserByMail(mail)).thenReturn(users);
		assertEquals(usersIOHandler.retrieveUser(mail), users.get(0));
	}
	
	@Test
	public void makeTransactionTest() {
		String ownerID = "owner@mail";
		String buyerID = "buyer@mail";
		int finalPrice = 10;
		
		List<User> owners = new ArrayList<>();
		User owner = new User();
		owner.setPointsAmount(10);
		owner.setMail(ownerID);
		owners.add(owner);
		
		List<User> buyers = new ArrayList<>();
		User buyer = new User();
		buyer.setMail(buyerID);
		buyer.setPointsAmount(22);
		buyers.add(buyer);
		
		Mockito.when(service.getUserByMail(ownerID)).thenReturn(owners);
		Mockito.when(service.getUserByMail(buyerID)).thenReturn(buyers);
		
		int ownerPoints = owner.getPointsAmount();
		int buyerPoints = buyer.getPointsAmount();
		
		usersIOHandler.makeTransaction(ownerID, buyerID, finalPrice);
		
		assertEquals(owner.getPointsAmount().intValue(), ownerPoints + finalPrice);
		assertEquals(buyer.getPointsAmount().intValue(), buyerPoints - finalPrice);
	}
	
	@Test
	public void testDebit() {
		String mail = "mail@mail";
		int finalPrice = 10;
		
		List<User> users = new ArrayList<>();
		User user = new User();
		int userPoints = 10;
		user.setPointsAmount(userPoints);
		user.setMail(mail);
		users.add(user);
		
		Mockito.when(service.getUserByMail(mail)).thenReturn(users);
		
		usersIOHandler.debit(mail, finalPrice);
		assertEquals(user.getPointsAmount().intValue(), userPoints - finalPrice);
	}
	
	@Test
	public void testCredit() {
		String mail = "mail@mail";
		int finalPrice = 10;
		
		List<User> users = new ArrayList<>();
		User user = new User();
		int userPoints = 10;
		user.setPointsAmount(userPoints);
		user.setMail(mail);
		users.add(user);
		
		Mockito.when(service.getUserByMail(mail)).thenReturn(users);
		
		usersIOHandler.credit(mail, finalPrice);
		assertEquals(user.getPointsAmount().intValue(), userPoints + finalPrice);
	}
	
}