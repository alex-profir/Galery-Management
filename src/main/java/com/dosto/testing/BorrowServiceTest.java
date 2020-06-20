package com.dosto.testing;

import com.dosto.models.ArtItem;
import com.dosto.models.Borrow;
import com.dosto.services.ArtItemService;
import com.dosto.services.BorrowService;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class BorrowServiceTest {
    private ArtItem first = new ArtItem("first","first","first", "", ArtItem.inGalleryStatus);
    private ArtItem second = new ArtItem("second","second","second", "", ArtItem.inGalleryStatus);
    private List<ArtItem> artItems = new ArrayList<>();


    @Test
    public void createAndDeleteBorrow() {
        ArtItemService.addArtItem(first);
        ArtItemService.addArtItem(second);

        artItems.add(first);
        artItems.add(second);


        List<ArtItem> lastTwo = new ArrayList<>();
        List<ArtItem> all = ArtItemService.getArtItems();

        first = all.get(all.size()-2);
        second =all.get(all.size()-1);
        lastTwo.add(first);
        lastTwo.add(second);

        BorrowService.createBorrow(lastTwo,"test");

        Borrow borrow = BorrowService.getBorrowByArtItem(first);

        Assert.assertNotNull(borrow);
        Assert.assertEquals(lastTwo.stream().map(ArtItem::getId).collect(Collectors.toList()), borrow.getArtItems());

        BorrowService.deleteBorrow(borrow);

        borrow = BorrowService.getBorrowByArtItem(first);

        assertNull(borrow);

        ArtItemService.deleteArtItem(first);
        ArtItemService.deleteArtItem(second);

    }

    @Test
    public void updateAndReturnBorrowItems() {
        ArtItemService.addArtItem(first);
        ArtItemService.addArtItem(second);

        artItems.add(first);
        artItems.add(second);


        List<ArtItem> lastTwo = new ArrayList<>();
        List<ArtItem> all = ArtItemService.getArtItems();

        first = all.get(all.size()-2);
        second =all.get(all.size()-1);
        lastTwo.add(first);
        lastTwo.add(second);

        BorrowService.createBorrow(lastTwo,"test");
        Borrow borrow = BorrowService.getBorrowByArtItem(first);

        Assert.assertNotNull(borrow);
        Assert.assertEquals(lastTwo.stream().map(ArtItem::getId).collect(Collectors.toList()), borrow.getArtItems());
        BorrowService.updateBorrow(borrow,lastTwo);

        borrow = BorrowService.getBorrowByArtItem(first);
        Assert.assertEquals(Borrow.borrowedStatus,borrow.getStatus());

        BorrowService.returnBorrowItems(borrow , lastTwo.stream().map(ArtItem::getId).collect(Collectors.toList()));


        ArtItemService.deleteArtItem(first);
        ArtItemService.deleteArtItem(second);
    }

    @Test
    public void rejectBorrow() {
        ArtItemService.addArtItem(first);
        ArtItemService.addArtItem(second);

        artItems.add(first);
        artItems.add(second);


        List<ArtItem> lastTwo = new ArrayList<>();
        List<ArtItem> all = ArtItemService.getArtItems();

        first = all.get(all.size()-2);
        second =all.get(all.size()-1);
        lastTwo.add(first);
        lastTwo.add(second);

        BorrowService.createBorrow(lastTwo,"test");
        Borrow borrow = BorrowService.getBorrowByArtItem(first);

        Assert.assertNotNull(borrow);
        Assert.assertEquals(lastTwo.stream().map(ArtItem::getId).collect(Collectors.toList()), borrow.getArtItems());

        BorrowService.rejectBorrow(borrow);

        borrow = BorrowService.getBorrowByArtItem(first);

        assertNull(borrow);


        ArtItemService.deleteArtItem(first);
        ArtItemService.deleteArtItem(second);
    }

}