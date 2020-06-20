package com.dosto.testing;

import com.dosto.models.ArtItem;
import com.dosto.models.Coder;
import com.dosto.models.User;
import com.dosto.services.ArtItemService;
import com.dosto.services.GlobalVars;
import com.dosto.services.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ArtItemServiceTest {

    @Before
    public void setup() {
        User user1 = new User("user1",Coder.encode("1234"));
        List<User> users = UserService.getUsers();
        for (User user : users) {
            if(user.equals(user1)){
                GlobalVars.loggedUser = user;
                return;
            }
        }

    }

    @Test
    public void loggedUserArtItemsNotNull() {
        List<ArtItem> artItemList = ArtItemService.getLoggedUserArtItems();
        Assert.assertNotNull(artItemList);
    }

    @Test
    public void acceptedArtItemsNotNull() {
        List<ArtItem> artItemList = ArtItemService.getAcceptedArtItems();
        Assert.assertNotNull(artItemList);
    }

    @Test
    public void pendingArtItemsNotNull() {
        List<ArtItem> artItemList = ArtItemService.getPendingArtItems();
        Assert.assertNotNull(artItemList);
    }

    @Test
    public void artItemsByOwnerNameNotNull() {
        List<ArtItem> artItemList = ArtItemService.getArtItemsByOwnerName("user1");
        Assert.assertNotNull(artItemList);
    }

    @Test
    public void artItemsNotNull() {
        List<ArtItem> artItemList = ArtItemService.getArtItems();
        Assert.assertNotNull(artItemList);
    }
    @Test
    public void borrowArtItems() {
        List<ArtItem> items = new ArrayList<>();
        items.add(new ArtItem("test", "test", "test", Coder.encode("test"), "test"));
        ArtItemService.borrowArtItems(items);
    }

    @Test
    public void acceptArtItem() {
        ArtItemService.acceptArtItem(new ArtItem("test", "test", "test", Coder.encode("test"), "test"));
    }

    @Test
    public void deleteArtItem() {
        ArtItemService.deleteArtItem(new ArtItem("test", "test", "test", Coder.encode("test"), "test"));
    }

    @Test
    public void addAndDeleteArtItem() {
        List<ArtItem> artItemList = ArtItemService.getArtItems();
        Assert.assertNotNull(artItemList);

        int initialLength = artItemList.size();
        ArtItem artItem = new ArtItem("test", "test", "test", Coder.encode("test"), "test");
        ArtItemService.addArtItem(artItem);

        int secondLength = ArtItemService.getArtItems().size();
        Assert.assertEquals(secondLength,initialLength + 1);

        ArtItemService.deleteArtItem(artItem);

        int thirdLength = ArtItemService.getArtItems().size();

        assertEquals(thirdLength,initialLength);



    }
}