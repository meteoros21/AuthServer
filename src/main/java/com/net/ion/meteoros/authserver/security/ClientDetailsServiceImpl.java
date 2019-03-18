package com.net.ion.meteoros.authserver.security;

import com.net.ion.meteoros.authserver.entity.Client;
import com.net.ion.meteoros.authserver.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

@Service
public class ClientDetailsServiceImpl implements ClientDetailsService
{
    @Autowired
    ClientRepository clientRepository;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException
    {
        Client client = clientRepository.findOneByClientId(clientId);
        ClientDetailsImpl clientDetails = new ClientDetailsImpl();
        clientDetails.setClient(client);

        return clientDetails;
    }
}
