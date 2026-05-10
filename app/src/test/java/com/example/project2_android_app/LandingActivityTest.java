package com.example.project2_android_app;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Author: Luis Cotes
 */
public class LandingActivityTest {

    @Test
    public void testLandingActivityExists() {
        assertNotNull(LandingActivity.class);
    }

    @Test
    public void testIntentFactoryExists() throws NoSuchMethodException {
        assertNotNull(
                LandingActivity.class.getDeclaredMethod(
                        "landingActivityIntentFactory",
                        android.content.Context.class
                )
        );
    }
}