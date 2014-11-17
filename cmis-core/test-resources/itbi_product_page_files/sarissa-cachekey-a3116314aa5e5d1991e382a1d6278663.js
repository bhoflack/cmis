
/* - sarissa.js - */
// http://cmdb.elex.be/portal_javascripts/sarissa.js?original=1
function Sarissa(){};Sarissa.PARSED_OK="Document contains no parsing errors";Sarissa.PARSED_EMPTY="Document is empty";Sarissa.PARSED_UNKNOWN_ERROR="Not well-formed or other error";var _sarissa_iNsCounter=0;var _SARISSA_IEPREFIX4XSLPARAM="";var _SARISSA_HAS_DOM_IMPLEMENTATION=document.implementation&&true;var _SARISSA_HAS_DOM_CREATE_DOCUMENT=_SARISSA_HAS_DOM_IMPLEMENTATION&&document.implementation.createDocument;var _SARISSA_HAS_DOM_FEATURE=_SARISSA_HAS_DOM_IMPLEMENTATION&&document.implementation.hasFeature;var _SARISSA_IS_MOZ=_SARISSA_HAS_DOM_CREATE_DOCUMENT&&_SARISSA_HAS_DOM_FEATURE;var _SARISSA_IS_SAFARI=(navigator.userAgent&&navigator.vendor&&(navigator.userAgent.toLowerCase().indexOf("applewebkit")!=-1||navigator.vendor.indexOf("Apple")!=-1));var _SARISSA_IS_IE=document.all&&window.ActiveXObject&&navigator.userAgent.toLowerCase().indexOf("msie")>-1&&navigator.userAgent.toLowerCase().indexOf("opera")==-1;if(!window.Node||!Node.ELEMENT_NODE){Node={ELEMENT_NODE:1,ATTRIBUTE_NODE:2,TEXT_NODE:3,CDATA_SECTION_NODE:4,ENTITY_REFERENCE_NODE:5,ENTITY_NODE:6,PROCESSING_INSTRUCTION_NODE:7,COMMENT_NODE:8,DOCUMENT_NODE:9,DOCUMENT_TYPE_NODE:10,DOCUMENT_FRAGMENT_NODE:11,NOTATION_NODE:12}};if(typeof XMLDocument=="undefined"&&typeof Document!="undefined"){XMLDocument=Document}
if(_SARISSA_IS_IE){_SARISSA_IEPREFIX4XSLPARAM="xsl:";var _SARISSA_DOM_PROGID="";var _SARISSA_XMLHTTP_PROGID="";var _SARISSA_DOM_XMLWRITER="";Sarissa.pickRecentProgID=function(idList){var bFound=false,e;for(var i=0;i<idList.length&&!bFound;i++){try{var _=new ActiveXObject(idList[i]);_=_;var o2Store=idList[i];bFound=true}catch(objException){e=objException}};if(!bFound){throw "Could not retrieve a valid progID of Class: "+idList[idList.length-1]+". (original exception: "+e+")"};idList=null;return o2Store};_SARISSA_DOM_PROGID=null;_SARISSA_THREADEDDOM_PROGID=null;_SARISSA_XSLTEMPLATE_PROGID=null;_SARISSA_XMLHTTP_PROGID=null;if(!window.XMLHttpRequest){XMLHttpRequest=function(){if(!_SARISSA_XMLHTTP_PROGID){_SARISSA_XMLHTTP_PROGID=Sarissa.pickRecentProgID(["Msxml2.XMLHTTP.4.0","MSXML2.XMLHTTP.3.0","MSXML2.XMLHTTP","Microsoft.XMLHTTP"])};return new ActiveXObject(_SARISSA_XMLHTTP_PROGID)}};Sarissa.getDomDocument=function(sUri,sName){if(!_SARISSA_DOM_PROGID){_SARISSA_DOM_PROGID=Sarissa.pickRecentProgID(["Msxml2.DOMDocument.4.0","Msxml2.DOMDocument.3.0","MSXML2.DOMDocument","MSXML.DOMDocument","Microsoft.XMLDOM"])};var oDoc=new ActiveXObject(_SARISSA_DOM_PROGID);if(sName){var prefix="";if(sUri){if(sName.indexOf(":")>1){prefix=sName.substring(0,sName.indexOf(":"));sName=sName.substring(sName.indexOf(":")+1)}else{prefix="a"+(_sarissa_iNsCounter++)}};if(sUri){oDoc.loadXML('<'+prefix+':'+sName+" xmlns:"+prefix+"=\""+sUri+"\""+" />")} else{oDoc.loadXML('<'+sName+" />")}};return oDoc};Sarissa.getParseErrorText=function(oDoc){var parseErrorText=Sarissa.PARSED_OK;if(oDoc.parseError.errorCode!=0){parseErrorText="XML Parsing Error: "+oDoc.parseError.reason+"\nLocation: "+oDoc.parseError.url+"\nLine Number "+oDoc.parseError.line+", Column "+oDoc.parseError.linepos+":\n"+oDoc.parseError.srcText+"\n";for(var i=0;i<oDoc.parseError.linepos;i++){parseErrorText+="-"};parseErrorText+="^\n"}
else if(oDoc.documentElement==null){parseErrorText=Sarissa.PARSED_EMPTY};return parseErrorText};Sarissa.setXpathNamespaces=function(oDoc,sNsSet){oDoc.setProperty("SelectionLanguage","XPath");oDoc.setProperty("SelectionNamespaces",sNsSet)};XSLTProcessor=function(){if(!_SARISSA_XSLTEMPLATE_PROGID){_SARISSA_XSLTEMPLATE_PROGID=Sarissa.pickRecentProgID(["Msxml2.XSLTemplate.4.0","MSXML2.XSLTemplate.3.0"])};this.template=new ActiveXObject(_SARISSA_XSLTEMPLATE_PROGID);this.processor=null};XSLTProcessor.prototype.importStylesheet=function(xslDoc){if(!_SARISSA_THREADEDDOM_PROGID){_SARISSA_THREADEDDOM_PROGID=Sarissa.pickRecentProgID(["MSXML2.FreeThreadedDOMDocument.4.0","MSXML2.FreeThreadedDOMDocument.3.0"]);_SARISSA_DOM_XMLWRITER=Sarissa.pickRecentProgID(["Msxml2.MXXMLWriter.4.0","Msxml2.MXXMLWriter.3.0","MSXML2.MXXMLWriter","MSXML.MXXMLWriter","Microsoft.XMLDOM"])};xslDoc.setProperty("SelectionLanguage","XPath");xslDoc.setProperty("SelectionNamespaces","xmlns:xsl='http://www.w3.org/1999/XSL/Transform'");var converted=new ActiveXObject(_SARISSA_THREADEDDOM_PROGID);if(xslDoc.url&&xslDoc.selectSingleNode("//xsl:*[local-name() = 'import' or local-name() = 'include']")!=null){converted.async=false;converted.load(xslDoc.url)} else{converted.loadXML(xslDoc.xml)};converted.setProperty("SelectionNamespaces","xmlns:xsl='http://www.w3.org/1999/XSL/Transform'");var output=converted.selectSingleNode("//xsl:output");this.outputMethod=output?output.getAttribute("method"):"html";this.template.stylesheet=converted;this.processor=this.template.createProcessor();this.paramsSet=[]};XSLTProcessor.prototype.transformToDocument=function(sourceDoc){if(_SARISSA_THREADEDDOM_PROGID=="MSXML2.FreeThreadedDOMDocument.3.0"){this.processor.input=sourceDoc;var outDoc=new ActiveXObject(_SARISSA_DOM_PROGID);this.processor.output=outDoc;this.processor.transform();return outDoc}
else{this.processor.input=sourceDoc;var outDoc=new ActiveXObject(_SARISSA_DOM_XMLWRITER);this.processor.output=outDoc;this.processor.transform();var oDoc=new ActiveXObject(_SARISSA_DOM_PROGID);oDoc.loadXML(outDoc.output+"");return oDoc}};XSLTProcessor.prototype.transformToFragment=function(sourceDoc,ownerDoc){this.processor.input=sourceDoc;this.processor.transform();var s=this.processor.output;var f=ownerDoc.createDocumentFragment();if(this.outputMethod=='text'){f.appendChild(ownerDoc.createTextNode(s))} else if(ownerDoc.body&&ownerDoc.body.innerHTML){var container=ownerDoc.createElement('div');container.innerHTML=s;while(container.hasChildNodes()){f.appendChild(container.firstChild)}}
else{var oDoc=new ActiveXObject(_SARISSA_DOM_PROGID);if(s.substring(0,5)=='<?xml'){s=s.substring(s.indexOf('?>')+2)}
var xml=''.concat('<my>',s,'</my>');oDoc.loadXML(xml);var container=oDoc.documentElement;while(container.hasChildNodes()){f.appendChild(container.firstChild)}}
return f};XSLTProcessor.prototype.setParameter=function(nsURI,name,value){if(nsURI){this.processor.addParameter(name,value,nsURI)}else{this.processor.addParameter(name,value)};if(!this.paramsSet[""+nsURI]){this.paramsSet[""+nsURI]=[]};this.paramsSet[""+nsURI][name]=value};XSLTProcessor.prototype.getParameter=function(nsURI,name){nsURI=nsURI||"";if(this.paramsSet[nsURI]&&this.paramsSet[nsURI][name]){return this.paramsSet[nsURI][name]}else{return null}}}else{if(_SARISSA_HAS_DOM_CREATE_DOCUMENT){Sarissa.__handleLoad__=function(oDoc){Sarissa.__setReadyState__(oDoc,4)};_sarissa_XMLDocument_onload=function(){Sarissa.__handleLoad__(this)};Sarissa.__setReadyState__=function(oDoc,iReadyState){oDoc.readyState=iReadyState;oDoc.readystate=iReadyState;if(oDoc.onreadystatechange!=null&&typeof oDoc.onreadystatechange=="function"){oDoc.onreadystatechange()}};Sarissa.getDomDocument=function(sUri,sName){var oDoc=document.implementation.createDocument(sUri?sUri:null,sName?sName:null,null);if(!oDoc.onreadystatechange){oDoc.onreadystatechange=null};if(!oDoc.readyState){oDoc.readyState=0};oDoc.addEventListener("load",_sarissa_XMLDocument_onload,false);return oDoc};if(window.XMLDocument){}
else if(_SARISSA_HAS_DOM_FEATURE&&(typeof Document!='undefined')&&!Document.prototype.load&&document.implementation.hasFeature('LS','3.0')){Sarissa.getDomDocument=function(sUri,sName){var oDoc=document.implementation.createDocument(sUri?sUri:null,sName?sName:null,null);return oDoc}}
else{Sarissa.getDomDocument=function(sUri,sName){var oDoc=document.implementation.createDocument(sUri?sUri:null,sName?sName:null,null);if(oDoc&&(sUri||sName)&&!oDoc.documentElement){oDoc.appendChild(oDoc.createElementNS(sUri,sName))};return oDoc}}}};if(!window.DOMParser){if(_SARISSA_IS_SAFARI){DOMParser=function(){};DOMParser.prototype.parseFromString=function(sXml,contentType){var xmlhttp=new XMLHttpRequest();xmlhttp.open("GET","data:text/xml;charset=utf-8,"+encodeURIComponent(sXml),false);xmlhttp.send(null);return xmlhttp.responseXML}}else if(Sarissa.getDomDocument&&Sarissa.getDomDocument()&&Sarissa.getDomDocument(null,"bar").xml){DOMParser=function(){};DOMParser.prototype.parseFromString=function(sXml,contentType){var doc=Sarissa.getDomDocument();doc.loadXML(sXml);return doc}}};if(!document.importNode&&_SARISSA_IS_IE){try{document.importNode=function(oNode,bChildren){var tmp=document.createElement("div");if(bChildren){tmp.innerHTML=oNode.xml?oNode.xml:oNode.outerHTML}else{tmp.innerHTML=oNode.xml?oNode.cloneNode(false).xml:oNode.cloneNode(false).outerHTML};return tmp.getElementsByTagName("*")[0]}}catch(e){}};if(!Sarissa.getParseErrorText){Sarissa.getParseErrorText=function(oDoc){var parseErrorText=Sarissa.PARSED_OK;if(!oDoc.documentElement){parseErrorText=Sarissa.PARSED_EMPTY} else if(oDoc.documentElement.tagName=="parsererror"){parseErrorText=oDoc.documentElement.firstChild.data;parseErrorText+="\n"+oDoc.documentElement.firstChild.nextSibling.firstChild.data} else if(oDoc.getElementsByTagName("parsererror").length>0){var parsererror=oDoc.getElementsByTagName("parsererror")[0];parseErrorText=Sarissa.getText(parsererror,true)+"\n"} else if(oDoc.parseError&&oDoc.parseError.errorCode!=0){parseErrorText=Sarissa.PARSED_UNKNOWN_ERROR};return parseErrorText}};Sarissa.getText=function(oNode,deep){var s="";var nodes=oNode.childNodes;for(var i=0;i<nodes.length;i++){var node=nodes[i];var nodeType=node.nodeType;if(nodeType==Node.TEXT_NODE||nodeType==Node.CDATA_SECTION_NODE){s+=node.data} else if(deep==true&&(nodeType==Node.ELEMENT_NODE||nodeType==Node.DOCUMENT_NODE||nodeType==Node.DOCUMENT_FRAGMENT_NODE)){s+=Sarissa.getText(node,true)}};return s};if(!window.XMLSerializer&&Sarissa.getDomDocument&&Sarissa.getDomDocument("","foo",null).xml){XMLSerializer=function(){};XMLSerializer.prototype.serializeToString=function(oNode){return oNode.xml}};Sarissa.stripTags=function(s){return s.replace(/<[^>]+>/g,"")};Sarissa.clearChildNodes=function(oNode){while(oNode.firstChild){oNode.removeChild(oNode.firstChild)}};Sarissa.copyChildNodes=function(nodeFrom,nodeTo,bPreserveExisting){if((!nodeFrom)||(!nodeTo)){throw "Both source and destination nodes must be provided"};if(!bPreserveExisting){Sarissa.clearChildNodes(nodeTo)};var ownerDoc=nodeTo.nodeType==Node.DOCUMENT_NODE?nodeTo:nodeTo.ownerDocument;var nodes=nodeFrom.childNodes;if(typeof(ownerDoc.importNode)!="undefined"){for(var i=0;i<nodes.length;i++){nodeTo.appendChild(ownerDoc.importNode(nodes[i],true))}} else{for(var i=0;i<nodes.length;i++){nodeTo.appendChild(nodes[i].cloneNode(true))}}};Sarissa.moveChildNodes=function(nodeFrom,nodeTo,bPreserveExisting){if((!nodeFrom)||(!nodeTo)){throw "Both source and destination nodes must be provided"};if(!bPreserveExisting){Sarissa.clearChildNodes(nodeTo)};var nodes=nodeFrom.childNodes;if(nodeFrom.ownerDocument==nodeTo.ownerDocument){while(nodeFrom.firstChild){nodeTo.appendChild(nodeFrom.firstChild)}} else{var ownerDoc=nodeTo.nodeType==Node.DOCUMENT_NODE?nodeTo:nodeTo.ownerDocument;if(ownerDoc.importNode){for(var i=0;i<nodes.length;i++){nodeTo.appendChild(ownerDoc.importNode(nodes[i],true))}}else{for(var i=0;i<nodes.length;i++){nodeTo.appendChild(nodes[i].cloneNode(true))}};Sarissa.clearChildNodes(nodeFrom)}};Sarissa.xmlize=function(anyObject,objectName,indentSpace){indentSpace=indentSpace?indentSpace:'';var s=indentSpace+'<'+objectName+'>';var isLeaf=false;if(!(anyObject instanceof Object)||anyObject instanceof Number||anyObject instanceof String||anyObject instanceof Boolean||anyObject instanceof Date){s+=Sarissa.escape(""+anyObject);isLeaf=true}else{s+="\n";var isArrayItem=anyObject instanceof Array;for(var name in anyObject){s+=Sarissa.xmlize(anyObject[name],(isArrayItem?"array-item key=\""+name+"\"":name),indentSpace+"   ")};s+=indentSpace};return(s+=(objectName.indexOf(' ')!=-1?"</array-item>\n":"</"+objectName+">\n"))};Sarissa.escape=function(sXml){return sXml.replace(/&/g,"&amp;").
replace(/</g,"&lt;").
replace(/>/g,"&gt;").
replace(/"/g, "&quot;").
replace(/'/g,"&apos;")};Sarissa.unescape=function(sXml){return sXml.replace(/&apos;/g,"'").
replace(/&quot;/g,"\"").
replace(/&gt;/g,">").
replace(/&lt;/g,"<").
replace(/&amp;/g,"&")};
