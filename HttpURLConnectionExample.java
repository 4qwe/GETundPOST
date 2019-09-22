package com.company;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpURLConnectionExample {

    private final String USER_AGENT = "Mozilla/5.0";

    public static void main(String[] args) throws Exception {

        HttpURLConnectionExample http = new HttpURLConnectionExample();


        System.out.println("Testing 1 - Send Http GET request");
        http.sendGet();

        System.out.println("\nTesting 2 - Send Http POST request");
        http.sendPost();

    }

    //sendGet methode - ein HTTP GET request wird gesendet
    private void sendGet() throws Exception {

        String url = "http://www.google.com/search?q=mkyong";

        //diese zwei Zeilen sind wohl wo das meiste an Internetmagie passiert
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection(); //das Objekt ist erstellt und eine Verbindung existiert nun iwie

        con.setRequestMethod("GET"); //überflüssig? default ist schon GET?

        con.setRequestProperty("User-Agent", USER_AGENT); //Header fields gehören zu einem guten HTTP request
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5"); //Header fields gehören zu einem guten HTTP request


        //response aus dem connection-object wird ausgelesen
        int responseCode = con.getResponseCode(); //todo ist der Response code schon geschrieben bevor diese Methode ausgeführt wird? Was macht die Methode?
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        //vgl vor allem mit IO-Kram - dies ist das meiste davon
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream())); //getInputStream liefert daten, InputStreamReader, BufferedReader
        // und schließlich StringBuffer (append, toString) formen die Ausgabe mit println

        String inputLine;
        StringBuffer response = new StringBuffer();

        //endet alles mit diesem while-Konstrukt:
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        in.close();

        //Ausgabe
        System.out.println(response.toString());

    }

    // HTTP POST request

    private void sendPost() throws Exception {

        String url = "https://selfsolve.apple.com/wcResults.do";
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add request headers - UA und Language
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

        // Send post request
        con.setDoOutput(true); //...application is intended to WRITE to the CONNECTION OBJECT
        DataOutputStream wr = new DataOutputStream(con.getOutputStream()); //"Returns an output stream that writes to this connection."
        wr.writeBytes(urlParameters); //und zwar die URL Parameter- darauf beschränkt sich der Input des Users/der Output unseres Requests
        wr.flush();
        wr.close(); //... formt den "Outputstream" korrekt

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        //ab hier wie GET Request.. unterschiedlich war nur das Beschreiben des con Objekts mit dem Input/Outputstream

        BufferedReader in = new BufferedReader(
            new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());
    }

}
