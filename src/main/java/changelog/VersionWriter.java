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
        h1(writer, version + "(" + date + ")");
        newLine(writer, 2);
    }

    public void writeType(Writer writer, String typeName) throws IOException {
        h3(writer, typeName);
        newLine(writer, 1);
    }

    public void writeTypeContent(Writer writer, ParsedMessage commitMessage) throws IOException {
        // TODO :: a href commit
        if (commitMessage.getScope() == null) {
            li(writer, commitMessage.getDescription());
        } else {
            li(writer, highlight(commitMessage.getScope() + ":") + " " + commitMessage.getDescription());
        }

        if (commitMessage.getBody() != null) {
            pre(writer, commitMessage.getBody());
        }
    }

    public abstract void pre(Writer writer, String body) throws IOException;

    public abstract String highlight(String message) throws IOException;

    public abstract void li(Writer writer, String content) throws IOException;

    public abstract void newLine(Writer writer, int count) throws IOException;

    public abstract void h1(Writer writer, String content) throws IOException;

    public abstract void h2(Writer writer, String content) throws IOException;

    public abstract void h3(Writer writer, String content) throws IOException;

    public abstract void h4(Writer writer, String content) throws IOException;

    public abstract void h5(Writer writer, String content) throws IOException;


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