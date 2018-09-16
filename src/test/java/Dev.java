import java.io.File;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.RemoteListCommand;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.RemoteConfig;
import org.junit.Test;

/**
 * @author zacconding
 * @Date 2018-09-14
 * @GitHub : https://github.com/zacscoding
 */
public class Dev {

    @Test
    public void readSample() {
        String destDir = "E:\\changelog";
        String branchName = "master";

        try (Git git = Git.open(new File(destDir)); Repository repository = git.getRepository()) {
            Iterable<RevCommit> logs = git.log().add(repository.resolve(branchName)).call();
            for (RevCommit rev : logs) {
                SimpleLogger.println("commit : {}\n{}", rev.getId().getName(), rev.getFullMessage());
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test() {
        String destDir = "E:\\jgit-test";
        String branchName = "test";
        try (Git git = Git.open(new File(destDir)); Repository repository = git.getRepository()) {
            RemoteListCommand remoteListCommand = git.remoteList();
            for(RemoteConfig remoteConfig :  remoteListCommand.call()) {
                SimpleLogger.println("Remote name : {} ", remoteConfig.getName());
            }

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