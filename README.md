# Simple git change log generator  

> ### Convention  

```$xslt
[_change_log]
<type>[optional scope]: <description>
[optional body]
[_change_log]
```  

> ### Example  

- no change log message    

```$xslt
simple commit message..
```  

- feature message  

```$xslt
this is commit message.. 

[_change_log]
  feat() : bug is fixed  
[_change_log]
```

> ### TODO  

- [x] convention
- [x] read git log
- [ ] parse commit message
- [ ] markdown generator
- [ ] maven repo or else