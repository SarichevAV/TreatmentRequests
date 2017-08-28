import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    List<User> userList = new ArrayList<>();

    public void parse() {
        try {
            parseFile();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    private void parseFile() throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = documentBuilder.parse("Users.xml");

        NodeList usersNodeList = document.getElementsByTagName("List");

        for (int i = 0; i < usersNodeList.getLength(); i++) {
            if (usersNodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element userElement = (Element) usersNodeList.item(i);
                User user = new User();

                NodeList childNodeList = userElement.getChildNodes();
                for (int j = 0; j < childNodeList.getLength(); j++) {
                    if (childNodeList.item(j).getNodeType() == Node.ELEMENT_NODE) {
                        Element childElement = (Element) childNodeList.item(j);

                        switch (childElement.getNodeName()) {
                            case "Id": {
                                user.setId(Integer.valueOf(childElement.getTextContent()));
                            } break;
                            case "Name": {
                                user.setName(childElement.getTextContent());
                            } break;
                            case "FriendlyName": {
                                user.setFriendlyName(childElement.getTextContent());
                            } break;
                            case "Language": {
                                user.setLanguage(childElement.getTextContent());
                            } break;
                            case "OptInMode": {
                                user.setOptInMode(childElement.getTextContent());
                            }
                        }
                    }
                }
                userList.add(user);
            }
        }
    }

    public List<User> getUserList() {
        return userList;
    }

    class User {
        private int id;
        private String name;
        private String friendlyName;
        private String Language;
        private String OptInMode;

        public User() {
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getFriendlyName() {
            return friendlyName;
        }

        public String getLanguage() {
            return Language;
        }

        public String getOptInMode() {
            return OptInMode;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setFriendlyName(String friendlyName) {
            this.friendlyName = friendlyName;
        }

        public void setLanguage(String language) {
            Language = language;
        }

        public void setOptInMode(String optInMode) {
            OptInMode = optInMode;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", friendlyName='" + friendlyName + '\'' +
                    ", Language='" + Language + '\'' +
                    ", OptInMode='" + OptInMode + '\'' +
                    '}';
        }
    }
}
