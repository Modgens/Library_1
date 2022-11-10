import entity.PersonalInfo;
import entity.megaEntity.MegaUser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import service.Searching;
import java.util.ArrayList;
import java.util.List;

public class UserSearchTest {
    private final Searching searching = Searching.getInstance();
    List<MegaUser> testList;

    @Before
    public void setUp(){
        testList = new ArrayList<>();
        PersonalInfo personalInfo1 = new PersonalInfo();
        personalInfo1.setLogin("MALI");
        personalInfo1.setFirstName("Eugene");
        personalInfo1.setLastName("Brah");

        PersonalInfo personalInfo2 = new PersonalInfo();
        personalInfo2.setLogin("Khimik");
        personalInfo2.setFirstName("David");
        personalInfo2.setLastName("Kasatkin");

        PersonalInfo personalInfo3 = new PersonalInfo();
        personalInfo3.setLogin("Machach");
        personalInfo3.setFirstName("Yaroslav");
        personalInfo3.setLastName("Bryginets");

        PersonalInfo personalInfo4 = new PersonalInfo();
        personalInfo4.setLogin("Tavr");
        personalInfo4.setFirstName("Bohdan");
        personalInfo4.setLastName("Krotevich");

        MegaUser user1 = new MegaUser();
        MegaUser user2 = new MegaUser();
        MegaUser user3 = new MegaUser();
        MegaUser user4 = new MegaUser();
        user1.setPersonalInfo(personalInfo1);
        user2.setPersonalInfo(personalInfo2);
        user3.setPersonalInfo(personalInfo3);
        user4.setPersonalInfo(personalInfo4);

        testList.add(user1);
        testList.add(user2);
        testList.add(user3);
        testList.add(user4);
    }
    @Test
    public void testUserSearchingNull(){
        boolean isThrow = false;
        try {
            searching.userSearch(null,null,null, null);
        } catch (NullPointerException e){
            isThrow = true;
        }
        Assert.assertTrue(isThrow);
    }
    @Test
    public void testUserSearchingEmpty(){
        Assert.assertEquals(testList, searching.userSearch(new ArrayList<>(testList),"","", ""));
    }
    @Test
    public void testUserSearchingFirstNameTrue(){
        List<MegaUser> listAfterAll = searching.userSearch(new ArrayList<>(testList),"Eugene","", "");
        Assert.assertEquals(testList.get(0), listAfterAll.get(0));
        Assert.assertEquals(1, listAfterAll.size());
    }
    @Test
    public void testUserSearchingFirstNameFalse(){
        List<MegaUser> listAfterAll = searching.userSearch(new ArrayList<>(testList),"fhjjsdjdkdjfk","", "");
        Assert.assertEquals(0, listAfterAll.size());
    }
    @Test
    public void testUserSearchingLastNameTrue(){
        List<MegaUser> listAfterAll = searching.userSearch(new ArrayList<>(testList),"","Bryginets", "");
        Assert.assertEquals(testList.get(2), listAfterAll.get(0));
        Assert.assertEquals(1, listAfterAll.size());
    }
    @Test
    public void testUserSearchingLastNameFalse(){
        List<MegaUser> listAfterAll = searching.userSearch(new ArrayList<>(testList),"","jfdjsjfjlkdfs", "");
        Assert.assertEquals(0, listAfterAll.size());
    }
    @Test
    public void testUserSearchingLoginTrue(){
        List<MegaUser> listAfterAll = searching.userSearch(new ArrayList<>(testList),"","", "Tavr");
        Assert.assertEquals(testList.get(3), listAfterAll.get(0));
        Assert.assertEquals(1, listAfterAll.size());
    }
    @Test
    public void testUserSearchingLoginFalse(){
        List<MegaUser> listAfterAll = searching.userSearch(new ArrayList<>(testList),"","", "lnddfsknsfd");
        Assert.assertEquals(0, listAfterAll.size());
    }
}
