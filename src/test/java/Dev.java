import java.io.File;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Test;

/**
 * @author zacconding
 * @Date 2018-09-14
 * @GitHub : https://github.com/zacscoding
 */
public class Dev {

    @Test
    public void test() {
        String destDir = "E:\\jgit-test";
        String branchName = "test";
        try (Git git = Git.open(new File(destDir)); Repository repository = git.getRepository()) {
            Iterable<RevCommit> logs = git.log().add(repository.resolve(branchName)).call();
            for (RevCommit rev : logs) {
                SimpleLogger.build()
                            .appendln("Commit hash : " + rev.getId().getName())
                            .appendln("Commit time : " + rev.getCommitterIdent().getWhen())
                            .appendln("Commit message  : \n" + rev.getFullMessage())
                            .appendln("rev.getCommitTime() : " + rev.getCommitTime())
                            .flush();
                System.out.println("-----------------------------------------------------\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}