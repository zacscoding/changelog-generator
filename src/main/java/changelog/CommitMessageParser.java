package changelog;

import changelog.model.ParsedMessage;
import org.eclipse.jgit.revwalk.RevCommit;

/**
 * @author zacconding
 * @Date 2018-09-14
 * @GitHub : https://github.com/zacscoding
 */
public class CommitMessageParser {

    public static ParsedMessage parseCommitMessage(RevCommit rev) {
        String[] token = parseCommitMessage(rev.getFullMessage());
        if (token == null) {
            return null;
        }

        return null;
    }

    /**
     * @return {type name, description, body} or null
     */
    private static String[] parseCommitMessage(String message) {

        return null;
    }
}