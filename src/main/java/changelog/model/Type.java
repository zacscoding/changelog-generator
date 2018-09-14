package changelog.model;

import java.util.EnumSet;
import java.util.Set;

/**
 * @author zacconding
 * @Date 2018-09-14
 * @GitHub : https://github.com/zacscoding
 */
public class Type {

    public static Type FEATURE = new Type(TypeEnum.FEATURE, "Features");
    public static Type FIX = new Type(TypeEnum.FIX, "Bug Fixes");

    private TypeEnum type;
    private String displayName;

    public Type(TypeEnum type, String displayName) {
        this.type = type;
        this.displayName = displayName;
    }

    public static Type getType(String name) {
        TypeEnum type = TypeEnum.getType(name);

        switch (type) {
            case FEATURE:
                return Type.FEATURE;
            case FIX:
                return Type.FIX;
            case VERSION :
                return new Type(TypeEnum.VERSION, name);
            case CUSTOM:
            default:
                return new Type(TypeEnum.CUSTOM, name);
        }
    }

    public TypeEnum getType() {
        return type;
    }

    public String getDisplayName() {
        return displayName;
    }

    public enum TypeEnum {
        VERSION, FEATURE, FIX, CUSTOM;

        private static final Set<TypeEnum> TYPES = EnumSet.allOf(TypeEnum.class);

        public static TypeEnum getType(String name) {
            if (name != null && name.length() > 0) {
                for (TypeEnum type : TYPES) {
                    if (type.name().equalsIgnoreCase(name)) {
                        return type;
                    }
                }
            }

            return CUSTOM;
        }
    }
}