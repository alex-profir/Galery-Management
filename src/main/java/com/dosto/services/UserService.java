package com.dosto.services;

import com.dosto.App;
import com.dosto.models.Coder;
import com.dosto.models.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserService {
    public static List<User> getUsers(){
        List<User> users = new ArrayList<>();
        JSONParser parser = new JSONParser();
        String v = System.getProperty("user.dir");
        try (Reader reader = new FileReader(v + "\\src\\main\\java\\com\\dosto\\data\\users.json")) {
            JSONArray array = (JSONArray) parser.parse(reader);
            Iterator<JSONObject> iterator = array.iterator();
            while(iterator.hasNext()){
                JSONObject object = iterator.next();
                User user= new User(object);
                users.add(user);
            }
        } catch (IOException | ParseException e) {
            System.out.println("error getting users");
            System.out.println(e.toString());
        }
        return users;
    }
}
