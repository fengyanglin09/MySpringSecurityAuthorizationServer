package diy.mqml.jpahibernatestudy.persistence.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MessageTest {

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    public void setUp() {

    }

    @AfterEach
    public void tearDown(){
        this.entityManager.createQuery("DELETE FROM Message").executeUpdate();
    }

    @Test
    public void storeLoadMessage() {

        Message message = new Message();
        message.setText("Hello, World!");


        entityManager.persist(message);


        List<Message> messages = entityManager.createQuery("SELECT m FROM Message m", Message.class).getResultList();

        assertEquals(1, messages.size());
        assertEquals("Hello, World!", messages.get(0).getText());

        assertAll(
                () -> assertEquals(1, messages.size()),
                () -> assertEquals("Hello, World!", messages.get(0).getText())
        );

    }

}