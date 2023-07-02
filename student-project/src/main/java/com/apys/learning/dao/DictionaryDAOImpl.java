package com.apys.learning.dao;

import com.apys.learning.config.Config;
import com.apys.learning.domain.CountryArea;
import com.apys.learning.domain.PassportOffice;
import com.apys.learning.domain.RegisterOffice;
import com.apys.learning.domain.Street;
import com.apys.learning.exception.DAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DictionaryDAOImpl implements DictionaryDAO {

    private static final Logger logger = LoggerFactory.getLogger(DictionaryDAOImpl.class);
    private static final String GET_STREET = "SELECT street_code, street_name " +
            "FROM jc_street WHERE UPPER(street_name) " +
            "LIKE UPPER(?)";

    private static final String GET_PASSPORT = "SELECT * " +
            "FROM jc_passport_office WHERE p_office_area_id = ?";

    private static final String GET_REGISTER = "SELECT * " +
            "FROM jc_register_office WHERE r_office_area_id = ?";

    private static final String GET_AREA = "SELECT * " +
            "FROM jc_country_struct WHERE  area_id like ? and area_id <> ?";

    private Connection getConnection() throws SQLException {
        return ConnectionBuilder.getConnection();
    }

    public List<Street> findStreet(String pattern) throws DAOException {
        List<Street> result = new LinkedList<>();


        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_STREET)) {

            statement.setString(1, "%" + pattern + "%");

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
               Street street = new Street(rs.getLong("street_code"),
                       rs.getString("street_name"));
               result.add(street);

            }
            return result;
        } catch (SQLException ex) {
            logger.error(ex.getMessage(), ex);
            throw new DAOException(ex);
        }
    }

    @Override
    public List<PassportOffice> findPassportOffices(String areaId) throws DAOException {
        List<PassportOffice> result = new LinkedList<>();

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_PASSPORT)) {

            statement.setString(1, areaId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                PassportOffice str = new PassportOffice(
                        rs.getLong("p_office_id"),
                        rs.getString("p_office_area_id"),
                        rs.getString("p_office_name"));
                result.add(str);
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage(), ex);
            throw new DAOException(ex);
        }

        return result;
    }

    @Override
    public List<RegisterOffice> findRegisterOffices(String areaId) throws DAOException {
        List<RegisterOffice> result = new LinkedList<>();

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_REGISTER)) {

            statement.setString(1, areaId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                RegisterOffice str = new RegisterOffice(
                        rs.getLong("r_office_id"),
                        rs.getString("r_office_area_id"),
                        rs.getString("r_office_name"));
                result.add(str);
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage(), ex);
            throw new DAOException(ex);
        }

        return result;
    }

    @Override
    public List<CountryArea> findAreas(String areaId) throws DAOException {
        List<CountryArea> result = new LinkedList<>();

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_AREA)) {

            statement.setString(1, buildParam(areaId));
            statement.setString(2, areaId == null ? "" : areaId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                CountryArea ca = new CountryArea(
                        rs.getString("area_id"),
                        rs.getString("area_name"));
                result.add(ca);
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage(), ex);
            throw new DAOException(ex);
        }

        return result;
    }

    private String buildParam(String areaId) throws SQLException {
        if (areaId == null || areaId.trim().isEmpty()) {
            return "__0000000000";
        } else if (areaId.endsWith("0000000000")) {
            return areaId.substring(0, 2) + "___0000000";
        } else if (areaId.endsWith("0000000")) {
            return areaId.substring(0, 5) + "___0000";
        } else if (areaId.endsWith("0000")) {
            return areaId.substring(0, 8) + "____";
        }
        throw new SQLException("Invalid parameter 'areaId':" + areaId);
    }

}
