package com.net.ion.meteoros.authserver.repository;

import com.net.ion.meteoros.authserver.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface UserRepository extends CrudRepository<User, String>
{
    User findOneByUserId(String userId);
    User findOneByUserIdAndUserPwd(String userId, String passwd);
}
