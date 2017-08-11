package gr.personal.oauth.model;

/**
 * Created by nkanakis on 7/12/2017.
 *
 * AccessToken is used to hold all data (and metadata) regarding the authorization at Reddit.
 */
public class AccessToken {
    /**
     * Access token provided after OAuth2 authentication
     */
    private String token;

    /**
     * Epoch time till access token expiration
     */
    private long expiration;

    public AccessToken(String token, long expiration) {
        this.token = token;
        this.expiration = expiration + System.currentTimeMillis()/1000 ;
    }

    public AccessToken(OAuthResponse response){
        this.token = response.getAccess_token();
        this.expiration = response.getExpires_in() + System.currentTimeMillis()/1000 ;
    }

    public String getToken() {
        return token;
    }

    public long getExpiration() {
        return expiration;
    }

    /**
     * If current time is 5sec from expiration consider the token expired for safety
     */
    public boolean hasExpired(){
        return expiration - (System.currentTimeMillis()/1000) < 4;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccessToken that = (AccessToken) o;

        if (expiration != that.expiration) return false;
        return token.equals(that.token);
    }

    @Override
    public int hashCode() {
        int result = token.hashCode();
        result = 31 * result + (int) (expiration ^ (expiration >>> 32));
        return result;
    }
}
