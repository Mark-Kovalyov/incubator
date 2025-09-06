package mayton.oracle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;

public class HtmlWriter implements Closeable {

    static String css = "<style type=\"text/css\">body.awr {font:bold 10pt Arial,Helvetica,Geneva,sans-serif;color:black; background:White;}\n" +
            "pre.awr  {font:8pt Courier;color:black; background:White;}h1.awr   {font:bold 20pt Arial,Helvetica,Geneva,sans-serif;color:#336699;background-color:White;border-bottom:1px solid #cccc99;margin-top:0pt; margin-bottom:0pt;padding:0px 0px 0px 0px;}\n" +
            "h2.awr   {font:bold 18pt Arial,Helvetica,Geneva,sans-serif;color:#336699;background-color:White;margin-top:4pt; margin-bottom:0pt;}\n" +
            "h3.awr {font:bold 16pt Arial,Helvetica,Geneva,sans-serif;color:#336699;background-color:White;margin-top:4pt; margin-bottom:0pt;}li.awr {font: 8pt Arial,Helvetica,Geneva,sans-serif; color:black; background:White;}\n" +
            "th.awrnobg {font:bold 8pt Arial,Helvetica,Geneva,sans-serif; color:black; background:White;padding-left:4px; padding-right:4px;padding-bottom:2px}th.awrbg {font:bold 8pt Arial,Helvetica,Geneva,sans-serif; color:White; background:#0066CC;padding-left:4px; padding-right:4px;padding-bottom:2px}\n" +
            "td.awrnc {font:8pt Arial,Helvetica,Geneva,sans-serif;color:black;background:White;vertical-align:top;}\n" +
            "td.awrc    {font:8pt Arial,Helvetica,Geneva,sans-serif;color:black;background:#FFFFCC; vertical-align:top;}a.awr {font:bold 8pt Arial,Helvetica,sans-serif;color:#663300; vertical-align:top;margin-top:0pt; margin-bottom:0pt;}\n" +
            "</style>";

    static Logger logger = LoggerFactory.getLogger(HtmlWriter.class);



    public void writeHeader(String name){

    }

    public void writeSplitter() {

    }

    public void writeTable(String table) {
        String html =
                "<TABLE BORDER=1 WIDTH=500>\n" +
                "<TR>" +
                        "<TH class='awrbg'>DB Name</TH>" +
                        "<TH class='awrbg'>DB Id</TH>" +
                        "<TH class='awrbg'>Instance</TH>" +
                        "<TH class='awrbg'>Inst num</TH>" +
                        "<TH class='awrbg'>Release</TH>" +
                        "<TH class='awrbg'>RAC</TH>" +
                        "<TH class='awrbg'>Host</TH></TR>\n" +
                "<TR>" +
                        "<TD class='awrnc'>DNASCR</TD>" +
                        "<TD ALIGN='right' class='awrnc'>755616871</TD>" +
                        "<TD class='awrnc'>DNASCR5</TD>" +
                        "<TD ALIGN='right' class='awrnc'>5</TD>" +
                        "<TD class='awrnc'>10.2.0.4.0</TD>" +
                        "<TD class='awrnc'>YES</TD>" +
                        "<TD class='awrnc'>dc-odw01</TD>" +
                "</TR>\n" +
                "</TABLE>";
    }

    @Override
    public void close() throws IOException {

    }

}
