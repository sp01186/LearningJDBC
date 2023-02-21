package com.sparta;

import com.sparta.entities.Actor;
import com.sparta.mysql.ActorDAO;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        //usePreparedStatementsToRead();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        ActorDAO actorDAO = new ActorDAO();

        System.out.println(actorDAO.findById(20));
        System.out.println(actorDAO.findById(21));

//        List<Actor> actors = actorDAO.findAll();
//        System.out.println(actors.get(0));


    }


}