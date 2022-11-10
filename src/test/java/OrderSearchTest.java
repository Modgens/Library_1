import dao.implementation.PersonalInfoDaoImpl;
import dao.implementation.UserDaoImpl;
import dao.implementation.UserOrdersDaoImpl;
import entity.PersonalInfo;
import entity.User;
import entity.UserOrders;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import service.Searching;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class OrderSearchTest {
    private final Searching searching = Searching.getInstance();

    private ArrayList<UserOrders> testList;

    @Mock
    private UserOrdersDaoImpl userOrdersDao;
    @Mock
    private UserDaoImpl userDao;
    @Mock
    private PersonalInfoDaoImpl personalInfoDao;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        testList = new ArrayList<>();
        testList.add(new UserOrders(0L, 0L, 0L, null, null, null, null));
        testList.add(new UserOrders(1L, 1L, 1L, null, null, null, null));
        testList.add(new UserOrders(2L, 2L, 2L, null, null, null, null));

        when(userOrdersDao.getAllForReadingRoom()).thenReturn(testList);

        when(userDao.get(0L)).thenReturn(new User(0L, 0L, "",""));
        when(userDao.get(1L)).thenReturn(new User(1L, 1L, "",""));
        when(userDao.get(2L)).thenReturn(new User(2L, 2L, "",""));

        when(personalInfoDao.get(0L)).thenReturn(new PersonalInfo(0L, "user1", "", "Taras", "Shevchenko"));
        when(personalInfoDao.get(1L)).thenReturn(new PersonalInfo(1L, "user2", "", "Stepan", "Chornovil"));
        when(personalInfoDao.get(2L)).thenReturn(new PersonalInfo(2L, "user3", "", "Vitaly", "Klitschko"));
    }

    @Test
    public void mockTest(){
        when(userDao.get(anyLong())).thenReturn(new User(0L, 0L, "qwert@gmail.com", ""));
        Assert.assertEquals("qwert@gmail.com", userDao.get(0).getEmail());
    }

    @Test
    public void OrderSearchNullTest(){
        boolean isThrow=false;
        try {
            searching.orderSearch(null, null, null, null, null, null, null);
        } catch (NullPointerException e){
            isThrow=true;
        }
        Assert.assertTrue(isThrow);
    }
    @Test
    public void OrderSearchPageEmptyOrIncorrectTest(){
        boolean isThrow=false;
        try {
            searching.orderSearch("", new UserOrdersDaoImpl(), new UserDaoImpl(), new PersonalInfoDaoImpl(), "", "", "");
        } catch (IllegalArgumentException e){
            isThrow=true;
        }
        Assert.assertTrue(isThrow);
        isThrow=false;
        try {
            searching.orderSearch("ffgfjkfgj", new UserOrdersDaoImpl(), new UserDaoImpl(), new PersonalInfoDaoImpl(), "", "", "");
        } catch (IllegalArgumentException e){
            isThrow=true;
        }
        Assert.assertTrue(isThrow);
    }
    @Test
    public void OrderSearchEmptyTest(){
        HashMap<String, List<UserOrders>> result = searching.orderSearch("/reading_room.jsp", userOrdersDao, userDao, personalInfoDao, "", "", "");
        Assert.assertEquals(testList, result.values().stream().findFirst().get());
    }
    @Test
    public void OrderSearchFirstNameTestTrue(){
        List<UserOrders> result = searching.orderSearch("/reading_room.jsp", userOrdersDao, userDao, personalInfoDao, "Stepan", "", "").values().stream().findFirst().get();
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(1, result.get(0).getId());//order_id=1 => ordered by Stepan Chornovil
    }
    @Test
    public void OrderSearchFirstNameTestFalse(){
        List<UserOrders> result = searching.orderSearch("/reading_room.jsp", userOrdersDao, userDao, personalInfoDao, "dskdsjjs", "", "").values().stream().findFirst().get();
        Assert.assertEquals(0, result.size());
    }
    @Test
    public void OrderSearchAllTest(){
        List<UserOrders> result = searching.orderSearch("/reading_room.jsp", userOrdersDao, userDao, personalInfoDao, "Vitaly", "Klitschko", "user3").values().stream().findFirst().get();
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(2, result.get(0).getId());//order_id=2 => Vitaly Klitschko
    }

}
