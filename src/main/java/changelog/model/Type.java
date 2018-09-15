package changelog.model;

import java.util.EnumSet;
import java.util.Set;

/**
 * @author zacconding
 * @Date 2018-09-14
 * @GitHub : https://github.com/zacscoding
 */
public class Type {

    public static final Type FEAT = new Type(CommitType.FEAT, "Features");
    public static final Type FIX = new Type(CommitType.FIX, "Bug Fixes");

    private final CommitType type;
    private final String displayName;

    public Type(CommitType type, String displayName) {
        this.type = type;
        this.displayName = displayName;
    }

    public static Type getType(String name) {
        CommitType type = CommitType.getType(name);

        switch (type) {
            case FEAT:
                return Type.FEAT;
            case FIX:
                return Type.FIX;
            case VERSION :
                return new Type(CommitType.VERSION, name);
            case CUSTOM:
            default:
                return new Type(CommitType.CUSTOM, name);
        }
    }

    public CommitType getType() {
        return type;
    }

    public String getDisplayName() {
        return displayName;
    }

    public enum CommitType {
        VERSION, FEAT, FIX, CUSTOM;

        private static final Set<CommitType> TYPES = EnumSet.allOf(CommitType.class);

        public static CommitType getType(String name) {
            if (name != null && name.length() > 0) {
                for (CommitType type : TYPES) {
                    if (type.name().equalsIgnoreCase(name)) {
                        return type;
                    }
                }
            }

            return CUSTOM;
        }
    }
}