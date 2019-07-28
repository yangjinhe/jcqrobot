package com.yjh.cqa;


import org.junit.Test;

import java.net.URL;
import java.net.URLConnection;

public class AppTest {

    @Test
    public void TestJenkins() throws Exception {
        String str = "-s http://127.0.0.1:8080/jenkins -auth admin:116726e6ed9b4dab1295c018c790d6f9a1 console test-efmp-actapp-be 1";
        String[] args = str.split(" ");
        //hudson.cli.CLI.main(args);
    }

    @Test
    public void testConn() throws Exception {
        URL url = new URL("http://192.168.74.15:8866");
        URLConnection connection = url.openConnection();
        String headerField = connection.getHeaderField(0);

    }

}
