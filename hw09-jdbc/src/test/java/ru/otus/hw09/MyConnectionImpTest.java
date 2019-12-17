//package ru.otus.hw09;
//
//import org.junit.Test;
//import org.slf4j.LoggerFactory;
//import ru.otus.hw09.sessionmanager.SessionManagerImp;
//
//import java.sql.Connection;
//
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertThrows;
//
//public class MyConnectionImpTest {
//
//    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(MyConnectionImpTest.class);
//
//
//    @Test
//    public void getConnection1(){
//        SessionManagerImp connectionImp = new SessionManagerImp();
//        Connection connection = connectionImp.getConnection();
//        assertNotNull(connection);
//    }
//
//    @Test
//    public void getConnection2(){
//        try {
//            new SessionManagerImp("",true);
//        } catch (Exception e) {
//            logger.info(e.getMessage());
//        }
//
//        assertThrows(IllegalArgumentException.class, () -> new SessionManagerImp("",true));
//    }
//
//    @Test
//    public void getConnection3(){
//
//        SessionManagerImp connectionImp = new SessionManagerImp("jdbc:h2:mem:",true);
//        Connection connection = connectionImp.getConnection();
//        assertNotNull(connection);
//    }
//
//    @Test
//    public void getConnection4(){
//        try {
//            new SessionManagerImp("123",true);
//        } catch (Exception e) {
//            logger.info(e.getMessage());
//        }
//
//        assertThrows(IllegalArgumentException.class, () -> new SessionManagerImp("",true));
//    }
//}
