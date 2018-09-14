import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 * @author zacconding
 * @Date 2018-09-15
 * @GitHub : https://github.com/zacscoding
 */
public class ReadTest {

    @Test
    public void temp() {
        List<String> messages = Arrays.asList(
            new StringBuilder("[_change_log] version : v1.0.0 [_change_log]").toString(),
            new StringBuilder("[_change_log] \nfeature : this is feature message \n[_change_log]").toString(),
            new StringBuilder("[_change_log] fix : this is fix message\n \n[_change_log]").toString(),
            new StringBuilder("[_change_log] custom : this is custom message [_change_log]").toString(),
            new StringBuilder("[_change_log] custom : this is custom message \nthis is body..[_change_log]").toString(),
            new StringBuilder("[_change_log][_change_log]").toString(),
            new StringBuilder("this is a normal commit message").toString()
        );

        for (String message : messages) {
            displayMessage(message);
        }
    }

    private void displayMessage(String message) {
        System.out.println("--------------------------------------------");
        System.out.println(">> read message : \n" + message);
        System.out.println(">> Result");
        final String delimiter = "[_change_log]";
        if (message == null || message.length() == 0) {
            System.out.println(">> Skip");
            return;
        }

        int startIdx = message.indexOf(delimiter);
        if (startIdx < 0) {
            System.out.println(">> Skip");
            return;
        }

        int lastIdx = message.lastIndexOf(delimiter);
        if (lastIdx < 0) {
            System.out.println(">> Skip");
            return;
        }

        String commitMessage = message.substring(startIdx + delimiter.length(), lastIdx).trim();
        parseCommitMessage(commitMessage);
    }

    private void parseCommitMessage(String message) {
        if (message == null || message.length() == 0) {
            System.out.println("Invalid commit message..");
            return;
        }

        String[] token = tokenizeMessage(message);
        if (token == null) {
            return;
        }

        SimpleLogger.println("Type : {}\nDescription : {}\nBody : {}", token[0], token[1], token[2]);
    }

    private String[] tokenizeMessage(String message) {
        // type / description / body
        String[] token = new String[3];

        int typeStartLastIdx = message.indexOf(':');
        if (typeStartLastIdx < 0) {
            System.out.println("Invalid message. there is no : at message");
            return null;
        }

        token[0] = message.substring(0, typeStartLastIdx).trim();
        boolean findLineSeparator = false;
        for (int i = typeStartLastIdx; i < message.length(); i++) {
            if (message.charAt(i) == '\n') {
                token[1] = message.substring(typeStartLastIdx + 1, i).trim();
                if (i != message.length() - 1) {
                    token[2] = message.substring(i+1).trim();
                }
                findLineSeparator = true;
                break;
            }
        }

        if (!findLineSeparator) {
            token[1] = message.substring(typeStartLastIdx + 1).trim();
        }

        return token;
    }
}