package com.apys.learning;

import com.apys.learning.dao.DictionaryDAO;
import com.apys.learning.dao.DictionaryDAOImpl;
import com.apys.learning.dao.StudentDAOImpl;
import com.apys.learning.dao.StudentOrderDAO;
import com.apys.learning.domain.*;
import com.apys.learning.exception.DAOException;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class SaveStudentOrder {
    public static void main(String[] args) throws DAOException {

        StudentOrderDAO sDAO = new StudentDAOImpl();

        List<StudentOrder> studentOrderList = sDAO.getStudentOrders();
        for(StudentOrder so : studentOrderList) {
            System.out.println(so.getStudentOrderId());
        }
    }
    static long saveStudentOrder(StudentOrder studentOrder) {
        long answer = 199;
        System.out.println("SaveStudentOrder running: ");
        return answer;
    }

}
