package com.dosto.services;

import com.dosto.models.ArtItem;
import com.dosto.models.Borrow;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class BorrowService {
    @SuppressWarnings("unchecked")
    public static void createBorrow(List<ArtItem> artItemList,String creator){
        String userDirectory = System.getProperty("user.dir");
        JSONParser parser = new JSONParser();
        JSONObject borrowObject =new JSONObject();
        List<Long> idList = artItemList.stream().map(ArtItem::getId).collect(Collectors.toList());

        borrowObject.put("creator",creator);
        borrowObject.put("artItems",idList);
        borrowObject.put("status",Borrow.pendingStatus);
        ArtItemService.borrowArtItems(artItemList);
        try (Reader reader = new FileReader(userDirectory + "\\src\\main\\java\\com\\dosto\\data\\borrows.json")) {
            JSONArray array = (JSONArray) parser.parse(reader);
            array.add(borrowObject);
            FileWriter file = new FileWriter(userDirectory + "\\src\\main\\java\\com\\dosto\\data\\borrows.json");
            file.write(array.toJSONString());
            file.flush();
        }
        catch (IOException | ParseException err){
            System.out.println("Error writing borrow");
        }
    }

    public static void deleteBorrow(Borrow borrow){
        String userDirectory = System.getProperty("user.dir");
        JSONParser parser = new JSONParser();
        try (Reader reader = new FileReader(userDirectory + "\\src\\main\\java\\com\\dosto\\data\\borrows.json")) {
            JSONArray array = (JSONArray) parser.parse(reader);
            Iterator itr = array.iterator();
            while(itr.hasNext()){
                Borrow item = new Borrow((JSONObject) itr.next());
                if(item.equals(borrow)){
                    itr.remove();
                }
            }
            FileWriter file = new FileWriter(userDirectory + "\\src\\main\\java\\com\\dosto\\data\\borrows.json");
            file.write(array.toJSONString());
            file.flush();
        }
        catch (IOException | ParseException err){
            System.out.println("Error deleting borrow");
        }
    }

    @SuppressWarnings("unchecked")
    public static void returnPendingItems(Borrow borrow,List<Long> idList){
        String userDirectory = System.getProperty("user.dir");

        JSONParser parser = new JSONParser();

        try (Reader reader = new FileReader(userDirectory + "\\src\\main\\java\\com\\dosto\\data\\artItems.json")) {
            JSONArray array = (JSONArray) parser.parse(reader);
            Iterator itr = array.iterator();
            while(itr.hasNext()){
                JSONObject next = (JSONObject) itr.next();
                ArtItem item = new ArtItem(next);
                String originalOwner = item.getOriginalOwner();
                System.out.println(originalOwner);
                if(idList.contains(item.getId())) {
                    next.put("status", ArtItem.inGalleryStatus);
                }
            }
            FileWriter file = new FileWriter(userDirectory + "\\src\\main\\java\\com\\dosto\\data\\artItems.json");
            file.write(array.toJSONString());
            file.flush();
        }
        catch (IOException | ParseException err){
            System.out.println("Error updating borrow");
        }
    }

    @SuppressWarnings("unchecked")
    public static void returnBorrowItems(Borrow borrow,List<Long> idList){
        String userDirectory = System.getProperty("user.dir");

        JSONParser parser = new JSONParser();

        try (Reader reader = new FileReader(userDirectory + "\\src\\main\\java\\com\\dosto\\data\\artItems.json")) {
            JSONArray array = (JSONArray) parser.parse(reader);
            Iterator itr = array.iterator();
            while(itr.hasNext()){
                JSONObject next = (JSONObject) itr.next();
                ArtItem item = new ArtItem(next);
                String originalOwner = item.getOriginalOwner();
                System.out.println(originalOwner);
                if(idList.contains(item.getId())) {
                    next.put("status", ArtItem.inGalleryStatus);
                    next.put("owner", originalOwner);
                    next.remove("originalOwner");
                }
            }
            FileWriter file = new FileWriter(userDirectory + "\\src\\main\\java\\com\\dosto\\data\\artItems.json");
            file.write(array.toJSONString());
            file.flush();
        }
        catch (IOException | ParseException err){
            System.out.println("Error updating borrow");
        }

        updateBorrowStatus(borrow,Borrow.returnedStatus);
    }


    @SuppressWarnings("unchecked")
    public static void updateBorrowItems(Borrow borrow,List<Long> idList) {
        String userDirectory = System.getProperty("user.dir");

        JSONParser parser = new JSONParser();
        try (Reader reader = new FileReader(userDirectory + "\\src\\main\\java\\com\\dosto\\data\\borrows.json")) {
            JSONArray array = (JSONArray) parser.parse(reader);
            Iterator itr = array.iterator();
            while(itr.hasNext()){
                JSONObject next = (JSONObject) itr.next();
                Borrow item = new Borrow(next);
                if(item.equals(borrow)) {
                    next.put("artItems", idList);
                }
            }
            FileWriter file = new FileWriter(userDirectory + "\\src\\main\\java\\com\\dosto\\data\\borrows.json");
            file.write(array.toJSONString());
            file.flush();
        }
        catch (IOException | ParseException err) {
            System.out.println("Error updating borrow");
        }
    }
    @SuppressWarnings("unchecked")
    public static void updateBorrowStatus(Borrow borrow,String status) {
        System.out.println("At update");
        String userDirectory = System.getProperty("user.dir");
        JSONParser parser = new JSONParser();
        try (Reader reader = new FileReader(userDirectory + "\\src\\main\\java\\com\\dosto\\data\\borrows.json")) {
            JSONArray array = (JSONArray) parser.parse(reader);
            Iterator itr = array.iterator();
            while(itr.hasNext()){
                JSONObject next = (JSONObject) itr.next();
                Borrow item = new Borrow(next);
                if(item.equals(borrow)) {
                    next.put("status", status);
                }
            }
            FileWriter file = new FileWriter(userDirectory + "\\src\\main\\java\\com\\dosto\\data\\borrows.json");
            file.write(array.toJSONString());
            file.flush();
        }
        catch (IOException | ParseException err) {
            System.out.println("Error updating borrow");
        }
    }
    @SuppressWarnings("unchecked")
    public static void rejectBorrow(Borrow borrow){
        String userDirectory = System.getProperty("user.dir");
        JSONParser parser = new JSONParser();
        List<Long> idList = borrow.getArtItems();

        try (Reader reader = new FileReader(userDirectory + "\\src\\main\\java\\com\\dosto\\data\\artItems.json")) {
            JSONArray array = (JSONArray) parser.parse(reader);
            Iterator itr = array.iterator();
            while(itr.hasNext()){
                JSONObject next = (JSONObject) itr.next();
                ArtItem item = new ArtItem(next);
                if(idList.contains(item.getId())) {
                    next.put("status", ArtItem.inGalleryStatus);
                }
            }
            FileWriter file = new FileWriter(userDirectory + "\\src\\main\\java\\com\\dosto\\data\\artItems.json");
            file.write(array.toJSONString());
            file.flush();
        }
        catch (IOException | ParseException err){
            System.out.println("Error rejecting borrow");
        }
        deleteBorrow(borrow);
    }


    @SuppressWarnings("unchecked")
    public static void updateBorrow(Borrow borrow,List<ArtItem> artItems){
        String userDirectory = System.getProperty("user.dir");

        JSONParser parser = new JSONParser();
        List<Long> idList = artItems.stream().map(ArtItem::getId).collect(Collectors.toList());
        List<Long> unselected = borrow.getArtItems().stream().filter(id -> !idList.contains(id)).collect(Collectors.toList());
        System.out.println(unselected);
        try (Reader reader = new FileReader(userDirectory + "\\src\\main\\java\\com\\dosto\\data\\artItems.json")) {
            JSONArray array = (JSONArray) parser.parse(reader);
            Iterator itr = array.iterator();
            while(itr.hasNext()){
                JSONObject next = (JSONObject) itr.next();
                ArtItem item = new ArtItem(next);
                if(idList.contains(item.getId())) {
                    next.put("status", ArtItem.borrowedStatus);
                    next.put("owner", borrow.getCreator());
                    next.put("originalOwner",item.getOwner());
                }
            }
            FileWriter file = new FileWriter(userDirectory + "\\src\\main\\java\\com\\dosto\\data\\artItems.json");
            file.write(array.toJSONString());
            file.flush();
        }
        catch (IOException | ParseException err){
            System.out.println("Error updating borrow");
        }
        updateBorrowStatus(borrow, Borrow.borrowedStatus);
        updateBorrowItems(borrow, idList);
        returnPendingItems(borrow, unselected);
    }
    @SuppressWarnings("unchecked")
    public static Borrow getBorrowByArtItem(ArtItem artItem){

        JSONParser parser = new JSONParser();
        String userDirectory = System.getProperty("user.dir");
        try (Reader reader = new FileReader(userDirectory + "\\src\\main\\java\\com\\dosto\\data\\borrows.json")) {
            JSONArray array = (JSONArray) parser.parse(reader);
            for (JSONObject object : (Iterable<JSONObject>) array) {
                Borrow borrow = new Borrow(object);
                for (Long item : borrow.getArtItems()) {
                    if(artItem.getId().equals(item) && !borrow.getStatus().equals(Borrow.returnedStatus)){
                        return borrow;
                    }
                }
            }
        } catch (IOException | ParseException e) {
            System.out.println("error getting borrow by art item");
            System.out.println(e.toString());
        }
        return null;
    }
}
