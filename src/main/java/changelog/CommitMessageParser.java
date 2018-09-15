package changelog;

import changelog.model.ParsedMessage;
import changelog.model.Type;
import org.eclipse.jgit.revwalk.RevCommit;

/**
 * @author zacconding
 * @Date 2018-09-14
 * @GitHub : https://github.com/zacscoding
 */
public class CommitMessageParser {

    public static final String DELIMITER = "[_change_log]";

    public static ParsedMessage parseCommitMessage(RevCommit rev) {
        String[] token = parseCommitMessage(rev.getFullMessage());
        if (token == null || token.length != 4) {
            return null;
        }

        ParsedMessage parsedMessage = new ParsedMessage();
        parsedMessage.setType(Type.getType(token[0]));
        parsedMessage.setScope(token[1]);
        parsedMessage.setDescription(token[2]);
        parsedMessage.setBody(token[3]);
        parsedMessage.setCommitTime(rev.getCommitterIdent().getWhen());

        return parsedMessage;
    }

    /**
     * @return {type name, description, body} or null
     */
    private static String[] parseCommitMessage(String message) {
        if (message == null || message.length() == 0) {
            return null;
        }

        int startIdx = message.indexOf(DELIMITER);
        if (startIdx < 0) {
            return null;
        }

        int lastIdx = message.lastIndexOf(DELIMITER);
        if (lastIdx < 0) {
            return null;
        }

        String extractMessage = message.substring(startIdx + DELIMITER.length(), lastIdx).trim();
        int typeStartLastIdx = extractMessage.indexOf(':');
        if (typeStartLastIdx < 0) {
            return null;
        }

        String[] token = new String[4];

        // type
        token[0] = extractMessage.substring(0, typeStartLastIdx).trim();
        int scopeStart = token[0].indexOf('(');
        int scopeEnd = token[0].lastIndexOf(')');

        // scope
        if (scopeStart > -1 && scopeEnd > -1) {
            token[1] = token[0].substring(scopeStart + 1, scopeEnd);
            token[0] = token[0].substring(0, scopeStart);
        }

        boolean findLineSeparator = false;
        for (int i = typeStartLastIdx; i < extractMessage.length(); i++) {
            if (extractMessage.charAt(i) == '\n') {
                // description
                token[2] = extractMessage.substring(typeStartLastIdx + 1, i).trim();
                if (i != extractMessage.length() - 1) {
                    // body
                    token[3] = extractMessage.substring(i+1).trim();
                }
                findLineSeparator = true;
                break;
            }
        }

        if (!findLineSeparator) {
            // description
            token[2] = extractMessage.substring(typeStartLastIdx + 1).trim();
        }

        return token;
    }
}