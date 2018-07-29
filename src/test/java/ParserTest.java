import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import ru.job.parser.TLVParser;

import java.io.*;
import java.math.BigDecimal;

import static ru.job.parser.Converter.*;

public class ParserTest {

    @Test
    public void ParserTest() throws IOException {
        byte[] data = new byte[]{
                (byte) 0x01, (byte) 0x00, (byte) 0x04, (byte) 0x00, (byte) 0xA8, (byte) 0x32, (byte) 0x92, (byte) 0x56,
                (byte) 0x02, (byte) 0x00, (byte) 0x03, (byte) 0x00, (byte) 0x04, (byte) 0x71, (byte) 0x02, (byte) 0x03,
                (byte) 0x00, (byte) 0x0B, (byte) 0x00, (byte) 0x8E, (byte) 0x8E, (byte) 0x8E, (byte) 0x20, (byte) 0x90,
                (byte) 0xAE, (byte) 0xAC, (byte) 0xA0, (byte) 0xE8, (byte) 0xAA, (byte) 0xA0, (byte) 0x04, (byte) 0x00,
                (byte) 0x1D, (byte) 0x00, (byte) 0x0B, (byte) 0x00, (byte) 0x07, (byte) 0x00, (byte) 0x84, (byte) 0xEB,
                (byte) 0xE0, (byte) 0xAE, (byte) 0xAA, (byte) 0xAE, (byte) 0xAB, (byte) 0x0C, (byte) 0x00, (byte) 0x02,
                (byte) 0x00, (byte) 0x20, (byte) 0x4E, (byte) 0x0D, (byte) 0x00, (byte) 0x02, (byte) 0x00, (byte) 0x00,
                (byte) 0x02, (byte) 0x0E, (byte) 0x00, (byte) 0x02, (byte) 0x00, (byte) 0x40, (byte) 0x9C};

        ByteArrayInputStream stream=new ByteArrayInputStream(data);
        TLVParser tlvParser = new TLVParser(stream,stream.available());
        tlvParser.parseFis();

        JSONArray parseResult = tlvParser.getJsonArray();
        Assert.assertNotNull(parseResult);
        Assert.assertEquals(1,parseResult.length());
        JSONArray firstOrder = parseResult.getJSONArray(0);
        Assert.assertEquals(4,firstOrder.length());
        JSONObject orderNumber = firstOrder.getJSONObject(1);
        Assert.assertEquals(160004,orderNumber.getInt("orderNumber"));
        JSONObject customerName = firstOrder.getJSONObject(2);
        Assert.assertEquals("ООО Ромашка",customerName.getString("customerName"));
        JSONObject items = firstOrder.getJSONObject(3);
        JSONArray itemsArray = items.getJSONArray("items");
        Assert.assertEquals(1, itemsArray.length());
        JSONArray item = itemsArray.getJSONArray(0);
        JSONObject itemName = item.getJSONObject(0);
        Assert.assertEquals("Дырокол",itemName.getString("name"));
        JSONObject itemPrice = item.getJSONObject(1);
        Assert.assertEquals(20000,itemPrice.getInt("price"));
        JSONObject itemQuantity = item.getJSONObject(2);
        Assert.assertEquals(2,itemQuantity.getInt("quantity"));
        JSONObject itemSum = item.getJSONObject(3);
        Assert.assertEquals(40000,itemSum.getInt("sum"));
        Assert.assertEquals(40000,itemPrice.getInt("price")*itemQuantity.getInt("quantity"));
    }
    @Test
    public void ByteConvertorTest() throws IOException {
        Assert.assertEquals(3496,convertByteToInt(new byte[]{(byte) 0xA8, (byte) 0x0D}));
        Assert.assertEquals(2048,convertByteToInt(new byte[]{(byte) 0x00, (byte) 0x08}));

        Assert.assertEquals(BigDecimal.valueOf(2),convertByteToFVLN(new byte[]{(byte) 0x00, (byte) 0x02}));
        Assert.assertEquals(BigDecimal.valueOf(1.3),convertByteToFVLN(new byte[]{(byte) 0x01, (byte) 0x0D}));

        Assert.assertEquals("dateTime",tagToName(1));
        Assert.assertEquals("sum",tagToName(14));

        Assert.assertEquals((Integer)8,calcLength(2, 14));
        Assert.assertEquals((Integer)27,calcLength(11, 27));
    }

}
