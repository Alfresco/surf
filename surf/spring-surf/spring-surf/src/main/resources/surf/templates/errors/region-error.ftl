<#assign templateId = context.properties["error-templateId"]!"null">
<#assign regionId = context.properties["error-regionId"]!"null">
<#assign regionScopeId = context.properties["error-regionScopeId"]!"null">
<#assign regionSourceId = context.properties["error-regionSourceId"]!"null">

<div width="100%">

<font color="#cc0000">

A problem has occurred.
<br/>
This region could not be rendered:
<br/>
<br/>
templateId: ${templateId}
<br/>
regionId: ${regionId}
<br/>
regionScopeId: ${regionScopeId}
<br/>
regionSourceId: ${regionSourceId}
<br/>
<br/>
Please notify your system administrator.
</b>
</font>

</div>