package com.dosto.testing;

import com.dosto.models.Coder;
import com.dosto.models.User;
import com.dosto.services.UserService;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class UserServiceTest {
    private User user = new User("test","test","admin","some@mail.com","ion","miron");
    @Test
    public void getUsersNotNull() {
        List<User> users = UserService.getUsers();
        Assert.assertNotNull(users);
    }

    @Test
    public void addAndDeleteUser() {
        List<User> users = UserService.getUsers();
        int firstLength = users.size();

        UserService.addUser(user);

        users = UserService.getUsers();
        int secondLength = users.size();
        User u = users.get(users.size()-1);
        Assert.assertNotNull(u);
        Assert.assertEquals(firstLength + 1,secondLength);

        Assert.assertEquals("test",u.getUsername());
        Assert.assertEquals(Coder.encode("test"),u.getPassword());
        Assert.assertEquals("admin",u.getRole());
        Assert.assertEquals("some@mail.com",u.getEmail());

        UserService.deleteUser(u);

        users = UserService.getUsers();
        int thirdLength = users.size();

        Assert.assertEquals(firstLength,thirdLength);


    }
}