package com.github.artemdvn.webpublisher.services;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.github.artemdvn.webpublisher.dao.UserDao;
import com.github.artemdvn.webpublisher.model.User;
import com.github.artemdvn.webpublisher.model.UserRole;

public class UserServiceTest {
	
	@InjectMocks
    UserService userService = new UserServiceImpl();
	
	@Mock
	private UserDao userDao;
	
	User adminUser = new User(101, "Tom", "tom@webpublisher.com", UserRole.ADMIN);
	User adopsUser = new User(102, "Alex", "alex@webpublisher.com", UserRole.ADOPS);
	
	@Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
    public void testGet() throws Exception {
        when(userDao.get(101)).thenReturn(adminUser);

        User actualPlayer = userService.get(101);

        verify(userDao).get(101);
        assertThat(actualPlayer, is(adminUser));
    }
	
	@Test
    public void testGetByName() throws Exception {
        when(userDao.getByName("Tom")).thenReturn(adminUser);
        
        User actualPlayer = userService.getByName("Tom");

        verify(userDao).getByName("Tom");
        assertThat(actualPlayer, is(adminUser));
    }
	
	@Test
    public void testGetByRole() throws Exception {
        when(userDao.getByRole("ADOPS")).thenReturn(Arrays.asList(adopsUser));
        
        List<User> actualPlayers = userService.getByRole("ADOPS");

        verify(userDao).getByRole("ADOPS");
        assertThat(actualPlayers, is(Arrays.asList(adopsUser)));
    }
	
	@Test
    public void testGetAll() throws Exception {
        when(userDao.getAll()).thenReturn(Arrays.asList(adminUser, adopsUser));
        
        List<User> actualPlayers = userService.getAll();

        verify(userDao).getAll();
        assertThat(actualPlayers, is(Arrays.asList(adminUser, adopsUser)));
    }
	
	@Test
    public void testDeleteByUser() throws Exception {
		userService.delete(adminUser);

        verify(userDao).delete(adminUser);
    }

    @Test
    public void testDeleteById() throws Exception {
        userService.delete(101);

        verify(userDao).delete(101);
    }

}
