package ru.job.parser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;

import static ru.job.parser.Params.getTagEndTLV;

public class TLVParser {
    private JSONArray jsonArray = new JSONArray();
    private InputStream fileInputStream;
    private Integer length;
    private Integer lengthSeq = 0;

    public TLVParser(String filePath) {
        File file = new File(filePath);

        try {
            setFileInputStream(new FileInputStream(file));
            setLength(getFileInputStream().available());
            parseFis();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (getFileInputStream() != null) getFileInputStream().close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public TLVParser(InputStream fileInputStream, Integer length) {
        setFileInputStream(fileInputStream);
        setLength(length);
    }

    public JSONArray parseFis() throws IOException {
        JSONArray jArray = new JSONArray();
        TLV tlv;
        do {
            tlv = new TLV();
            JSONObject jobj = tlv.parseFIS(getFileInputStream());
            jArray.put(jobj);
            if(getTagEndTLV().contains(tlv.getTag())){
                jsonArray.put(jArray);
                jArray = new JSONArray();
            }
            lengthSeq += tlv.getLength()+4;
        }while(lengthSeq<length);
        return jArray;
    }

    public InputStream getFileInputStream() {
        return fileInputStream;
    }

    public void setFileInputStream(InputStream fileInputStream) {
        this.fileInputStream = fileInputStream;
    }

    public JSONArray getJsonArray() {
        return jsonArray;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public void saveToFile(String s) {
        try (FileWriter file = new FileWriter(s)) {
            file.write(getJsonArray().toString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
