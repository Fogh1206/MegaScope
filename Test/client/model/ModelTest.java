package client.model;

import client.core.ClientFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shared.MovieShow;

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
    public void addMovie(MovieShow movieShow) {

    }


}