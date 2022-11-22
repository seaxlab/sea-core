hi,${name!}!
值：
<#list tags?keys as key>
    ${key}=${tags["${key}"]},<#t>
</#list>
----
值：<#list tags?keys as key>${key}=${tags["${key}"]},<#lt></#list>
---
值：
<#list tags?keys as key>
    ${key}=${tags["${key}"]},<#rt>
</#list>