package dev.carlos.citiesAPI.user.models.requests.updated;

public class UserRequestChangeUserName {

    private String userName;
    private String userToken;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}
