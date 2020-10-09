package au.com.anthonybruno.lichessclient;

import static au.com.anthonybruno.lichessclient.LichessClient.BASE_URL;

public enum URLS {

    ACCOUNT("account"), BOT("api/bot"), CHALLENGE("api/challenge"), TOURNAMENT("api/tournament"), TEAM("/team"), STREAM("api/stream"), BOARD("api/game/stream"), API("api");

    private final String url;

    URLS(String url) {
        this.url = BASE_URL + "/" + url;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return url;
    }
}
