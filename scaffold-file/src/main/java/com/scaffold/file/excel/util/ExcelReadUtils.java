package com.scaffold.file.excel.util;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.microsoft.ooxml.OOXMLParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelReadUtils {

    public static void main(String[] args) throws IOException, TikaException, SAXException {

        // Tika默认是10*1024*1024，这里防止文件过大导致Tika报错
        BodyContentHandler handler = new BodyContentHandler(1024 * 1024 * 10);


        Metadata metadata = new Metadata();
        String path = "E:/公司办公/项目管理/2022/大连港/物联网接口/大连港电力公司-物联网设备统计清单.xlsx";
        FileInputStream inputstream = new FileInputStream(new File(path));
        ParseContext pcontext = new ParseContext();

        // 解析Excell文档时应由超类AbstractParser的派生类OOXMLParser实现
        OOXMLParser msofficeparser = new OOXMLParser();
        msofficeparser.parse(inputstream, handler, metadata, pcontext);
        // 获取Excell文档的内容

        handler.startDocument();
        System.out.println("Excell文档内容:" + handler.toString());


    }
}
