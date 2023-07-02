package com.apys.learning.dao;

import com.apys.learning.domain.StudentOrder;
import com.apys.learning.exception.DAOException;

import java.util.List;

public interface StudentOrderDAO {
    long saveStudentOrder(StudentOrder studentOrder) throws DAOException;
    List<StudentOrder> getStudentOrders() throws DAOException;
}
