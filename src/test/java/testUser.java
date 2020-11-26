import com.source.bean.Permission;
import com.source.bean.User;
import com.source.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Source
 * @Date: 2020/11/18/9:52
 * @Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-mybatis.xml")
public class testUser {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void findAllUser(){
        List<User> users = userMapper.selectAll();
        users.forEach(s -> System.out.println(s.toString()));
    }

    @Test
    public void findAllPermission(){
        List<Permission> zhangsan = userMapper.findPermissionByUsername("zhangsan");
        zhangsan.forEach(s -> System.out.println(s.toString()));
    }

    @Test
    public void findByName(){
        User zhangsan = userMapper.findByUsername("zhangsan");
        System.out.println(zhangsan.toString());
    }

    @Test
    public void updatePassword(){
        User user = new User();
        user.setUsername("ls");

        //哈希算法加盐
        PasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("123");
        user.setPassword(encode);
        userMapper.updatePassword(user);
    }
}
