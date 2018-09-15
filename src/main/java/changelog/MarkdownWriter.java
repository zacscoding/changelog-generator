package changelog;

import java.io.IOException;
import java.io.Writer;

/**
 * @author zacconding
 * @Date 2018-09-15
 * @GitHub : https://github.com/zacscoding
 */
public class MarkdownWriter extends VersionWriter {

    @Override
    public void pre(Writer writer, String body) throws IOException {
        writer.write("```");
        newLine(writer, 1);
        writer.write(body);
        writer.write("```");
        newLine(writer, 1);
    }

    @Override
    public String highlight(String message) throws IOException {
        return "**" + message + "**";
    }

    @Override
    public void li(Writer writer, String content) throws IOException {
        writer.write("* " + content);
        newLine(writer, 1);
    }

    @Override
    public void newLine(Writer writer, int count) throws IOException {
        for (int i = 0; i < count; i++) {
            writer.write(System.lineSeparator());
        }
    }

    @Override
    public void h1(Writer writer, String content) throws IOException {
        writer.write("# " + content);
        newLine(writer, 1);
    }

    @Override
    public void h2(Writer writer, String content) throws IOException {
        writer.write("## " + content);
        newLine(writer, 1);
    }

    @Override
    public void h3(Writer writer, String content) throws IOException {
        writer.write("### " + content);
        newLine(writer, 1);
    }

    @Override
    public void h4(Writer writer, String content) throws IOException {
        writer.write("#### " + content);
        newLine(writer, 1);
    }

    @Override
    public void h5(Writer writer, String content) throws IOException {
        writer.write("##### " + content);
        newLine(writer, 1);
    }
}
