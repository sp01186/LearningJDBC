package com.sparta.mysql;

import com.sparta.ConnectionFactory;
import com.sparta.entities.Actor;
import com.sparta.interfaces.DAO;

import javax.swing.plaf.nimbus.State;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ActorDAO implements DAO<Actor> {
    public static void usePreparedStatementsToRead() { // protect against sql injection attacks
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try (Connection connection = ConnectionFactory.getConnection();){
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM Actor WHERE last_name = ?"
            );
            preparedStatement.setString(1, "HURT");
            rs = preparedStatement.executeQuery();
            while(rs.next()) {
                System.out.println(rs.getInt("actor_id") +
                        " " + rs.getString(2) +
                        " " + rs.getString(3)
                );
            }
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deletedById(int id) {
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(
                    "DELETE FROM Actor WHERE actor_id = ?"
            );
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                ConnectionFactory.closeConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public void updated(Actor update) {
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(
                    "UPDATE Actor SET first_name = ?, last_name = ?" +
                            "WHERE actor_id = ?"
            );
            preparedStatement.setString(1, update.getFirst_name());
            preparedStatement.setString(2, update.getLast_name());
            preparedStatement.setInt(3, update.getActor_id());
            preparedStatement.execute();
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                ConnectionFactory.closeConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public int insert(Actor newRow) {
//        INSERT INTO Customers (CustomerName, ContactName, Address, City, PostalCode, Country)
//        VALUES ('Cardinal', 'Tom B. Erichsen', 'Skagen 21', 'Stavanger', '4006', 'Norway');

        PreparedStatement preparedStatement = null;
        ResultSet rs = null;


        Actor obtainedActor = new Actor();

        int answer = 0;

        try {
            Connection connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO Actor (first_name, last_name) VALUES (?, ?)"
            );
            preparedStatement.setString(1, newRow.getFirst_name());
            preparedStatement.setString(2, newRow.getLast_name());
            preparedStatement.execute();

            Statement statement = connection.createStatement();
            rs = statement.executeQuery("SELECT LAST_INSERT_ID()");

            rs.next();
            answer = rs.getInt(1);



        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                ConnectionFactory.closeConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return answer;
    }

    @Override
    public Actor findById(int id) {
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        Actor obtainedActor = new Actor();


        try {
            Connection connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM Actor WHERE actor_id = ?"
            );
            preparedStatement.setString(1, String.valueOf(id));
            rs = preparedStatement.executeQuery();
            while(rs.next()) {
                obtainedActor.setActor_id(rs.getInt("actor_id"));
                obtainedActor.setFirst_name(rs.getString("first_name"));
                obtainedActor.setLast_name(rs.getString("last_name"));
                obtainedActor.setLast_update(rs.getDate("last_update"));
            }
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        } //finally {
//            try {
//                ConnectionFactory.closeConnection();
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//        }
        return obtainedActor;
    }

    @Override
    public List<Actor> findAll() {
        List<Actor> actors = new ArrayList<>();
        Statement statement = null;
        ResultSet rs = null;
        try {
            Connection connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();

            rs = statement.executeQuery("SELECT * FROM sakila.actor");
            while (rs.next()) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:", Locale.ENGLISH);

                Actor newActor = new Actor();
                newActor.setActor_id(rs.getInt("actor_id"));
                newActor.setFirst_name(rs.getString("first_name"));
                newActor.setLast_name(rs.getString("last_name"));
                newActor.setLast_update(rs.getDate("last_update"));
                actors.add(newActor);
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                ConnectionFactory.closeConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return actors;
    }
}
