package com.andersenlab.dao;

import com.andersenlab.dao.impl.ClientDaoImpl;
import com.andersenlab.entity.Client;
import com.andersenlab.util.IdGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

public class ClientDaoTest {
    private ClientDao clientDao;
    private Client client, client1;


    @BeforeEach
    public void setup() {
        client = new Client(IdGenerator.generateClientId(), "Evgen", 1);
        client1 = new Client(IdGenerator.generateClientId(), "Viktoria", 2);

        clientDao = new ClientDaoImpl();
        clientDao.save(client);

    }

    @Test
    public void saveTest() {

        Assertions.assertEquals(client1, clientDao.save(client1));
        Assertions.assertEquals(client1.getId(), Objects.requireNonNull(clientDao.getById(client1.getId()).orElse(null)).getId());
    }

    @Test
     public void getByIdTest() {
        Assertions.assertEquals(client.getId(), Objects.requireNonNull(clientDao.getById(client.getId()).orElse(null)).getId());
        Assertions.assertTrue(clientDao.getById(Long.MAX_VALUE).isEmpty());
    }

    @Test
    public void getAllTest() {
        clientDao.save(client1);
        Assertions.assertNotNull(clientDao.getAll());
        Assertions.assertEquals(2,  (long) clientDao.getAll().size());
    }

    @Test
    public void updateTest() {
            client.setName("Evgen-Evgen");

            Assertions.assertAll("clientUpdate",
                    () -> Assertions.assertNotNull(clientDao.update(client)),
                    () -> Assertions.assertEquals(client,clientDao.update(client).orElse(null))
            );
    }

    @Test
    public void removeTest() {
        Assertions.assertTrue(clientDao.remove(client.getId()));
        Assertions.assertTrue(clientDao.getById(client.getId()).isEmpty());
    }
}
