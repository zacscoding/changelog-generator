# Simple git change log generator  

> ### Convention  

```$xslt
[_change_log]
<type>[optional scope]: <description>
[optional body]
[_change_log]
```  

> ### Example  

> commit messages

```$xslt
commit : c05ed621ddc6d801a1342187868d8fd565367acf
[_change_log]
feat(new) : new features ...
[_change_log]

commit : c4799fe46345f6b95ec32df5f6b25b9f49f85c1b
version 1.0.0

[_change_log]
version : v1.0.0
[_change_log]

commit : eaa1227c6a12fdd5ef2876bbee4bebfb3ec17b89
[_change_log]
Temp2 : this is for temp2..
[_change_log]

commit : 8d4b0fbf93bf3a124570cf721b0f4d8c84f2ceee
this is for temp..

[_change_log]
Temp1 : this is temp 2..
[_change_log]

commit : 140b504cbdd5c326597a93bfc5669c3f06cce09a
[_change_log]
Temp1 : this is temp1..
[_change_log]

commit : fa8d24dd61ce7fa0ba3c3c6dafd58ad00b1bc316
[_change_log]
fix : this is change log..
[_change_log]

commit : 1f24840434554f4267b71bf21357199a471469e2
this is normal

commit : a1e2dac951d7468c1f5edddc5de6c3e18554ffc5
[_change_log]
feat(this is title) : this is feature message2
[_change_log]

commit : 98de7047f28e4f4f7b0930ec8248d537c8e8b1f5
[_change_log]
feat : this is feature message
[_change_log]

commit : 4b68ce0f147798c568dddb67048641523a809f0c
init project
```  

> Sample.java  

```$xslt
public static void main(String[] args) {
  String gitDir = "E:\\changelog";
  String branchName = "master";
  String destFile = "E:\\CHANGELOG.md";
  ChangeLogGenerator.generateChangeLog(gitDir, branchName, destFile, "https://github.com/zacscoding/changelog-generator");
}
```

> Generated CHANGELOG.md  

# (2018-09-16)


### Features

* **new:** new features ... (<a href="https://github.com/zacscoding/changelog-generator/commit/c05ed621ddc6d801a1342187868d8fd565367acf">c05ed62</a>)


# v1.0.0(2018-09-16)


### Bug Fixes

* this is change log.. (<a href="https://github.com/zacscoding/changelog-generator/commit/fa8d24dd61ce7fa0ba3c3c6dafd58ad00b1bc316">fa8d24d</a>)


### Features

* **this is title:** this is feature message2 (<a href="https://github.com/zacscoding/changelog-generator/commit/a1e2dac951d7468c1f5edddc5de6c3e18554ffc5">a1e2dac</a>)
* this is feature message (<a href="https://github.com/zacscoding/changelog-generator/commit/98de7047f28e4f4f7b0930ec8248d537c8e8b1f5">98de704</a>)


### Temp2

* this is for temp2.. (<a href="https://github.com/zacscoding/changelog-generator/commit/eaa1227c6a12fdd5ef2876bbee4bebfb3ec17b89">eaa1227</a>)


### Temp1

* this is temp 2.. (<a href="https://github.com/zacscoding/changelog-generator/commit/8d4b0fbf93bf3a124570cf721b0f4d8c84f2ceee">8d4b0fb</a>)
* this is temp1.. (<a href="https://github.com/zacscoding/changelog-generator/commit/140b504cbdd5c326597a93bfc5669c3f06cce09a">140b504</a>)

---  

> ### TODO  

- [x] convention
- [x] read git log
- [x] parse commit message
- [x] commit link
- [ ] issue link
- [x] markdown generator
- [ ] maven repo or else