import changelog.CommitMessageParser;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.Test;

/**
 * @author zacconding
 * @Date 2018-09-15
 * @GitHub : https://github.com/zacscoding
 */
public class ReadTest {

    public static final String DELIMITER = "[_change_log]";

    @Test
    public void issue() {
        Pattern pattern = Pattern.compile("(#\\d)");
        List<String> descriptions = Arrays.asList(
          "#123a",
          "#111 asdfhasldf",
          "#aadsf asdfasdf"
        );

        for (String description : descriptions) {
            Matcher matcher = pattern.matcher(description);
            System.out.println(matcher.matches());
        }
    }

    @Test
    public void temp() {
        List<String> messages = Arrays.asList(
            new StringBuilder("[_change_log] version : v1.0.0 [_change_log]").toString(),
            new StringBuilder("[_change_log] \nfeature : this is feature message \n[_change_log]").toString(),
            new StringBuilder("[_change_log] fix : this is fix message\n \n[_change_log]").toString(),
            new StringBuilder("[_change_log] fix(lang) : this is fix message\n \n[_change_log]").toString(),
            new StringBuilder("[_change_log] custom : this is custom message [_change_log]").toString(),
            new StringBuilder("[_change_log] custom : this is custom message \nthis is body..[_change_log]").toString(),
            new StringBuilder("[_change_log][_change_log]").toString(),
            new StringBuilder("this is a normal commit message").toString()
        );

        for (String message : messages) {
            System.out.println("---------------------------------------------------------");
            System.out.println("Origin : \n" + message);
            System.out.println(">>>");
            System.out.println(Arrays.toString(parseCommit(message)));
        }
    }

    public String[] parseCommit(String message) {
        if (message == null || message.length() == 0) {
            return null;
        }

        int startIdx = message.indexOf(DELIMITER);
        if (startIdx < 0) {
            return null;
        }

        int lastIdx = message.lastIndexOf(DELIMITER);
        if (lastIdx < 0) {
            return null;
        }

        String extractMessage = message.substring(startIdx + DELIMITER.length(), lastIdx).trim();
        int typeStartLastIdx = extractMessage.indexOf(':');
        if (typeStartLastIdx < 0) {
            return null;
        }

        String[] token = new String[4];

        // type
        token[0] = extractMessage.substring(0, typeStartLastIdx).trim();
        int scopeStart = token[0].indexOf('(');
        int scopeEnd = token[0].lastIndexOf(')');

        // scope
        if (scopeStart > -1 && scopeEnd > -1) {
            token[1] = token[0].substring(scopeStart + 1, scopeEnd);
            token[0] = token[0].substring(0, scopeStart);
        }

        boolean findLineSeparator = false;
        for (int i = typeStartLastIdx; i < extractMessage.length(); i++) {
            if (extractMessage.charAt(i) == '\n') {
                token[2] = extractMessage.substring(typeStartLastIdx + 1, i).trim();
                if (i != extractMessage.length() - 1) {
                    token[3] = extractMessage.substring(i+1).trim();
                }
                findLineSeparator = true;
                break;
            }
        }

        if (!findLineSeparator) {
            token[2] = extractMessage.substring(typeStartLastIdx + 1).trim();
        }

        return token;
    }
}