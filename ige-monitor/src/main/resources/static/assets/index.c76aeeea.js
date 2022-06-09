var x=Object.defineProperty;var d=Object.getOwnPropertySymbols;var F=Object.prototype.hasOwnProperty,I=Object.prototype.propertyIsEnumerable;var p=(t,a,e)=>a in t?x(t,a,{enumerable:!0,configurable:!0,writable:!0,value:e}):t[a]=e,i=(t,a)=>{for(var e in a||(a={}))F.call(a,e)&&p(t,e,a[e]);if(d)for(var e of d(a))I.call(a,e)&&p(t,e,a[e]);return t};import{i as o,V as l,n,A as N}from"./index.a1219e87.js";import{C as O,m as S}from"./index.8e90c859.js";import{b as u,O as k}from"./server-config.de2c5900.js";import{b as c}from"./namespace-config.5600bdfa.js";const b=t=>o({url:`/api/fullStatically/${t}`,method:"get"}),q=t=>o({url:"/api/fullStatically",method:"post",data:t}),T=t=>o({url:"/api/fullStatically",method:"put",data:t}),C=(t,a)=>o({url:`/api/fullStatically/enable/${t}/${a}`,method:"put"}),A={id:void 0,jobName:"",uniqueId:"",storageType:"0",localPath:void 0,namingKey:void 0,remoteUrlReceive:void 0,namespaceId:"",cron:"",template:""};var L=l.extend({props:{id:{type:String},visible:{type:Boolean}},data(){return{rules:{jobName:[{required:!0,message:"\u8BF7\u8F93\u5165",type:"error"}],uniqueId:[{required:!0,message:"\u8BF7\u8F93\u5165",type:"error"}],storageType:[{required:!0,message:"\u8BF7\u9009\u62E9",type:"error"}],namespaceId:[{required:!0,message:"\u8BF7\u9009\u62E9",type:"error"}],cron:[{required:!0,message:"\u8BF7\u8F93\u5165",type:"error"}],template:[{required:!0,message:"\u8BF7\u8F93\u5165",type:"error"}]},formData:i({},A),namespaceOptions:[],storageOptions:[]}},computed:{formVisible:{get(){return this.visible},set(t){this.$emit("update:visible",t)}}},methods:{opened(){this.queryAllNamespaces(),this.queryAllStorage(),this.id&&this.init(this.id)},async queryAllStorage(){const{data:t}=await u();this.storageOptions=t.map(a=>(a.label=a.serverName,a.value=a.id,a)),this.storageOptions.push({label:"\u539F\u8DEF\u8FD4\u56DE",value:0}),this.formData.id||(this.formData.storageType=this.storageOptions[0].value)},async queryAllNamespaces(){const{data:t}=await c();this.namespaceOptions=t.map(a=>(a.label=a.namespaceName,a.value=a.id,a))},async init(t){const{data:a}=await b(t);this.formData=a},async onSubmit({result:t,firstError:a}){a||(this.formData.id?await T(this.formData):await q(this.formData),this.$message.success("\u63D0\u4EA4\u6210\u529F"),this.formVisible=!1,this.$emit("refreshDataList"))},onClickCloseBtn(){this.formVisible=!1},closed(){this.$nextTick(()=>{this.$refs.form.reset()})}}}),y=function(){var t=this,a=t.$createElement,e=t._self._c||a;return e("t-dialog",{attrs:{header:(this.id?"\u7F16\u8F91":"\u6DFB\u52A0")+"\u5168\u91CF\u9759\u6001\u5316",visible:t.formVisible,width:876,footer:!1},on:{"update:visible":function(s){t.formVisible=s},opened:t.opened,closed:t.closed}},[e("div",{attrs:{slot:"body"},slot:"body"},[e("t-form",{ref:"form",attrs:{data:t.formData,rules:t.rules,resetType:"initial",labelWidth:150,requiredMark:!1},on:{submit:t.onSubmit}},[e("t-row",{staticClass:"t-form-inline"},[e("t-col",{attrs:{span:6}},[e("t-form-item",{attrs:{label:"\u4EFB\u52A1\u540D\u79F0\uFF1A",name:"jobName"}},[e("t-input",{attrs:{placeholder:"\u8BF7\u8F93\u5165"},model:{value:t.formData.jobName,callback:function(s){t.$set(t.formData,"jobName",s)},expression:"formData.jobName"}})],1)],1),e("t-col",{attrs:{span:6}},[e("t-form-item",{attrs:{label:"\u5B58\u50A8\u670D\u52A1\uFF1A",name:"storageType"}},[e("t-select",{attrs:{clearable:""},model:{value:t.formData.storageType,callback:function(s){t.$set(t.formData,"storageType",s)},expression:"formData.storageType"}},t._l(t.storageOptions,function(s,r){return e("t-option",{key:r,attrs:{value:s.value,label:s.label}},[t._v(" "+t._s(s.label)+" ")])}),1)],1)],1),e("t-col",{attrs:{span:6}},[e("t-form-item",{attrs:{label:"\u6240\u5C5E\u9879\u76EE\uFF1A",name:"namespaceId"}},[e("t-select",{attrs:{clearable:""},model:{value:t.formData.namespaceId,callback:function(s){t.$set(t.formData,"namespaceId",s)},expression:"formData.namespaceId"}},t._l(t.namespaceOptions,function(s,r){return e("t-option",{key:r,attrs:{value:s.value,label:s.label}},[t._v(" "+t._s(s.label)+" ")])}),1)],1)],1),e("t-col",{attrs:{span:6}},[e("t-form-item",{attrs:{label:"\u6267\u884C\u65F6\u95F4\uFF1A",name:"cron"}},[e("t-input",{attrs:{placeholder:"\u8BF7\u8F93\u5165"},model:{value:t.formData.cron,callback:function(s){t.$set(t.formData,"cron",s)},expression:"formData.cron"}})],1)],1),e("t-col",{attrs:{span:12}},[e("t-form-item",{attrs:{label:"\u4E1A\u52A1\u552F\u4E00ID\uFF1A",name:"uniqueId"}},[e("t-input",{attrs:{placeholder:"\u8BF7\u8F93\u5165"},model:{value:t.formData.uniqueId,callback:function(s){t.$set(t.formData,"uniqueId",s)},expression:"formData.uniqueId"}})],1)],1),e("t-col",{attrs:{span:12}},[e("t-form-item",{attrs:{label:"\u6A21\u677F\u5185\u5BB9\uFF1A",name:"template"}},[e("codemirror",{model:{value:t.formData.template,callback:function(s){t.$set(t.formData,"template",s)},expression:"formData.template"}})],1)],1),e("t-col",{attrs:{span:12}},[e("t-form-item",{staticClass:"footer"},[e("t-button",{attrs:{variant:"outline"},on:{click:t.onClickCloseBtn}},[t._v("\u53D6\u6D88")]),e("t-button",{attrs:{theme:"primary",type:"submit"}},[t._v("\u786E\u5B9A")])],1)],1)],1)],1)],1)])},V=[];y._withStripped=!0;const f={};var _=n(L,y,V,!1,j,null,null,null);function j(t){for(let a in f)this[a]=f[a]}_.options.__file="src/pages/full-statically/components/add-or-update-dialog.vue";var B=function(){return _.exports}();const E={id:void 0,serverName:"",namespaceId:"",serverType:1,ip:"",port:"",group:"",address:"",secret:""};var U=l.extend({props:{id:{type:String},visible:{type:Boolean}},data(){return{formData:i({},E),namespaceOptions:[],storageOptions:[]}},computed:{formVisible:{get(){return this.visible},set(t){this.$emit("update:visible",t)}},namespaceName(){var t;return(t=this.namespaceOptions.find(a=>a.id===this.formData.namespaceId))==null?void 0:t.namespaceName}},methods:{async opened(){await this.queryAllNamespaces(),await this.queryAllStorage(),this.init(this.id)},outTypeShow(t){for(var a=0;a<this.storageOptions.length;a++){let e=this.storageOptions[a];if(e.value==t)return e.label}},async queryAllStorage(){const{data:t}=await u();this.storageOptions=t.map(a=>(a.label=a.serverName,a.value=a.id,a)),this.storageOptions.push({label:"\u539F\u8DEF\u8FD4\u56DE",value:0}),this.formData.id||(this.formData.outType=this.storageOptions[0].value)},async queryAllNamespaces(){const{data:t}=await c();this.namespaceOptions=t.map(a=>(a.label=a.namespaceName,a.value=a.id,a)),this.formData.namespaceId=this.namespaceOptions[0].value},async init(t){const{data:a}=await b(t);this.formData=a}}}),g=function(){var t=this,a=t.$createElement,e=t._self._c||a;return e("t-dialog",{attrs:{header:"\u5168\u91CF\u9759\u6001\u5316\u8BE6\u60C5",visible:t.formVisible,width:876,footer:!1},on:{"update:visible":function(s){t.formVisible=s},opened:t.opened}},[e("div",{attrs:{slot:"body"},slot:"body"},[e("t-form",{ref:"form",attrs:{data:t.formData,labelWidth:150}},[e("t-row",{staticClass:"t-form-inline"},[e("t-col",{attrs:{span:6}},[e("t-form-item",{attrs:{label:"\u4EFB\u52A1\u540D\u79F0\uFF1A"}},[t._v(" "+t._s(t.formData.jobName)+" ")])],1),e("t-col",{attrs:{span:6}},[e("t-form-item",{attrs:{label:"\u5B58\u50A8\u670D\u52A1\uFF1A"}},[t._v(" "+t._s(t.outTypeShow(t.formData.storageType))+" ")])],1),e("t-col",{attrs:{span:6}},[e("t-form-item",{attrs:{label:"\u6240\u5C5E\u9879\u76EE\uFF1A"}},[t._v(" "+t._s(t.namespaceName)+" ")])],1),e("t-col",{attrs:{span:6}},[e("t-form-item",{attrs:{label:"\u6267\u884C\u65F6\u95F4\uFF1A"}},[t._v(" "+t._s(t.formData.cron)+" ")])],1),e("t-col",{attrs:{span:12}},[e("t-form-item",{attrs:{label:"\u4E1A\u52A1\u552F\u4E00ID\uFF1A"}},[t._v(" "+t._s(t.formData.uniqueId)+" ")])],1),e("t-col",{attrs:{span:12}},[e("t-form-item",{attrs:{label:"\u6A21\u7248\u5185\u5BB9\uFF1A"}},[e("codemirror",{model:{value:t.formData.template,callback:function(s){t.$set(t.formData,"template",s)},expression:"formData.template"}})],1)],1)],1)],1)],1)])},K=[];g._withStripped=!0;const v={};var D=n(U,g,K,!1,M,null,null,null);function M(t){for(let a in v)this[a]=v[a]}D.options.__file="src/pages/full-statically/components/detail-dialog.vue";var R=function(){return D.exports}(),H=l.extend({name:"full-statically",components:{AddIcon:N,Card:O,AddOrUpdateDialog:B,DetailDialog:R},mixins:[S],data(){return{mixinViewModuleOptions:{getDataListURL:"/api/fullStatically/search",getDataListIsPage:!0,activatedIsNeed:!1,deleteURL:"/api/fullStatically"},OUT_TYPE:k,columns:[{title:"\u540D\u79F0",align:"left",width:200,colKey:"jobName",fixed:"left"},{title:"\u4FDD\u5B58\u7C7B\u578B",width:200,ellipsis:!0,colKey:"outType"},{title:"\u662F\u5426\u542F\u7528",width:200,ellipsis:!0,colKey:"status"},{align:"left",fixed:"right",width:200,colKey:"op",title:"\u64CD\u4F5C"}],formData:{jobName:void 0,namespaceId:void 0},tabList:[],storageOptions:[]}},async created(){await this.queryAllNamespaces(),await this.query(),await this.queryAllStorage()},methods:{outTypeShow(t){for(var a=0;a<this.storageOptions.length;a++){let e=this.storageOptions[a];if(e.value==t)return e.label}},async queryAllStorage(){const{data:t}=await u();this.storageOptions=t.map(a=>(a.label=a.serverName,a.value=a.id,a)),this.storageOptions.push({label:"\u539F\u8DEF\u8FD4\u56DE",value:0}),this.formData.id||(this.formData.storageType=this.storageOptions[0].value)},async queryAllNamespaces(){const{data:t}=await c();this.tabList=t.map(a=>(a.label=a.namespaceName,a.value=a.id,a)),this.formData.namespaceId=this.tabList[0].id},async onChange(t){const{id:a,status:e}=t;await C(a,e),await this.query()}}}),w=function(){var t=this,a=t.$createElement,e=t._self._c||a;return e("div",[e("card",[e("t-form",{ref:"form",attrs:{data:t.formData,"label-width":50,colon:""},on:{submit:t.query}},[e("t-row",[e("t-col",{attrs:{flex:"none"}},[e("t-form-item",{attrs:{label:"\u540D\u79F0",name:"name"}},[e("t-input",{staticClass:"form-item-content",style:{width:"182px"},attrs:{type:"search",placeholder:"\u8BF7\u8F93\u5165"},model:{value:t.formData.jobName,callback:function(s){t.$set(t.formData,"jobName",s)},expression:"formData.jobName"}})],1)],1),e("t-col",{attrs:{flex:"auto"}},[e("t-button",{style:{marginLeft:"20px"},attrs:{theme:"primary",type:"submit"}},[t._v(" \u641C\u7D22 ")])],1),e("t-col",{attrs:{flex:"none"}},[e("t-button",{attrs:{theme:"primary"},on:{click:function(s){return t.addOrUpdateHandle()}}},[e("add-icon",{attrs:{slot:"icon"},slot:"icon"}),t._v("\u6DFB\u52A0")],1)],1)],1)],1)],1),e("card",{staticClass:"container-base-margin-top table-content"},[e("t-tabs",{on:{change:t.query},model:{value:t.formData.namespaceId,callback:function(s){t.$set(t.formData,"namespaceId",s)},expression:"formData.namespaceId"}},t._l(t.tabList,function(s,r){return e("t-tab-panel",{key:r,attrs:{value:s.value,label:s.label}})}),1),e("div",{staticClass:"table-container"},[e("t-table",{attrs:{columns:t.columns,data:t.dataList,rowKey:t.rowKey,verticalAlign:t.verticalAlign,stripe:t.stripe,bordered:t.bordered,hover:t.hover,pagination:t.pagination,loading:t.dataListLoading},on:{"page-change":t.rehandlePageChange},scopedSlots:t._u([{key:"outType",fn:function(s){var r=s.row;return[e("p",[t._v(t._s(t.outTypeShow(r.storageType)))])]}},{key:"status",fn:function(s){var r=s.row;return[e("t-switch",{attrs:{customValue:[1,2]},on:{change:function(m){return t.onChange(r)}},model:{value:r.status,callback:function(m){t.$set(r,"status",m)},expression:"row.status"}})]}},{key:"op",fn:function(s){return[e("a",{staticClass:"t-button-link",on:{click:function(r){return t.handleClickDetail(s.row.id)}}},[t._v("\u8BE6\u60C5")]),e("a",{staticClass:"t-button-link",on:{click:function(r){return t.addOrUpdateHandle(s.row.id)}}},[t._v("\u7F16\u8F91")]),e("a",{staticClass:"t-button-link",on:{click:function(r){return t.handleClickDelete(s.row.id)}}},[t._v("\u5220\u9664")])]}}])})],1)],1),e("add-or-update-dialog",{attrs:{id:t.id,visible:t.addOrUpdateVisible},on:{"update:visible":function(s){t.addOrUpdateVisible=s},refreshDataList:t.getDataList}}),e("detail-dialog",{attrs:{id:t.id,visible:t.detailVisible},on:{"update:visible":function(s){t.detailVisible=s}}}),e("t-dialog",{attrs:{theme:"danger",header:"\u786E\u5B9A\u8981\u5220\u9664\u8FD9\u6761\u6570\u636E\u5417\uFF1F",visible:t.confirmVisible},on:{"update:visible":function(s){t.confirmVisible=s},confirm:t.onConfirmDelete}})],1)},P=[];w._withStripped=!0;const h={};var $=n(H,w,P,!1,W,"559cb413",null,null);function W(t){for(let a in h)this[a]=h[a]}$.options.__file="src/pages/full-statically/index.vue";var X=function(){return $.exports}();export{X as default};