package com.apys.learning;

import com.apys.learning.dao.StudentDAOImpl;
import com.apys.learning.domain.*;
import com.apys.learning.domain.register.AnswerCityRegister;
import com.apys.learning.exception.DAOException;
import com.apys.learning.mail.MailSender;
import com.apys.learning.validator.ChildrenValidator;
import com.apys.learning.validator.CityRegisterValidator;
import com.apys.learning.validator.StudentValidator;
import com.apys.learning.validator.WeddingValidator;

import java.util.LinkedList;
import java.util.List;

public class StudentOrderValidator {
    private CityRegisterValidator cityRegisterVal;
    private WeddingValidator weddingVal;
    private ChildrenValidator childrenVal;
    private StudentValidator studentVal;
    private MailSender mailSender;

    public StudentOrderValidator() {
        cityRegisterVal = new CityRegisterValidator();
        weddingVal = new WeddingValidator();
        childrenVal = new ChildrenValidator();
        studentVal = new StudentValidator();
        mailSender = new MailSender();
    }

    public static void main(String[] args) {
        StudentOrderValidator studentOrderValidator = new StudentOrderValidator();
        studentOrderValidator.checkAll();
    }
    public void checkAll() {
        try {
            List<StudentOrder> orderList = readStudentOrders();

            for(StudentOrder studentOrder : orderList) {
                checkOneOrder(studentOrder);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<StudentOrder> readStudentOrders() throws DAOException {
//        List<StudentOrder> orderList = new LinkedList<>();
//
//        for(int i = 0; i < 5; i++) {
//            StudentOrder so = SaveStudentOrder.buildStudentOrder(i);
//            orderList.add(so);
//        }
        return new StudentDAOImpl().getStudentOrders();
    }
    public void checkOneOrder(StudentOrder so) {
        AnswerCityRegister cityAnswer = checkCityRegister(so);
//        AnswerWedding wedAnswer = checkWedding(so);
//        AnswerChildren childAnswer = checkChildren(so);
//        AnswerStudent studentAnswer = checkStudent(so);
//        sendMail(so);
    }
    public AnswerCityRegister checkCityRegister(StudentOrder studentOrder) {
        return cityRegisterVal.checkCityRegister(studentOrder);
    }
    public AnswerWedding checkWedding(StudentOrder studentOrder) {
        return weddingVal.checkWedding(studentOrder);
    }
    public AnswerChildren checkChildren(StudentOrder studentOrder) {
        return childrenVal.checkChildren(studentOrder);
    }
    public AnswerStudent checkStudent(StudentOrder studentOrder) {
        return studentVal.checkStudent(studentOrder);
    }
    public void sendMail(StudentOrder so) {
        new MailSender().sendMail(so);
    }
}
