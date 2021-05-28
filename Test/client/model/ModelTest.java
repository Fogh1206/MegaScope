package client.model;

import client.core.ClientFactory;
import client.core.ModelFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shared.Show;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    private Model model;

    @BeforeEach
    void setUp() {
        ClientFactory clientFactory = new ClientFactory();
        model = new Model(clientFactory.getClient());

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void login() {
    }

    @Test
    public void addMovie(Show show) {

    }


}