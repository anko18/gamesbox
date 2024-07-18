package com.gamesbox.igdbapi;

import com.api.igdb.request.IGDBWrapper;
import com.api.igdb.request.TwitchAuthenticator;
import com.api.igdb.utils.TwitchToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class IGDB {
    private ConfigProperties properties;
    private final TwitchAuthenticator twitchAuthenticator;
    private final IGDBWrapper wrapper;
    private TwitchToken token;

    @Autowired
    public IGDB(ConfigProperties properties) {
        this.properties = properties;
        this.twitchAuthenticator = TwitchAuthenticator.INSTANCE;
        this.wrapper = IGDBWrapper.INSTANCE;
        setup();
    }

    public void setup() {
        try {
            this.token = twitchAuthenticator.requestTwitchToken(properties.getId(), properties.getSecret());
            this.wrapper.setCredentials(properties.getId(), token.getAccess_token());
        } catch (Exception e) {
            System.out.println("Error!: " + e.getMessage());
        }
    }

    public TwitchAuthenticator getTwitchAuthenticator() {
        return twitchAuthenticator;
    }

    public IGDBWrapper getWrapper() {
        return wrapper;
    }

    public TwitchToken getToken() {
        return token;
    }

    public void renewToken() {
        this.token = twitchAuthenticator.requestTwitchToken(properties.getId(), properties.getSecret());
        this.wrapper.setCredentials(properties.getId(), token.getAccess_token());
    }


}
