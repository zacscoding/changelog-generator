package changelog;

/**
 * @author zacconding
 * @Date 2018-09-16
 * @GitHub : https://github.com/zacscoding
 */
public class Sample {

    public static void main(String[] args) {
        String gitDir = "E:\\changelog";
        String branchName = "master";
        String destFile = "E:\\CHANGELOG.md";
        ChangeLogGenerator.generateChangeLog(gitDir, branchName, destFile, "https://github.com/zacscoding/changelog-generator");
    }
}
