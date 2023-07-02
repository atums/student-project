package com.apys.learning.dao;

import com.apys.learning.domain.CountryArea;
import com.apys.learning.domain.PassportOffice;
import com.apys.learning.domain.RegisterOffice;
import com.apys.learning.domain.Street;
import com.apys.learning.exception.DAOException;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

public class DictionaryDAOImplTest {

    private static final Logger logger = LoggerFactory.getLogger(DictionaryDAOImplTest.class);

    public DictionaryDAOImplTest() throws DAOException {
    }

    @BeforeClass // Start only one times
    public static void startUp() throws Exception {
        System.out.println("Start Up");
        DBInit.startIp();
    }

//    @AfterClass // Start before all test complite
//    public static void startDown() {
//        System.out.println("Start Down");
//    }
//    @Before //Start before each test
//    public void startTest() {
//        System.out.println("Start Test");
//    }
//    @After // Start before each test
//    public void stopTest() {
//        System.out.println("Test Stop");
//    }

    // TODO Чистый Maven с этим тестом не собирает
    @Test
    @Ignore
    public void testStreet() throws DAOException {
        LocalDateTime ldt = LocalDateTime.now();
        logger.info("Test Logger {} ", ldt);
//        logger.debug("Test Logger");
        List<Street> d = new DictionaryDAOImpl().findStreet("Про");
        Assert.assertTrue(d.size() == 2);
    }
    @Test
//    @Ignore
    public void testPassportOffice() throws DAOException {
        List<PassportOffice> po = new DictionaryDAOImpl().findPassportOffices("010020000000");
        Assert.assertTrue(po.size() == 2);
    }
    @Test
    public void testRegisterOffice() throws DAOException {
        List<RegisterOffice> ro = new DictionaryDAOImpl().findRegisterOffices("010010000000");
        Assert.assertTrue(ro.size() == 2);
    }
    @Test
    public void testCountryArea0() throws DAOException {
        List<CountryArea> ca0 = new DictionaryDAOImpl().findAreas("");
        Assert.assertTrue(ca0.size() == 2);
    }
    @Test
    public void testCountryArea1() throws DAOException {
        List<CountryArea> ca1 = new DictionaryDAOImpl().findAreas("020000000000");
        Assert.assertTrue(ca1.size() == 2);
    }
    @Test
    public void testCountryArea2() throws DAOException {
        List<CountryArea> ca2 = new DictionaryDAOImpl().findAreas("020010000000");
        Assert.assertTrue(ca2.size() == 2);
    }
    @Test
    public void testCountryArea3() throws DAOException {
        List<CountryArea> ca3 = new DictionaryDAOImpl().findAreas("020010010000");
        Assert.assertTrue(ca3.size() == 2);
    }
}