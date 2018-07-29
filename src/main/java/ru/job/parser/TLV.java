package ru.job.parser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import static ru.job.parser.Converter.*;
import static ru.job.parser.Params.*;
import static ru.job.parser.Params.dataTypeTLV;

public class TLV {
    private JSONObject jsonObject;
    private Integer tag;
    private Integer length;

    public TLV() {
        this.tag = null;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public JSONObject parseFIS(InputStream fis) throws IOException {
        byte[] tagBuf = new byte[getTagLength()];
        fis.read(tagBuf);
        setTag(convertByteToInt(tagBuf));

        getJsonObject().put(tagToName(tag),convertValue(fis, tag));
        return getJsonObject();
    }

    public Object convertValue(InputStream fis, Integer tag) throws IOException {
        byte[] lengthBuf = new byte[getLenLength()];
        fis.read(lengthBuf);
        setLength(calcLength(tag, convertByteToInt(lengthBuf)));
        byte[] valueBuf = new byte[getLength()];
        if(!tag.equals(4)) fis.read(valueBuf);
        if(dataTypeUnit32.contains(tag)){
            return convertToDate(valueBuf);
        }else if(dataTypeString.contains(tag)){
            return new String(valueBuf,getCharset());
        }else if(dataTypeVLN.contains(tag)){
            return convertByteToInt(valueBuf);
        }else if(dataTypeFVLN.contains(tag)){
            return convertByteToFVLN(valueBuf);
        }else if(dataTypeTLV.contains(tag)){
            TLVParser tlvParser = new TLVParser(fis, getLength());
            tlvParser.parseFis();
            JSONArray jsonArray = tlvParser.getJsonArray();
            return jsonArray;
        }
        return null;
    }

    public JSONObject getJsonObject() {
        if(jsonObject==null){
            jsonObject = new JSONObject();
        }
        return jsonObject;
    }

    public Integer getTag() {
        return tag;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }
}
