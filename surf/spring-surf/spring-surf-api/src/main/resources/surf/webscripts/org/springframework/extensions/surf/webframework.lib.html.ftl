[#ftl]

[#macro head]
<head>
   <title>[#nested]</title> 
   <link rel="stylesheet" href="${url.context}/res/css/webscripts.css" type="text/css" />
   <link rel="stylesheet" href="${url.context}/res/css/surf.css" type="text/css" />
</head>
[/#macro]

[#macro header]
<table width="100%">
   <tr>
      <td nowrap><img src="${url.context}/res/images/logo/SurfLogo200.png" alt="Surf" /></td>
      <td class="title" nowrap>&nbsp;[#nested]</td>
      <td width="100%"></td>
      <td nowrap valign="top"><div class="title">Spring Surf - ${server.edition?html} v${server.version?html}</div></td>
   </tr>
</table>
[/#macro]

[#macro onlinedoc]
<table>
    <tr><td><a href="http://wiki.alfresco.com/wiki/Surf_Platform">Surf documentation</a>.</td></tr>
    <tr><td><a href="http://wiki.alfresco.com/wiki/HTTP_API">Web Scripts documentation</a>.</td></tr>
    <tr><td><a href="http://wiki.alfresco.com/wiki/JavaScript_API">JavaScript API</a>.</td></tr>
    <tr><td><a href="http://wiki.alfresco.com/wiki/Template_Guide">Template API</a>.</td></tr>
</table>
[/#macro]

[#macro indexheader size=-1]
[@header][#nested][/@header]
<table>
   <tr><td>[#if size == -1]${webscripts?size}[#else]${size}[/#if] Web Scripts</td></tr>
</table>
[/#macro]

[#macro home]
<table>
   <tr><td><a href="${url.serviceContext}/index">Back to Web Scripts Home</a></td></tr>
</table>
[/#macro]

[#macro parent path pathname]
[#if path.parent?exists]
   <br>
   <table>
      <tr><td><a href="${url.serviceContext}/index/${pathname}${path.parent.path}">Up to ${pathname} ${path.parent.path}</a>
   </table>
[/#if]
[/#macro]