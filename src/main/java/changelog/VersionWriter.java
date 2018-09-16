package changelog;

import changelog.model.ParsedMessage;
import java.io.IOException;
import java.io.Writer;

/**
 * @author zacconding
 * @Date 2018-09-15
 * @GitHub : https://github.com/zacscoding
 */
public abstract class VersionWriter {

    public void writeVersion(Writer writer, String version, String date) throws IOException {
        writer.write(h1(version + "(" + date + ")"));
        writer.write(newLine(2));
    }

    public void writeType(Writer writer, String typeName) throws IOException {
        writer.write(h3(typeName));
        writer.write(newLine(1));
    }

    public void writeTypeContent(Writer writer, ParsedMessage commitMessage, String remoteUrl) throws IOException {
        String prefix = "* ";
        // TODO :: a href commit
        if (commitMessage.getScope() == null) {
            prefix += commitMessage.getDescription();
        } else {
            prefix += highlight(commitMessage.getScope() + ":") + " " + commitMessage.getDescription();
        }

        prefix += " (" + a(remoteUrl + "/commit/" + commitMessage.getCommitHash(), commitMessage.getCommitHash().substring(0, 7)) + ")";

        writer.write(prefix);

        // li(writer, commitMessage.getDescription());
        if (commitMessage.getBody() != null) {
            writer.write(pre(commitMessage.getBody()));
        }
        writer.write(newLine(1));
    }

    public void newLine(Writer writer, int count) throws IOException {
        writer.write(newLine(count));
    }

    public abstract String pre(String body);

    public abstract String highlight(String message);

    public abstract String li(String content);

    public abstract String newLine(int count);

    public abstract String a(String href, String content);

    public abstract String h1(String content);

    public abstract String h2(String content);

    public abstract String h3(String content);

    public abstract String h4(String content);

    public abstract String h5(String content);

    public enum FileFormat {
        UNKNOWN, MARKDOWN;

        public static FileFormat getType(String fileName) {
            if (fileName == null) {
                return UNKNOWN;
            }

            int dotIdx = fileName.lastIndexOf('.');
            if (dotIdx < 0) {
                return UNKNOWN;
            }

            String format = fileName.substring(dotIdx + 1);

            if ("md".equalsIgnoreCase(format)) {
                return MARKDOWN;
            } // another file

            return UNKNOWN;
        }
    }
}