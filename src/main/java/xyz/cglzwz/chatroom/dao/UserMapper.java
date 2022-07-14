package xyz.cglzwz.chatroom.dao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import xyz.cglzwz.chatroom.domain.SysUser;

@Repository
public interface UserMapper {
    /**
     * @param username
     * @return
     */
    public SysUser findByUsername(@Param("username") String username);

    /**
     * @param sysUser
     */
    public void insertUser(SysUser sysUser);
}
