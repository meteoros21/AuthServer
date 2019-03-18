package com.net.ion.meteoros.authserver.repository;

import com.net.ion.meteoros.authserver.entity.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, String>
{
    Client findOneByClientId(String clientId);
}
