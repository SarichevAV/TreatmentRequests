import java.util.ArrayList;
import java.util.List;

public class Users {
    private List<Parser.User> users;

    public void setUsers(List<Parser.User> users) {
        this.users = users;
    }

    public List<Parser.User> getUsers() {
        return users;
    }

    public List<List<Parser.User>> sortingListOptInMode() {
        List<List<Parser.User>> res = new ArrayList<>();
        List<Parser.User> SingleOptInList = new ArrayList<>();
        List<Parser.User> DoubleOptInList = new ArrayList<>();
        for (Parser.User user: users) {
            if (user.getOptInMode().equals("DoubleOptIn")) {
                DoubleOptInList.add(user);
            }
            else {
                SingleOptInList.add(user);
            }
        }
        res.add(SingleOptInList);
        res.add(DoubleOptInList);
        return res;
    }
}
