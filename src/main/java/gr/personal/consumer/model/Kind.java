package gr.personal.consumer.model;

/**
 * Enumeration that represents the various types of Things. (https://www.reddit.com/dev/api/#fullnames)
 */
public enum Kind {
    COMMENT("t1"), ACCOUNT("t2"), LINK("t3"), MESSAGE("t4"), SUBREDDIT("t5"), AWARD("t6"), PROMO_CAMPAIGN("t8"), MORE("more"), LISTING("listing");

    private String value;

    Kind(String value){
        this.value = value;
    }

    public String value() {
        return value;
    }
}
