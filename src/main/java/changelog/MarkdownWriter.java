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
    public String pre(String body) {
        return new StringBuilder(body.length() + 16).append("```").append(newLine(1)).append(body).append("```").append(newLine(1)).toString();
    }

    @Override
    public String highlight(String message) {
        return "**" + message + "**";
    }

    @Override
    public String li(String content) {
        return "* " + content + newLine(1);
    }

    @Override
    public String newLine(int count) {
        final String lineSeparator = System.lineSeparator();
        StringBuilder sb = new StringBuilder(lineSeparator.length() * count);
        for (int i = 0; i < count; i++) {
            sb.append(lineSeparator);
        }

        return sb.toString();
    }

    @Override
    public String a(String href, String content) {
        return "<a href=\"" + href + "\">" + content + "</a>";
    }

    @Override
    public String h1(String content) {
        return "# " + content + newLine(1);
    }

    @Override
    public String h2(String content) {
        return "## " + content + newLine(1);
    }

    @Override
    public String h3(String content) {
        return "### " + content + newLine(1);
    }

    @Override
    public String h4(String content) {
        return "#### " + content + newLine(1);
    }

    @Override
    public String h5(String content) {
        return "##### " + content + newLine(1);
    }
}
