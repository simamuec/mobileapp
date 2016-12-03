package de.hszg.apps.playground;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import de.hszg.apps.playground.util.RestCallExecution;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void testExecuteGet() {
        String url = "http://80.153.90.104/RisikousRESTful/rest/publication";
        Map<String, String> params = new HashMap<>();
        params.put("id", "4");  //PUB_56
        String result = RestCallExecution.executeGet(url, params);
        assertEquals("404 Not Found", result);
    }

    @Test
    public void testExecuteGet2() {
        String url = "http://80.153.90.104/RisikousRESTful/rest/publications";
        String result = RestCallExecution.executeGet(url, null);
        assertTrue(result.contains("<"));
    }
}