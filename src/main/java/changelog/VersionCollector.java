package changelog;

import changelog.model.ParsedMessage;
import changelog.model.Type.CommitType;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * @author zacconding
 * @Date 2018-09-15
 * @GitHub : https://github.com/zacscoding
 */
public class VersionCollector {

    private String version;
    private Date time;

    private Queue<ParsedMessage> features;
    private Queue<ParsedMessage> fixes;
    private Map<String, Queue<ParsedMessage>> customMap;

    public VersionCollector(String version, Date time) {
        this.version = version;
        this.time = time;

        this.features = new LinkedList<>();
        this.fixes = new LinkedList<>();
        this.customMap = new LinkedHashMap<>();
    }

    public boolean pushMessage(ParsedMessage parsedMessage) {
        switch (parsedMessage.getType().getType()) {
            case FEAT:
                return pushFeatures(parsedMessage);
            case FIX:
                return pushFixes(parsedMessage);
            case CUSTOM:
                return pushCustom(parsedMessage);
            default:
                return false;
        }
    }

    public boolean pushFeatures(ParsedMessage parsedMessage) {
        if (!isSameType(CommitType.FEAT, parsedMessage)) {
            return false;
        }

        return features.add(parsedMessage);
    }

    public boolean pushFixes(ParsedMessage parsedMessage) {
        if (!isSameType(CommitType.FIX, parsedMessage)) {
            return false;
        }

        return fixes.add(parsedMessage);
    }

    public boolean pushCustom(ParsedMessage parsedMessage) {
        if (!isSameType(CommitType.CUSTOM, parsedMessage)) {
            return false;
        }

        String displayName = parsedMessage.getType().getDisplayName();
        Queue<ParsedMessage> messages = customMap.get(displayName);
        if (messages == null) {
            messages = new LinkedList<>();
            customMap.put(displayName, messages);
        }

        return messages.add(parsedMessage);
    }

    // getters
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Queue<ParsedMessage> getFeatures() {
        return features;
    }

    public void setFeatures(Queue<ParsedMessage> features) {
        this.features = features;
    }

    public Queue<ParsedMessage> getFixes() {
        return fixes;
    }

    public void setFixes(Queue<ParsedMessage> fixes) {
        this.fixes = fixes;
    }

    public Map<String, Queue<ParsedMessage>> getCustomMap() {
        return customMap;
    }

    public void setCustomMap(Map<String, Queue<ParsedMessage>> customMap) {
        this.customMap = customMap;
    }

    private boolean isSameType(CommitType commitType, ParsedMessage parsedMessage) {
        return parsedMessage.getType().getType() == commitType;
    }
}
