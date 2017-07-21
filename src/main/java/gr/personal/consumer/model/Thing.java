package gr.personal.consumer.model;

import org.springframework.util.Assert;

/**
 * This class represents the Thing, more info: http://www.reddit.com/dev/api#fullname
 */
public class Thing {
    protected final Kind kind;
    protected final String id;
    protected final String fullName;

    public Thing(String fullName) {
        Assert.isTrue(fullName.contains("_"),"Fullnames must contain an underscore");
        this.fullName = fullName;
        String[] split = fullName.split("_");
        this.kind = Kind.valueOf(split[0]);
        this.id = split[1];;
    }

    public Thing(String kind, long idToDecimal){
        this.kind = Kind.valueOf(kind);
        this.id = Long.toString(idToDecimal, 36);
        this.fullName = this.kind + this.id;
    }

    public Thing(Kind kind, long idToDecimal){
        this.kind = kind;
        this.id = Long.toString(idToDecimal, 36);
        this.fullName = this.kind + "_" + this.id;
    }

    /**
     * @return The kind of this thing.
     * Eg: t1 indicates Account
     */
    public Kind getKind() {
        return kind;
    }

    /**
     * @return The id of this thing
     * Eg: 6n0o17
     */
    public String getId() {
        return id;
    }

    /**
     * @return the fullname of the this thing. Fullnames are constructed like this: kind + "_" + id
     * Eg: t1_6n0o17
     */
    public String getFullName() {
        return fullName;
    }
}
