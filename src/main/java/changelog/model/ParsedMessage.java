package changelog.model;

import java.util.Date;

/**
 * @author zacconding
 * @Date 2018-09-15
 * @GitHub : https://github.com/zacscoding
 */
public class ParsedMessage {

    private Type type;
    private String scope;
    private String description;
    private String body;
    private Date commitTime;
    private String commitHash;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(Date commitTime) {
        this.commitTime = commitTime;
    }

    public String getCommitHash() {
        return commitHash;
    }

    public void setCommitHash(String commitHash) {
        this.commitHash = commitHash;
    }

    @Override
    public String toString() {
        return "ParsedMessage{" + "type=" + type + ", scope='" + scope + '\'' + ", description='" + description + '\'' + ", body='" + body + '\'' + ", commitTime=" + commitTime + ", commitHash='"
            + commitHash + '\'' + '}';
    }
}