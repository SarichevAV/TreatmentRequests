import java.io.IOException;
import java.util.List;

public class Start {
    public static void main(String[] args) throws IOException {
        HttpClient httpClient = new HttpClient("GET https://api.esv2.com/Api/Lists?apiKey=9DfWEl04zzEcnWsEc6mx\n" +
                "HTTP/1.1\n" +
                "Accept-Encoding: gzip,deflate\n" +
                "Host: api.esv2.com\n" +
                "Connection: Keep-Alive\n" +
                "User-Agent: Apache-HttpClient/4.1.1 (java 1.5)\n");
        httpClient.Request();
        String answer = httpClient.getAnswer();
        httpClient.stringToDom(answer,"Users.xml");
        Parser parser = new Parser();
        parser.parse();
        List<Parser.User> userList = parser.getUserList();
        Users users = new Users();
        users.setUsers(userList);
        List<List<Parser.User>> sortingList = users.sortingListOptInMode();
        sortingList.get(0).forEach(System.out::println);
        System.out.println();
        sortingList.get(1).forEach(System.out::println);
    }
}
