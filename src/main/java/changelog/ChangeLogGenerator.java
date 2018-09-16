package changelog;

import changelog.VersionWriter.FileFormat;
import changelog.model.ParsedMessage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Queue;
import java.util.Stack;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

/**
 * @author zacconding
 * @Date 2018-09-14
 * @GitHub : https://github.com/zacscoding
 */
public class ChangeLogGenerator {

    public static void generateChangeLog(String gitDir, String branchName, String destFile, String remoteUrl) {
        Objects.requireNonNull(gitDir, "gitDir must be not null");
        Objects.requireNonNull(branchName, "branchName must be not null");
        Objects.requireNonNull(destFile, "destFile must be not null");

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
            Deque<VersionCollector> versionCollectors = new LinkedList<>();
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
                        versionCollectors.addLast(version);
                        break;
                    case FEAT:
                    case FIX:
                    case CUSTOM:
                        ensureExistVersion(versionCollectors, parsedMessage);
                        versionCollectors.getLast().pushMessage(parsedMessage);
                }
            }

            File file = new File(destFile);
            if (file.exists()) {
                file.delete();
            }

            file.createNewFile();

            try (FileWriter writer = new FileWriter(file)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                while (!versionCollectors.isEmpty()) {
                    VersionCollector version = versionCollectors.poll();
                    // write version
                    versionWriter.writeVersion(writer, version.getVersion(), sdf.format(version.getTime()));

                    // write fix
                    writeTypes(version.getFixes(), versionWriter, writer, remoteUrl);

                    // write features
                    writeTypes(version.getFeatures(), versionWriter, writer, remoteUrl);

                    // write custom
                    if (!version.getCustomMap().isEmpty()) {
                        for(Entry<String, Queue<ParsedMessage>> entry : version.getCustomMap().entrySet()) {
                            writeTypes(entry.getValue(), versionWriter, writer, remoteUrl);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void writeTypes(Queue<ParsedMessage> que, VersionWriter versionWriter, Writer writer, String remoteUrl) throws IOException  {
        if (!que.isEmpty()) {
            versionWriter.writeType(writer, que.peek().getType().getDisplayName());
            while (!que.isEmpty()) {
                ParsedMessage fix = que.poll();
                versionWriter.writeTypeContent(writer, fix, remoteUrl);
            }
            versionWriter.newLine(writer,2);
        }
    }

    private static void ensureExistVersion(Deque<VersionCollector> versionCollectors, ParsedMessage parsedMessage) {
        if (versionCollectors.isEmpty()) {
            versionCollectors.addLast(new VersionCollector("", parsedMessage.getCommitTime()));
        }
    }
}