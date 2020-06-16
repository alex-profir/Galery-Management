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
        Long[] ids = {};
        List<Long> idList = artItemList.stream().map(artItem -> artItem.getId()).collect(Collectors.toList());

        borrowObject.put("creator",creator);
        borrowObject.put("artItems",idList);
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
    @SuppressWarnings("unchecked")
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
            System.out.println("Error writing artItem");
        }
        deleteBorrow(borrow);
    }
    @SuppressWarnings("unchecked")
    public static void updateBorrow(Borrow borrow,List<ArtItem> artItems){
        String userDirectory = System.getProperty("user.dir");
        JSONParser parser = new JSONParser();
        List<Long> idList = artItems.stream().map(artItem -> artItem.getId()).collect(Collectors.toList());

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
            System.out.println("Error writing artItem");
        }
        deleteBorrow(borrow);

    }
    @SuppressWarnings("unchecked")
    public static Borrow getBorrowByArtItem(ArtItem artItem){
        Borrow returnBorrow= new Borrow();
        List<ArtItem> artItems = new ArrayList<>();

        JSONParser parser = new JSONParser();
        String userDirectory = System.getProperty("user.dir");
        try (Reader reader = new FileReader(userDirectory + "\\src\\main\\java\\com\\dosto\\data\\borrows.json")) {
            JSONArray array = (JSONArray) parser.parse(reader);
            for (JSONObject object : (Iterable<JSONObject>) array) {
                Borrow borrow = new Borrow(object);
                for (Long item : borrow.getArtItems()) {
                    if(artItem.getId().equals(item)){
                        return borrow;
                    }
                }
            }
        } catch (IOException | ParseException e) {
            System.out.println("error getting borrow by art item");
            System.out.println(e.toString());
        }
        return returnBorrow;
    }
}
