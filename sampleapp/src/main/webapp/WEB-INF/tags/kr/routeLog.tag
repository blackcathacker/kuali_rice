<%--
 Copyright 2005-2007 The Kuali Foundation.
 
 Licensed under the Educational Community License, Version 1.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 
 http://www.opensource.org/licenses/ecl1.php
 
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
--%>
<%@ include file="/kr/WEB-INF/jsp/tldHeader.jsp"%>

<kul:tab tabTitle="Route Log" defaultOpen="false">
<div class="tab-container" align=center>
  <iframe onload="setRouteLogIframeDimensions();" name="routeLogIFrame" id="routeLogIFrame" src="${ConfigProperties.workflow.url}/RouteLog.do?routeHeaderId=${KualiForm.workflowDocument.routeHeaderId}" height="500" width="95%" hspace='0' vspace='0' frameborder='0' title='OneStart Workflow Route Log for document id: ${KualiForm.workflowDocument.routeHeaderId}'>
  </iframe>
</kul:tab>
</div>