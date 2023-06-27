package dao.impl;

import dao.ContactDAO;
import model.Contact;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactDAOImplementation implements ContactDAO {
    String url = "jdbc:mysql://localhost:3306/agenda";
    String username = "root";
    String password = "root";

    @Override
    public void create(Contact contact) {

        try (Connection con = DriverManager.getConnection(url,username,password)){

            String sql = "INSERT INTO contact (firstName, lastName, phoneNumber, email) VALUES (?, ?, ?, ?)";

            try (PreparedStatement ps = con.prepareStatement(sql)){

                ps.setString(1, contact.firstName);
                ps.setString(2, contact.lastName);
                ps.setString(3, contact.phoneNumber);
                ps.setString(4, contact.email);

                ps.executeUpdate();

            }

        }
        catch (SQLException e){

            e.printStackTrace();

        }
    }

    @Override
    public Contact getByEmail(String email) {

        try (Connection con = DriverManager.getConnection(url,username,password)){

            String sql = "SELECT firstName, lastName, phoneNumber, email from contact where email = ?";

            try (PreparedStatement ps = con.prepareStatement(sql)){

                ps.setString(1, email);
                ResultSet rs = ps.executeQuery();

                while(rs.next()) {
                    String firstName = rs.getString(1);
                    String lastName = rs.getString(2);
                    String phoneNumber = rs.getString(3);
                    Contact contact = new Contact();
                    contact.firstName = firstName;
                    contact.lastName = lastName;
                    contact.phoneNumber = phoneNumber;
                    contact.email = email;
                    return contact;
                }
            }

        }
        catch (SQLException e){

            e.printStackTrace();

        }

        return null;
    }

    @Override
    public Contact getByFirstName(String firstName) {

        try (Connection con = DriverManager.getConnection(url,username,password)){

            String sql = "SELECT firstName, lastName, phoneNumber, email from contact where firstName = ?";

            try (PreparedStatement ps = con.prepareStatement(sql)){

                ps.setString(1, firstName);
                ResultSet rs = ps.executeQuery();

                while(rs.next()) {

                    String lastName = rs.getString(2);
                    String phoneNumber = rs.getString(3);
                    String email = rs.getString(4);
                    Contact contact = new Contact();
                    contact.firstName = firstName;
                    contact.lastName = lastName;
                    contact.phoneNumber = phoneNumber;
                    contact.email = email;
                    return contact;

                }
            }
        }
        catch (SQLException e){

            e.printStackTrace();

        }

        return null;
    }

    @Override
    public Contact getByLastName(String lastName) {

        try (Connection con = DriverManager.getConnection(url,username,password)){

            String sql = "SELECT firstName, lastName, phoneNumber, email from contact where lastName = ?";

            try (PreparedStatement ps = con.prepareStatement(sql)){

                ps.setString(1, lastName);
                ResultSet rs = ps.executeQuery();

                while(rs.next()) {

                    String firstName = rs.getString(1);
                    String phoneNumber = rs.getString(3);
                    String email = rs.getString(4);
                    Contact contact = new Contact();
                    contact.firstName = firstName;
                    contact.lastName = lastName;
                    contact.phoneNumber = phoneNumber;
                    contact.email = email;
                    return contact;

                }
            }
        }
        catch (SQLException e){

            e.printStackTrace();

        }

        return null;
    }

    @Override
    public Contact getByPhoneNumber(String phoneNumber) {

        try (Connection con = DriverManager.getConnection(url,username,password)){

            String sql = "SELECT firstName, lastName, phoneNumber, email from contact where phoneNumber = ?";

            try (PreparedStatement ps = con.prepareStatement(sql)){

                ps.setString(1, phoneNumber);
                ResultSet rs = ps.executeQuery();

                while(rs.next()) {

                    String firstName = rs.getString(1);
                    String lastName = rs.getString(2);
                    String email = rs.getString(4);
                    Contact contact = new Contact();
                    contact.firstName = firstName;
                    contact.lastName = lastName;
                    contact.phoneNumber = phoneNumber;
                    contact.email = email;
                    return contact;

                }
            }
        }
        catch (SQLException e){

            e.printStackTrace();

        }

        return null;
    }

    @Override
    public int delete(String phoneNumber) {

        int affectedRows = 0;

        try(Connection con = DriverManager.getConnection(url,username,password)){

            String sql = "DELETE FROM contact WHERE phoneNumber = ?";

            try(PreparedStatement ps = con.prepareStatement(sql)){

                ps.setString(1, phoneNumber);
                affectedRows = ps.executeUpdate();

            }
        }
        catch (SQLException e){

            e.printStackTrace();

        }

        return affectedRows;
    }

    @Override
    public List<Contact> showAll() {

        List<Contact> contactList = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(url,username,password)){

            String sql = "SELECT * FROM contact";

            try (PreparedStatement ps = con.prepareStatement(sql)){

                ResultSet rs = ps.executeQuery();

                while (rs.next()){

                    int id = rs.getInt("id");
                    String firstName = rs.getString("firstName");
                    String lastName = rs.getString("lastName");
                    String phoneNumber = rs.getString("phoneNumber");
                    String email = rs.getString("email");

                    Contact contact = new Contact();
                    contact.firstName = firstName;
                    contact.lastName = lastName;
                    contact.phoneNumber = phoneNumber;
                    contact.email = email;

                    contactList.add(contact);
                }
            }
        }
        catch (SQLException e){

            e.printStackTrace();

        }

        return contactList;
    }
}
