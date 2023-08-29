package com.example.gta_geo_torpedoassault;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import com.example.gta_geo_torpedoassault.models.Enemy;
import com.example.gta_geo_torpedoassault.models.GameObject;
import com.example.gta_geo_torpedoassault.models.Torpedo;
import com.example.gta_geo_torpedoassault.services.GameService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import java.util.List;

@RunWith(RobolectricTestRunner.class)
public class GameServiceTest {
    private GameService gameService;
    private Context context;

    @Before
    public void setUp() {
        context = RuntimeEnvironment.systemContext;
        gameService = GameService.getInstance(context);
    }


    @Test
    public void testAddObject() {
        GameObject gameObject = new Enemy();
        gameService.addObject(gameObject);

        List<GameObject> gameObjects = gameService.getGameObjects();
        assertTrue(gameObjects.contains(gameObject));
    }

    @Test
    public void testRemoveObject() {
        GameObject gameObject = new Torpedo();
        gameService.addObject(gameObject);
        gameService.removeObject(gameObject);

        List<GameObject> gameObjects = gameService.getGameObjects();
        assertFalse(gameObjects.contains(gameObject));
    }

    @Test
    public void testInitializeTorpedos() {
        List<Torpedo> torpedos = gameService.getRetrievedTorpedos();
        assertNotNull(torpedos);
        assertEquals(5, torpedos.size());
    }
}
