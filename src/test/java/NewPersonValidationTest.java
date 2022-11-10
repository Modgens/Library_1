import dao.implementation.PersonalInfoDaoImpl;
import entity.PersonalInfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import service.Validator;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class NewPersonValidationTest {
    private final Validator validator = Validator.getInstance();

    @Mock
    PersonalInfoDaoImpl personalInfoDao;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(personalInfoDao.getId(anyString())).thenReturn(0L);
        when(personalInfoDao.getId("zero")).thenReturn(1L);
    }

    @Test
    public void NewPersonNullTest(){
        boolean isThrow=false;
        try {
            validator.newPersonValidate(null, null, null, null, null, null);
        } catch (NullPointerException e){
            isThrow=true;
        }
        Assert.assertTrue(isThrow);
    }
    @Test
    public void NewPersonEmptyTest(){
        Assert.assertEquals("Incorrect first name | Невірне ім'я", validator.newPersonValidate(personalInfoDao, new PersonalInfo(), "", "", "", ""));
    }
    @Test
    public void NewPersonF_L_NameOrLoginTest(){
        Assert.assertEquals("", validator.newPersonValidate(personalInfoDao, new PersonalInfo(), "Taras", "Shevchenko", "user1", "ZXC123zxc@"));
        Assert.assertEquals("This login is already in used | Цей логін вже зареєстрований", validator.newPersonValidate(personalInfoDao, new PersonalInfo(), "Taras", "Shevchenko", "zero", "ZXC123zxc@"));//login=zero =>is already in use
        Assert.assertEquals("Incorrect first name | Невірне ім'я", validator.newPersonValidate(personalInfoDao, new PersonalInfo(), "123", "Shevchenko", "user1", "ZXC123zxc@"));
        Assert.assertEquals("Incorrect last name | Невірна фамилія", validator.newPersonValidate(personalInfoDao, new PersonalInfo(), "Taras", "123", "user1", "ZXC123zxc@"));
        Assert.assertEquals("", validator.newPersonValidate(personalInfoDao, new PersonalInfo(), "Taras", "Shevchenko", "djjsfjflljfsljfsdklsfj121232@@#$%", "ZXC123zxc@"));//login hasn't validation
    }
    @Test
    public void NewPersonPasswordValidationTest(){
        Assert.assertTrue(validator.newPersonValidate(personalInfoDao, new PersonalInfo(), "Taras", "Shevchenko", "user1", "111").length()>20);
        Assert.assertTrue(validator.newPersonValidate(personalInfoDao, new PersonalInfo(), "Taras", "Shevchenko", "user1", "q1@QW").length()>20);
        Assert.assertTrue(validator.newPersonValidate(personalInfoDao, new PersonalInfo(), "Taras", "Shevchenko", "user1", "qqqqQQQQQQ@@@12313131313131313113").length()>20);
        Assert.assertEquals("", validator.newPersonValidate(personalInfoDao, new PersonalInfo(), "Taras", "Shevchenko", "user1", "q1@QWQQQ"));
    }
}
