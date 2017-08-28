import java.io.*;
import java.text.ParseException;
import java.net.Socket;

public class HttpClient {
    private String header;
    private String answer;

    public HttpClient(String header) {
        this.header = header;
    }

    public void Request() {
        try {
            getHeader();
            answer = sendRequest(header);
            getAnswer(answer);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.getCause().printStackTrace();
        }
    }

    public String sendRequest(String httpHeader) throws Exception {
        String host = null;
        int port = 0;
        try {
            host = getHost(httpHeader);
            port = getPort(host);
            host = getHostWithoutPort(host);
        } catch (Exception e) {
            throw new Exception("Не удалось получить адрес сервера.", e);
        }
        /* Отправляется запрос на сервер */
        Socket socket;
        try {
            socket = new Socket(host, port);
            System.out.println("Создан сокет: " + host + " port:" + port);
            OutputStream os = socket.getOutputStream();
            os.write(httpHeader.getBytes());
            os.flush();
            System.out.println("Заголовок отправлен. \n");
        } catch (Exception e) {
            throw new Exception("Ошибка при отправке запроса: " + e.getMessage(), e);
        }
        /* Ответ от сервера записывается в результирующую строку */
        String res = null;
        try {
            BufferedReader bfr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            StringBuffer sbf = new StringBuffer();
            int ch = bfr.read();
            while (ch != -1) {
                sbf.append((char) ch);
                ch = bfr.read();
            }
            res = sbf.toString();
        } catch (Exception e) {
            throw new Exception("Ошибка при чтении ответа от сервера.", e);
        }
        socket.close();
        return res;
    }

    public void stringToDom(String xmlSource, String fileName)
            throws IOException {
        java.io.FileWriter fw = new java.io.FileWriter(fileName);
        fw.write(xmlSource);
        fw.close();
    }

    private String getHost(String header) throws ParseException {
        final String host = "Host: ";
        final String normalEnd = "\n";
        final String msEnd = "\r\n";

        int s = header.indexOf(host, 0);
        if (s < 0) {
            return "localhost";
        }
        s += host.length();
        int e = header.indexOf(normalEnd, s);
        e = (e > 0) ? e : header.indexOf(msEnd, s);
        if (e < 0) {
            throw new ParseException(
                    "В заголовке запроса не найдено " +
                            "закрывающих символов после пункта Host.",
                    0);
        }
        String res = header.substring(s, e).trim();
        return res;
    }

    private int getPort(String hostWithPort) {
        int port = hostWithPort.indexOf(":", 0);
        port = (port < 0) ? 80 : Integer.parseInt(hostWithPort
                .substring(port + 1));
        return port;
    }

    private String getHostWithoutPort(String hostWithPort) {
        int portPosition = hostWithPort.indexOf(":", 0);
        if (portPosition < 0) {
            return hostWithPort;
        } else {
            return hostWithPort.substring(0, portPosition);
        }
    }

    public void getHeader() {
        System.out.println("Заголовок: \n" + header);
    }

    public void getAnswer(String answer) throws IOException {
        System.out.println("Ответ от сервера: \n");
        System.out.write(answer.getBytes());
    }

    public String getAnswer() {
        return answer;
    }
}
