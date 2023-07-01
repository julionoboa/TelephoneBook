package dao.impl;

import dao.ContactDAO;
import model.Contact;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ContactDAOImplementation implements ContactDAO {
    String url = "jdbc:mysql://localhost:3306/agenda";
    String username = "root";
    String password = "root";

    @Override
    public void create(Contact contact) {

        try (Connection con = DriverManager.getConnection(url, username, password)) {

            String sql = "INSERT INTO contact (firstName, lastName, phoneNumber, email) VALUES (?, ?, ?, ?)";

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, contact.firstName);
                ps.setString(2, contact.lastName);
                ps.setString(3, contact.phoneNumber);
                ps.setString(4, contact.email);

                ps.executeUpdate();

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }
    }

    @Override
    public List<Contact> getByEmail(String email) {

        List<Contact> contactList = new ArrayList<>();

        ResultSet rs = null;

        try (Connection con = DriverManager.getConnection(url, username, password)) {

            String sql = "SELECT firstName, lastName, phoneNumber, email from contact where email like CONCAT('%', ?, '%')";

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, email);
                rs = ps.executeQuery();

                return mapResultSet(rs);
            }

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {
            try {
                if(rs != null) rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public List<Contact> getByFirstName(String firstName) {

        List<Contact> contactList = new ArrayList<>();

        ResultSet rs = null;

        try (Connection con = DriverManager.getConnection(url, username, password)) {

            String sql = "SELECT firstName, lastName, phoneNumber, email from contact where firstName like CONCAT('%', ?, '%')";

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, firstName);
                rs = ps.executeQuery();

                contactList = mapResultSet(rs);

            }
        } catch (SQLException e) {

            e.printStackTrace();

        }finally {
            try {
                if(rs != null) rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return contactList;
    }

    @Override
    public List<Contact> getByLastName(String lastName) {

        List<Contact> contactList = new ArrayList<>();

        ResultSet rs = null;

        try {
            Connection con = DriverManager.getConnection(url, username, password);

            String sql = "SELECT firstName, lastName, phoneNumber, email from contact where lastName like CONCAT ('%', ?,'%')";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, lastName);
            rs = ps.executeQuery();

            contactList = mapResultSet(rs);

        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new RuntimeException("Error obteniendo el apellido");
        } catch (SQLException s) {
            if (s instanceof SQLSyntaxErrorException) {

            }
            Logger.getAnonymousLogger().info("Prueba");
        } finally {
            try {
                if(rs != null) rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return contactList;
    }


    @Override
    public List<Contact> getByPhoneNumber(String phoneNumber) {

        List<Contact> contactList = new ArrayList<>();

        ResultSet rs = null;

        try (Connection con = DriverManager.getConnection(url, username, password)) {

            String sql = "SELECT firstName, lastName, phoneNumber, email from contact where phoneNumber like CONCAT('%', ?, '%')";

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, phoneNumber);
                rs = ps.executeQuery();

                contactList = mapResultSet(rs);

            }
        } catch (SQLException e) {

            e.printStackTrace();

        } finally {
            try {
                if(rs != null) rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return contactList;
    }

    @Override
    public int delete(String phoneNumber) {

        int affectedRows = 0;

        try (Connection con = DriverManager.getConnection(url, username, password)) {

            String sql = "DELETE FROM contact WHERE phoneNumber like CONCAT ('%', ?, '%')";

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, phoneNumber);
                affectedRows = ps.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {

            e.printStackTrace();

        }

        return affectedRows;
    }

    @Override
    public List<Contact> showAll() {

        List<Contact> contactList = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(url, username, password)) {

            String sql = "SELECT firstName, lastName, phoneNumber, email FROM contact";

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                ResultSet rs = ps.executeQuery();

                contactList = mapResultSet(rs);

                rs.close();
            }
        } catch (SQLException e) {

            e.printStackTrace();

        }

        return contactList;
    }

    private List<Contact> mapResultSet(ResultSet rs) throws SQLException {
        List<Contact> contactList = new ArrayList<>();
        while (rs.next()) {

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
        return contactList;
    }
}
