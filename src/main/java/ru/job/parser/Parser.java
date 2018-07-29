package ru.job.parser;

import java.io.IOException;

public class Parser {
    public static void main(String[] args) throws IOException {
        //args[0] = "D:\\Nikita\\Projects\\job\\parser\\file";
        //args[1] = "D:\\Nikita\\Projects\\job\\parser\\result.txt";
        TLVParser tlvParser = new TLVParser(args[0]);
        tlvParser.saveToFile(args[1]);
    }
}
