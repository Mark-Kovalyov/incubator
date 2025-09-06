package mayton.chess;

import com.google.gson.Gson;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class Position9CodeJsonTest {

    @Test
    public void testWithoutQuotas(){
        Gson gson = new Gson();
        PositionCodeJson positionCodeJson = gson.fromJson("{positionCode:OK}", PositionCodeJson.class);
        assertNotNull(positionCodeJson);
        assertEquals("OK", positionCodeJson.getPositionCode() );
    }

}
