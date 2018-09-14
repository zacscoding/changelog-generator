package changelog;

import changelog.model.ParsedMessage;
import java.io.File;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

/**
 * @author zacconding
 * @Date 2018-09-14
 * @GitHub : https://github.com/zacscoding
 */
public class ChangeLogGenerator {

    public static void generateChangeLog(String fileDir, String branchName, String suffix) {
        try (Git git = Git.open(new File(fileDir)); Repository repository = git.getRepository()) {
            Iterable<RevCommit> logs = git.log().add(repository.resolve(branchName)).call();
            for (RevCommit rev : logs) {
                ParsedMessage parsedMessage = CommitMessageParser.parseCommitMessage(rev);
                if (parsedMessage == null) {
                    continue;
                }

                // TODO :: collect change logs..
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}