package dao;

import javabean.Account;
import org.apache.ibatis.annotations.Param;

public interface AccountMapper {
    public void insertAccount(Account account);
    public Account selectByPhoneNumber(String phoneNumber);
    public String verify(String phoneNumber);
    public void updateImage(@Param("image")String image, @Param("phoneNumber")String phoneNumber);
    public void update(Account account);
}
