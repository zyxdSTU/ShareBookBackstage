package dao;

import javabean.HBookStore;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HBookStoreMapper {
    public List<HBookStore> selectHBookStore(String phoneNumber);
    public void insertHBookStore(HBookStore hBookStore);
    public void deleteHBookStore(@Param("phoneNumber") String phoneNumber,
                                 @Param("isbnNumber") String isbnNumber);
}
