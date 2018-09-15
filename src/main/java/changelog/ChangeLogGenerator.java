package changelog;

import changelog.VersionWriter.FileFormat;
import changelog.model.ParsedMessage;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Queue;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

/**
 * @author zacconding
 * @Date 2018-09-14
 * @GitHub : https://github.com/zacscoding
 */
public class ChangeLogGenerator {

    public static void main(String[] args) {
        String gitDir = "E:\\changelog";
        String branchName = "master";
        generateChangeLog(gitDir, branchName, null);
    }

    public static void generateChangeLog(String gitDir, String branchName, String destFile) {
        Objects.requireNonNull(gitDir, "gitDir must be not null");
        Objects.requireNonNull(branchName, "branchName must be not null");
        Objects.requireNonNull(destFile, "destFile must be not null");

        StringWriter stringWriter = new StringWriter();
        VersionWriter versionWriter = null;
        FileFormat writeFileFormat = FileFormat.getType(destFile);
        switch (writeFileFormat) {
            case MARKDOWN:
                versionWriter = new MarkdownWriter();
                break;
            case UNKNOWN:
                throw new UnsupportedOperationException("Not supported file format : " + destFile);
        }

        try (Git git = Git.open(new File(gitDir)); Repository repository = git.getRepository()) {
            Queue<VersionCollector> versionCollectors = new LinkedList<>();
            Iterable<RevCommit> logs = git.log().add(repository.resolve(branchName)).call();

            // collect commit logs..
            for (RevCommit rev : logs) {
                ParsedMessage parsedMessage = CommitMessageParser.parseCommitMessage(rev);
                if (parsedMessage == null) {
                    continue;
                }

                // TODO :: collect change logs..
                switch (parsedMessage.getType().getType()) {
                    case VERSION:
                        VersionCollector version = new VersionCollector(parsedMessage.getDescription(), parsedMessage.getCommitTime());
                        versionCollectors.add(version);
                        break;
                    case FEAT:
                    case FIX:
                    case CUSTOM:
                        ensureExistVersion(versionCollectors, parsedMessage);
                        versionCollectors.peek().pushMessage(parsedMessage);
                }
            }


            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            while (!versionCollectors.isEmpty()) {
                VersionCollector version = versionCollectors.poll();
                // write version
                versionWriter.writeVersion(stringWriter, version.getVersion(), sdf.format(version.getTime()));

                // write fix
                writeTypes(version.getFixes(), versionWriter, stringWriter);

                // write features
                writeTypes(version.getFeatures(), versionWriter, stringWriter);

                // write custom
                if (!version.getCustomMap().isEmpty()) {
                    for(Entry<String, Queue<ParsedMessage>> entry : version.getCustomMap().entrySet()) {
                        writeTypes(entry.getValue(), versionWriter, stringWriter);
                    }
                }
            }

            System.out.println(stringWriter.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void writeTypes(Queue<ParsedMessage> que, VersionWriter versionWriter, Writer writer) throws IOException  {
        if (!que.isEmpty()) {
            versionWriter.writeType(writer, que.peek().getType().getDisplayName());
            while (!que.isEmpty()) {
                ParsedMessage fix = que.poll();
                versionWriter.writeTypeContent(writer, fix);
            }
            versionWriter.newLine(writer,2);
        }
    }

    private static void ensureExistVersion(Queue<VersionCollector> versionCollectors, ParsedMessage parsedMessage) {
        if (versionCollectors.isEmpty()) {
            versionCollectors.add(new VersionCollector("", parsedMessage.getCommitTime()));
        }
    }
}