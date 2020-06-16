package com.dosto.services;

import com.dosto.models.ArtItem;
import com.dosto.models.User;
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

public class ArtItemService {
    private static long id=0;


    public static void setId() {
        JSONParser parser = new JSONParser();
        String userDirectory = System.getProperty("user.dir");
        try (Reader reader = new FileReader(userDirectory + "\\src\\main\\java\\com\\dosto\\data\\id.json")) {
            JSONObject obj = (JSONObject) parser.parse(reader);
            id = (long) obj.get("id");
        } catch (IOException | ParseException e) {
            System.out.println("error getting id");
            System.out.println(e.toString());
        }
    }
    @SuppressWarnings("unchecked")
    public static List<ArtItem> getLoggedUserArtItems(){
        List<ArtItem> artItems = new ArrayList<>();
        JSONParser parser = new JSONParser();
        String userDirectory = System.getProperty("user.dir");
        try (Reader reader = new FileReader(userDirectory + "\\src\\main\\java\\com\\dosto\\data\\artItems.json")) {
            JSONArray array = (JSONArray) parser.parse(reader);
            for (JSONObject object : (Iterable<JSONObject>) array) {
                ArtItem artItem = new ArtItem(object);
                if(artItem.getOwner().equals(GlobalVars.loggedUser.getUsername()))
                    artItems.add(artItem);
            }
        } catch (IOException | ParseException e) {
            System.out.println("error getting artItems");
            System.out.println(e.toString());
        }
        return artItems;
    }

    @SuppressWarnings("unchecked")
    public static List<ArtItem> getAcceptedArtItems(){
        List<ArtItem> artItems = new ArrayList<>();
        JSONParser parser = new JSONParser();
        String userDirectory = System.getProperty("user.dir");
        try (Reader reader = new FileReader(userDirectory + "\\src\\main\\java\\com\\dosto\\data\\artItems.json")) {
            JSONArray array = (JSONArray) parser.parse(reader);
            for (JSONObject object : (Iterable<JSONObject>) array) {
                ArtItem artItem = new ArtItem(object);
                if(artItem.getStatus().equals(ArtItem.inGalleryStatus))
                    artItems.add(artItem);
            }
        } catch (IOException | ParseException e) {
            System.out.println("error getting artItems");
            System.out.println(e.toString());
        }
        return artItems;
    }
    @SuppressWarnings("unchecked")
    public static List<ArtItem> getPendingArtItems(){
        List<ArtItem> artItems = new ArrayList<>();
        JSONParser parser = new JSONParser();
        String userDirectory = System.getProperty("user.dir");
        try (Reader reader = new FileReader(userDirectory + "\\src\\main\\java\\com\\dosto\\data\\artItems.json")) {
            JSONArray array = (JSONArray) parser.parse(reader);
            for (JSONObject object : (Iterable<JSONObject>) array) {
                ArtItem artItem = new ArtItem(object);
                if(artItem.getStatus().equals(ArtItem.pendingStatus))
                    artItems.add(artItem);
            }
        } catch (IOException | ParseException e) {
            System.out.println("error getting artItems");
            System.out.println(e.toString());
        }
        return artItems;
    }

    @SuppressWarnings("unchecked")
    public static List<ArtItem> getArtItemsByOwnerName(String ownerName){
        List<ArtItem> artItems = new ArrayList<>();
        JSONParser parser = new JSONParser();
        String userDirectory = System.getProperty("user.dir");
        try (Reader reader = new FileReader(userDirectory + "\\src\\main\\java\\com\\dosto\\data\\artItems.json")) {
            JSONArray array = (JSONArray) parser.parse(reader);
            for (JSONObject object : (Iterable<JSONObject>) array) {
                ArtItem artItem = new ArtItem(object);
                if(artItem.getOwner().equals(ownerName))
                    artItems.add(artItem);
            }
        } catch (IOException | ParseException e) {
            System.out.println("error getting artItems");
            System.out.println(e.toString());
        }
        return artItems;
    }
    @SuppressWarnings("unchecked")
    public static List<ArtItem> getArtItems(){
        List<ArtItem> artItems = new ArrayList<>();
        JSONParser parser = new JSONParser();
        String userDirectory = System.getProperty("user.dir");
        try (Reader reader = new FileReader(userDirectory + "\\src\\main\\java\\com\\dosto\\data\\artItems.json")) {
            JSONArray array = (JSONArray) parser.parse(reader);
            for (JSONObject object : (Iterable<JSONObject>) array) {
                ArtItem artItem = new ArtItem(object);
                artItems.add(artItem);
            }
        } catch (IOException | ParseException e) {
            System.out.println("error getting artItems");
            System.out.println(e.toString());
        }
        return artItems;
    }

    @SuppressWarnings("unchecked")
    public static void borrowArtItems(List<ArtItem> artItems){
        String userDirectory = System.getProperty("user.dir");
        JSONParser parser = new JSONParser();
        try (Reader reader = new FileReader(userDirectory + "\\src\\main\\java\\com\\dosto\\data\\artItems.json")) {
            JSONArray array = (JSONArray) parser.parse(reader);
            Iterator itr = array.iterator();
            while(itr.hasNext()){
                JSONObject next = (JSONObject) itr.next();
                ArtItem item = new ArtItem(next);
                for (ArtItem artItem : artItems) {
                    if(item.equals(artItem)){
                        next.put("status", ArtItem.pendingBorrow);
                    }
                }
            }
            FileWriter file = new FileWriter(userDirectory + "\\src\\main\\java\\com\\dosto\\data\\artItems.json");
            file.write(array.toJSONString());
            file.flush();
        }
        catch (IOException | ParseException err){
            System.out.println("Error writing artItem");
        }
    }
    @SuppressWarnings("unchecked")
    public static void acceptArtItem(ArtItem artItem){
        String userDirectory = System.getProperty("user.dir");
        JSONParser parser = new JSONParser();
        try (Reader reader = new FileReader(userDirectory + "\\src\\main\\java\\com\\dosto\\data\\artItems.json")) {
            JSONArray array = (JSONArray) parser.parse(reader);
            Iterator itr = array.iterator();
            while(itr.hasNext()){
                JSONObject next = (JSONObject) itr.next();
                ArtItem item = new ArtItem(next);
                if(item.equals(artItem)){
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
    }
    @SuppressWarnings("unchecked")
    public static void deleteArtItem(ArtItem artItem){
        String userDirectory = System.getProperty("user.dir");
        JSONParser parser = new JSONParser();
        try (Reader reader = new FileReader(userDirectory + "\\src\\main\\java\\com\\dosto\\data\\artItems.json")) {
            JSONArray array = (JSONArray) parser.parse(reader);
            Iterator itr = array.iterator();
            while(itr.hasNext()){
                ArtItem item = new ArtItem((JSONObject) itr.next());
                if(item.equals(artItem)){
                    itr.remove();
                }
            }
            FileWriter file = new FileWriter(userDirectory + "\\src\\main\\java\\com\\dosto\\data\\artItems.json");
            file.write(array.toJSONString());
            file.flush();
        }
        catch (IOException | ParseException err){
            System.out.println("Error writing artItem");
        }
    }
    @SuppressWarnings("unchecked")
    public static void addArtItem(ArtItem artItem){
        String userDirectory = System.getProperty("user.dir");
        JSONParser parser = new JSONParser();
        JSONObject userObject =new JSONObject();
        JSONObject idObject = new JSONObject();
        userObject.put("name",artItem.getName());
        userObject.put("artist",artItem.getArtist());
        userObject.put("description",artItem.getDescription());
        userObject.put("owner",artItem.getOwner());
        userObject.put("image", artItem.getEncodedImageString());
        userObject.put("status",artItem.getStatus());
        userObject.put("id", id);
        idObject.put("id",id + 1);
        id++;
        try (Reader reader = new FileReader(userDirectory + "\\src\\main\\java\\com\\dosto\\data\\artItems.json")) {
            JSONArray array = (JSONArray) parser.parse(reader);
            array.add(userObject);
            FileWriter file = new FileWriter(userDirectory + "\\src\\main\\java\\com\\dosto\\data\\artItems.json");
            file.write(array.toJSONString());
            file.flush();
            FileWriter idFile = new FileWriter(userDirectory + "\\src\\main\\java\\com\\dosto\\data\\id.json");
            idFile.write(idObject.toJSONString());
            idFile.flush();
        }
        catch (IOException | ParseException err){
            System.out.println("Error writing artItem");
        }
    }
}
