
import com.lomtom.test.Application;
import com.lomtom.test.service.MyServiceTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author lomtom
 * @date 2021/8/16 10:34
 **/
@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class ExcelTest {

    @Autowired
    private MyServiceTest myServiceTest;

    @Test
    public void test() throws Exception {
        myServiceTest.testTransactional();
    }
}
