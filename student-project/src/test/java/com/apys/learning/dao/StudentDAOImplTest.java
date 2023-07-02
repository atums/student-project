package com.apys.learning.dao;

import com.apys.learning.domain.*;
import com.apys.learning.exception.DAOException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class StudentDAOImplTest {

    @BeforeClass // Start only one times
    public static void startUp() throws Exception {
        System.out.println("Start Up");
        DBInit.startIp();
    }

    @Test
    public void saveStudentOrder() throws DAOException {
        StudentOrder studentOrder = buildStudentOrder(10);
        long orderId = new StudentDAOImpl().saveStudentOrder(studentOrder);
    }

    @Test(expected =  DAOException.class)
    public void saveStudentOrderError() throws DAOException {
            StudentOrder studentOrder = buildStudentOrder(10);
            studentOrder.getHusband().setSurName(null);
            long orderId = new StudentDAOImpl().saveStudentOrder(studentOrder);
    }

    @Test
    public void getStudentOrders() throws DAOException {
        List<StudentOrder> orderList = new StudentDAOImpl().getStudentOrders();
//        Assert.assertTrue(orderList.size() == 1);
    }

    public StudentOrder buildStudentOrder(long id) {
        StudentOrder so = new StudentOrder();

        so.setStudentOrderId(id);
        so.setMarriageCertificate("" + (123456000 + id));
        so.setMarriageDate(LocalDate.of(2020, 12, 01));
        RegisterOffice ro = new RegisterOffice(1L, "", "");
        so.setMarriageOffice(ro);

        Street street = new Street(2L, "Greendale St");

        Address address = new Address("192168", street,
                "1313", "", "13");
        // Husband
        Adult husband = new Adult("Tums", "Aleksandr",
                " ", LocalDate.of(1978, 1, 27));
        husband.setPassportSeria("" + (4004));
        husband.setPassportNumber("" + (427593));
        husband.setIssueDate(LocalDate.of(2003, 6, 01));
        PassportOffice po1 = new PassportOffice(1L, "", "");
        husband.setIssueDepartment(po1);
        husband.setStudentId("" + (10000 + id));
        husband.setAddress(address);
        husband.setUniversity(new University(2L, ""));
        husband.setStudentId("HH12345");
        // Wife
        Adult wife = new Adult("Tums", "Marina",
                " ", LocalDate.of(1976, 4, 24));
        wife.setPassportSeria("" + (4002));
        wife.setPassportNumber("" + (123456));
        wife.setIssueDate(LocalDate.of(2022, 4, 1));
        PassportOffice po2 = new PassportOffice(2L, "", "");
        wife.setIssueDepartment(po2);
        wife.setStudentId("" + (20000 + id));
        wife.setAddress(address);
        wife.setUniversity(new University(1L, ""));
        wife.setStudentId("WW12345");
        // Child 1
        Child child1 = new Child("Tums", "Nika",
                " ", LocalDate.of(2020, 3, 5));
        child1.setCertificateNumber("" + (11121314));
        child1.setIssueDate(LocalDate.of(2020, 8, 1));
        RegisterOffice registerOffice = new RegisterOffice(2L, "", "");
        child1.setIssueDepartment(registerOffice);
        child1.setAddress(address);
        // Child 2
        Child child2 = new Child("Tums", "Max",
                " ", LocalDate.of(2023, 6, 7));
        child2.setCertificateNumber("" + (41312111));
        child2.setIssueDate(LocalDate.of(2023, 6, 8));
        RegisterOffice registerOffice2 = new RegisterOffice(2L, "", "");
        child2.setIssueDepartment(registerOffice2);
        child2.setAddress(address);

        so.setHusband(husband);
        so.setWife(wife);
        so.addChild(child1);
        so.addChild(child2);

        return so;
    }
}