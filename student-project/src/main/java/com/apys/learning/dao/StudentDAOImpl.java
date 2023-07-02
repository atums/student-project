package com.apys.learning.dao;

import com.apys.learning.config.Config;
import com.apys.learning.domain.*;
import com.apys.learning.exception.DAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StudentDAOImpl implements StudentOrderDAO{

    private static final Logger logger = LoggerFactory.getLogger(StudentDAOImpl.class);
    public static final String INSERT_ORDER =
            "INSERT INTO jc_student_order(" +
                    " student_order_status, student_order_date, " +
                    "h_sur_name, h_given_name, h_patronymic, h_date_of_birth, " +
                    "h_passport_seria, h_passport_number, h_passport_date, " +
                    "h_passport_offece_id, h_post_index, h_street_code, h_building, " +
                    "h_extension, h_apartment, h_university_id, h_student_number, " +
                    "w_sur_name, w_given_name, w_patronymic, w_date_of_birth, w_passport_seria, " +
                    "w_passport_number, w_passport_date, w_passport_offece_id, w_post_index, w_street_code, " +
                    "w_building, w_extension, w_apartment, w_university_id, w_student_number, " +
                    "certificate_id, register_office_id, marriage_date)" +
                    "VALUES (?, ?, ?, ?, " +
                    "?, ?, ?, ?, ?, " +
                    "?, ?, ?, ?, ?, " +
                    "?, ?, ?, ?, ?, " +
                    "?, ?, ?, ?, ?, " +
                    "?, ?, ?, ?, ?, " +
                    "?, ?, ?, ?, ?, ?);";
    
    private static final String INSERT_CHILD =
            "INSERT INTO jc_student_child(" +
                    "student_order_id, c_sur_name, c_given_name, c_patronymic, " +
                    "c_date_of_birth, c_certificate_number, c_certificate_date, c_register_offece_id, " +
                    "c_post_index, c_street_code, c_building, c_extension, c_apartment)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    private static final String SELECT_STUDENT_ORDERS =
            "SELECT so.*, ro.r_office_area_id, ro.r_office_name, " +
                    "      po_h.p_office_area_id as h_p_office_area_id, " +
                    "      po_h.p_office_name as h_p_office_name, " +
                    "      po_w.p_office_area_id as w_p_office_area_id, " +
                    "      po_w.p_office_name as w_p_office_name " +
                    "FROM jc_student_order so " +
                    "INNER JOIN jc_register_office ro ON ro.r_office_id = so.register_office_id " +
                    "INNER JOIN jc_passport_office po_h ON po_h.p_office_id = so.h_passport_offece_id " +
                    "INNER JOIN jc_passport_office po_w ON po_w.p_office_id = so.w_passport_offece_id " +
                    "WHERE student_order_status = ? " +
                    "ORDER BY student_order_date " +
                    "LIMIT ?;";

    private static final String SELECT_CHILD =
            "SELECT sc.*, ro.r_office_area_id, ro.r_office_name " +
                    "FROM jc_student_child sc " +
                    "JOIN jc_register_office ro ON sc.c_register_offece_id = ro.r_office_id " +
                    "WHERE student_order_id IN ";

    private static final String SELECT_STUDENT_ORDERS_FULL =
            "SELECT so.*, ro.r_office_area_id, ro.r_office_name, " +
                    "po_h.p_office_area_id as h_p_office_area_id, " +
                    "po_h.p_office_name as h_p_office_name, " +
                    "po_w.p_office_area_id as w_p_office_area_id, " +
                    "po_w.p_office_name as w_p_office_name, " +
                    "soc.*, ro_c.r_office_area_id, ro_c.r_office_name " +
                    "FROM jc_student_order so " +
                    "INNER JOIN jc_register_office ro ON ro.r_office_id = so.register_office_id " +
                    "INNER JOIN jc_passport_office po_h ON po_h.p_office_id = so.h_passport_offece_id " +
                    "INNER JOIN jc_passport_office po_w ON po_w.p_office_id = so.w_passport_offece_id " +
                    "INNER JOIN jc_student_child soc ON soc.student_order_id = so.student_order_id " +
                    "INNER JOIN jc_register_office ro_c ON ro_c.r_office_id = soc.c_register_offece_id " +
                    "WHERE student_order_status = ? " +
                    "ORDER BY so.student_order_id " +
                    "LIMIT ? ;";

    private Connection getConnection() throws SQLException {
        return ConnectionBuilder.getConnection();
    }
    @Override
    public long saveStudentOrder(StudentOrder studentOrder) throws DAOException {
        long result = -1L;

        logger.debug("studentOrder {}", studentOrder);

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_ORDER,
                     new String[]{"student_order_id"})) {

            connection.setAutoCommit(false);

            try {
                statement.setInt(1, StudentOrderStatus.START.ordinal());
                statement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
                //Husband & Wife
                setParamsAdult(statement, 3, studentOrder.getHusband());
                setParamsAdult(statement, 18, studentOrder.getWife());
                //Marriage
                statement.setString(33, studentOrder.getMarriageCertificate());
                statement.setLong(34, studentOrder.getMarriageOffice().getOfficeId());
                statement.setDate(35, Date.valueOf(studentOrder.getMarriageDate()));

                statement.executeUpdate();

                ResultSet resultSet = statement.getGeneratedKeys();
                if(resultSet.next()) {
                    result = resultSet.getLong(1);
                }
                saveChildren(connection, studentOrder, result);

                connection.commit();
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                connection.rollback();
                throw e;
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage(), ex);
            throw new DAOException(ex);
        }
        return result;
    }

    @Override
    public List<StudentOrder> getStudentOrders() throws DAOException {
        return getStudentOrderOneSelect();
//        return getStudentOrderTwoSelect();
    }
    private List<StudentOrder> getStudentOrderOneSelect() throws DAOException {
        List<StudentOrder> result = new LinkedList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_STUDENT_ORDERS_FULL)) {

            Map<Long, StudentOrder> maps = new HashMap<>();

            int limit = Integer.parseInt(Config.getProperties(Config.DB_LIMIT));

            statement.setInt(1, StudentOrderStatus.START.ordinal());
            statement.setInt(2, limit);

            ResultSet rs = statement.executeQuery();
            int count = 0;
            while(rs.next()) {
                Long studentOrderId = rs.getLong("student_order_id");

                if(!maps.containsKey(studentOrderId)) {
                    StudentOrder studentOrder = getFullStudentOrder(rs);

                    result.add(studentOrder);
                    maps.put(studentOrderId, studentOrder);
                }
                StudentOrder studentOrderBe = maps.get(studentOrderId);
                studentOrderBe.addChild(fillChild(rs));
                count++;

                if(count >= limit) {
                    result.remove(result.size() - 1);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DAOException(e);
        }
        return result;
    }

    private List<StudentOrder> getStudentOrderTwoSelect() throws DAOException {
        List<StudentOrder> result = new LinkedList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_STUDENT_ORDERS)) {

            statement.setInt(1, StudentOrderStatus.START.ordinal());
            statement.setInt(2, Integer.parseInt(Config.getProperties(Config.DB_LIMIT)));

            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                StudentOrder studentOrder = getFullStudentOrder(rs);

                result.add(studentOrder);
            }
            findChildren(connection, result);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DAOException(e);
        }
        return result;
    }

    private StudentOrder getFullStudentOrder(ResultSet rs) throws SQLException {
        StudentOrder studentOrder = new StudentOrder();
        fillStudentOrder(rs, studentOrder);
        fillMarriage(rs, studentOrder);

        studentOrder.setHusband(fillAdult(rs, "h_"));
        studentOrder.setWife(fillAdult(rs, "w_"));
        return studentOrder;
    }

    private void findChildren(Connection connection, List<StudentOrder> result) throws SQLException {
        String cl = "(" + result
                .stream().map(studentOrder -> String.valueOf(studentOrder.getStudentOrderId()))
                .collect(Collectors.joining(",")) + ")";

        Map<Long, StudentOrder> soMap = result
                .stream()
                .collect(Collectors.toMap(so -> so.getStudentOrderId(), so -> so));

        try(PreparedStatement statement = connection.prepareStatement(SELECT_CHILD + cl)) {
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                Child child = fillChild(rs);
                StudentOrder so = soMap.get(rs.getLong("student_order_id"));
                so.addChild(child);
            }
        }
    }

    private Child fillChild(ResultSet rs) throws SQLException {
        String surName = rs.getString("c_sur_name");
        String givenName = rs.getString("c_given_name");
        String patronymic = rs.getString("c_patronymic");
        LocalDate dateOfBirth = rs.getDate("c_date_of_birth").toLocalDate();

        Child child = new Child(surName, givenName, patronymic, dateOfBirth);

        child.setCertificateNumber(rs.getString("c_certificate_number"));
        child.setIssueDate(rs.getDate("c_certificate_date").toLocalDate());

        RegisterOffice registerOffice = new RegisterOffice(rs.getLong("c_register_offece_id"),
                rs.getString("r_office_area_id"), rs.getString("r_office_name"));

        child.setIssueDepartment(registerOffice);

        Address address = new Address();
        Street street = new Street(rs.getLong("c_street_code"), "");
        address.setPostCode(rs.getString("c_post_index"));
        address.setStreet(street);
        address.setBuilding(rs.getString("c_building"));
        address.setExtension(rs.getString("c_extension"));
        address.setApartment(rs.getString("c_apartment"));
        child.setAddress(address);

        return child;
    }

    private Adult fillAdult(ResultSet rs, String prefix) throws SQLException {
        Adult adult = new Adult();
        adult.setSurName(rs.getString(prefix + "sur_name"));
        adult.setGivenName(rs.getString(prefix + "given_name"));
        adult.setPatronymic(rs.getString(prefix + "patronymic"));
        adult.setDateOfBirth(rs.getDate(prefix + "date_of_birth").toLocalDate());
        adult.setPassportSeria(rs.getString(prefix + "passport_seria"));
        adult.setPassportNumber(rs.getString(prefix + "passport_number"));
        adult.setIssueDate(rs.getDate(prefix + "passport_date").toLocalDate());

        PassportOffice passportOffice = new PassportOffice(rs.getLong(prefix + "passport_offece_id"),
                rs.getString(prefix + "p_office_area_id"),
                rs.getString(prefix + "p_office_name"));

        adult.setIssueDepartment(passportOffice);

        Address address = new Address();
        Street street = new Street(rs.getLong(prefix + "street_code"), "");
        address.setPostCode(rs.getString(prefix + "post_index"));
        address.setStreet(street);
        address.setBuilding(rs.getString(prefix + "building"));
        address.setExtension(rs.getString(prefix + "extension"));
        address.setApartment(rs.getString(prefix + "apartment"));
        adult.setAddress(address);

        University university = new University(rs.getLong(prefix + "university_id"), "");
        adult.setUniversity(university);

        adult.setStudentId(rs.getString(prefix + "student_number"));

        return adult;

    }

    private void fillStudentOrder(ResultSet rs, StudentOrder studentOrder) throws SQLException {
        studentOrder.setStudentOrderId(rs.getLong("student_order_id"));
        studentOrder.setStudentOrderDate(rs.getTimestamp("student_order_date").toLocalDateTime());
        studentOrder.setStudentOrderStatus(StudentOrderStatus.fromValue(rs.getInt("student_order_status")));
    }

    private void fillMarriage(ResultSet rs, StudentOrder studentOrder) throws SQLException {
        studentOrder.setMarriageCertificate(rs.getString("certificate_id"));
        studentOrder.setMarriageDate(rs.getDate("marriage_date").toLocalDate());

        RegisterOffice registerOffice = new RegisterOffice(rs.getLong("register_office_id"),
                rs.getString("r_office_area_id"), rs.getString("r_office_name"));
        studentOrder.setMarriageOffice(registerOffice);
    }

    private void saveChildren(Connection connection, StudentOrder studentOrder, long result) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(INSERT_CHILD)) {
            int count = 0;
            for (Child child : studentOrder.getChildren()) {
                statement.setLong(1, result);
                setParamsChild(statement, child);
                statement.addBatch();
                count++;
                if(count > 4) {
                    statement.executeBatch();
                    count = 0;
                }
            }
            if(count > 0) {
                statement.executeBatch();
            }
        }
    }

    private void setParamsAdult(PreparedStatement statement, int start, Adult adult) throws SQLException {
        setParamForPerson(statement, start, adult);
        statement.setString(start + 4, adult.getPassportSeria());
        statement.setString(start + 5, adult.getPassportNumber());
        statement.setDate(start + 6, Date.valueOf(adult.getIssueDate()));
        statement.setLong(start + 7, adult.getIssueDepartment().getOfficeId());
        setParamForAddress(statement, start + 8, adult);
        statement.setLong(start + 13, adult.getUniversity().getUniversityId());
        statement.setString(start + 14, adult.getStudentId());
    }

    private void setParamsChild(PreparedStatement statement, Child child) throws SQLException {
        setParamForPerson(statement, 2, child);
        statement.setString(6, child.getCertificateNumber());
        statement.setDate(7, Date.valueOf(child.getIssueDate()));
        statement.setLong(8, child.getIssueDepartment().getOfficeId());
        setParamForAddress(statement, 9, child);
    }

    private void setParamForPerson(PreparedStatement statement, int start, Person person) throws SQLException {
        statement.setString(start, person.getSurName());
        statement.setString(start + 1, person.getGivenName());
        statement.setString(start + 2, person.getPatronymic());
        statement.setDate(start + 3, Date.valueOf(person.getDateOfBirth()));
    }

    private void setParamForAddress(PreparedStatement statement, int start, Person person) throws SQLException {
        Address h_address = person.getAddress();
        statement.setString(start, h_address.getPostCode());
        statement.setLong(start + 1, h_address.getStreet().getStreet_code());
        statement.setString(start + 2, h_address.getBuilding());
        statement.setString(start + 3, h_address.getExtension());
        statement.setString(start + 4, h_address.getApartment());
    }
}
