package com.ipsos.cpm.ipsospt;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.widget.Toast;

import com.ipsos.cpm.ipsospt.helper.Constants;
import com.ipsos.cpm.ipsospt.helper.Utils;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.ipsos.cpm.ipsospt", appContext.getPackageName());
    }

    @Test
    public void getFam() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        HttpsURLConnection connection;
        BufferedReader reader;
        String resultJsonStr;

        try {
            URL url = new URL(Constants.BASE_URL + Constants.API_GET_FAM);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setConnectTimeout(Constants.NETWORK_TIMEOUT);
            connection.setRequestMethod("GET");
            connection.connect();

            // Read the input stream into a String
            InputStream inputStream = connection.getInputStream();
            StringBuilder buffer = new StringBuilder();

            assertTrue("Return is empty", inputStream != null);

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            assertTrue("Buffer is empty!", buffer.length() > 0);

            resultJsonStr = buffer.toString();

            JSONObject resultJson = new JSONObject(resultJsonStr);
            Utils.parseJson(resultJson);
        }
        catch (Exception ex) {
            assertTrue(ex.getMessage(), false);
        }
    }
}
